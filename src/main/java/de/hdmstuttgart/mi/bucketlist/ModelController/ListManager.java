package de.hdmstuttgart.mi.bucketlist.ModelController;


import de.hdmstuttgart.mi.bucketlist.Model.*;
import de.hdmstuttgart.mi.bucketlist.Persistance.EventlistRepository;
import de.hdmstuttgart.mi.bucketlist.Persistance.Sourcetype;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//todo modify event description-method
public class ListManager {

    // initialize Logger
    private static final Logger log = LogManager.getLogger(ListManager.class);

    private ArrayList<Eventlist> eventlists = new ArrayList<>();
    private final EventlistRepository eventlistRepository = new EventlistRepository(Sourcetype.FILESOURCE);


    public ListManager(){

    }

    /**
     * creates new Eventlist
     * @param eventlistName -- the name of the eventlist
     */
    public void createEventlist(String eventlistName){
        log.info("createEventlist method started");
        if(this.eventlists.stream().anyMatch(eventlist -> eventlist.getName().equals(eventlistName))){
            log.info("There is already an eventlist with the name " + "\"" + eventlistName + "\"" + " please choose another one");
        }else{
            this.eventlists.add(new Eventlist(eventlistName));
            log.info( "Eventlist " + "\"" + eventlistName + "\"" + " added successfully");
        }
    }

    /**
     * creates new Eventlist with expiry date
     * @param eventlistName -- the name of the Eventlist
     * @param expiryDay -- day of the expiry date
     * @param expiryMonth -- month of the expiry date
     * @param expiryYear -- year of the expiry date
     */
    public void createEventlist(String eventlistName, int expiryDay, int expiryMonth, int expiryYear){
        log.debug("createEventlist method started");
        if(this.eventlists.stream().anyMatch(eventlist -> eventlist.getName().equals(eventlistName))){
            log.info("There is already an eventlist with the name " + "\"" + eventlistName + "\"" + " please choose another one");
        }else{
            this.eventlists.add(new Eventlist(eventlistName, expiryDay, expiryMonth, expiryYear));
            log.debug( "Eventlist " + "\"" + eventlistName + "\"" + " added successfully");
        }
    }

    /**
     * deletes Eventlist
     * @param eventlistName -- the name of the eventlist which should be deleted
     */
    public void deleteEventlist(String eventlistName){
        log.debug("deleteEventlist method started");
        List<Eventlist> temp = this.eventlists.stream().filter(eventlist -> eventlist.getName().equals(eventlistName)).collect(Collectors.toList());
        if(temp.size() == 0){
            log.error("No Eventlist with matching name found");
        }else if(temp.size() > 1){
            log.info("Multiple Eventlists with matching name found");
        }else{
            this.eventlists.removeAll(temp);
            log.debug("Eventlist " + "\"" + eventlistName + "\"" + " deleted successfully");
        }
    }

    /**
     * finds the right list to add an event
     * than calls the addEventMethod of eventlist
     * creates a new event within an already existing eventlist
     * @param eventName -- the name of the event
     * @param eventCategory -- the category of the event
     * @param eventlistName -- the name of the eventlist where the event should be added
     */

   public void addEventToList(String eventName, Category eventCategory, String eventlistName){

       log.debug("addEventToList method started");
        for (int i = 0; i < this.eventlists.size(); i++) {
            if(this.eventlists.get(i).getName().equals(eventlistName)){
                this.eventlists.get(i).addEvent(eventName,eventCategory);
            }
        }
       log.debug( "Event " + "\"" + eventName + "\"" + " added successfully to " + eventlistName);
    }

    /**
     * deletes Event
     * @param eventName -- the name of the event, you want to delete
     * @param eventlistName -- the name of the eventlist which contains the event
     */
    public void deleteEvent(String eventName, String eventlistName){
        log.debug("deleteEvent method started");
        for (int i = 0; i < this.eventlists.size(); i++) {
            if(this.eventlists.get(i).getName().equals(eventlistName)){
                this.eventlists.get(i).deleteEvent(eventName);
            }
        }
        log.debug("Event " + "\"" + eventName + "\"" + " deleted successfully");
    }

    /**
     * completes event
     * @param eventName -- the name of the event, you want to complete
     * @param eventlistName -- the name of the eventlist which contains the event
     * @param eventImageUrl -- the url of the eventImage chosen by the user
     * @param eventDescription -- the event description written by the user
     */
    public void completeEvent(String eventName, String eventlistName, String eventImageUrl, String eventDescription, int eventDay, int eventMonth, int eventYear) {
        log.debug("completeEvent method started");
        for (int i = 0; i < this.eventlists.size(); i++) {
            if(this.eventlists.get(i).getName().equals(eventlistName)){
                this.eventlists.get(i).completeEvent(eventName,eventImageUrl,eventDescription, eventDay, eventMonth, eventYear);
                log.debug("Event "  + "\"" + eventName + "\"" + " completed succesfully");
            }
        }
        log.debug("Event completed successfully");
    }

    /**
     * saves the eventlists
     */
    public void save(){
        this.eventlistRepository.writeSaveable(this.eventlists);
    }


    /**
     * loads the eventlists which have been saved
     */
    public void load(){
        this.eventlists = this.eventlistRepository.loadSaveable();
    }

    /**
     *
     * @return -- returns a COPY of all Eventlists saved in the Manager
     */
    public ArrayList<Eventlist> getEventlists() {
        ArrayList<Eventlist> copy = new ArrayList<>(this.eventlists);
        return copy;
    }



}
