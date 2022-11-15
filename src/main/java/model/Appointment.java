package model;
import helper.SQLUtil;
import helper.TimeUtil;

import java.time.LocalDateTime;
import java.time.Month;

public class Appointment{
    /**
     * Appointment properties
     */
    public int id;
    public String title;
    public String description;
    public String location;
    public String type;
    public LocalDateTime start;
    public LocalDateTime end;
    public LocalDateTime create_date;
    public String created_by;
    public LocalDateTime last_update;
    public String last_update_by;
    public int customer_id;
    public int user_id;
    public int contact_id;
    public String contact;
    public String customer;
    public Month month;

    /**
     * Default appointment constructor, used to initialize new appointment form
     */
    public Appointment(){
        this.start = TimeUtil.localNow().plusMinutes(1);
        this.end = TimeUtil.localNow().plusMinutes(2);
    }

    /**
     * Primary appointment constructor, used to extract from and save appointments to the database
     *
     * @param id                Appointment ID
     * @param title             Appointment Title
     * @param description       Appointment Description
     * @param location          Appointment Location
     * @param type              Appointment Type
     * @param start             Appointment Start Time
     * @param end               Appointment End Time
     * @param create_date       Appointment Created On
     * @param created_by        Appointment Created By
     * @param last_update       Appointment Last Updated On
     * @param last_update_by    Appointment Last Updated By
     * @param customer_id       Appointment Customer ID
     * @param user_id           Appointment User ID
     * @param contact_id        Appointment Contact ID
     */
    public Appointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime create_date,
                       String created_by, LocalDateTime last_update, String last_update_by, int customer_id, int user_id, int contact_id){
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.create_date = create_date;
        this.created_by = created_by;
        this.last_update = last_update;
        this.last_update_by = last_update_by;
        this.customer_id = customer_id;
        this.user_id = user_id;
        this.contact_id = contact_id;
        this.contact = SQLUtil.getContact(this.contact_id);
        this.customer = SQLUtil.getCustomer(this.customer_id);
        this.month = start.getMonth();
    }

    public Appointment utcToLocal(){
        return new Appointment(id, title, description, location, type, TimeUtil.utcToLocal(start), TimeUtil.utcToLocal(end), TimeUtil.utcToLocal(create_date),
                created_by, TimeUtil.utcToLocal(last_update), last_update_by, customer_id, user_id, contact_id);
    }

    /**
     * Appointment getter methods, setter methods excluded
     * getCustomer_id, getUser_id, getContact_id kept for posterity
     */
    public int getId(){ return this.id; }
    public String getTitle(){ return this.title; }
    public String getDescription(){ return this.description; }
    public String getLocation(){ return this.location; }
    public String getType(){ return this.type; }
    public LocalDateTime getStart(){ return this.start; }
    public LocalDateTime getEnd(){ return this.end; }
    public LocalDateTime getCreate_date(){ return this.create_date; }
    public String getCreated_by(){ return this.created_by; }
    public LocalDateTime getLast_update(){ return this.last_update; }
    public String getLast_update_by(){ return this.last_update_by; }
    public int getCustomer_id(){ return this.customer_id; }
    public int getUser_id(){ return this.user_id; }
    public int getContact_id(){ return this.contact_id; }
    public String getContact(){ return this.contact; }
    public String getCustomer(){ return this.customer; }
    public Month getMonth(){ return this.month; }
}