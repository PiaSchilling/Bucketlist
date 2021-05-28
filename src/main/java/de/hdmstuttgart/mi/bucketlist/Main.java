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
        //gets the listManager handed over, so it can use it too
        StatisticsManager statisticsManager = new StatisticsManager(listManager);

        listManager.createEventlist("Test");
        listManager.addEvent("Event1", Category.SKILLS,"Test");
        listManager.addEvent("Event2", Category.SHOPPING,"Test");
        listManager.addEvent("Event3", Category.LIFEGOALS,"Test");

        System.out.println(listManager.getEventlists().toString());
        System.out.println(categoryManager.getFilledCatgeoryLists().toString());

    }
}
