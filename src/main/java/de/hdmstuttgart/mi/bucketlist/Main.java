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

        listManager.createEventlist("Traumdates");
        listManager.createEventlist("Lebensziele");

        listManager.addEvent("Zebra Reiten", Category.RELATIONSHIP,"Traumdates");
        listManager.addEvent("Romantisches Dinner mit Martin", Category.CULINARY,"Traumdates");
        listManager.addEvent("Über wolken gehen", Category.CULTURE,"Traumdates");

        listManager.addEvent("Mit Papagei sprechen", Category.LIFEGOALS,"Lebensziele");
        listManager.addEvent("Eigene Hüpfburg besitzen", Category.LIFEGOALS,"Lebensziele");
        listManager.addEvent("John Lennon treffen", Category.LIFEGOALS,"Lebensziele");

        listManager.save();
        //listManager.load();

        System.out.println(listManager.getEventlists().toString());
        System.out.println(categoryManager.getFilledCatgeoryLists().toString());

    }
}
