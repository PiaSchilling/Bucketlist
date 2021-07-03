package de.hdmstuttgart.mi.bucketlist.ModelController;

import de.hdmstuttgart.mi.bucketlist.Exceptions.ElementAlreadyExistsException;
import de.hdmstuttgart.mi.bucketlist.Model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticsManagerTest {


    private StatisticsManager statisticsManagerTest;

    StatisticsManagerTest() {
    }

    @BeforeEach
    public void prepareDummyFiles(){
        ListManager listManager = new ListManager();
        statisticsManagerTest= new StatisticsManager(listManager);

        try{
            listManager.createEventlist("TestList1");
            listManager.createEventlist("TestList2", 16, 6, 2021);
            listManager.createEventlist("TestList3",5, 5,2055);

            listManager.addEventToList("TestEvent1_1", Category.FAMILY, "TestList1");
            listManager.addEventToList("TestEvent2_1", Category.CULINARY, "TestList1");

            listManager.addEventToList("TestEvent1_2", Category.RELATIONSHIP, "TestList2");
            listManager.addEventToList("TestEvent2_2", Category.CULTURE, "TestList2");

            listManager.addEventToList("TestEvent1_3", Category.TRAVEL, "TestList3");
            listManager.addEventToList("TestEvent2_3", Category.SPORT, "TestList3");

            listManager.completeEvent("TestEvent1_1", "TestList1", "...", "The quick brown fox", 12, 3, 2020);
            listManager.completeEvent("TestEvent2_1", "TestList1", "...", "The quick yellow fox", 12, 3, 2020);

            listManager.completeEvent("TestEvent1_2", "TestList2", "...", "The quick ornage fox", 12, 3, 2020);
        }catch (ElementAlreadyExistsException e){
            e.printStackTrace();
        }

        //listManager.load();
    }



    @Test
    @DisplayName("testCountListsAsString() should work")
    void testCountListsAsString() {
        assertEquals("3", statisticsManagerTest.countListsAsString());
    }





    @Test
    @DisplayName("testCountEventsAsString() should work")
    void testCountEventsAsString(){
        assertEquals("6", statisticsManagerTest.countEventsAString());
    }

    @Test
    @DisplayName("testCountEvents() should work")
    void testCountEvents(){
        assertEquals(6, statisticsManagerTest.countEvents());
    }




    @Test
    @DisplayName("testCountCompletedEvents() should work")
    void testCountCompletedEvents() {
        assertEquals("3", statisticsManagerTest.countCompletedEventsAsString());
    }





    @Test
    @DisplayName("testCountEventsPerList() should work")
    void testCountEventsPerList() {
        assertEquals(2,statisticsManagerTest.countEventsPerList("TestList1"));
        assertEquals(2,statisticsManagerTest.countEventsPerList("TestList2"));
        assertEquals(2,statisticsManagerTest.countEventsPerList("TestList3"));

    }




    @Test
    @DisplayName("testcountCompletedEventsPerList() should work")
    void testcountCompletedEventsPerList() {
        assertEquals(2, statisticsManagerTest.countCompletedEventsPerList("TestList1"));
        assertEquals(1, statisticsManagerTest.countCompletedEventsPerList("TestList2"));
        assertEquals(0, statisticsManagerTest.countCompletedEventsPerList("TestList3"));
    }




    @Test
    @DisplayName("testCalculatePercentageCompletedEventsPerListAsString() should work")
    void testCalculatePercentageCompletedEventsPerListAsString() {
        // test when mulitiple or all events in a list are completed
        assertEquals("100 %" , statisticsManagerTest.calculatePercentageCompletedEventsPerListAsString("TestList1"));
        assertEquals("50 %" , statisticsManagerTest.calculatePercentageCompletedEventsPerListAsString("TestList2"));
        // test when theres no event completed
        assertEquals("0 %", statisticsManagerTest.calculatePercentageCompletedEventsPerListAsString("TestList3"));
    }

    @Test
    @DisplayName("testCalculatePercentageCompletedEventsPerList() should work")
    void testCalculatePercentageCompletedEventsPerList(){
        // test when mulitiple or all events in a list are completed
        assertEquals(1.00 , statisticsManagerTest.calculatePercentageCompletedEventsPerList("TestList1"));
        assertEquals(0.50 , statisticsManagerTest.calculatePercentageCompletedEventsPerList("TestList2"));
        // test when theres no event completed
        assertEquals(0.0, statisticsManagerTest.calculatePercentageCompletedEventsPerList("TestList3"));
    }





    @Test
    @DisplayName("testDaysLeftAsString() should work")
    void testDaysLeftAsString() {
        //test TestList2
        GregorianCalendar today= new GregorianCalendar();
        GregorianCalendar testDate1= new GregorianCalendar(2021, 6-1, 16+1);
        long difference2;
        int testDays2=0;
        difference2 = testDate1.getTimeInMillis() - today.getTimeInMillis();
        testDays2 = (int) (difference2/(1000*60*60*24));
        assertEquals(String.valueOf(testDays2) , statisticsManagerTest.daysLeftAsString("TestList2"));

        //test TestList3
        GregorianCalendar testDate2= new GregorianCalendar(2055, 5-1, 5+1);
        long difference3;
        int testDays3=0;
        difference3 = testDate2.getTimeInMillis() - today.getTimeInMillis();
        testDays3 = (int) (difference3/(1000*60*60*24));
        assertEquals(String.valueOf(testDays3) , statisticsManagerTest.daysLeftAsString("TestList3"));
    }

    @Test
    @DisplayName("testDaysLeft() should work")
    void testDaysLeft() {
        //test TestList2
        GregorianCalendar today= new GregorianCalendar();
        GregorianCalendar testDate1= new GregorianCalendar(2021, 6-1, 16+1);
        long difference2;
        int testDays2=0;
        difference2 = testDate1.getTimeInMillis() - today.getTimeInMillis();
        testDays2 = (int) (difference2/(1000*60*60*24));
        assertEquals(testDays2 , statisticsManagerTest.daysLeftAsInt("TestList2"));

        //test TestList3
        GregorianCalendar testDate2= new GregorianCalendar(2055, 5-1, 5+1);
        long difference3;
        int testDays3=0;
        difference3 = testDate2.getTimeInMillis() - today.getTimeInMillis();
        testDays3 = (int) (difference3/(1000*60*60*24));
        assertEquals(testDays3 , statisticsManagerTest.daysLeftAsInt("TestList3"));
    }
}