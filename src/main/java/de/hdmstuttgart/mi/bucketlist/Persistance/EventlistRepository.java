package de.hdmstuttgart.mi.bucketlist.Persistance;

import de.hdmstuttgart.mi.bucketlist.Model.Eventlist;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * mediates between the data store and the business logic
 * gets Objects from the business logic and hands them over to a source where its going to be saved
 * gets Data from the source and casts them in the right format so the business logic can work with it
 */
public class EventlistRepository {

    // initialize Logger
    private static final Logger log = LogManager.getLogger(EventlistRepository.class);

    final private Saver saver;

    /**
     * uses the Factory to create a saver based on the parameters sourcetype
     * @param sourcetype the type of source you want the saver to be
     */
    public EventlistRepository(Sourcetype sourcetype){
        SourceFactory sourceFactory = new SourceFactory();
        this.saver = sourceFactory.getSource(sourcetype);
    }

    /**
     * uses the saver to write to a source
     * @param eventlistsObservable -- list of eventlists which should be saved
     */
    public void writeSaveable(ObservableList<Eventlist> eventlistsObservable){
        log.debug("writeSaveable method started");

        ArrayList<Eventlist> eventlists = new ArrayList<>(eventlistsObservable);

        //prepares the source (old data is deleted)
        this.saver.updateSource();

        for (int i = 0; i < eventlists.size(); i++) {
            this.saver.writeToSource(eventlists.get(i));
        }
        log.debug("writeSaveable method ended");
    }

    /**
     * Casts the savables-list, which is filled by the source, to the concrete object type
     * @return -- a Filled Arraylist of Eventlists
     */
    public ArrayList<Eventlist> loadSaveable(){

        log.debug("loadSaveable method started");
        //stores the Saveables created by the readFromSource Method
        ArrayList<Saveable> saveables = new ArrayList<>();
        //stores the casted Eventlists
        ArrayList<Eventlist> eventlists = new ArrayList<>();
        //readFromSource Method needs this to know which Object should be created
        Saveable eventlist = new Eventlist();

        this.saver.readFromSource(saveables,eventlist);

        //cast the savable list to a eventlist list
        for (int i = 0; i < saveables.size(); i++) {
            try{
                eventlists.add((Eventlist) saveables.get(i));
            }catch (ClassCastException classCastException){
                log.error(classCastException.getMessage());
            }
        }
        log.debug("loadSaveable method ended");
        //return the list of eventlists
        return eventlists;
    }
}
