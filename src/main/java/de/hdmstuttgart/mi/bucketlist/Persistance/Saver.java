package de.hdmstuttgart.mi.bucketlist.Persistance;

import java.util.ArrayList;

public interface Saver {

    void writeToSource(Saveable saveable);

    void readFromSource(ArrayList<Saveable> saveables, Saveable saveable);

    void updateSource();
}
