package helper;
import model.Appointment;
import model.Country;
import model.Customer;
import model.Division;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class SQLUtil {
    /**
     * Method used to extract customer information from the database
     *
     * @return the extracted Customer list
     */
    public static ArrayList<Customer> extractCust() {
        ArrayList<Customer> custAL = new ArrayList<>();
        try {
            PreparedStatement ps = JDBC.con.prepareStatement(getCustsSQL());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String post = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divId = rs.getInt("Division_ID");

                Customer cust = new Customer(id, name, address, post, phone, divId);
                custAL.add(cust);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return custAL;
    }

    private static String getCustsSQL() { return "SELECT * FROM customers"; }

    /**
     * Method used to extract appointment information from the database
     *
     * @return the extracted Appointment list
     */
    public static ArrayList<Appointment> extractAppt() {
        ArrayList<Appointment> apptAL = new ArrayList<>();
        try {
            PreparedStatement ps = JDBC.con.prepareStatement(getApptsSQL());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DateTimeFormatter dtf = TimeUtil.dtf;

                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = LocalDateTime.parse(rs.getString("Start"), dtf);
                LocalDateTime end = LocalDateTime.parse(rs.getString("End"), dtf);
                LocalDateTime created = LocalDateTime.parse(rs.getString("Create_Date"), dtf);
                String creator = rs.getString("Created_By");
                LocalDateTime updt = LocalDateTime.parse(rs.getString("Last_Update"), dtf);
                String updtr = rs.getString("Last_Updated_By");
                int custId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contId = rs.getInt("Contact_ID");

                Appointment appt = new Appointment(id, title, description, location, type, start, end, created,
                        creator, updt, updtr, custId, userId, contId);
                apptAL.add(appt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apptAL;
    }

    private static String getApptsSQL() { return "SELECT * FROM appointments"; }

    /**
     * Method used to extract country information from the database
     *
     * @return the extracted Country list
     */
    public static ArrayList<Country> extractCtry() {
        ArrayList<Country> ctryAL = new ArrayList<>();
        try {
            PreparedStatement ps = JDBC.con.prepareStatement(getCtrysSQL());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ctryId = rs.getInt("Country_ID");
                String ctry = rs.getString("Country");

                Country country = new Country(ctryId, ctry);
                ctryAL.add(country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ctryAL;
    }

    private static String getCtrysSQL() { return "SELECT * FROM countries"; }

    /**
     * Method used to extract country name from division ID
     *
     * @param divId the ID to be parsed
     * @return the extracted name
     */
    public static String getCtry(int divId) {
        try {
            PreparedStatement ps = JDBC.con.prepareStatement(getCtrySQL(divId));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("Country");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getCtrySQL(int divId) { return "SELECT first_level_divisions.Division_ID, first_level_divisions.Country_ID, countries.Country_ID, countries.Country FROM first_level_divisions " +
            "INNER JOIN countries ON first_level_divisions.Country_ID=countries.Country_ID WHERE Division_ID=" + divId; }

    /**
     * Method used to extract country ID from country name
     *
     * @param ctry the name to be parsed
     * @return the extracted ID
     */
    public static int getCtryId(String ctry) {
        try {
            PreparedStatement ps = JDBC.con.prepareStatement(getCtryIdSQL(ctry));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("Country_ID");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static String getCtryIdSQL(String ctry) { return "SELECT Country_ID FROM countries WHERE Country='" + ctry + "'"; }

    /**
     * Method used to extract country ID from division ID
     *
     * @param divId the ID to be parsed
     * @return the extracted ID
     */
    public static int getCtryId(int divId) {
        try {
            PreparedStatement ps = JDBC.con.prepareStatement(getCtryIdSQL(divId));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("Country_ID");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    private static String getCtryIdSQL(int divId) { return "SELECT Country_ID FROM first_level_divisions WHERE Division_ID='" + divId + "'"; }

    /**
     * Method used to extract division information from the database
     *
     * @return the extracted Division list
     */
    public static ArrayList<Division> extractDiv() {
        ArrayList<Division> divAL = new ArrayList<>();
        try {
            PreparedStatement ps = JDBC.con.prepareStatement(getDivsSQL());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divId = rs.getInt("Division_ID");
                String div = rs.getString("Division");
                int ctryId = rs.getInt("Country_ID");

                Division division = new Division(divId, div, ctryId);
                divAL.add(division);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divAL;
    }

    private static String getDivsSQL() { return "SELECT * FROM first_level_divisions"; }

    /**
     * Method used to extract division from division ID
     *
     * @param divId the division ID to be parsed
     * @return the extracted division
     */
    public static String getDiv(int divId) {
        String s = "";
        try {
            PreparedStatement ps = JDBC.con.prepareStatement(getDivSQL(divId));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                s = rs.getString("Division");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    private static String getDivSQL(int divId) { return "SELECT Division FROM first_level_divisions WHERE Division_ID=" + divId; }

    /**
     * Method used to extract the next available customer ID
     *
     * @return the extracted ID
     */
    public static int getNextCustId() {
        int id = 1;
        ArrayList<Integer> al = new ArrayList<>();
        try {
            PreparedStatement ps = JDBC.con.prepareStatement(getNextCustIdSQL());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                al.add(rs.getInt("Customer_ID"));
            }
            al.sort(Comparator.naturalOrder());
            if (al.isEmpty()) return 1;
            else {
                for (int i = 1; i <= al.size(); i++) {
                    if (i != (al.get(i - 1))) return i;
                }
                return al.size() + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private static String getNextCustIdSQL() { return "SELECT Customer_ID FROM customers"; }

    /**
     * Method used to add a customer to the database
     *
     * @param id    Customer ID
     * @param name  Customer Name
     * @param addr  Customer Address
     * @param post  Customer Postal Code
     * @param phone Customer Phone Number
     * @param divId Customer Division ID
     * @return the added customer, to be sent to Reports
     */
    public static Customer addCustomer(int id, String name, String addr, String post, String phone, int divId) {
        try {
            PreparedStatement ps = JDBC.con.prepareStatement(getAddCustSQL());
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, addr);
            ps.setString(4, post);
            ps.setString(5, phone);
            ps.setTimestamp(6, Timestamp.valueOf(TimeUtil.utcNow()));
            ps.setString(7, JDBC.user.getName());
            ps.setTimestamp(8, Timestamp.valueOf(TimeUtil.utcNow()));
            ps.setString(9, JDBC.user.getName());
            ps.setInt(10, divId);
            ps.executeUpdate();

            return new Customer(id, name, addr, post, phone, divId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getAddCustSQL() { return "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, " +
            "Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; }

    /**
     * Method used to update an existing customer
     *
     * @param id    Customer ID
     * @param name  Customer Name
     * @param addr  Customer Address
     * @param post  Customer Postal Code
     * @param phone Customer Phone Number
     * @param divId Customer Division ID
     * @return the updated customer, to be sent to Reports
     */
    public static Customer updtCustomer(int id, String name, String addr, String post, String phone, int divId) {
        try {
            PreparedStatement ps = JDBC.con.prepareStatement(getUpdtCustSQL());
            ps.setString(1, name);
            ps.setString(2, addr);
            ps.setString(3, post);
            ps.setString(4, phone);
            ps.setTimestamp(5, Timestamp.valueOf(TimeUtil.utcNow()));
            ps.setString(6, JDBC.user.getName());
            ps.setInt(7, divId);
            ps.setInt(8, id);
            ps.executeUpdate();

            return new Customer(id, name, addr, post, phone, divId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getUpdtCustSQL() { return "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Last_Update=?, " +
            "Last_Updated_By=?, Division_ID=? WHERE Customer_ID=?"; }

    /**
     * Method used to delete a customer from the database
     *
     * @param cust the customer to delete
     * @return the deleted customer, to be sent to Reports
     */
    public static Customer delCustomer(Customer cust) {
        try {
            ArrayList<Appointment> apptAL = extractAppt();
            for (Appointment appt : apptAL){
                if (appt.getCustomer().equals(cust.getName())){
                    delAppt(appt);
                    Reports.addApptDeleted(appt);
                }
            }
            PreparedStatement ps = JDBC.con.prepareStatement(getDelCustSQL());
            ps.setInt(1, cust.getId());
            ps.executeUpdate();
            return cust;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getDelCustSQL() { return "DELETE FROM customers WHERE Customer_ID=?"; }

    /**
     * Method used to extract customer ID from customer name
     *
     * @param custName the name to parse
     * @return the extracted ID
     */
    public static int getCustomerId(String custName) {
        int i = 0;
        try {
            PreparedStatement ps = JDBC.con.prepareStatement(getCustIdSQL(custName));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                i = rs.getInt("Customer_ID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    private static String getCustIdSQL(String custName) { return "SELECT Customer_ID FROM customers WHERE Customer_Name='" + custName + "'"; }

    /**
     * Method used to extract customer name from customer ID
     *
     * @param custId the ID to parse
     * @return the extracted name
     */
    public static String getCustomer(int custId) {
        String s = "";
        try {
            PreparedStatement ps = JDBC.con.prepareStatement(getCustSQL(custId));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                s = rs.getString("Customer_Name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    private static String getCustSQL(int custId) { return "SELECT Customer_Name FROM customers WHERE Customer_ID=" + custId; }

    /**
     * Method used to extract the next available appointment ID
     *
     * @return the extracted appointment ID
     */
    public static int getNextApptId() {
        int id = 1;
        ArrayList<Integer> al = new ArrayList<>();
        try {
            PreparedStatement ps = JDBC.con.prepareStatement(getApptIdsSQL());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                al.add(rs.getInt("Appointment_ID"));
            }
            al.sort(Comparator.naturalOrder());
            if (al.isEmpty()) return 1;
            else {
                for (int i = 1; i <= al.size(); i++) {
                    if (i != (al.get(i - 1))) return i;
                }
                return al.size() + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private static String getApptIdsSQL() { return "SELECT Appointment_ID FROM appointments"; }

    /**
     * Method used to add an appointment to the database
     *
     * @param id            Appointment ID
     * @param title         Appointment Title
     * @param description   Appointment Description
     * @param location      Appointment Location
     * @param type          Appointment Type
     * @param start         Appointment Start Time
     * @param end           Appointment End Time
     * @param custId        Appointment for Customer ID
     * @param userId        Appointment from User ID
     * @param contactId     Appointment with Contact ID
     * @return the added appointment, to be sent to Reports
     */
    public static Appointment addAppt(int id, String title, String description, String location, String type, LocalDateTime start,
                                      LocalDateTime end, int custId, int userId, int contactId) {
        try {
            PreparedStatement ps = JDBC.con.prepareStatement(getAddApptSQL());
            ps.setInt(1, id);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setString(4, location);
            ps.setString(5, type);
            ps.setTimestamp(6, Timestamp.valueOf(TimeUtil.localToUtc(start)));
            ps.setTimestamp(7, Timestamp.valueOf(TimeUtil.localToUtc(end)));
            ps.setTimestamp(8, Timestamp.valueOf(TimeUtil.utcNow()));
            ps.setString(9, JDBC.user.getName());
            ps.setTimestamp(10, Timestamp.valueOf(TimeUtil.utcNow()));
            ps.setString(11, JDBC.user.getName());
            ps.setInt(12, custId);
            ps.setInt(13, userId);
            ps.setInt(14, contactId);

            if (ps.executeUpdate() > 0) return new Appointment(id, title, description, location, type, start, end, TimeUtil.localNow(),
                    JDBC.user.getName(), TimeUtil.localNow(), JDBC.user.getName(), custId, userId, contactId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getAddApptSQL() { return "INSERT INTO appointments VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; }

    /**
     * Method used to update an existing appointment
     *
     * @param id            Appointment ID
     * @param title         Appointment Title
     * @param description   Appointment Description
     * @param location      Appointment Location
     * @param type          Appointment Type
     * @param start         Appointment Start Time
     * @param end           Appointment End Time
     * @param custId        Appointment for Customer ID
     * @param userId        Appointment from User ID
     * @param contactId     Appointment with Contact ID
     * @return the updated appointment, to be sent to Reports
     */
    public static Appointment updtAppt(int id, String title, String description, String location, String type, LocalDateTime start,
                                       LocalDateTime end, int custId, int userId, int contactId) {
        try {
            PreparedStatement ps = JDBC.con.prepareStatement(getUpdtApptSLQL());
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(TimeUtil.localToUtc(start)));
            ps.setTimestamp(6, Timestamp.valueOf(TimeUtil.localToUtc(end)));
            ps.setTimestamp(7, Timestamp.valueOf(TimeUtil.utcNow()));
            ps.setString(8, JDBC.user.getName());
            ps.setInt(9, custId);
            ps.setInt(10, userId);
            ps.setInt(11, contactId);
            ps.setInt(12, id);

            if (ps.executeUpdate() > 0){
                ps = JDBC.con.prepareStatement(getCreatedSQL());
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()){
                    return new Appointment(id, title, description, location, type, start, end, rs.getTimestamp("Create_Date").toLocalDateTime(),
                            rs.getString("Created_By"), TimeUtil.localNow(), JDBC.user.getName(), custId, userId, contactId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getUpdtApptSLQL() { return "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?," +
            " Last_Update=?, Last_Updated_By=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID=?"; }
    private static String getCreatedSQL() { return "SELECT Create_Date, Created_By FROM appointments WHERE Appointment_ID=?"; }

    /**
     * Method used to delete an appointment from the database
     *
     * @param appt the appointment to delete
     * @return the deleted appointment, to be sent to Reports
     */
    public static Appointment delAppt(Appointment appt) {
        try {
            PreparedStatement ps = JDBC.con.prepareStatement(getDelApptSQL());
            ps.setInt(1, appt.getId());
            ps.executeUpdate();

            return appt;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getDelApptSQL() { return "DELETE FROM appointments WHERE Appointment_ID=?"; }

    /**
     * Method used to extract contacts from the database
     *
     * @return the extracted contact list
     */
    public static ObservableList<String> extractContacts() {
        ObservableList<String> ol = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.con.prepareStatement(getContactsSQL());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String s = rs.getString("Contact_Name");
                ol.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ol;
    }

    private static String getContactsSQL() { return "SELECT * FROM contacts"; }

    /**
     * Method used to extract contact name from contact ID
     *
     * @param contactId the ID to parse
     * @return the extracted name
     */
    public static String getContact(int contactId) {
        String s = "";
        try {
            PreparedStatement ps = JDBC.con.prepareStatement(getContactSQL(contactId));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                s = rs.getString("Contact_Name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }

    private static String getContactSQL(int contactId) { return "SELECT Contact_Name FROM contacts WHERE Contact_ID=" + contactId; }

    /**
     * Method used to extract contact ID from contact name
     *
     * @param contactName the name to parse
     * @return the extracted ID
     */
    public static int getContactId(String contactName) {
        int i = 0;
        try {
            PreparedStatement ps = JDBC.con.prepareStatement(getContactIdSQL(contactName));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                i = rs.getInt("Contact_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    private static String getContactIdSQL(String contactName) { return "SELECT Contact_ID FROM contacts WHERE Contact_Name='" + contactName + "'"; }

    /**
     * Method used to extract appointment types from the database
     *
     * @return the extracted type list
     */
    public static ObservableList<String> extractTypes() {
        ArrayList<String> al = new ArrayList<>();
        ObservableList<String> ol = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = JDBC.con.prepareStatement(getTypesSQL());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                al.add(rs.getString("Type"));
            }
            List<String> l = al.stream().distinct().toList();
            ol.addAll(l);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ol;
    }

    private static String getTypesSQL() { return "SELECT Type FROM appointments"; }

    /**
     * Method used to verify an appointment's validity: checks whether appointment overlaps with existing appointments
     *
     * @param id    Appointment ID
     * @param start Appointment Start Time
     * @param end   Appointment End Time
     * @return whether appointment is overlapping
     */
    public static boolean isOverlapping(int id, LocalDateTime start, LocalDateTime end) {
        try {
            PreparedStatement ps = JDBC.con.prepareStatement(getOverlappingSQL());
            ps.setTimestamp(1, Timestamp.valueOf(TimeUtil.localToUtc(start)));
            ps.setTimestamp(2, Timestamp.valueOf(TimeUtil.localToUtc(end)));
            ps.setTimestamp(3, Timestamp.valueOf(TimeUtil.localToUtc(start)));
            ps.setTimestamp(4, Timestamp.valueOf(TimeUtil.localToUtc(end)));
            ps.setInt(5, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return true;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String getOverlappingSQL() { return "SELECT * FROM appointments WHERE (? BETWEEN start AND end OR ? BETWEEN start AND end OR ? < start AND ? > end) AND Appointment_ID !=?"; }
}