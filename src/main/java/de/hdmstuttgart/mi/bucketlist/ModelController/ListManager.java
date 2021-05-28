package de.hdmstuttgart.mi.bucketlist.ModelController;


import de.hdmstuttgart.mi.bucketlist.Model.*;
import de.hdmstuttgart.mi.bucketlist.Persitance.EventlistRepository;
import de.hdmstuttgart.mi.bucketlist.Persitance.Sourcetype;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListManager {

    private ArrayList<Eventlist> eventlists = new ArrayList<>();
    private final EventlistRepository eventlistRepository = new EventlistRepository(Sourcetype.FILESOURCE);


    public ListManager(){

    }

    /**
     * creates new Eventlist
     * @param eventlistName -- the name of the eventlist
     */
    public void createEventlist(String eventlistName){
        if(this.eventlists.stream().anyMatch(eventlist -> eventlist.getName().equals(eventlistName))){
            System.out.println("There is already an eventlist with the name " + "\"" + eventlistName + "\"" + " please choose another one");
        }else{
            this.eventlists.add(new Eventlist(eventlistName));
            System.out.println("Eventlist " + "\"" + eventlistName + "\"" + " added successfully");
            //todo log here: eventlist added
        }
    }

    /**
     * deletes Eventlist
     * @param eventlistName -- the name of the eventlist which should be deleted
     */
    public void deleteEventlist(String eventlistName){
        List<Eventlist> temp = this.eventlists.stream().filter(eventlist -> eventlist.getName().equals(eventlistName)).collect(Collectors.toList());
        if(temp.size() == 0){
            System.out.println("No Eventlist with matching name found"); //todo log here
        }else if(temp.size() > 1){
            System.out.println("Multiple Eventlists with matching name found");
        }else{
            this.eventlists.removeAll(temp);
            System.out.println("Eventlist " + "\"" + eventlistName + "\"" + " deleted successfully");
            //todo log here: eventlist deleted
        }
    }

    /**
     * finds the right list to add an event
     * than colls the addEventMethod of eventlist
     * creates a new event within an already existing eventlist
     * @param eventName -- the name of the event
     * @param eventCategory -- the categroy of the event
     * @param eventlistName -- the name of the eventlist where the event should be added
     */
    //todo change method name
   public void addEvent(String eventName, Category eventCategory, String eventlistName){

        for (int i = 0; i < this.eventlists.size(); i++) {
            if(this.eventlists.get(i).getName().equals(eventlistName)){
                this.eventlists.get(i).addEvent(eventName,eventCategory);
            }
        }
        //todo log here process finished
    }

    /**
     * deletes Event
     * @param eventName -- the name of the event, you want to delete
     * @param eventlistName -- the name of the eventlist which contains the event
     */
    public void deleteEvent(String eventName, String eventlistName){
        for (int i = 0; i < this.eventlists.size(); i++) {
            if(this.eventlists.get(i).getName().equals(eventlistName)){
                this.eventlists.get(i).deleteEvent(eventName);
            }
        }
        //todo log here process finished
    }

    /**
     * completes event
     * @param eventName -- the name of the event, you want to complete
     * @param eventlistName -- the name of the eventlist which contains the event
     * @param eventImageUrl -- the url of the eventImage chosen by the user
     * @param eventDescritption -- the event description written by the user
     */
    public void completeEvent(String eventName, String eventlistName, String eventImageUrl, String eventDescritption) {
        for (int i = 0; i < this.eventlists.size(); i++) {
            if(this.eventlists.get(i).getName().equals(eventlistName)){
                this.eventlists.get(i).completeEvent(eventName,eventImageUrl,eventDescritption);
                System.out.println("Event "  + "\"" + eventName + "\"" + " completed succesfully"); //todo log here
            }
        }
        //todo log here process finished
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
