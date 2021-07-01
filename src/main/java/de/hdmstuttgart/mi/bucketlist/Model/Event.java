package de.hdmstuttgart.mi.bucketlist.Model;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY )
public abstract class Event implements Narrator {

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
     * default constructor for json parsing //todo needed ?
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
        return eventDay + "/" + eventMonth + "/" + eventYear;
    }

    @Override
    public String toString(){
        return "Eventname:" + this.eventName + ", " + this.eventCategory + ", abgeschlossen:" + this.isCompleted + " Datum " + this.eventDateString;
    }

    public void updateName(String eventNameNew){
        this.eventName = eventNameNew;
    }

    public String getEventName(){
        return this.eventName;
    }

    public Category getEventCategory(){
        return this.eventCategory;
    }

    public boolean getIsCompleted() {
        return  this.isCompleted;
    }

    public String getEventImageUrl(){
        return this.eventImageUrl;
    }

    public String getEventDateString(){
        return this.eventDateString;
    }

    public String getEventDescription(){
        return this.eventDescription;
    }

}
