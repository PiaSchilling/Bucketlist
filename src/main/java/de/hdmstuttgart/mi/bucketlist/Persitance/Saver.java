package de.hdmstuttgart.mi.bucketlist.Persitance;

import java.util.ArrayList;

public interface Saver {

    void writeToSource(Saveable saveable);

    void readFromSource(ArrayList<Saveable> saveables, Saveable saveable);

    void updateSource();
}
