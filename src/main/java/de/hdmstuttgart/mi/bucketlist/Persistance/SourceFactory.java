package de.hdmstuttgart.mi.bucketlist.Persistance;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SourceFactory {

    // initialize Logger
    private static final Logger log = LogManager.getLogger(SourceFactory.class);

    public Saver getSource (Sourcetype sourcetype){
      if(sourcetype == Sourcetype.FILESOURCE){
          return new FileSource();
      }else if(sourcetype == Sourcetype.DATABASESOURCE){
          return new DatabaseSource();
      }else{
          log.error("No matching sourcetype");
      }
      return null;
    }
}
