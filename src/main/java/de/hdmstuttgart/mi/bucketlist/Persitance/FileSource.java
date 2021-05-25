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

public class FileSource implements Saver{




    /**
     * writes Eventlist to File
     * File has the name of the eventlist
     * @param saveable
     */
    @Override
    public void writeToSrouce(Saveable saveable) {

        String filename = saveable.getName();
        String filepath = "Data/" + filename;
        File outputfile = new File(filepath);
        saveable.toJson(outputfile);

        // saveable.toJson(new File(saveable.getName()));
    }

    @Override
    public void readFromSource(String source, Saveable saveable) {

         saveable.fromJson(new File(source));

    }

    public List<Path> walkDirectory(){
        //todo understand it completly

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

    public static void main(String[] args) {
        FileSource s = new FileSource();
        List l = s.walkDirectory();
        System.out.println(l.toString());
    }
}
