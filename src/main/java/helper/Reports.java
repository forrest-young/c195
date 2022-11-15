package helper;
import model.Appointment;
import model.Customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class Reports {
    /**
     * Lists of each object added/updated/deleted from the database
     */
    public static ObservableList<Object> custAdded = FXCollections.observableArrayList();
    public static ObservableList<Object> custUpdated = FXCollections.observableArrayList();
    public static ObservableList<Object> custDeleted = FXCollections.observableArrayList();

    public static ObservableList<Object> apptAdded = FXCollections.observableArrayList();
    public static ObservableList<Object> apptUpdated = FXCollections.observableArrayList();
    public static ObservableList<Object> apptDeleted = FXCollections.observableArrayList();

    /**
     * Report setter methods
     */
    public static void addCustAdded(Customer cust){ custAdded.add(cust); }
    public static void addCustUpdated(Customer cust){ custUpdated.add(cust); }
    public static void addCustDeleted(Customer cust){ custDeleted.add(cust); }
    public static void addApptAdded(Appointment appt){ apptAdded.add(appt); }
    public static void addApptUpdated(Appointment appt){ apptUpdated.add(appt); }
    public static void addApptDeleted(Appointment appt){ apptDeleted.add(appt); }

    /**
     * Report getter methods, setter methods excluded
     */
    public static ObservableList<Object> getCustAdded(){ return custAdded; }
    public static ObservableList<Object> getCustUpdated(){ return custUpdated; }
    public static ObservableList<Object> getCustDeleted(){ return custDeleted; }
    public static ObservableList<Object> getApptAdded(){ return apptAdded; }
    public static ObservableList<Object> getApptUpdated(){ return apptUpdated; }
    public static ObservableList<Object> getApptDeleted(){ return apptDeleted; }
}