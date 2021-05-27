package de.hdmstuttgart.mi.bucketlist.ModelController;

/**
 * gloabl controller to control the listManager
 */
public class ListController {

    private final ListManager listManager;

    public ListController(){
        this.listManager = new ListManager();
    }
}
