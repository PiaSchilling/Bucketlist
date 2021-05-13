package core;

import java.util.ArrayList;

public class EventlistManager {

    private ArrayList<Eventlist> eventlists;


    public EventlistManager() {
        this.eventlists = new ArrayList<>();
    }

    public void addEventlist(String eventlistName, String expiryDate) {
        this.eventlists.add(new Eventlist(eventlistName, expiryDate));
    }


    public void deleteEventlist(){
        //toDo: needs to be implemented
    }


    public void showEventlists(){
        // toDo: needs to be implemented. JavaFX?
    }


}
