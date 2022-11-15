package model;
import helper.SQLUtil;

public class Customer {
    /**
     * Customer properties
     */
    public int id;
    public String name;
    public String address;
    public String post;
    public String phone;
    public int divId;
    public String div;

    /**
     * Default customer constructor, used to initialize new customer form
     */
    public Customer(){
        this.divId = 1;
    }

    /**
     * Primary customer constructor, used to extract from and save customers to the database
     *
     * @param id        Customer ID
     * @param name      Customer Name
     * @param address   Customer Address
     * @param post      Customer Postal Code
     * @param phone     Customer Phone Number
     * @param divId     Customer Division ID
     */
    public Customer(int id, String name, String address, String post, String phone, int divId){
        this.id=id;
        this.name=name;
        this.address=address;
        this.post = post;
        this.phone = phone;
        this.divId = divId;
        this.div = getDiv();
    }

    /**
     * Customer getter methods, setter methods excluded
     */
    public int getId(){ return this.id; }
    public String getName(){ return this.name; }
    public String getAddress(){ return this.address; }
    public String getPost(){ return this.post; }
    public String getPhone(){ return this.phone; }
    public int getDivId(){ return this.divId; }
    public int getCtryId(){ return SQLUtil.getCtryId(this.divId); }
    public String getCtry(){ return SQLUtil.getCtry(this.divId); }
    public String getDiv(){ return SQLUtil.getDiv(this.divId); }
}