package de.hdmstuttgart.mi.bucketlist.Persitance;

public interface Saver {

    void writeToSrouce(Saveable saveable);


    void readFromSource(String source, Saveable saveable);
}
