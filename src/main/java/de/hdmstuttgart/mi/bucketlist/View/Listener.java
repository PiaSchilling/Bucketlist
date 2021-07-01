package de.hdmstuttgart.mi.bucketlist.View;

/**
 * this interface needs to be implemented by all controller classes which need to be notified when a model-state changed
 */
public interface Listener {

    void update();
}
