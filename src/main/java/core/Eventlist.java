package core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Eventlist {

    private ArrayList <Event> events ;
    private String eventlistName;
    private String expiryDate;


    public Eventlist(String eventlistName, String expiryDate) {
        this.eventlistName = eventlistName;
        this.expiryDate = expiryDate;
        this.events = new ArrayList<>();
    }

    public Eventlist(String eventlistName) {
        this.eventlistName = eventlistName;
        this.events = new ArrayList<>();
    }


    private Date configureExpiryDate(String expiryDate) throws ParseException {
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd.MM.yyyy"); //toDo: Format definition is not that what we want

        // toDo: here is the check sequence expiryDate still missing
        // Caution! It needs no be in the future.
        Date date = dateFormat.parse(expiryDate);
        return date;
    }


    public void addEvent (String eventName, Category eventCategory){
        Event e = new Event(eventName, eventCategory);
            this.events.add(e);
    }

    @Override
    public String toString() {
        return "Eventlist: " +
                "events: " + events +
                ", eventlistName: " + eventlistName +
                ", expiryDate: " + expiryDate ;
    }

    public static void main(String[] args) {
        Eventlist eventlistTest = new Eventlist("BlubBlub","29.20.2029");
        System.out.println(eventlistTest);

        eventlistTest.addEvent("Spätzle essen", Category.CULINARY);
        eventlistTest.addEvent("im Restaurant essen", Category.LIFEGOALS);
        System.out.println(eventlistTest);
    }
}
