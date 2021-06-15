package de.hdmstuttgart.mi.bucketlist.Model;

import de.hdmstuttgart.mi.bucketlist.Exceptions.ElementAlreadyExistsException;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EventTest {

    Event eventInfo = new Event();

    ListManager listTest1 = new ListManager();
    ListManager listTest2 = new ListManager();
    ListManager listTest3 = new ListManager();

    ListManager listCompare1 = new ListManager();
    ListManager listCompare2 = new ListManager();
    ListManager listCompare3 = new ListManager();

    Event eventNameTest1 = new Event("Meet Friends",Category.FRIENDS);
    Event eventNameTest2 = new Event("Eat Burger",Category.CULINARY);
    Event eventNameTest3 = new Event("Get Married",Category.RELATIONSHIP);

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
        }catch (ElementAlreadyExistsException e){
            e.printStackTrace();
        }

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
    void closingDate() {
        assertEquals("1.2.2020",eventInfo.closingDate(1,2,2020));
        assertEquals("5.8.2021",eventInfo.closingDate(5,8,2021));
        assertEquals("28.11.2022",eventInfo.closingDate(28,11,2022));

        assertNotEquals("20.3.2019",eventInfo.closingDate(1,2,2020));
        assertNotEquals("30.8.2025",eventInfo.closingDate(3,5,1996));
        assertNotEquals("12.5.2020",eventInfo.closingDate(15,12,2000));
    }

    @Test
    void getEventName() {
        assertEquals("Meet Friends",eventNameTest1.getEventName());
        assertEquals("Eat Burger", eventNameTest2.getEventName());
        assertEquals("Get Married",eventNameTest3.getEventName());
    }

    @Test
    void getEventCategory() {
        assertEquals(Category.FRIENDS, eventNameTest1.getEventCategory());
        assertEquals(Category.CULINARY, eventNameTest2.getEventCategory());
        assertEquals(Category.RELATIONSHIP, eventNameTest3.getEventCategory());
    }

    @Test
    void getIsCompleted() {
        eventNameTest1.completeEvent("...","We had fun.",25,5,2021);

        assertEquals(true, eventNameTest1.getIsCompleted());
        assertEquals(false, eventNameTest2.getIsCompleted());
        assertEquals(false, eventNameTest3.getIsCompleted());
    }
}