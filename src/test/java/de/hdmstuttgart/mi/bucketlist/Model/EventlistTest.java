package de.hdmstuttgart.mi.bucketlist.Model;

import de.hdmstuttgart.mi.bucketlist.Exceptions.ElementAlreadyExistsException;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EventlistTest {

    private ArrayList<Event> events;
    private String eventlistName;

    Eventlist addTest = new Eventlist();
    Eventlist addTest2 = new Eventlist();

    ListManager listTest1 = new ListManager();
    ListManager listTest2 = new ListManager();
    ListManager listTest3 = new ListManager();

    ListManager listCompare1 = new ListManager();
    ListManager listCompare2 = new ListManager();
    ListManager listCompare3 = new ListManager();

    Event eventNameTest1 = new Event("Meet Friends",Category.FRIENDS);
    Event eventNameTest2 = new Event("Eat Burger",Category.CULINARY);

    @BeforeEach
    public void prepareTestContent(){

        try {
        listTest1.createEventlist("TestList-1",1,8,2025);
        listTest2.createEventlist("TestList-2", 20,10,2022);
        listTest3.createEventlist("TestList-3", 15,1,2023);

        listTest1.addEventToList("Meet Friends",Category.FRIENDS,"TestList-1");
        listTest2.addEventToList("Eat Burger",Category.CULINARY,"TestList-2");
        listTest3.addEventToList("Get married",Category.RELATIONSHIP,"TestList-3");

        listTest1.completeEvent("Meet Friends","TestList-1","...","We had fun.",25,5,2021);
        listTest2.completeEvent("Eat Burger","TestList-2","...","Yummy.",5,3,2021);

        listCompare1.createEventlist("TestList-1",1,8,2025);
        listCompare2.createEventlist("TestList-2", 20,10,2022);
        listCompare3.createEventlist("TestList-3", 20,10,2022);

        listCompare1.addEventToList("Meet Friends",Category.FRIENDS,"TestList-1");
        listCompare2.addEventToList("Eat Burger",Category.CULINARY,"TestList-2");
        listCompare3.addEventToList("Get married",Category.RELATIONSHIP,"TestList-3");

        listCompare1.completeEvent("Meet Friends","TestList-1","...","We had fun.",25,5,2021);
        listCompare2.completeEvent("Eat Burger","TestList-2","...","Yummy.",5,3,2021);
        listCompare3.completeEvent("Get married","TestList-3","...","Best Day.",2,5,2020);
        }catch (
        ElementAlreadyExistsException e){
            e.printStackTrace();
        }
    }

    @Test
    void addEvent() {
        this.events = new ArrayList<>();

        try {
        addTest.addEvent("Learn Spanish",Category.EDUCATION);
        addTest.addEvent("Buy leather jacket",Category.SHOPPING);
        addTest2.addEvent("Learn Spanish",Category.EDUCATION);
        addTest2.addEvent("Buy leather jacket",Category.SHOPPING);
        }catch (ElementAlreadyExistsException e){
            e.printStackTrace();
        }

        assertEquals(addTest.toString(),addTest2.toString());
    }

    @Test
    void deleteEvent() {
        this.events = new ArrayList<>();

        try {
        addTest.addEvent("Learn Spanish",Category.EDUCATION);
        addTest2.addEvent("Bake a cake",Category.HOBBY);

        addTest.deleteEvent("Learn Spanish");
        addTest2.deleteEvent("Bake a cake");
        }catch (ElementAlreadyExistsException e){
            e.printStackTrace();
        }

        assertNotEquals("Eventname:Learn Spanish, EDUCATION, abgeschlossen:false Datum null",addTest.getEvents().toString());
        assertNotEquals("Eventname:Bake a cake, HOBBY, abgeschlossen:false Datum null",addTest2.getEvents().toString());
    }

    @Test
    void completeEvent() {
        assertEquals(listTest1.getEventlists().toString(),listCompare1.getEventlists().toString());
        assertEquals(listTest2.getEventlists().toString(),listCompare2.getEventlists().toString());

        assertNotEquals(listTest1.getEventlists(),listCompare1.getEventlists().toString());
        assertNotEquals(listTest1.getEventlists().toString(),listCompare2.getEventlists().toString());
        assertNotEquals(listTest2.getEventlists().toString(),listCompare1.getEventlists().toString());
        assertNotEquals(listTest3.getEventlists().toString(),listCompare3.getEventlists().toString());

    }

    @Test
    void getName() {
        this.events = new ArrayList<>();

        assertEquals("Learn Spanish",addTest.getName());
        assertEquals("Eat Burger", addTest2.getName());

        assertNotEquals("Learn Italian",addTest.getName());
        assertNotEquals("Eat a sandwich",addTest2.getName());
    }

    @Test
    void getEvents() {
        this.events = new ArrayList<>();

        assertEquals("Eventlistname:" + addTest.getName() + ", " + Arrays.toString(this.events.toArray()), addTest.getEvents().toString());
        assertEquals("Eventlistname:" + addTest2.getName() + ", " + Arrays.toString(this.events.toArray()), addTest2.getEvents().toString());
    }

    @Test
    void getEventByName() {
        assertEquals(addTest.getEventByName("Meet friends"), eventNameTest1.getEventName());
        assertEquals(addTest2.getEventByName("Eat Burger"), eventNameTest2.getEventName());
    }

}