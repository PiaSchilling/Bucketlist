package de.hdmstuttgart.mi.bucketlist.Model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import de.hdmstuttgart.mi.bucketlist.Persitance.Saveable;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Eventlist implements Saveable {

    private ArrayList<Event> events;
    private String eventlistName;
    //private Date expiryDate;



    public Eventlist(String eventlistName /*String dateString*/){
        this.eventlistName = eventlistName;
        this.events = new ArrayList<>();
        //this.expiryDate = configureDate(dateString);
    }

    /**
     * default constructor for json parsing
     */
    public Eventlist(){
    }

    /**
     * returns a copy of the Eventlist
     * @return -- copy of the eventlist
     */
    public ArrayList<Event> getEvents() {
        ArrayList<Event> copy = new ArrayList<>(this.events);
        return copy;
    }

    /**
     * parses Date String in an Object of type date
     * //todo check date
     * @param dateString Date as a String
     * @return the date as an date object
     */
    private Date configureDate(String dateString){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date date = format.parse(dateString);
            return date;
        }catch (ParseException e){
            System.out.println("Parse exception");//todo log right here
        }
        return null;
    }

    @Override
    public String toString(){
        return "Eventlistname:" + this.eventlistName + ", " + Arrays.toString(this.events.toArray());
    }

    /**
     * writes the object as a json string to a file
     * @param file -- file which should hold the created json strings
     */
    @Override
    public void toJson(File file){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, this);
        }catch(FileNotFoundException e){
            System.out.println("FIle not found");
        } catch (IOException e) {
            System.out.println("IO Exeption in Method toJson");//todo log here
        }
    }

    /**
     * loads json string from a file
     * @param file -- source
     * @return -- the created object
     */
    @Override
    public Saveable fromJson(File file) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //todo understand why
        try {
            Eventlist temp = objectMapper.readerFor(Eventlist.class).readValue(file);

            this.eventlistName = temp.eventlistName;
            this.events = temp.getEvents();

            return temp;

        } catch (IOException e) {
            System.out.println("IO execption"); //todo log here
        }
        return null;
    }

    /**
     * adds an event to "this" eventlist
     * its not possible to add two events with the same name within one list
     * @param eventName -- the name which the event should have
     * @param eventCategory -- the category the event should have
     */
   public void addEvent(String eventName, Category eventCategory){
       if(this.events.stream().anyMatch(event -> event.getName().equals(eventName))){
           System.out.println("There ist already an event with the name "  + "\"" + eventName +  "\"" + " in the list " +  "\"" + this.eventlistName +  "\"" + ". Please select another name.");
           //todo log here
       }else{
           this.events.add(new Event(eventName,eventCategory));
           System.out.println("Event " + "\"" + eventName +  "\"" + " added successfully to the list " + "\"" + this.eventlistName +  "\"");
           //todo log here
       }

    }

    /**
     * deletes an event form "this" eventlist
     * takes care of the case, when no event with matching name found
     * @param eventName -- the name of the event you want to delete
     */
    public void deleteEvent(String eventName){

        List<Event> temp = this.events.stream().filter(event -> event.getName().equals(eventName)).collect(Collectors.toList());

        if(temp.size() == 0){
            System.out.println("No event with matching name found"); //todo log here
        }else if(temp.size() > 1){
            //this should normally never be the case (watch addEvent method, problem already treated there)
            System.out.println("Multiple events with matching events found"); //todo log here
        }else{
            this.events.removeAll(temp);
            System.out.println("Event " + "\"" + eventName +  "\"" + " deleted successfully from the list " + "\"" + this.eventlistName +  "\""); //todo log here
        }
    }

    /**
     * completes an event of "this" list
     * @param eventName -- the name of the event which should be completed
     * @param eventImageUrl -- the url of the image the user chose
     * @param eventDescription -- the description entered by the user
     */
    public void completeEvent(String eventName, String eventImageUrl,String eventDescription){
        //todo when no event found
        for (int i = 0; i < this.events.size(); i++) {
            if(this.events.get(i).getName().equals(eventName)){
                this.events.get(i).completeEvent(eventImageUrl,eventDescription);
                System.out.println("Event completed successfully");
            }
        }
    }

    /**
     * returns the name of the eventlist
     * @return
     */
    public String getName(){
        return this.eventlistName;
    }

}
