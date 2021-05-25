package de.hdmstuttgart.mi.bucketlist.Persitance;

public class SourceFactory {

    public Saver getSource (Sourcetype sourcetype){
      if(sourcetype == Sourcetype.FILESOURCE){
          return new FileSource();
      }else if(sourcetype == Sourcetype.DATABASESOURCE){
          return new DatabaseSource();
      }else{
          System.out.println("No matching sourcetype"); //todo log right here
      }
      return null;
    }
}
