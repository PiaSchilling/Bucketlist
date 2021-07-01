package de.hdmstuttgart.mi.bucketlist.Model;

import de.hdmstuttgart.mi.bucketlist.View.Listener;

/**
 * similar to Observable Interface (Observable is deprecated this is why we made our own)
 * Needs to be implemented by classes which should be observable by gui classes (notify them about changes of a model state)
 */
public interface Narrator {

    void addListener(Listener listener);

    void informListeners();
}
