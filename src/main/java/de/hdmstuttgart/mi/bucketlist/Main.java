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


        listManager.createEventlist("TestList");
        //listManager.createEventlist("Lebensziele", 3,3,2033);

        listManager.addEventToList("Zebra Reiten", Category.RELATIONSHIP,"TestList");
        listManager.addEventToList("Romantisches Dinner mit Martin", Category.CULINARY,"TestList");
        listManager.addEventToList("Über wolken gehen", Category.CULTURE,"TestList");

        listManager.addEventToList("Mit Papagei sprechen", Category.LIFEGOALS,"TestList");
        listManager.addEventToList("Eigene Hüpfburg besitzen", Category.LIFEGOALS,"TestList");
        listManager.addEventToList("John Lennon treffen", Category.LIFEGOALS,"TestList");

        //listManager.completeEvent("Mit Papagei sprechen", "Lebensziele", "data/Lebensziele", "It was awesome", 12,11,2033);


        //listManager.save();
        //listManager.load();

        System.out.println(listManager.getEventlists().toString());
        System.out.println(categoryManager.getFilledCatgeoryLists().toString());


        // Main methods to check the StatisticsManager class
        listManager.createEventlist("Liste 3", 05,05,2055);


        listManager.addEventToList("Spagetti Essen", Category.CULINARY, "Liste 3");
        listManager.addEventToList("Skydive", Category.LIFEGOALS, "Liste 3");
        listManager.addEventToList("Hund besitzen", Category.FAMILY, "Liste 3");
        listManager.addEventToList("Haus bauen", Category.FAMILY, "Liste 3");
        listManager.addEventToList("Niagarafälle sehen", Category.TRAVEL, "Liste 3");


        listManager.completeEvent("Mit Papagei sprechen", "Lebensziele", "data/Lebensziele", "It was awesome", 12,11,2033);
        listManager.completeEvent("Skydive", "Liste 3", "data/Liste 3", "Es war sehr schön", 13, 3, 2021);
        listManager.completeEvent("Haus bauen", "Liste 3", "data/Liste 3", "Es war auch sehr schön", 13, 3, 2021);

        listManager.save();

        //gets the listManager handed over, so it can use it too
        StatisticsManager statisticsManager = new StatisticsManager(listManager);
        System.out.println("CountLists: " + statisticsManager.countLists());
        System.out.println("Count Completed Events: " + statisticsManager.countCompletedEvents());
        System.out.println("Count Events in List: " + statisticsManager.countEventsPerList("Liste 3"));
        System.out.println("Count Completed Events in choosen List: " + statisticsManager.countCompletedEventsPerList("Liste 3"));
        System.out.println("Percentage of completed events in choosen List " + statisticsManager.calculatePercentageCompletedEventsPerList("Liste 3"));
        System.out.println(statisticsManager.daysLeft("Liste 3"));

    }
}
