package de.hdmstuttgart.mi.bucketlist.Model;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hdmstuttgart.mi.bucketlist.Persitance.Saveable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

//todo no need to implement the saveable interface ?
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY )
public class Event implements Saveable {

    private String eventName;
    private Category eventCategory;

    //private Date eventDate;
    private String eventImageUrl;
    private String eventDescription;
    private boolean isCompleted;



    public Event(String eventName, Category eventCategory){
        this.eventName = eventName;
        this.eventCategory = eventCategory;
        this.isCompleted = false;
    }

    /**
     * default constructor for json parsing
     */
    public Event(){

    }

    /**
     * completes the event, sets the attributes which can be set when a event is completed
     * @param eventImageUrl -- the url which points to the url of the image the user chose
     * @param eventDescription -- short description about the event (entered by the user)
     */
    public void completeEvent(String eventImageUrl,String eventDescription){
        this.eventImageUrl = eventImageUrl;
        this.eventDescription = eventDescription;
        this.isCompleted = true;
    }

    @Override
    public String toString(){
        return "Eventname:" + this.eventName + ", " + this.eventCategory + ", abgeschlossen:" + this.isCompleted;
    }

    /**
     * parses an event object ito a json object and writes it to a file
     */
    @Override
    public void toJson(File file) {
        ObjectMapper objectMapper = new ObjectMapper();


        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("test"), this);
        }catch(FileNotFoundException e){
            System.out.println("FIle not found");
        } catch (IOException e) {
            System.out.println("IO Exeption");//todo log here
        }
    }


    @Override
    public Saveable fromJson(File file){
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Event temp = objectMapper.readValue(new File("test"),this.getClass());

            this.eventName = temp.eventName;
            this.eventDescription = temp.eventDescription;
            this.eventCategory = temp.eventCategory;
            this.isCompleted = temp.isCompleted;
            this.eventImageUrl = temp.eventImageUrl;

            return temp;

        } catch (IOException e) {
            System.out.println("IO execption"); //todo log here
        }
        return null;
    }

    @Override
    public String getName(){
        return this.eventName;
    }

    public Category getEventCategory(){
        return this.eventCategory;
    }



    public static void main(String[] args) {
        Event e = new Event("Essen",Category.SKILLS);
        //e.toJson();
       // e.fromJson();
        System.out.println(e.eventName);
    }
}
