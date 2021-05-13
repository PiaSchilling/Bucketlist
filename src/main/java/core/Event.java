package core;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {

    private String eventName;
    private Date eventDate;
    private Category eventCategory;
    private boolean isCompleted;
    private String eventDescription;

    //already written for the picture upload function
    private String eventImageUrl;


    public Event(String eventName, Category eventCategory){
        this.eventName = eventName;
        this.eventCategory = eventCategory;
        this.isCompleted= false;
    }


    private Date configureEventDate(String eventDate) throws ParseException {
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd.MM.yyyy"); //toDo: Format definition is not that what we want

        // toDo: here is the check sequence eventDate still missing
        // Caution! It needs to be in the past (or the current date)
        Date date = dateFormat.parse(eventDate);
        return date;
    }

    public void completeEvent (String eventDate, String eventDescription, String eventImageUrl) throws ParseException {
        this.eventDate= configureEventDate(eventDate);
        this.eventDescription= eventDescription;
        this.eventImageUrl= eventImageUrl;  // URL!
        this.isCompleted= true;
    }

    @Override
    public String toString() {
        return "\n"+
                "Event " +
                "eventName: " + this.eventName  +
                ", eventDate: " + this.eventDate +
                ", is completed: " + this.isCompleted +
                ", eventCategory: " + this.eventCategory + "\n";
    }


    public static void main(String[] args) throws ParseException {
        Event e1 = new Event("Skydive" , Category.LIFEGOALS);
        System.out.println(e1);
    }
}
