package de.hdmstuttgart.mi.bucketlist.Exceptions;

/**
 * should be thrown when there already exists a list/event with the same name
 */
public class ElementAlreadyExistsException extends Exception{

    public ElementAlreadyExistsException(String message){
        super(message);
    }
}
