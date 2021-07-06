package de.hdmstuttgart.mi.bucketlist.ModelController;

import de.hdmstuttgart.mi.bucketlist.Exceptions.ElementAlreadyExistsException;
import de.hdmstuttgart.mi.bucketlist.View.Listener;
import de.hdmstuttgart.mi.bucketlist.Model.*;
import de.hdmstuttgart.mi.bucketlist.Persistance.EventlistRepository;
import de.hdmstuttgart.mi.bucketlist.Persistance.Sourcetype;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//todo create a modify event description-method
public class ListManager implements Narrator {

    // initialize Logger
    private static final Logger log = LogManager.getLogger(ListManager.class);

    //private ArrayList<Eventlist> eventlists = new ArrayList<>();
    private final ObservableList<Eventlist> eventlists = FXCollections.observableArrayList();
    private final EventlistRepository eventlistRepository = new EventlistRepository(Sourcetype.FILESOURCE);
    private final ArrayList<Listener> listeners = new ArrayList<>();

    //default constructor //todo why is this here ?
    public ListManager(){
    }

    //------------------------ model manipulating methods -----------------------------------

    /**
     * creates new Eventlist without expiry Date
     * @param eventlistName -- the name of the eventlist
     */
    public void createEventlist(String eventlistName) throws ElementAlreadyExistsException {
        log.debug("createEventlist method started");
        if(this.eventlists.stream()
                .anyMatch(eventlist -> eventlist.getName().equals(eventlistName))){
            log.info("There is already an eventlist with the name " + "\"" + eventlistName + "\"" + " please choose another one");
            throw new ElementAlreadyExistsException("There is already an eventlist with the name " + "\"" + eventlistName + "\"" + " please choose another one");
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
    public void createEventlist(String eventlistName, int expiryDay, int expiryMonth, int expiryYear) throws ElementAlreadyExistsException {
        log.debug("createEventlist method started");
        if(this.eventlists
                .stream()
                .parallel()
                .anyMatch(eventlist -> eventlist.getName().equals(eventlistName))){
            log.info("There is already an eventlist with the name " + "\"" + eventlistName + "\"" + " please choose another one");
            throw new ElementAlreadyExistsException("There is already an eventlist with the name " + "\"" + eventlistName + "\"" + " please choose another one");
        }else{
            this.eventlists.add(new Eventlist(eventlistName, expiryDay, expiryMonth, expiryYear));
            log.info( "Eventlist " + "\"" + eventlistName + "\"" + " added successfully");
        }
    }

    /**todo remove unnecessary if statements for cases which never can happen
     * todo simplify with getEventlistByName
     * deletes Eventlist
     * @param eventlistName -- the name of the eventlist which should be deleted
     */
    public void deleteEventlist(String eventlistName){
        log.debug("deleteEventlist method started");
        List<Eventlist> temp = this.eventlists.stream()
                .filter(eventlist -> eventlist.getName().equals(eventlistName))
                .collect(Collectors.toList());
        if(temp.size() == 0){
            log.error("No Eventlist with matching name found");
        }else if(temp.size() > 1){
            log.info("Multiple Eventlists with matching name found");
        }else{
            this.eventlists.removeAll(temp);
            log.info("Eventlist " + "\"" + eventlistName + "\"" + " deleted successfully");
        }
    }

    /** todo will never be used, can be deleted
     * finds the right list to add an event
     * than calls the addEventMethod of eventlist
     * creates a new event within an already existing eventlist
     * @param eventName -- the name of the event
     * @param eventCategory -- the category of the event
     * @param eventlistName -- the name of the eventlist where the event should be added
     */
   public void addEventToList(String eventName, Category eventCategory, String eventlistName) throws ElementAlreadyExistsException {

       log.debug("addEventToList method started");
       for (Eventlist eventlist : this.eventlists) {
           if (eventlist.getName().equals(eventlistName)) {
               eventlist.addEvent(eventName, eventCategory);
           }
       }
       log.debug( "Event " + "\"" + eventName + "\"" + " added successfully to " + eventlistName);
    }

    /** todo will never be used, can be deleted
     * deletes Event
     * @param eventName -- the name of the event, you want to delete
     * @param eventlistName -- the name of the eventlist which contains the event
     */
    public void deleteEvent(String eventName, String eventlistName){
        log.debug("deleteEvent method started");
        for (Eventlist eventlist : this.eventlists) {
            if (eventlist.getName().equals(eventlistName)) {
                eventlist.deleteEvent(eventName);
            }
        }
        log.debug("Event " + "\"" + eventName + "\"" + " deleted successfully");
    }

    /**  todo will never be used, can be deleted
     * completes event
     * @param eventName -- the name of the event, you want to complete
     * @param eventlistName -- the name of the eventlist which contains the event
     * @param eventImageUrl -- the url of the eventImage chosen by the user
     * @param eventDescription -- the event description written by the user
     */
    public void completeEvent(String eventName, String eventlistName, String eventImageUrl, String eventDescription, int eventDay, int eventMonth, int eventYear) {
        log.debug("completeEvent method started");
        for (Eventlist eventlist : this.eventlists) {
            if (eventlist.getName().equals(eventlistName)) {
                eventlist.completeEvent(eventName, eventImageUrl, eventDescription, eventDay, eventMonth, eventYear);
                log.debug("Event " + "\"" + eventName + "\"" + " completed succesfully");
            }
        }
        log.debug("Event completed successfully");
    }

    // ---------------------- persistance ------------------------
    /**
     * saves the eventlists
     */
    public void save(){
        log.debug("Save Method in listManager started");
        this.eventlistRepository.writeSaveable(this.eventlists);
    }

    /**
     * loads the eventlists which have been saved
     */
    public void load(){
        log.debug("Load Method in listManager started");
        //create temp list and set all Items to this.eventlists because otherwise no change report is fired
        ArrayList<Eventlist> temp = this.eventlistRepository.loadSaveable();
        this.eventlists.setAll(temp);
        log.debug("Lists in listManager loaded " + this.eventlists);
    }

    // ------------------------ getter ----------------------------

    /**
     * todo does not really return a copy (Elements in the list are the same)
     * @return -- returns a COPY of all Eventlists saved in the Manager
     */
    public ArrayList<Eventlist> getEventlists() {
        ArrayList<Eventlist> copy = new ArrayList<>(this.eventlists);
        return copy;
    }

    /**
     * @param eventlistName -- the name of the eventlist you want to get
     * @return -- the matching eventlist
     */
    public Eventlist getEventlistByName(String eventlistName){
        Eventlist eventlist = new Eventlist();
        for (Eventlist value : this.eventlists) {
            if (value.getName().equals(eventlistName)) {
                eventlist = value;
            }
        }
        return eventlist;
    }

    // ------------------------- GUI communication --------------------------------------

    /**
     * @param listener -- the listener which should be added to the list
     */
    @Override
    public void addListener(Listener listener){
        this.listeners.add(listener);
    }

    /**
     * informs all Listeners about a change
     */
    @Override
    public void informListeners() {
        for (Listener listener : this.listeners) {
            listener.update();
        }
    }

    /**
     * register a listener to the observable list
     * @param listener -- class/object which wants to get informed about changes in the list
     */
    public void addListListener(ListChangeListener<Eventlist> listener){
        this.eventlists.addListener(listener);
        log.debug("Listener added");
    }

}
