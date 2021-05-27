package de.hdmstuttgart.mi.bucketlist.ModelController;


import de.hdmstuttgart.mi.bucketlist.Model.*;
import de.hdmstuttgart.mi.bucketlist.Persitance.EventlistRepository;
import de.hdmstuttgart.mi.bucketlist.Persitance.Sourcetype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ListManager {

    private ArrayList<Eventlist> eventlists;
    private final EventlistRepository eventlistRepository;


    public ListManager(){
        this.eventlists = new ArrayList<>();
        this.eventlistRepository = new EventlistRepository(Sourcetype.FILESOURCE);
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
     * get the filled CategoryLists
     * Loops through every Eventlist and collects the events to the matching eventlists
     * @return -- a Map of all CategoryLists which contain the matching events
     */
    public HashMap<Category, Categorylist> getFilledCatgeoryLists(){

        //todo make it less complicated !!!

        CategoryListContainer categoryListContainer = new CategoryListContainer();
        //get empty CategoryList-HashMap from the container
        HashMap<Category, Categorylist> categoryLists = categoryListContainer.getCategoryListHashMap();

        //loop through the hashmap and get every categorylist
        for(Categorylist c: categoryLists.values()){

            //temp attributes to shortly save the filtered lists
            ArrayList<Event> temp = new ArrayList<>();
            ArrayList<Event> filteredList = new ArrayList<>();


            //loop through all the eventlists
            for (int i = 0; i < this.eventlists.size(); i++) {

                //get the Arraylist of events of the current eventlist
                Eventlist tempEventlist = this.eventlists.get(i);

                //get all Events with the matching Category and collect them into a temp list
                temp = (ArrayList<Event>) tempEventlist.getEvents()
                        .stream()
                        .filter(event -> event.getEventCategory() == c.getListCategory())
                        .collect(Collectors.toList());

                // add the temp list to the list which might already contain events with the same catgeory from another eventlist
                filteredList.addAll(temp);
            }

            //fill the arraylist of the categorylist with the filtered list
            c.fill(filteredList);
        }

        //return the filled list
        return categoryLists;
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
     * @return -- returns a copy of all Eventlists saved in the Manager
     */
    public ArrayList<Eventlist> getEventlists() {
        ArrayList<Eventlist> copy = new ArrayList<>(this.eventlists);
        return copy;
    }

    /**
     * only for testing
     * @param args --
     */
    public static void main(String[] args) {
        ListManager l = new ListManager();
        l.createEventlist("Lifegoals");
        l.createEventlist("HobbyGoals");

        l.addEvent("Test1",Category.SKILLS,"Lifegoals");
        l.addEvent("Test2",Category.CULINARY,"Lifegoals");
        l.addEvent("Test3",Category.CULTURE,"Lifegoals");

        l.addEvent("Test1",Category.SKILLS,"HobbyGoals");
        l.addEvent("Test2",Category.CULINARY,"HobbyGoals");
        l.addEvent("Test3",Category.CULTURE,"HobbyGoals");

        l.save();

        ListManager m = new ListManager();
        m.load();
        System.out.println(m.eventlists.toString());
        System.out.println(m.eventlists.get(0));

        System.out.println(m.getFilledCatgeoryLists());

    }
}
