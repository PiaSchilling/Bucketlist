package de.hdmstuttgart.mi.bucketlist.Threads;

import de.hdmstuttgart.mi.bucketlist.ModelController.ListManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PersistenceRunnable implements Runnable{

    private final ListManager listManager;
    private final static Logger log = LogManager.getLogger(PersistenceRunnable.class);

    public PersistenceRunnable(ListManager listManager){
        this.listManager = listManager;
    }

    @Override
    public void run() {
        log.debug("PersistenceRunnable - Run method started");
        //only for testing
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.listManager.load();
        log.info("PersistenceRunnable - Run method ended");
    }
}
