package core;

/**
 * predefined Categories that can be chosen when a new Event is added
 * Every Category has a short description about what kind of event might fit to it
 */
public enum Category {

    SKILLS("Things you want to learn"),
    TRAVEL("Places you want to visit"),
    CULTURE("Cultural events/places you want to visit"),
    SHOPPING("Things you want to buy"),
    LIFEGOALS("Goals you want to achieve in your life"),
    CULINARY("Things you want to eat/drink"),
    EDUCATION("Knowledge you want to earn"),
    SPORT("Sporty things you want to do"),
    HOBBY("Hobbies you want to try"),
    FAMILY("Things you want to do with you family"),
    RELATIONSHIP("Things you want to do with your partner"),
    FRIENDS("Things you want to do with your friends");

    String description;

    Category(String description){
        this.description = description;
    }

}
