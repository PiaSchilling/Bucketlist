package de.hdmstuttgart.mi.bucketlist.ViewController;

import de.hdmstuttgart.mi.bucketlist.Model.Eventlist;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * makes it possible to display statistics about the events
 * should not manipulate the original list, only use the getEventlists Method (returns a copy of the original list)
 */
public class StatisticsManager {

    private final ListManager listManager;

    ArrayList<Eventlist> eventlists;

    private CategoryManager categoryManager;

    // initialize Logger
    private static final Logger log = LogManager.getLogger(StatisticsManager.class);




    /**
     * @param listManager -- the only existing ListManager
     */
    public StatisticsManager(ListManager listManager){
        this.listManager = listManager;
        this.eventlists= this.listManager.getEventlists();
        this.categoryManager= new CategoryManager(listManager);
    }


    /**
     * Counts the created lists
     * @return the number of the created lists
     */
    public int countLists(){
        return this.eventlists.size();
    }


    /**
     * counts the completed events in general
     * @return the number of completed events in general
     */
    public int countCompletedEvents(){
        ArrayList<Eventlist> temp = this.listManager.getEventlists();
        int count=0;
        for (int i = 0; i < this.eventlists.size(); i++) {
            // für nicht gefüllte Listen definieren
            for (int j = 0; j < this.eventlists.get(i).getEvents().size(); j++) {
                if (this.eventlists.get(i).getEvents().get(j).getIsCompleted()== true){
                    count++;
                }
            }
        } return count;
    }

    /**
     * Counts the events in a specific List, ignoring the fact if it's completed or not
     * @param eventlistName
     * @return the number of events
     */
    public int countEventsPerList(String eventlistName) {
        int count = 0;

        for (int i = 0; i < this.eventlists.size(); i++) {
            if (this.eventlists.get(i).getName().equals(eventlistName)) {
                count= this.eventlists.get(i).getEvents().size();
            }
        } return count;
    }


    /**
     * Counts just the completed events in a specific List
     * @param eventlistName
     * @return the number of completed events
     */
    public int countCompletedEventsPerList(String eventlistName){
        int count=0;

        for (int i = 0; i < this.eventlists.size(); i++) {
            if( this.eventlists.get(i).getName().equals(eventlistName)){
                for (int j = 0; j < this.eventlists.get(i).getEvents().size(); j++) {
                    if (this.eventlists.get(i).getEvents().get(j).getIsCompleted()== true){
                        count++;
                    }
                }
            }
        } return count;
    }

    /**
     * Calculates the process of the completed events in a choosen list
     * @param eventlistName
     * @return String with the calculates percentage
     */
    public String calculatePercentageCompletedEventsPerList(String eventlistName){
        double percentage=-1;
        for (int i = 0; i < this.eventlists.size(); i++) {
            if( this.eventlists.get(i).getName().equals(eventlistName)){
                double numerator= countCompletedEventsPerList(eventlistName);
                double denominator= countEventsPerList(eventlistName);

                percentage= numerator/denominator * 100;
            }
        }return percentage + " %";
    }

    public String daysLeft (String eventlistName) {
        log.debug("daysLeft method started");
        int days=0;
        long difference;

        GregorianCalendar today = new GregorianCalendar();
        GregorianCalendar future = new GregorianCalendar();

        for (int i = 0; i < this.eventlists.size(); i++) {
            if (this.eventlists.get(i).getName().equals(eventlistName)) {
                future = this.eventlists.get(i).getExpiryDateGregorian();
                difference = future.getTimeInMillis() - today.getTimeInMillis();
                days = (int) (difference / (1000 * 60 * 60 * 24));
            }

            log.debug("daysLeft method ended");
        }return String.valueOf(days) + " days left for " + "'" + eventlistName + "'";
    }



}
