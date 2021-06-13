package de.hdmstuttgart.mi.bucketlist.Persistance;

import de.hdmstuttgart.mi.bucketlist.Exceptions.ElementAlreadyExistsException;
import de.hdmstuttgart.mi.bucketlist.Model.Category;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//todo @pia some more test methods
class EventlistRepositoryTest {

    @BeforeAll
    static void prepareDummyFiles(){
        ListManager listManager = new ListManager();
        try{
            listManager.createEventlist("Testlist1",12,12,2021);
            listManager.createEventlist("Testlist2");
            listManager.addEventToList("Event1", Category.SKILLS,"Testlist1");
            listManager.addEventToList("Event1", Category.SKILLS,"Testlist2");
        }catch (ElementAlreadyExistsException e){
            e.printStackTrace();
        }
        listManager.completeEvent("Event1","Testlist1","url","desc",12,12,2021);
        listManager.save();
    }
    @Test
    void testLoadSaveable() {
        EventlistRepository eventlistRepository = new EventlistRepository(Sourcetype.FILESOURCE);
        ListManager listManagerToCompare = new ListManager();

        try{
            listManagerToCompare.createEventlist("Testlist2");
            listManagerToCompare.createEventlist("Testlist1",12,12,2021);
            listManagerToCompare.addEventToList("Event1", Category.SKILLS,"Testlist1");
            listManagerToCompare.addEventToList("Event1", Category.SKILLS,"Testlist2");
        }catch (ElementAlreadyExistsException e){
            e.printStackTrace();
        }

        listManagerToCompare.completeEvent("Event1","Testlist1","url","desc",12,12,2021);

        assertNotNull(eventlistRepository.loadSaveable());
        assertEquals(listManagerToCompare.getEventlists().toString(), eventlistRepository.loadSaveable().toString());
        assertEquals(listManagerToCompare.getEventlists().get(1).getExpiryDateGregorian(), eventlistRepository.loadSaveable().get(1).getExpiryDateGregorian());

    }

}