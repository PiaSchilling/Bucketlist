package de.hdmstuttgart.mi.bucketlist.Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.DeserializationFeature;

import de.hdmstuttgart.mi.bucketlist.Exceptions.ElementAlreadyExistsException;
import de.hdmstuttgart.mi.bucketlist.Gui.Listener;
import de.hdmstuttgart.mi.bucketlist.Persistance.Saveable;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// ObjectMapper parses every attribute (despite of its marked as ignorable)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY )
public class Eventlist implements Saveable {

    // initialize Logger
    private static final Logger log = LogManager.getLogger(Eventlist.class);

    private ArrayList<Event> events;
    private String eventlistName;
    private String expiryDateString;

    @JsonIgnore
    private GregorianCalendar expiryDateGregorian;
    @JsonIgnore
    private final ArrayList<Listener> listeners = new ArrayList<>();

    // Constructor without date
    public Eventlist(String eventlistName) {
        this.eventlistName = eventlistName;
        this.events = new ArrayList<>();
    }

    // Constructor with date
    public Eventlist(String eventlistName, int expiryDay, int expiryMonth, int expiryYear){
        this.eventlistName = eventlistName;
        this.events = new ArrayList<>();
        this.expiryDateGregorian = configureExpiryDate(expiryDay, expiryMonth ,expiryYear);
    }

    /**
     * default constructor for json parsing
     */
    public Eventlist(){
    }

    /**
     * @param listener -- the listener which should be added to the list
     */
    public void addListener(Listener listener){
        this.listeners.add(listener);
    }

    /**
     *
     * @param expiryDay -- the day of the date as an int
     * @param expiryMonth -- the month of the date as an int
     * @param expiryYear -- the year of the date as an int
     * @return GregorianCalender Date with the selected Month, Year and Day from the User
     */
    private GregorianCalendar configureExpiryDate(int expiryDay, int expiryMonth, int expiryYear) {
        log.debug("configureExpiryDate method started");

        //StringDate needs to have 2 chars for day and month
        StringBuffer stringBuffer = new StringBuffer();
        if(expiryDay < 10){
            stringBuffer.append("0" + expiryDay + ".");
        }else{
            stringBuffer.append(expiryDay + ".");
        }

        if(expiryMonth < 10){
            stringBuffer.append("0" + expiryMonth + ".");
        }else{
            stringBuffer.append(expiryMonth + ".");
        }
        stringBuffer.append(expiryYear);
        expiryDateString= stringBuffer.toString();

        GregorianCalendar gregorianCalendar = new GregorianCalendar(expiryYear,expiryMonth-1,expiryDay+1);
        log.debug("configureExpiryDate method ended");
        return gregorianCalendar;
    }

   // ------------------------- persitance methods ---------------------------------------------

