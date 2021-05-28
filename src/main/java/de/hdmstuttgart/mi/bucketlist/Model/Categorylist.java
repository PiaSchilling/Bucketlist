package de.hdmstuttgart.mi.bucketlist.Model;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Categorylist {

    // initialize Logger
    private static final Logger log = LogManager.getLogger(Categorylist.class);

    private ArrayList<Event> events;
    private Category listCategory;

    public Categorylist(Category listCategory){
        this.listCategory = listCategory;
        this.events = new ArrayList<>();
    }


    /**
     * fills the empty Array-List of Events with a (filled) ArrayList
     * @param events -- List of Events which replaces the empty list
     */
    public void fill(ArrayList<Event> events){
        log.debug("fill method started");
        if(events.stream().anyMatch(event -> event.getEventCategory() != this.listCategory)){
            //normaly this should be never reached
            log.error("List Contains Events with the wrong category");
        }else{
            this.events = events;
            log.debug("Eventlist of " + events.size() + " items added sucessfully to the categorylist " + this.listCategory);
        }

    }

    /**
     * returns the category of the list
     * @return
     */
    public Category getListCategory(){
        return this.listCategory;
    }

    @Override
    public String toString(){
        return "Categorylist:" + this.listCategory + ", " + Arrays.toString(this.events.toArray());
    }

}
