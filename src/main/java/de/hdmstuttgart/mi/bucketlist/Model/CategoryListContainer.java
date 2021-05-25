package de.hdmstuttgart.mi.bucketlist.Model;

import java.util.HashMap;

public class CategoryListContainer {

    private HashMap<Category,Categorylist> categoryListHashMap = new HashMap<>();

    public CategoryListContainer(){
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
    }

    public HashMap<Category, Categorylist> getCategoryListHashMap() {
        return this.categoryListHashMap;
    }
}
