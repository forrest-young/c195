package model;

public class User {
    /**
     * User properties
     */
    public int id;
    public String name;

    /**
     * Primary user constructor, used to extract from and save appointments to the database
     *
     * @param id    User ID
     * @param name  User Name
     */
    public User(int id, String name){
        this.id = id;
        this.name = name;
    }

    /**
     * User getter methods, setter methods excluded
     */
    public int getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
}