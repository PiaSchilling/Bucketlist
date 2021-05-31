package de.hdmstuttgart.mi.bucketlist.Model;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.hdmstuttgart.mi.bucketlist.Persistance.Saveable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//todo no need to implement the saveable interface ?
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY )
public class Event implements Saveable {

    // initialize Logger
    private static final Logger log = LogManager.getLogger(Event.class);

    private String eventName;
    private Category eventCategory;

    private String eventDateString;
    private String eventImageUrl;
    private String eventDescription;
    private boolean isCompleted;



    public Event(String eventName, Category eventCategory){
        this.eventName = eventName;
        this.eventCategory = eventCategory;
        this.isCompleted = false;
    }

    /**
     * default constructor for json parsing
     */
    public Event(){

    }

    /**
     * completes the event, sets the attributes which can be set when a event is completed
     * @param eventImageUrl -- the url which points to the url of the image the user chose
     * @param eventDescription -- short description about the event (entered by the user)
     * @param eventDay, -- the day of the date
     * @param eventMonth,  -- the month of the date
     * @param eventYear  -- the year of the date
     */
    public void completeEvent(String eventImageUrl,String eventDescription, int eventDay, int eventMonth, int eventYear){
        log.debug("completeEvent method started");
        this.eventImageUrl = eventImageUrl;
        this.eventDescription = eventDescription;
        this.isCompleted = true;
        this.eventDateString= closingDate(eventDay,eventMonth,eventYear);
    }

    /**
     * configures a String with a formatted output for the selected date
     * @param eventDay -- the day of the event as an int
     * @param eventMonth -- the month of the event as an int
     * @param eventYear -- the year of the event as an int
     * @return -- the whole date as a String
     */
    public String closingDate (int eventDay, int eventMonth, int eventYear){
        return eventDay + "." + eventMonth + "." + eventYear;
    }

    @Override
    public String toString(){
        return "Eventname:" + this.eventName + ", " + this.eventCategory + ", abgeschlossen:" + this.isCompleted + " Datum " + this.eventDateString;
    }


    /**
     * parses an event object into a json object and writes it to a file
     */
    @Override
    public void toJson(File file) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.debug("toJson method started");
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("test"), this);
        }catch(FileNotFoundException fileNotFoundException){
            log.error(fileNotFoundException.getMessage());
        } catch (IOException ioException) {
            log.error(ioException.getMessage());
        } finally {
            log.debug("toJson method ended");
        }
    }


    @Override
    public Saveable fromJson(File file){
        log.debug("fromJson method started");
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Event temp = objectMapper.readValue(new File("test"),this.getClass());

            this.eventName = temp.eventName;
            this.eventDescription = temp.eventDescription;
            this.eventCategory = temp.eventCategory;
            this.isCompleted = temp.isCompleted;
            this.eventImageUrl = temp.eventImageUrl;

            return temp;

        } catch (IOException ioException) {
            log.error(ioException.getMessage());
        } finally {
            log.debug("fromJson method ended");
        }
        return null;
    }

    @Override
    public String getName(){
        return this.eventName;
    }

    public Category getEventCategory(){
        return this.eventCategory;
    }

    public boolean getIsCompleted() {
        return  this.isCompleted;
    }

}
