package de.hdmstuttgart.mi.bucketlist.ModelController;

import de.hdmstuttgart.mi.bucketlist.Exceptions.ElementAlreadyExistsException;
import de.hdmstuttgart.mi.bucketlist.Model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListManagerTest {

    ListManager listManager;


    @BeforeEach
    public void prepareDummyFiles(){
        this. listManager= new ListManager();
    }

    @Test
    @DisplayName("testCreateEventlist should work")
    void testCreateEventlist() {
        try{
            listManager.createEventlist("TestListA",18,8,2022);
            listManager.createEventlist("TestListB");

            ListManager compareListManager = new ListManager();
            compareListManager.createEventlist("TestListA",18,8,2022);
            compareListManager.createEventlist("TestListB");

            assertEquals(compareListManager.getEventlists().toString(), listManager.getEventlists().toString());
        }catch (ElementAlreadyExistsException e){
            e.printStackTrace();
        }

    }


    @Test
    @DisplayName("testDeleteEventlist should work")
    void testDeleteEventlist() {
        try{
            listManager.createEventlist("TestListA",18,8,2022);
            listManager.createEventlist("TestListB");
            listManager.deleteEventlist("TestListB");

            ListManager compareListManager = new ListManager();
            compareListManager.createEventlist("TestListA",18,8,2022);

            assertEquals(compareListManager.getEventlists().toString(), listManager.getEventlists().toString());
        }catch (ElementAlreadyExistsException e){
            e.printStackTrace();
        }

    }

    @Test
    @DisplayName("testAddEventToList should work")
    void testAddEventToList() {
        try{
            listManager.createEventlist("TestListA",18,8,2022);
            listManager.createEventlist("TestListB");
            listManager.addEventToList("TestEventA_A", Category.FAMILY,"TestListA");
            listManager.addEventToList("TestEventB_A", Category.SKILLS,"TestListA");


            ListManager compareListManager = new ListManager();
            compareListManager.createEventlist("TestListA",18,8,2022);
            compareListManager.createEventlist("TestListB");
            compareListManager.addEventToList("TestEventA_A", Category.FAMILY,"TestListA");
            compareListManager.addEventToList("TestEventB_A", Category.SKILLS,"TestListA");

            assertEquals(compareListManager.getEventlists().toString(), listManager.getEventlists().toString());


            listManager.addEventToList("TestEventA_B", Category.SKILLS,"TestListB");
            compareListManager.addEventToList("TestEventA_B", Category.SKILLS,"TestListB");
            assertEquals(compareListManager.getEventlists().toString(), listManager.getEventlists().toString());
        }catch (ElementAlreadyExistsException e){
            e.printStackTrace();
        }


    }

    @Test
    @DisplayName("testDeleteEvent should work")
    void testDeleteEvent() {

        try{
            listManager.createEventlist("TestListA",18,8,2022);
            listManager.createEventlist("TestListB");
            listManager.addEventToList("TestEventA_A", Category.FAMILY,"TestListA");
            listManager.addEventToList("TestEventB_A", Category.SKILLS,"TestListA");
            listManager.deleteEvent("TestEventA_A", "TestListA");
            listManager.deleteEvent("TestEventB_A", "TestListA");

            ListManager compareListManager = new ListManager();
            compareListManager.createEventlist("TestListA",18,8,2022);
            compareListManager.createEventlist("TestListB");

            assertEquals(compareListManager.getEventlists().toString(), listManager.getEventlists().toString());
        }catch (ElementAlreadyExistsException e){
            e.printStackTrace();
        }

    }

    @Test
    @DisplayName("testCompleteEvent should work")
    void testCompleteEvent() {

        try{
            listManager.createEventlist("TestListA",18,8,2022);
            listManager.createEventlist("TestListB");
            listManager.addEventToList("TestEventA_A", Category.FAMILY,"TestListA");
            listManager.addEventToList("TestEventB_A", Category.SKILLS,"TestListA");
            listManager.completeEvent("TestEventB_A", "TestListA", "...", "The slow purple fox", 5,12,2020);

            ListManager compareListManager = new ListManager();
            compareListManager.createEventlist("TestListA",18,8,2022);
            compareListManager.createEventlist("TestListB");
            compareListManager.addEventToList("TestEventA_A", Category.FAMILY,"TestListA");
            compareListManager.addEventToList("TestEventB_A", Category.SKILLS,"TestListA");
            compareListManager.completeEvent("TestEventB_A", "TestListA", "...", "The slow purple fox", 5,12,2020);

            assertEquals(compareListManager.getEventlists().toString(), listManager.getEventlists().toString());
        }catch (ElementAlreadyExistsException e){
            e.printStackTrace();
        }

    }

    /*
    @Test
    void testSave() {
    }

    @Test
    void testLoad() {
    }

    @Test
    void testGetEventlists() {
    }

     */
}