package de.hdmstuttgart.mi.bucketlist.Persitance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        String filepath = "Data/" + filename;
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
            FileUtils.cleanDirectory(new File("Data"));
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
        //List of all Files in the Directory "Data"
        List<Path> listOfFiles = walkDirectory();

        for (int i = 0; i < listOfFiles.size(); i++) {
            saveables.add(saveable.fromJson(new File(listOfFiles.get(i).toString())));
        }
    }


    /**
     * walks through the whole directory "Data" and writes all file names to a List
     * @return -- list of filenames of the data Directory
     */
    public List<Path> walkDirectory(){

        Path path = Paths.get("Data");

        List<Path> result = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk.filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }catch (IOException e) {
            System.out.println("IO ");
        }
        return result;
    }

}
