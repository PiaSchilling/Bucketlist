package de.hdmstuttgart.mi.bucketlist.Persistance;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import de.hdmstuttgart.mi.bucketlist.Exceptions.EmptyDirectoryException;
import org.apache.commons.io.FileUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Read/ Write form/to Files
 * only used by a repository
 */
public class FileSource implements Saver{

    // initialize Logger
    private static final Logger log = LogManager.getLogger(FileSource.class);

    /**
     * writes Savables to Files
     * File has the name of the saveable (only makes sense if the saveable is a list)
     * @param saveable -- saveable which should be saved
     */
    @Override
    public void writeToSource(Saveable saveable) {
        String filename = saveable.getName();
        String filepath = "Data/" + filename;
        File outputfile = new File(filepath);
        saveable.toJson(outputfile);
    }

    /**
     * cleans the directory and checks if directory exists (if not it will be created)
     * should be used before saving (get rid of outdated files)
     */
    @Override
    public void updateSource() {
        log.debug("updateSource method started");
        try {
            FileUtils.cleanDirectory(new File("Data"));
            log.debug("directory cleaned");
        } catch (IOException ioException) {
            log.error(ioException.getMessage());
        }catch (IllegalArgumentException illegalArgumentException){
            //if Data Directory doesnt exist, it will be created
            File dataDirectory = new File("Data");
            boolean isCreated = dataDirectory.mkdir();
            log.info("Data directory does not exist. Data directory created: " + isCreated);
        }
    }

    /**
     * loops through the list of files in a Directory, creates objects from this data and stores them in a List
     * calls the Method fromJson to fill the Saveable with the data loaded from the fileSource
     * @param saveables -- empty List of saveables which is going to be filled by this method
     * @param saveable -- a concrete Saveable, so the right fromJson Method is called (Polymorphism)
     */
    @Override
    public void readFromSource(ArrayList<Saveable> saveables, Saveable saveable) {
        log.debug("readFromSource method started");
        //List of all Files in the Directory
        File[] listOfFiles = new File[0];

        try {
            listOfFiles = listDirectory("Data");
        } catch (EmptyDirectoryException e) {
            log.error(e.getMessage() + "no Data will be loaded");
        }

        for (int i = 0; i < listOfFiles.length; i++) {
            saveables.add(saveable.fromJson(new File(listOfFiles[i].toString())));
        }
    }


    /**
     * * lists all Files in the given directory
     * @param path -- the path to the directory which should be listed
     * @return -- list of filenames of the directory
     * @throws EmptyDirectoryException when the directory is empty or not found
     */
    public File[] listDirectory(String path) throws EmptyDirectoryException {
        File directory = new File(path);
        File[] files = directory.listFiles();

        if(files == null || files.length == 0){
            throw new EmptyDirectoryException("The Directory " + path + " is empty or doesn't exist");
        }else{
            return files;
        }
    }
}
