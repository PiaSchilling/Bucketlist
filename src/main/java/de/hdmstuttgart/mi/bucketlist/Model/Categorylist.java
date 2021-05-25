package de.hdmstuttgart.mi.bucketlist.Model;

import java.util.ArrayList;
import java.util.Arrays;

public class Categorylist {

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
        if(events.stream().anyMatch(event -> event.getEventCategory() != this.listCategory)){
            //normaly this should be never reached
            System.out.println("Error. List Contains Events with the wrong category"); //todo leg here
        }else{
            this.events = events;
            System.out.println("Eventlist of " + events.size() + " items added sucessfully to the categorylist " + this.listCategory);//todo log here
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
