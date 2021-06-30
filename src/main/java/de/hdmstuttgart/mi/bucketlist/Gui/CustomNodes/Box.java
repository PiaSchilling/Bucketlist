package de.hdmstuttgart.mi.bucketlist.Gui.CustomNodes;

/**
 * this Interface has to be implemented by the eventlistBoxes so the listsController can store both of the box types (expired and normal) in one
 * list and still have access to the boxControllers
 */
public interface Box {

    Object getController();
}
