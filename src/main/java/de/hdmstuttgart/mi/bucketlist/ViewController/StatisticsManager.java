package de.hdmstuttgart.mi.bucketlist.ViewController;

import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;

/**
 * makes it possible to display statistics about the events
 * should not manipulate the original list, only use the getEventlists Method (returns a copy of the original list)
 */
public class StatisticsManager {

    private final ListManager listManager;

    /**
     * @param listManager -- the only existing ListManager
     */
    public StatisticsManager(ListManager listManager){
        this.listManager = listManager;
    }
}
