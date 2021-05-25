package de.hdmstuttgart.mi.bucketlist.Persitance;

public class Repository {

    private Saver saver;

    /**
     * uses the Factory to create a saver based on the parameters sourcetype
     * @param sourcetype the type of source you want the saver to be
     */
    public Repository(Sourcetype sourcetype){
        SourceFactory sourceFactory = new SourceFactory();
        this.saver = sourceFactory.getSource(sourcetype);
    }

    public void writeSaveable(Saveable saveable){
        this.saver.writeToSrouce(saveable);
    }

    public void loadSaveable(String source, Saveable saveable){
          this.saver.readFromSource(source,saveable);
    }
}
