package de.hdmstuttgart.mi.bucketlist.ModelController;


import de.hdmstuttgart.mi.bucketlist.Model.*;
import de.hdmstuttgart.mi.bucketlist.Persitance.Repository;
import de.hdmstuttgart.mi.bucketlist.Persitance.Sourcetype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ListManager {

    private ArrayList<Eventlist> eventlists;
    private Repository repository;
   // private Repository repositoryDB;


    public ListManager(){
        this.eventlists = new ArrayList<>();
        this.repository = new Repository(Sourcetype.FILESOURCE);
        //this.repositoryDB = new Repository(Sourcetype.DATABASESOURCE);
    }

    /**
     * creates new Eventlist
     * @param eventlistName -- the name of the eventlist
     */
    public void createEventlist(String eventlistName){
        this.eventlists.add(new Eventlist(eventlistName));
        //todo log here: eventlist added
    }

    /**
     * deletes Eventlist
     * @param eventlistName -- the name of the eventlist which sould be deleted
     */
    public void deleteEventlist(String eventlistName){
        for (int i = 0; i < this.eventlists.size(); i++){
            if(this.eventlists.get(i).getName().equals(eventlistName)){
                this.eventlists.remove(i);
                System.out.println("Eventlist " + "\"" + eventlistName + "\"" + " deleted successfully");
                //todo log enventlist deleted
            }
        }
        //todo log if no eventlist with matching name was found
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

        for (int i = 0; i < this.eventlists.size(); i++) {
            this.repository.writeSaveable(this.eventlists.get(i));
        }

        //this.repository.writeSaveable(this.eventlists.get(0));
        //this.repositoryDB.writeSaveable(this.eventlists.get(0));
    }


    public void load(){

        Eventlist list = new Eventlist();
        this.repository.loadSaveable("Data/Cool",list);
        System.out.println(list.toString());

        /*for (int i = 0; i < this.eventlists.size(); i++) {
            this.repository.loadSaveable(this.eventlists.get(i).getName(),this.eventlists.get(i));
        }*/
        /*this.eventlists.set(0,(Eventlist)this.repository.loadSaveable("repotest",this.eventlists.get(0)));
        System.out.println(this.eventlists.get(0).getName());*/


    }



    public static void main(String[] args) {
        ListManager l = new ListManager();
        l.createEventlist("Cool");
        l.createEventlist("Del");

        System.out.println(Arrays.toString(l.eventlists.toArray()));

        //l.deleteEventlist("Del");
        System.out.println(Arrays.toString(l.eventlists.toArray()));

        l.addEvent("Essen",Category.SKILLS,"Cool");
        l.addEvent("Spielen",Category.SKILLS,"Cool");
        l.addEvent("HUnger",Category.SKILLS,"Cool");
        l.addEvent("HIHI",Category.LIFEGOALS,"Cool");

        l.addEvent("hahah",Category.LIFEGOALS,"Del");
        l.addEvent("waaas",Category.SKILLS,"Del");

        System.out.println(l.eventlists.toString());
        l.deleteEvent("Spielen","Cool");
        System.out.println(l.eventlists.toString());

        l.completeEvent("Essen","Cool","url","tolle sache ist das");

        System.out.println(l.eventlists.toString());

        //l.save();
        //l.load();

       System.out.println(l.eventlists.toString());


        HashMap<Category,Categorylist> cat = l.getFilledCatgeoryLists();
        System.out.println(cat.get(Category.SKILLS));
        System.out.println(cat.get(Category.LIFEGOALS));



        //repository.loadSaveable("Lesen",eventlist);


    }
}
