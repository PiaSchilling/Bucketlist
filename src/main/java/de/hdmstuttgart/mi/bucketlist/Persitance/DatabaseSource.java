package de.hdmstuttgart.mi.bucketlist.Persitance;

import java.io.File;

public class DatabaseSource implements Saver{

    @Override
    public void writeToSrouce(Saveable saveable) {
        saveable.toJson(new File("repotest db"));
    }

    @Override
    public void readFromSource(String source, Saveable saveable) {

    }

    //todo implement interface
}
