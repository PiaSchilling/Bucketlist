package de.hdmstuttgart.mi.bucketlist.Model;

import com.fasterxml.jackson.databind.DeserializationFeature;

import de.hdmstuttgart.mi.bucketlist.Persistance.Saveable;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Eventlist implements Saveable {

    // initialize Logger
    private static final Logger log = LogManager.getLogger(Eventlist.class);

    private ArrayList<Event> events;
    private String eventlistName;
    private GregorianCalendar expiryDateGregorian;

    private String expiryDateString;



    // Constructor without date
    public Eventlist(String eventlistName) {
        this.eventlistName = eventlistName;
        this.events = new ArrayList<>();
    }

    // Constructor with date
    public Eventlist(String eventlistName, int expiryDay, int expiryMonth, int expiryYear){
        this.eventlistName = eventlistName;
        this.events = new ArrayList<>();
        this.expiryDateGregorian = configureExpiryDate(expiryDay, expiryMonth ,expiryYear);    }


    /**
     * default constructor for json parsing
     */
    public Eventlist(){
    }

    /**
     * returns a copy of the Eventlist
     * @return -- copy of the eventlist
     */
    public ArrayList<Event> getEvents() {
        ArrayList<Event> copy = new ArrayList<>(this.events);
        return copy;
    }

    /**
     *
     * @param expiryDay
     * @param expiryMonth
     * @param expiryYear
     * @return GregorianCalender Date with the selected Month, Year and Day from the User
     */

    private GregorianCalendar configureExpiryDate(int expiryDay, int expiryMonth, int expiryYear) {
        log.debug("configureDate method started");      // todo @merve is this correct? I (@sara) copied it from the old method
        expiryDateString= expiryDay + "." + expiryMonth + "." + expiryYear;
        GregorianCalendar gregorianCalendar = new GregorianCalendar(expiryYear,expiryMonth-1,expiryDay+1);
        return gregorianCalendar;
    }


    /**
     *
     * @return the expiry date as a String
     */
    private String expiryDateView(){
        // return copy?
        return expiryDateString;
    }

    @Override
    public String toString(){
        return "Eventlistname:" + this.eventlistName + ", " + Arrays.toString(this.events.toArray());
    }

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
        }catch(FileNotFoundException e){
            log.error("File not found");
        } catch (IOException e) {
            log.error("IO Exception in Method toJson");
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
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //todo understand why
        try {
            Eventlist temp = objectMapper.readerFor(Eventlist.class).readValue(file);

            this.eventlistName = temp.eventlistName;
            this.events = temp.getEvents();

            return temp;

        } catch (IOException e) {
            log.error("IO Exception");
        }
        return null;
    }

    /**
     * adds an event to "this" eventlist
     * its not possible to add two events with the same name within one list
     * @param eventName -- the name which the event should have
     * @param eventCategory -- the category the event should have
     */
   public void addEvent(String eventName, Category eventCategory){
       log.debug("addEvent method started");
       if(this.events.stream().anyMatch(event -> event.getName().equals(eventName))){
           log.info("There is already an event with the name "  + "\"" + eventName +  "\"" + " in the list " +  "\"" + this.eventlistName +  "\"" + ". Please select another name.");
       } else {
           this.events.add(new Event(eventName,eventCategory));
           log.debug("Event " + "\"" + eventName +  "\"" + " added successfully to the list " + "\"" + this.eventlistName +  "\"");
       }

    }

    /**
     * deletes an event from "this" eventlist
     * takes care of the case, when no event with matching name found
     * @param eventName -- the name of the event you want to delete
     */
    public void deleteEvent(String eventName){

        log.debug("deleteEvent method started");
        List<Event> temp = this.events.stream().filter(event -> event.getName().equals(eventName)).collect(Collectors.toList());

        if(temp.size() == 0){
            log.error("No event with matching name found");
        }else if(temp.size() > 1){
            //this should normally never be the case (watch addEvent method, problem already treated there)
            log.info("Multiple events with matching name found");
        }else{
            this.events.removeAll(temp);
            log.debug("Event " + "\"" + eventName +  "\"" + " deleted successfully from the list " + "\"" + this.eventlistName +  "\"");
        }
    }

    /**
     * completes an event of "this" list
     * @param eventName -- the name of the event which should be completed
     * @param eventImageUrl -- the url of the image the user chose
     * @param eventDescription -- the description entered by the user
     */
    public void completeEvent(String eventName, String eventImageUrl,String eventDescription, int eventDay, int eventMonth, int eventYear ){
        //todo when no event found
        log.debug("completeEvent method started");
        for (int i = 0; i < this.events.size(); i++) {
            if(this.events.get(i).getName().equals(eventName)){
                this.events.get(i).completeEvent(eventImageUrl,eventDescription,eventDay, eventMonth, eventYear);
                log.debug("Event completed successfully");
            }
        }
    }

    /**
     * returns the name of the eventlist
     * @return
     */
    public String getName(){
        return this.eventlistName;
    }


    /**
     *
     * @return the date as a GregorianCalendar Object
     */
    public GregorianCalendar getExpiryDateGregorian(){
        return this.expiryDateGregorian;
    }
}
