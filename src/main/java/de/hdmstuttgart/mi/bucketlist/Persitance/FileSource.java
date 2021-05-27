package de.hdmstuttgart.mi.bucketlist.Persitance;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

/**
 * Read/ Write form/to Files
 * only used by a repository
 */
public class FileSource implements Saver{

    /**
     * writes Savables to Files
     * File has the name of the saveable (only makes sense if the saveable is a list)
     * @param saveable -- saveable which should be saved
     */
    @Override
    public void writeToSource(Saveable saveable) {
        String filename = saveable.getName();
        String filepath = "src/main/resources/data/" + filename;
        File outputfile = new File(filepath);
        saveable.toJson(outputfile);
    }

    /**
     * cleans the directory
     * should be used before saving (get rid of outdated files)
     */
    @Override
    public void updateSource() {
        try {
            FileUtils.cleanDirectory(new File("src/main/resources/data"));
        } catch (IOException e) {
            System.out.println("IO Exception"); //todo log here
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
        //List of all Files in the Directory
        File[] listOfFiles = listDirectory("src/main/resources/data");

        for (int i = 0; i < listOfFiles.length; i++) {
            saveables.add(saveable.fromJson(new File(listOfFiles[i].toString())));
        }
    }


    /**
     * lists all Files in the given Directory
     * @return -- list of filenames of the Directory
     */
    public File[] listDirectory(String path){
        File directory = new File(path);
        File[] files = directory.listFiles();

        return files;
    }


}
