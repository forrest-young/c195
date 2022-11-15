package model;

public class Division {
    /**
     * Division properties
     */
    public int divId;
    public String div;
    public int ctryId;

    /**
     * Primary division constructor, used to extract from and save customers to the database
     *
     * @param divId     Division ID
     * @param div       Division Name
     * @param ctryId    Country ID
     */
    public Division(int divId, String div, int ctryId){
        this.divId = divId;
        this.div = div;
        this.ctryId = ctryId;
    }

    /**
     * Division getter methods, setter methods excluded
     */
    public int getDivId(){
        return this.divId;
    }
    public String getDiv(){
        return this.div;
    }
    public int getCtryId(){
        return this.ctryId;
    }
}