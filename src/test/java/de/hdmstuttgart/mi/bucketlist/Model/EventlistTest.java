package de.hdmstuttgart.mi.bucketlist.Model;

import de.hdmstuttgart.mi.bucketlist.Exceptions.ElementAlreadyExistsException;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EventlistTest {

    private ArrayList<Event> events;
    private String eventlistName;

    Eventlist addTest = new Eventlist("Learn Spanish");
    Eventlist addTest2 = new Eventlist("Eat Burger");
    Eventlist addTest3 = new Eventlist("Go shopping");

    Eventlist addTestCompare = new Eventlist("Learn Spanish");
    Eventlist addTest2Compare = new Eventlist("Eat Burger");
    Eventlist addTest3Compare = new Eventlist("Go shopping");

    @Test
    void addEvent() {
        try {
        addTest.addEvent("Learn Spanish",Category.EDUCATION);
        addTest.addEvent("Buy leather jacket",Category.SHOPPING);
        addTest2.addEvent("Learn Spanish",Category.EDUCATION);
        addTest2.addEvent("Buy leather jacket",Category.SHOPPING);
        }catch (ElementAlreadyExistsException e){
            e.printStackTrace();
        }

        assertEquals(addTest.getEvents().toString(),addTest2.getEvents().toString());
    }

    @Test
    void deleteEvent() {
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
        try {
        addTest.addEvent("Learn Spanish",Category.EDUCATION);
        addTest2.addEvent("Bake a cake",Category.HOBBY);
        addTest3.addEvent("Go shopping",Category.SHOPPING);

        addTestCompare.addEvent("Learn Spanish",Category.EDUCATION);
        addTest2Compare.addEvent("Bake a cake",Category.HOBBY);
        addTest3Compare.addEvent("Go shopping",Category.SHOPPING);

        addTest.completeEvent("Learn Spanish", "...", "Buenos Dias.",1,7,2020);
        addTest2.completeEvent("Bake a cake", "...", "Yummy.",15,6,2019);
        addTest3.completeEvent("Go shopping", "...", "I bought pants.",9,3,2018);

        addTestCompare.completeEvent("Learn Spanish", "...", "Buenos Dias.",1,7,2020);
        addTest2Compare.completeEvent("Bake a cake", "...", "Yummy.",15,6,2019);
        addTest3Compare.completeEvent("Go shopping", "...", "I bought pants.",9,3,2018);
        }catch (ElementAlreadyExistsException e){
            e.printStackTrace();
        }

        assertEquals(addTest.getEvents().toString(),addTestCompare.getEvents().toString());
        assertEquals(addTest2.getEvents().toString(),addTest2Compare.getEvents().toString());
        assertEquals(addTest3.getEvents().toString(),addTest3Compare.getEvents().toString());

        assertNotEquals(addTest.getEvents().toString(),addTest2Compare.getEvents().toString());
        assertNotEquals(addTest2.getEvents().toString(),addTest3Compare.getEvents().toString());
        assertNotEquals(addTest3.getEvents().toString(),addTestCompare.getEvents().toString());

    }

    @Test
    void getName() {
        assertEquals("Learn Spanish",addTest.getName());
        assertEquals("Eat Burger", addTest2.getName());

        assertNotEquals("Learn Italian",addTest.getName());
        assertNotEquals("Eat a sandwich",addTest2.getName());
    }

    @Test
    void getEvents()  {
        try{
            addTest.addEvent("TestEvent",Category.SKILLS);
            addTest2.addEvent("TestEvent",Category.SKILLS);
        }catch (ElementAlreadyExistsException e){
            e.printStackTrace();
        }

        assertEquals(addTest.getEvents().toString(),addTest2.getEvents().toString());

    }

    @Test
    void getEventByName() {
        try {
            addTest.addEvent("Test",Category.SKILLS);
        } catch (ElementAlreadyExistsException e) {
            e.printStackTrace();
        }

        assertEquals(addTest.getEventByName("Test"),addTest.getEvents().get(0));

    }

}