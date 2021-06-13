package de.hdmstuttgart.mi.bucketlist.Model;

import de.hdmstuttgart.mi.bucketlist.Exceptions.ElementAlreadyExistsException;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CategorylistTest {

    private ArrayList<Event> events;

    private Categorylist listCategory;

    ListManager listTest1 = new ListManager();
    ListManager listTest2 = new ListManager();
    ListManager listTest3 = new ListManager();

    Categorylist compareCategorylist = new Categorylist(Category.FAMILY);
    Categorylist compareCategorylist2 = new Categorylist(Category.CULINARY);
    Categorylist compareCategorylist3 = new Categorylist(Category.TRAVEL);

    @BeforeEach
    public void prepareTestContent(){

        try{
            listTest1.createEventlist("TestList1");
            listTest2.createEventlist("TestList2");
            listTest3.createEventlist("TestList3");

            listTest1.addEventToList("Game night with family", Category.FAMILY,"TestList1");
            listTest1.addEventToList("Meet Grandma",Category.FAMILY,"TestList1");

            listTest2.addEventToList("Eat at subway",Category.CULINARY,"TestList2");
            listTest2.addEventToList("Eat sushi",Category.CULINARY,"TestList2");

            listTest3.addEventToList("Explore Asia",Category.TRAVEL,"TestList3");
            listTest3.addEventToList("Go to Paris",Category.TRAVEL,"TestList3");
        }catch (ElementAlreadyExistsException e){
            e.printStackTrace();
        }


    }

    @Test
    void fill() {
        assertEquals(listCategory, events);

        assertNotEquals(listCategory, compareCategorylist);
        assertNotEquals(listCategory, compareCategorylist2);
        assertNotEquals(listCategory, compareCategorylist3);

    }

    @Test
    void getListCategory() {
        assertEquals(Category.FAMILY.toString(),compareCategorylist.getListCategory().toString());
        assertEquals(Category.CULINARY.toString(),compareCategorylist2.getListCategory().toString());
        assertEquals(Category.TRAVEL.toString(),compareCategorylist3.getListCategory().toString());

        assertNotEquals(Category.FRIENDS.toString(),compareCategorylist.getListCategory().toString());
        assertNotEquals(Category.RELATIONSHIP.toString(),compareCategorylist2.getListCategory().toString());
        assertNotEquals(Category.CULTURE.toString(),compareCategorylist3.getListCategory().toString());

    }

    @Test
    void testToString() {
        this.events = new ArrayList<>();

        assertEquals("Categorylist:" + compareCategorylist.getListCategory() + ", " + Arrays.toString(this.events.toArray()),compareCategorylist.toString());
        assertEquals("Categorylist:" + compareCategorylist2.getListCategory() + ", " + Arrays.toString(this.events.toArray()),compareCategorylist2.toString());
        assertEquals("Categorylist:" + compareCategorylist3.getListCategory() + ", " + Arrays.toString(this.events.toArray()),compareCategorylist3.toString());

        assertNotEquals("Categorylist:" + compareCategorylist3.getListCategory() + Arrays.toString(this.events.toArray()),compareCategorylist3.toString());
        assertNotEquals("No Categorylist:" + compareCategorylist3.getListCategory() + ", " + Arrays.toString(this.events.toArray()),compareCategorylist3.toString());
        assertNotEquals("Cateschnurylisti:" + compareCategorylist3.getListCategory() + ", " + Arrays.toString(this.events.toArray()),compareCategorylist3.toString());

    }
}