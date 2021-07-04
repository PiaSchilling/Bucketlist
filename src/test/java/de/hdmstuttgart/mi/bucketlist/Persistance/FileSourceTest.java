package de.hdmstuttgart.mi.bucketlist.Persistance;

import de.hdmstuttgart.mi.bucketlist.Exceptions.ElementAlreadyExistsException;
import de.hdmstuttgart.mi.bucketlist.Exceptions.EmptyDirectoryException;
import de.hdmstuttgart.mi.bucketlist.Model.Category;
import de.hdmstuttgart.mi.bucketlist.Model.Eventlist;
import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * not good: tests work only because they are in a particular order (tests should not depend on other tests)
 * test maybe should not touch the real Data directory but work in a own Test-directory (design problem of the fileSource because there the directory name it coded hard)
 */
class FileSourceTest {


    private final FileSource fileSource = new FileSource();
    private static final Logger log = LogManager.getLogger(FileSourceTest.class);

    @BeforeAll
    public static void prepareDummyDirectory(){
        ListManager listManager = new ListManager();

        try{
            listManager.createEventlist("Testlist1");
            listManager.createEventlist("Testlist2");
            listManager.addEventToList("TestEvent", Category.SKILLS,"Testlist");
        }catch (ElementAlreadyExistsException e){
            e.printStackTrace();
        }
        listManager.save();
    }

    @AfterAll
    public static void cleanUp(){
        FileSource fileSource = new FileSource();
        fileSource.updateSource();
        log.error("Cleaned up");
    }



    @Test
    void writeToSource() {
        log.debug("write source test started");
        Eventlist eventlist = new Eventlist("writeToSourceTest1");
        Eventlist eventlist1 = new Eventlist("writeToSourceTest2");

        fileSource.writeToSource(eventlist);
        fileSource.writeToSource(eventlist1);

        File directory = new File("Data");
        File[] files = directory.listFiles();

        assertTrue(Arrays.stream(files)
                .anyMatch(file -> file.getName().equals("writeToSourceTest1.json")));
        assertTrue(Arrays.stream(files)
                .anyMatch(file -> file.getName().equals("writeToSourceTest2.json")));
        log.debug("writeSourceTest ended");
    }

    @DisplayName("After update direcotry should be empty")
    @Test
    void updateSource() {
        fileSource.updateSource();
        File directory = new File("Data");
        File[] files = directory.listFiles();
        assertEquals(0,files.length);
    }


    @Test
    void readFromSource() {
        log.debug("Read source test started");
        ArrayList<Saveable> saveables = new ArrayList<>();
        Eventlist eventlist = new Eventlist();

        fileSource.readFromSource(saveables,eventlist);

        assertEquals(2,saveables.size());
        assertEquals("Testlist2",saveables.get(0).getName());
        assertEquals("Testlist1",saveables.get(1).getName());
        log.debug("read source test ended");
    }

    @Test
    void listDirectory() {
        try {
            assertEquals(2,fileSource.listDirectory("Data").length);
            assertEquals("Data/Testlist2.json",fileSource.listDirectory("Data")[0].toString());
            assertEquals("Data/Testlist1.json",fileSource.listDirectory("Data")[1].toString());
        } catch (EmptyDirectoryException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("Invalid directory should throw EmptyDirectoryException")
    @Test
    void shouldThrowException (){
        fileSource.updateSource();
        Exception exception = assertThrows(EmptyDirectoryException.class, ()-> fileSource.listDirectory("Data"));
        Exception exception1 = assertThrows(EmptyDirectoryException.class, ()-> fileSource.listDirectory("WrongDirecotry"));
    }
}