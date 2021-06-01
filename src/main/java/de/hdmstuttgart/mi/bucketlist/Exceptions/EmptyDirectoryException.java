package de.hdmstuttgart.mi.bucketlist.Exceptions;

/**
 * should be thrown when a directory is empty or doesnt exist
 */
public class EmptyDirectoryException extends Exception{

    public EmptyDirectoryException(String message){
        super(message);
    }
}