    /**
     * writes the object as a json string to a file
     * @param file -- file which should hold the created json strings
     */
    @Override
    public void toJson(File file){
        log.debug("toJson method started");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, this);
            log.debug("Object parsed successfully");
        }catch(FileNotFoundException fileNotFoundException){
            log.error(fileNotFoundException.getMessage() + ", File not found");
        } catch (IOException ioException) {
            log.error(ioException.getMessage());
        } finally {
            log.debug("toJson method ended");
        }
    }

    /**
     * loads json string from a file
     * @param file -- source
     * @return -- the created object
     */
    @Override
    public Saveable fromJson(File file) {
        log.debug("fromJson method started");

        ObjectMapper objectMapper = new ObjectMapper();
        //ignore Event-Attributes
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            Eventlist temp = objectMapper.readerFor(Eventlist.class).readValue(file);
            if(temp.expiryDateString != null){
                temp.expiryDateGregorian = configureExpiryDate(Integer.parseInt(temp.expiryDateString.substring(0,2)),Integer.parseInt(temp.expiryDateString.substring(3,5)),Integer.parseInt(temp.expiryDateString.substring(6,10)));
            }
            log.debug("Object parsed successfully");
            return temp;

        } catch (FileNotFoundException fileNotFoundException) {
            log.error(fileNotFoundException.getMessage() + ", File not found");
        } catch (IOException ioException){
            log.error(ioException.getMessage());
        }finally {
            log.debug("fromJson method ended");
        }
        return null;
    }

    // -------------------- list manipulation methods -------------------------------------------------------------

    /**todo remove unnecessary if statements for things which can never happen
     * adds an event to "this" eventlist
     * its not possible to add two events with the same name within one list
     * @param eventName -- the name which the event should have
     * @param eventCategory -- the category the event should have
     */
   public void addEvent(String eventName, Category eventCategory) throws ElementAlreadyExistsException {
       log.debug("addEvent method started");
       if(this.events.stream().anyMatch(event -> event.getEventName().equals(eventName))){
           //todo might be a system out (display for the user)
           log.info("There is already an event with the name "  + "\"" + eventName +  "\"" + " in the list " +  "\"" + this.eventlistName +  "\"" + ". Please select another name.");
           throw new ElementAlreadyExistsException("There is already an event with the name "  + "\"" + eventName +  "\"" + " in the list " +  "\"" + this.eventlistName +  "\"" + ". Please select another name.");
       } else {
           this.events.add(new Event(eventName,eventCategory));
           log.debug("Event " + "\"" + eventName +  "\"" + " added successfully to the list " + "\"" + this.eventlistName +  "\"");

           //notify the listeners about the change
           for (int i = 0; i < this.listeners.size(); i++) {
               listeners.get(i).update();
           }
       }
    }

    /**todo remove unnecessary if statements for things which can never happen
     * deletes an event from "this" eventlist
     * takes care of the case, when no event with matching name found
     * @param eventName -- the name of the event you want to delete
     */
    public void deleteEvent(String eventName){

        log.debug("deleteEvent method started");
        List<Event> temp = this.events.stream().filter(event -> event.getEventName().equals(eventName)).collect(Collectors.toList());

        if(temp.size() == 0){
            log.error("No event with matching name found");
        }else if(temp.size() > 1){
            //this should normally never be the case (watch addEvent method, problem already treated there)
            log.info("Multiple events with matching name found");
        }else{
            this.events.removeAll(temp);
            log.debug("Event " + "\"" + eventName +  "\"" + " deleted successfully from the list " + "\"" + this.eventlistName +  "\"");

            //notify the listeners about the change
            for (int i = 0; i < this.listeners.size(); i++) {
                listeners.get(i).update();
            }
        }
    }

    /**
     * completes an event of "this" list
     * @param eventName -- the name of the event which should be completed
     * @param eventImageUrl -- the url of the image the user chose
     * @param eventDescription -- the description entered by the user
     */
    public void completeEvent(String eventName, String eventImageUrl,String eventDescription, int eventDay, int eventMonth, int eventYear ){
        //todo streams
        log.debug("completeEvent method started");
        for (int i = 0; i < this.events.size(); i++) {
            if(this.events.get(i).getEventName().equals(eventName)){
                this.events.get(i).completeEvent(eventImageUrl,eventDescription,eventDay, eventMonth, eventYear);
                log.debug("Event completed successfully");

                //notify the listeners about the change
                for (int j = 0; j < this.listeners.size(); j++) {
                    listeners.get(j).update();
                }
            }
        }
    }

    //-------------------- getter & toString --------------------------------------------

    public String getName(){
        return this.eventlistName;
    }

    public GregorianCalendar getExpiryDateGregorian(){
        return this.expiryDateGregorian;
    }

    public String getExpiryDateString(){
        return expiryDateString;
    }

    /** todo does not really return a copy of the list (Elements in the list are not copied)
     * returns a COPY of the Eventlist
     * @return -- copy of the eventlist
     */
    public ArrayList<Event> getEvents() {
        ArrayList<Event> copy = new ArrayList<>(this.events);
        return copy;
    }

    /**
     * returns the event with the matching name
     * @param eventname -- the name of the event you want to get
     * @return -- the event
     */
    public Event getEventByName(String eventname){
        Event event = new Event();
        for (int i = 0; i < this.events.size(); i++) {
            if(this.events.get(i).getEventName().equals(eventname)){
                event = this.events.get(i);
            }
        }
        return event;
    }

    @Override
    public String toString(){
        return "Eventlistname:" + this.eventlistName + ", " + Arrays.toString(this.events.toArray());
    }

}
