package de.hdmstuttgart.mi.bucketlist.Persistance;

import java.util.ArrayList;

/**
 * needs to be implemented by all source classes
 */
public interface Saver {

    void writeToSource(Saveable saveable);

    void readFromSource(ArrayList<Saveable> saveables, Saveable saveable);

    void updateSource();
}
