package de.hdmstuttgart.mi.bucketlist.Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.DeserializationFeature;

import de.hdmstuttgart.mi.bucketlist.Exceptions.ElementAlreadyExistsException;
import de.hdmstuttgart.mi.bucketlist.View.Listener;
import de.hdmstuttgart.mi.bucketlist.Persistance.Saveable;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * List of Events, the lists can be created, modified und deleted by the user
 */
// ObjectMapper parses every attribute (despite of its marked as ignorable)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY )
public class Eventlist implements Saveable,Narrator {

    // initialize Logger
    private static final Logger log = LogManager.getLogger(Eventlist.class);

    private ArrayList<Event> events;
    private String eventlistName;
    private String expiryDateString;

    @JsonIgnore
    private final StringProperty listSize = new SimpleStringProperty();
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
     * Caution: do not use for creating Eventlists ! //todo kann dadruch zu doofen Fehlern kommen da man auch so Eventlisten ohne namen etc. erstellen kann
     */
    public Eventlist(){
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
            stringBuffer.append("0").append(expiryDay).append("/");
        }else{
            stringBuffer.append(expiryDay).append("/");
        }

        if(expiryMonth < 10){
            stringBuffer.append("0").append(expiryMonth).append("/");
        }else{
            stringBuffer.append(expiryMonth).append("/");
        }
        stringBuffer.append(expiryYear);
        expiryDateString= stringBuffer.toString();

        GregorianCalendar gregorianCalendar = new GregorianCalendar(expiryYear,expiryMonth-1,expiryDay+1);
        log.debug("configureExpiryDate method ended");
        return gregorianCalendar;
    }

   // ------------------------- persistence methods ---------------------------------------------

    /** todo SourceFactory macht eig keinen Sinn wenn hier sowieso ein File übergeben wird
     * writes the object as a json string to a file
     * @param file -- file which should hold the created json strings
     */
    @Override
    public void toJson(File file){
        log.debug("toJson method started");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, this);
            log.info("Object parsed successfully");
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
            log.info("Object parsed successfully");
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

    /**
     * adds an event to "this" eventlist
     * its not possible to add two events with the same name within one list
     * @param eventName -- the name which the event should have
     * @param eventCategory -- the category the event should have
     */
   public void addEvent(String eventName, Category eventCategory) throws ElementAlreadyExistsException {
       log.debug("addEvent method started");
       if(this.events.stream()
               .anyMatch(event -> event.getEventName().equals(eventName))){
           log.info("There is already an event with the name "  + "\"" + eventName +  "\"" + " in the list " +  "\"" + this.eventlistName +  "\"" + ". Please select another name.");
           throw new ElementAlreadyExistsException("There is already an event with the name "  + "\"" + eventName +  "\"" + " in the list " +  "\"" + this.eventlistName +  "\"" + ". Please select another name.");
       } else {
           this.events.add(new Event(eventName,eventCategory));
           this.listSize.set(this.events.size() + " EVENTS");
           log.debug("Event " + "\"" + eventName +  "\"" + " added successfully to the list " + "\"" + this.eventlistName +  "\"");

           informListeners();
       }
    }

    /**
     * deletes an event from "this" eventlist
     * takes care of the case, when no event with matching name found
     * @param eventName -- the name of the event you want to delete
     */
    public void deleteEvent(String eventName){
        if(this.events.stream().anyMatch(event -> event.getEventName().equals(eventName))){
            Event event = getEventByName(eventName);
            this.events.remove(event);
            informListeners();
            this.listSize.set(this.events.size() + " EVENTS");
            log.info("Event deleted successfully");
        }else{
            log.error("No Event with matching name found");
        }
    }

    /**
     * completes an event of "this" list
     * @param eventName -- the name of the event which should be completed
     * @param eventImageUrl -- the url of the image the user chose
     * @param eventDescription -- the description entered by the user
     */
    public void completeEvent(String eventName, String eventImageUrl,String eventDescription, int eventDay, int eventMonth, int eventYear ){
        if(this.events.stream().anyMatch(event -> event.getEventName().equals(eventName))){
            getEventByName(eventName).completeEvent(eventImageUrl,eventDescription,eventDay,eventMonth,eventYear);
            informListeners();
            log.info("Event completed successfully");
        }else{
            log.error("No Event with matching name found");
        }
    }
    // ------------------------ modify field methods ----------------------------------------

    /**
     * updates the name of an eventlist
     * @param eventlistNameNew -- the new name for the list
     */
    public void updateEventlistName(String eventlistNameNew){
        this.eventlistName = eventlistNameNew;
        informListeners(); //todo needed ?
        log.info("Eventlistname updated successfully to " + eventlistNameNew);
    }

    /**
     * updates the name of an event in this eventlist
     * its not possible to update the eventName to a name which is already assigned to an other event
     * @param eventNameOld -- the current name of the event
     * @param eventNameNew -- the new name for the event
     */
    public void updateEventName(String eventNameOld,String eventNameNew) throws ElementAlreadyExistsException {
        boolean match = this.events.stream()
                .anyMatch(event -> event.getEventName().equals(eventNameNew));
        if(match){
            log.error("Name change failed. Name already assigned");
            throw new ElementAlreadyExistsException("There is already an event with the name "  + "\"" + eventNameNew +  "\"" + " in the list " +  "\"" + this.eventlistName +  "\"" + ". Please select another name.");
        }else{
            Event event = getEventByName(eventNameOld);
            event.updateName(eventNameNew);

            informListeners();
            log.info("Eventname updated succeessfully from " + eventNameOld + " to " + eventNameNew);
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

    public StringProperty getListSizeProperty(){
        return this.listSize;
    }

    /** todo does not really return a copy of the list (Elements in the list are the same) but there is actually no real need for a deep copy
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
        for (Event value : this.events) {
            if (value.getEventName().equals(eventname)) {
                event = value;
            }
        }
        return event;
    }

    @Override
    public String toString(){
        return "Eventlistname:" + this.eventlistName + ", " + Arrays.toString(this.events.toArray());
    }

    //---------------------- GUI communication ---------------------------------------------

    /**
     * @param listener -- the listener which should be added to the list
     */
    @Override
    public void addListener(Listener listener){
        this.listeners.add(listener);
    }

    /**
     * informs all the Listeners about a change
     */
    @Override
    public void informListeners(){
        for (Listener listener : this.listeners) {
            listener.update();
        }
    }


}
