package de.hdmstuttgart.mi.bucketlist.ModelController;

import de.hdmstuttgart.mi.bucketlist.Model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * makes it possible to display the events sorted by Category
 * only works with a copy of the Eventlists, its not possible to manipulate the original List here (just a view)
 */
public class CategoryManager {

    private final ListManager listManager;

    private final HashMap<Category,Categorylist> categoryListHashMap = new HashMap<>();


    public CategoryManager(ListManager listManager){
        this.listManager = listManager;

        this.categoryListHashMap.put(Category.SKILLS, new Categorylist(Category.SKILLS));
        this.categoryListHashMap.put(Category.TRAVEL,new Categorylist(Category.TRAVEL));
        this.categoryListHashMap.put(Category.CULTURE,new Categorylist(Category.CULTURE));
        this.categoryListHashMap.put(Category.SHOPPING,new Categorylist(Category.SHOPPING));
        this.categoryListHashMap.put(Category.LIFEGOALS,new Categorylist(Category.LIFEGOALS));
        this.categoryListHashMap.put(Category.CULINARY,new Categorylist(Category.CULINARY));
        this.categoryListHashMap.put(Category.EDUCATION,new Categorylist(Category.EDUCATION));
        this.categoryListHashMap.put(Category.SPORT,new Categorylist(Category.SPORT));
        this.categoryListHashMap.put(Category.HOBBY,new Categorylist(Category.HOBBY));
        this.categoryListHashMap.put(Category.FAMILY,new Categorylist(Category.FAMILY));
        this.categoryListHashMap.put(Category.RELATIONSHIP,new Categorylist(Category.RELATIONSHIP));
        this.categoryListHashMap.put(Category.FRIENDS,new Categorylist(Category.FRIENDS));
        this.categoryListHashMap.put(Category.NO_CATEGORY,new Categorylist(Category.NO_CATEGORY));
    }

    /**
     * get the filled CategoryLists
     * Loops through every Eventlist and collects the events to the matching eventlists
     * @return -- a Map of all CategoryLists which contain the matching events
     */
    public HashMap<Category, Categorylist> getFilledCatgeoryLists(){

        //loop through the hashmap and get every categorylist
        for(Categorylist c: this.categoryListHashMap.values()){

            //temp attributes to shortly save the filtered lists
            ArrayList<Event> temp;
            ArrayList<Event> filteredList = new ArrayList<>();

            //loop through all the eventlists
            for (int i = 0; i < this.listManager.getEventlists().size(); i++) {

                //get the Arraylist of events of the current eventlist
                Eventlist tempEventlist = this.listManager.getEventlists().get(i);

                //get all Events with the matching Category and collect them into a temp list
                temp = (ArrayList<Event>) tempEventlist.getEvents()
                        .stream()
                        .filter(event -> event.getEventCategory() == c.getListCategory())
                        .collect(Collectors.toList());

                // add the temp list to the list which might already contain events with the same catgeory from another eventlist
                filteredList.addAll(temp);
            }

            //fill the arraylist of the categorylist with the filtered list
            c.fill(filteredList);
        }

        //return the filled list
        return this.categoryListHashMap;
    }
}
