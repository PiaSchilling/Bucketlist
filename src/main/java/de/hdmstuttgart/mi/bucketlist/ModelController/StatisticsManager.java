package de.hdmstuttgart.mi.bucketlist.ModelController;

import de.hdmstuttgart.mi.bucketlist.Model.Eventlist;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * makes it possible to display statistics about the events
 * should not manipulate the original list, only use the getEventlists Method (returns a copy of the original list)
 */
public class StatisticsManager {

    private final ListManager listManager;

    private CategoryManager categoryManager;

    private static final Logger log = LogManager.getLogger(StatisticsManager.class);




    /**
     * @param listManager -- the only existing ListManager
     */
    public StatisticsManager(ListManager listManager){
        this.listManager = listManager;
        this.categoryManager= new CategoryManager(listManager);
    }


    /**
     * Counts the created lists
     * @return the number of the created lists as String
     */
    public String countListsAsString(){
        log.debug("countListsAsString() method started");
        ArrayList<Eventlist> temp = this.listManager.getEventlists();
        return String.valueOf(temp.size());
    }


    /**
     * Counts the created events
     * @return the number of the created lists
     */
    public int countEvents(){
        log.debug("countEvents() method started");

        ArrayList<Eventlist> temp = this.listManager.getEventlists();
        int count = 0;

        for (int i = 0; i < temp.size(); i++) {
            count = count + temp.get(i).getEvents().size();
        }
        return count;
    }



    /**
     * counts the created events
     * @return the number of the created lists as String
     */
    public String countEventsAString(){
        log.debug("countEventsAString() method started");

        ArrayList<Eventlist> temp = this.listManager.getEventlists();

        int count = 0;

        for (int i = 0; i < temp.size(); i++) {
            count = count + temp.get(i).getEvents().size();
        }
        return String.valueOf(count);
    }


    /**
     * counts the completed events in general
     * @return the number of completed events in general as String
     */
    public String countCompletedEventsAsString(){
        log.debug("countCompletedEventsAsString() method started");

        ArrayList<Eventlist> temp = this.listManager.getEventlists();

        int count=0;
        for (int i = 0; i < temp.size(); i++) {
            for (int j = 0; j < temp.get(i).getEvents().size(); j++) {
                if (temp.get(i).getEvents().get(j).getIsCompleted()== true){
                    count++;
                }
            }
        } return String.valueOf(count);
    }



    /**
     * Counts the events in a specific List, ignoring the fact if it's completed or not
     * @param eventlistName
     * @return the number of events as int
     */
    public int countEventsPerList(String eventlistName) {
        log.debug("countEventsPerList() method started");

        ArrayList<Eventlist> temp = this.listManager.getEventlists();

        int count = 0;

        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getName().equals(eventlistName)) {
                count= temp.get(i).getEvents().size();
            }
        } return count;
    }


    /**
     * Counts just the completed events in a specific List
     * @param eventlistName
     * @return the number of completed events as int
     */
    public int countCompletedEventsPerList(String eventlistName){
        log.debug("countCompletedEventsPerList() method started");

        ArrayList<Eventlist> temp = this.listManager.getEventlists();

        int count=0;

        for (int i = 0; i < temp.size(); i++) {
            if( temp.get(i).getName().equals(eventlistName)){
                for (int j = 0; j < temp.get(i).getEvents().size(); j++) {
                    if (temp.get(i).getEvents().get(j).getIsCompleted()== true){
                        count++;
                    }
                }
            }
        }
        return count;
    }


    /**
     * Calculates the process of the completed events in a choosen list.
     * Returns the result without the positions after the decimal point when its an integer.
     * Otherwise it returns the formatted String with two decimal places.
     * @param eventlistName
     * @return String with the calculates percentage
     */
    public String calculatePercentageCompletedEventsPerListAsString(String eventlistName){
        log.debug("calculatePercentageCompletedEventsPerListAsString() method started");

        ArrayList<Eventlist> temp = this.listManager.getEventlists();

        double percentage=-1;
        for (int i = 0; i < temp.size(); i++) {
            if( temp.get(i).getName().equals(eventlistName)){
                double numerator= countCompletedEventsPerList(eventlistName);
                double denominator= countEventsPerList(eventlistName);

                percentage= numerator/denominator * 100;
            }
        }
        if(percentage==(int)percentage){
            return (int)percentage +" %";
        }
        String format = "0.00";
        DecimalFormat formatter = new DecimalFormat(format);
        String formattedPercentage = formatter.format(percentage);
        return formattedPercentage + " %";
    }



    /**
     * Calculates the process of the completed events in a choosen list.
     * @param eventlistName
     * @return double with the calculated percentage
     */
    public double calculatePercentageCompletedEventsPerList(String eventlistName){
        log.debug("calculatePercentageCompletedEventsPerList() method started");

        ArrayList<Eventlist> temp = this.listManager.getEventlists();

        double percentage=0;
        for (int i = 0; i < temp.size(); i++) {
            if( temp.get(i).getName().equals(eventlistName)){
                double numerator= countCompletedEventsPerList(eventlistName);
                double denominator= countEventsPerList(eventlistName);

                percentage= numerator/denominator;
            }
        }return percentage;
    }



    /**
     * Calculates the days which are left until the expiry date from the handed over eventlist
     * If the expiry date is not set, the method will return a default String
     * @param eventlistName
     * @return the date or the default value
     */
    public String daysLeftAsString(String eventlistName) {
        log.debug("daysLeftAsString() method started");
        ArrayList<Eventlist> temp = this.listManager.getEventlists();

        int days=0;
        long difference;

        GregorianCalendar today = new GregorianCalendar();
        GregorianCalendar future = new GregorianCalendar();

        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getName().equals(eventlistName)) {
                if (temp.get(i).getExpiryDateGregorian() != null) {
                    future = temp.get(i).getExpiryDateGregorian();
                    difference = future.getTimeInMillis() - today.getTimeInMillis();
                    days = (int) (difference / (1000 * 60 * 60 * 24));
                    return String.valueOf(days);
                }
            }
        } return "-";
    }



    /**
     * Calculates the days which are left until the expiry date from the handed over eventlist
     * @param eventlistName
     * @return the date or the default value
     */
    public int daysLeftAsInt (String eventlistName) {
        log.debug("daysLeftAsInt() method started");

        int days=0;
        long difference;

        GregorianCalendar today = new GregorianCalendar();
        GregorianCalendar future = new GregorianCalendar();

        for (int i = 0; i < this.listManager.getEventlists().size(); i++) {
            if (this.listManager.getEventlists().get(i).getName().equals(eventlistName)) {
                if (this.listManager.getEventlists().get(i).getExpiryDateGregorian() != null) {
                    future = this.listManager.getEventlists().get(i).getExpiryDateGregorian();
                    difference = future.getTimeInMillis() - today.getTimeInMillis();
                    days = (int) (difference / (1000 * 60 * 60 * 24));
                }
            }
        }return days;
    }


    /**
     * count days left form a handed over eventlist
     * @param eventlist -- the list the day should be counted of
     * @return -- the number of days left until expiry
     */
    public static int daysLeft (Eventlist eventlist) {
        log.debug("daysLeft() static method started");

        int days=0;
        long difference;

        GregorianCalendar today = new GregorianCalendar();
        GregorianCalendar future = new GregorianCalendar();

        future = eventlist.getExpiryDateGregorian();
        difference = future.getTimeInMillis() - today.getTimeInMillis();
        days = (int) (difference / (1000 * 60 * 60 * 24));

        return days;
    }



}
