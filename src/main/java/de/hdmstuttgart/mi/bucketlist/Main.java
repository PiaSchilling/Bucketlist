package de.hdmstuttgart.mi.bucketlist;

import de.hdmstuttgart.mi.bucketlist.Model.Category;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import de.hdmstuttgart.mi.bucketlist.ViewController.CategoryManager;
import de.hdmstuttgart.mi.bucketlist.ViewController.StatisticsManager;

/**
 * this is going to be the entry point for our application
 */
public class Main {

    public static void main(String[] args) {

        //the only listManager that should exist
        ListManager listManager = new ListManager();
        //gets the listManager handed over, so it can use it too
        CategoryManager categoryManager = new CategoryManager(listManager);

        listManager.createEventlist("List1");
        listManager.createEventlist("List2");
        listManager.createEventlist("List3",4,6,2021);

        listManager.addEventToList("event1",Category.SKILLS,"List1");
        listManager.addEventToList("event2",Category.SKILLS,"List1");
        listManager.addEventToList("event3",Category.SKILLS,"List1");

        listManager.addEventToList("event1",Category.SKILLS,"List2");
        listManager.addEventToList("event2",Category.SKILLS,"List2");
        listManager.addEventToList("event3",Category.SKILLS,"List2");

        listManager.addEventToList("event1",Category.SKILLS,"List3");
        listManager.addEventToList("event2",Category.SKILLS,"List3");
        listManager.addEventToList("event3",Category.SKILLS,"List3");


        listManager.completeEvent("event1","List1","URL","Beschreibung",3,6,2021);
        listManager.completeEvent("event1","List2","URL","Beschreibung",12,12,2021);
        listManager.completeEvent("event1","List3","URL","Beschreibung",12,12,2021);

        listManager.save();
        listManager.load();


        StatisticsManager statisticsManager = new StatisticsManager(listManager);

        System.out.println( "days left: " + statisticsManager.daysLeft("List3"));
        System.out.println("coutn lists: " + statisticsManager.countLists());
        System.out.println("completed events: " + statisticsManager.countCompletedEvents());
        System.out.println(statisticsManager.countCompletedEventsPerList("List2"));
        System.out.println(statisticsManager.calculatePercentageCompletedEventsPerList("List1"));

        System.out.println(categoryManager.getFilledCatgeoryLists().get(Category.SKILLS));

    }
}
