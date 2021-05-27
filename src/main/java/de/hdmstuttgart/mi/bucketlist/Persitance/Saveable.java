package de.hdmstuttgart.mi.bucketlist.Persitance;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.File;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY )
public interface Saveable {

    void toJson(File file);

    Saveable fromJson(File file);

    String getName();
}
