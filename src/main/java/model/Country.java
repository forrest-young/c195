package model;

public class Country {
    /**
     * Country properties
     */
    public int ctryId;
    public String ctry;

    /**
     * Primary country constructor, used to extract from and save customers to the database
     *
     * @param ctryId    Country ID
     * @param ctry      Country Name
     */
    public Country(int ctryId, String ctry){
        this.ctryId = ctryId;
        this.ctry = ctry;
    }

    /**
     * Country getter methods, setter methods excluded
     * getCtryId kept for posterity
     */
    public int getCtryId(){
        return this.ctryId;
    }
    public String getCtry(){
        return this.ctry;
    }
}