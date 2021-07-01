package de.hdmstuttgart.mi.bucketlist.ModelController;

import de.hdmstuttgart.mi.bucketlist.Exceptions.ElementAlreadyExistsException;
import de.hdmstuttgart.mi.bucketlist.Model.Category;
import de.hdmstuttgart.mi.bucketlist.ModelController.CategoryManager;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryManagerTest {
    private CategoryManager categoryManager;


    @BeforeEach
    public void prepareDummyFiles(){
        ListManager listManager = new ListManager();
        categoryManager = new CategoryManager(listManager);

        try{
            listManager.createEventlist("TestCat1");
            listManager.createEventlist("TestCat2");
            listManager.createEventlist("TestCat3",4,6,2021);

            listManager.addEventToList("SKILLS", Category.SKILLS,"TestCat1");
            listManager.addEventToList("TRAVEL",Category.TRAVEL,"TestCat1");
            listManager.addEventToList("CULTURE",Category.CULTURE,"TestCat1");
            listManager.addEventToList("SHOPPING",Category.SHOPPING,"TestCat1");

            listManager.addEventToList("LIFEGOALS",Category.LIFEGOALS,"TestCat2");
            listManager.addEventToList("CULINARY",Category.CULINARY,"TestCat2");
            listManager.addEventToList("EDUCATION",Category.EDUCATION,"TestCat2");
            listManager.addEventToList("SPORT",Category.SPORT,"TestCat2");

            listManager.addEventToList("HOBBY",Category.HOBBY,"TestCat3");
            listManager.addEventToList("FAMILY",Category.FAMILY,"TestCat3");
            listManager.addEventToList("RELATIONSHIP",Category.RELATIONSHIP,"TestCat3");
            listManager.addEventToList("FRIENDS",Category.FRIENDS,"TestCat3");
        }catch (ElementAlreadyExistsException e){
            e.printStackTrace();
        }


    }

    @Test
    @DisplayName("testGetFilledCatgeoryLists should work")
    void testGetFilledCatgeoryLists() {
        ListManager compareListManager = new ListManager();
        CategoryManager compareCategoryManager= new CategoryManager(compareListManager);

        try{
            compareListManager.createEventlist("TestCat1");
            compareListManager.createEventlist("TestCat2");
            compareListManager.createEventlist("TestCat3",4,6,2021);

            compareListManager.addEventToList("SKILLS", Category.SKILLS,"TestCat1");
            compareListManager.addEventToList("TRAVEL",Category.TRAVEL,"TestCat1");
            compareListManager.addEventToList("CULTURE",Category.CULTURE,"TestCat1");
            compareListManager.addEventToList("SHOPPING",Category.SHOPPING,"TestCat1");

            compareListManager.addEventToList("LIFEGOALS",Category.LIFEGOALS,"TestCat2");
            compareListManager.addEventToList("CULINARY",Category.CULINARY,"TestCat2");
            compareListManager.addEventToList("EDUCATION",Category.EDUCATION,"TestCat2");
            compareListManager.addEventToList("SPORT",Category.SPORT,"TestCat2");

            compareListManager.addEventToList("HOBBY",Category.HOBBY,"TestCat3");
            compareListManager.addEventToList("FAMILY",Category.FAMILY,"TestCat3");
            compareListManager.addEventToList("RELATIONSHIP",Category.RELATIONSHIP,"TestCat3");
            compareListManager.addEventToList("FRIENDS",Category.FRIENDS,"TestCat3");

            assertEquals(compareCategoryManager.getFilledCatgeoryLists().toString(),categoryManager.getFilledCatgeoryLists().toString());
        }catch (ElementAlreadyExistsException e){
            e.printStackTrace();
        }


    }
}