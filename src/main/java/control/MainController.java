package control;
import model.Appointment;
import model.Customer;
import helper.*;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

public class MainController implements Initializable {
    /**
     * Local variables
     */
    private static boolean del = false;
    private static boolean loggedIn = false;
    @FXML private Tab loginTab;
    @FXML private Tab mainTab;
    @FXML private Tab addCustTab;
    @FXML private Tab updtCustTab;
    @FXML private Tab addApptTab;
    @FXML private Tab updtApptTab;
    @FXML private Tab reportsTab;
    @FXML private Label custLabel;
    @FXML private Label apptLabel;
    @FXML private Button addAppt;
    @FXML private Button updtCust;
    @FXML private Button addCust;
    @FXML private Button delCust;
    @FXML private Button updtAppt;
    @FXML private Button delAppt;
    @FXML private Button viewDetails;
    @FXML private Button logout;
    @FXML private TableView<Customer> custTV;
    @FXML private TableColumn<Customer, Integer> custIdCol;
    @FXML private TableColumn<Customer, String> custNameCol;
    @FXML private TableColumn<Customer, String> custAddressCol;
    @FXML private TableColumn<Customer, String> custPostCol;
    @FXML private TableColumn<Customer, String> custPhoneCol;
    @FXML private TableColumn<Customer, String> custDivCol;
    @FXML private TableColumn<Customer, Integer> custDivIdCol;
    @FXML private ComboBox<String> custComboBox;
    @FXML private ToggleGroup apptFilter;
    @FXML private RadioButton viewAll;
    @FXML private RadioButton byCustomer;
    @FXML private RadioButton byMonth;
    @FXML private RadioButton byWeek;
    @FXML private DatePicker datePicker;
    @FXML private TableView<Appointment> apptTV;
    @FXML private TableColumn<Appointment, Integer> apptIdCol;
    @FXML private TableColumn<Appointment, String> apptTitleCol;
    @FXML private TableColumn<Appointment, String> apptDescriptionCol;
    @FXML private TableColumn<Appointment, String> apptContactCol;
    @FXML private TableColumn<Appointment, Integer> apptUserIdCol;
    @FXML private TableColumn<Appointment, String> apptLocationCol;
    @FXML private TableColumn<Appointment, String> apptTypeCol;
    @FXML private TableColumn<Appointment, LocalDateTime> apptStartCol;
    @FXML private TableColumn<Appointment, LocalDateTime> apptEndCol;
    @FXML private TableColumn<Appointment, Integer> apptCustIdCol;
    @FXML private TableView<Appointment> reportTotalTV;
    @FXML private TableColumn<Appointment, String> rtTitleCol;
    @FXML private TableColumn<Appointment, String> rtTypeCol;
    @FXML private TableColumn<Appointment, Month> rtMonthCol;
    @FXML private Label reportTotalLabel;
    @FXML private ComboBox<String> reportType;
    @FXML private ComboBox<Month> reportMonth;
    @FXML private Label totalLabel;
    @FXML private TableView<Object> reportChangesTV;
    @FXML private TableColumn<Object, String> rcCustIdCol;
    @FXML private TableColumn<Object, String> rcContactCol;
    @FXML private TableColumn<Object, String> rcTypeCol;
    @FXML private TableColumn<Object, LocalDateTime> rcStartCol;
    @FXML private TableColumn<Object, LocalDateTime> rcEndCol;
    @FXML private TableColumn<Object, String> rcCustNameCol;
    @FXML private TableColumn<Object, String> rcCustAddressCol;
    @FXML private TableColumn<Object, String> rcCustPostCol;
    @FXML private TableColumn<Object, String> rcCustPhoneCol;
    @FXML private TableColumn<Object, String> rcCustDivCol;
    @FXML private Label changesLabel;
    @FXML private ToggleGroup recordType;
    @FXML private RadioButton changesToCust;
    @FXML private RadioButton changesToAppt;
    @FXML private ToggleGroup changeType;
    @FXML private RadioButton add;
    @FXML private RadioButton update;
    @FXML private RadioButton delete;
    @FXML private ToggleGroup scheduleFilter;
    @FXML private RadioButton scheduleViewAll;
    @FXML private RadioButton scheduleByMonth;
    @FXML private RadioButton scheduleByWeek;
    @FXML private DatePicker scheduleDatePicker;
    @FXML private TableView<Appointment> scheduleTV;
    @FXML private TableColumn<Appointment, Integer> scheduleIdCol;
    @FXML private TableColumn<Appointment, String> scheduleTitleCol;
    @FXML private TableColumn<Appointment, String> scheduleDescriptionCol;
    @FXML private TableColumn<Appointment, String> scheduleTypeCol;
    @FXML private TableColumn<Appointment, LocalDateTime> scheduleStartCol;
    @FXML private TableColumn<Appointment, LocalDateTime> scheduleEndCol;
    @FXML private TableColumn<Appointment, Integer> scheduleCustIdCol;
    @FXML private Label scheduleLabel;
    @FXML private ComboBox<String> contactComboBox;
    @FXML private Button scheduleDetails;
    @FXML private Button reportLogout;

    /**
     * Controller initializer, lambda used for efficient conversion of customer objects to strings
     *
     * @param url default
     * @param rb default
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rb = ResourceBundle.getBundle("language", TimeUtil.getLoc());

        if (!loggedIn){
            upcomingAppt();
            loggedIn = true;
        }

        loginTab.setText(rb.getString("login"));
        mainTab.setText(rb.getString("main"));
        addCustTab.setText(rb.getString("add") + " " + rb.getString("cust"));
        updtCustTab.setText(rb.getString("updt") + " " + rb.getString("cust"));
        addApptTab.setText(rb.getString("add") + " " + rb.getString("appt"));
        updtApptTab.setText(rb.getString("updt") + " " + rb.getString("appt"));
        reportsTab.setText(rb.getString("report"));

        custIdCol.setText("ID");
        custNameCol.setText(rb.getString("name"));
        custAddressCol.setText(rb.getString("addr"));
        custPostCol.setText(rb.getString("post"));
        custPhoneCol.setText(rb.getString("phone"));
        custDivIdCol.setText(rb.getString("div") + " ID");
        custDivCol.setText(rb.getString("div"));

        custIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        custAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        custPostCol.setCellValueFactory(new PropertyValueFactory<>("post"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        custDivIdCol.setCellValueFactory(new PropertyValueFactory<>("divId"));
        custDivCol.setCellValueFactory(new PropertyValueFactory<>("div"));

        custLabel.setText(rb.getString("cust") + "s");
        addCust.setText(rb.getString("add") + " " + rb.getString("cust"));
        updtCust.setText(rb.getString("updt") + " " + rb.getString("cust"));
        delCust.setText(rb.getString("del") + " " + rb.getString("cust"));

        ArrayList<Customer> custAL = SQLUtil.extractCust();
        List<String> customers = custAL.stream().map(cust -> cust.getName()).toList();
        ObservableList<String> custOL = FXCollections.observableArrayList();
        custOL.addAll(customers);
        custComboBox.setItems(custOL);
        custComboBox.getSelectionModel().select(0);

        apptLabel.setText(rb.getString("appt") + "s");
        viewAll.setText(rb.getString("view") + " " + rb.getString("all"));
        byCustomer.setText(rb.getString("by") + " " + rb.getString("cust"));
        byMonth.setText(rb.getString("by") + " " + rb.getString("month"));
        byWeek.setText(rb.getString("by") + " " + rb.getString("week"));
        datePicker.setValue(LocalDate.now());

        apptIdCol.setText("ID");
        apptTitleCol.setText(rb.getString("title"));
        apptDescriptionCol.setText(rb.getString("desc"));
        apptLocationCol.setText(rb.getString("location"));
        apptContactCol.setText(rb.getString("contact"));
        apptTypeCol.setText(rb.getString("type"));
        apptStartCol.setText(rb.getString("start"));
        apptEndCol.setText(rb.getString("end"));
        apptCustIdCol.setText(rb.getString("cust") + " ID");
        apptUserIdCol.setText(rb.getString("user") + " ID");

        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCustIdCol.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        apptUserIdCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));

        addAppt.setText(rb.getString("add") + " " + rb.getString("appt"));
        updtAppt.setText(rb.getString("updt") + " " + rb.getString("appt"));
        delAppt.setText(rb.getString("del") + " " + rb.getString("appt"));
        viewDetails.setText(rb.getString("view") + " " + rb.getString("detl"));
        logout.setText(rb.getString("logout"));

        reportTotalLabel.setText(rb.getString("rtl"));
        changesLabel.setText(rb.getString("ctd"));
        rtTitleCol.setText(rb.getString("title"));
        rtTypeCol.setText(rb.getString("type"));
        rtMonthCol.setText(rb.getString("month"));
        rtTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        rtTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        rtMonthCol.setCellValueFactory(new PropertyValueFactory<>("month"));

        rcCustIdCol.setText(rb.getString("cust") + " ID");
        rcContactCol.setText(rb.getString("contact"));
        rcTypeCol.setText(rb.getString("type"));
        rcStartCol.setText(rb.getString("start"));
        rcEndCol.setText(rb.getString("end"));
        rcCustNameCol.setText(rb.getString("name"));
        rcCustAddressCol.setText(rb.getString("addr"));
        rcCustPostCol.setText(rb.getString("post"));
        rcCustPhoneCol.setText(rb.getString("phone"));
        rcCustDivCol.setText(rb.getString("div"));

        rcCustIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        rcContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        rcTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        rcStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        rcEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        rcCustNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        rcCustAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        rcCustPostCol.setCellValueFactory(new PropertyValueFactory<>("post"));
        rcCustPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        rcCustDivCol.setCellValueFactory(new PropertyValueFactory<>("div"));

        ObservableList<String> types = SQLUtil.extractTypes();
        reportType.setItems(types);
        reportType.getSelectionModel().select(0);

        ObservableList<Month> months = FXCollections.observableArrayList();
        for (int i=1;i<=12;i++){
            months.add(Month.of(i));
        }
        reportMonth.setItems(months);
        reportMonth.getSelectionModel().select(0);
        totalLabel.setText("0");

        changesToCust.setText(rb.getString("cust"));
        changesToAppt.setText(rb.getString("appt"));
        add.setText(rb.getString("add"));
        update.setText(rb.getString("updt"));
        delete.setText(rb.getString("del"));

        ObservableList<String> contacts = SQLUtil.extractContacts();
        contactComboBox.setItems(contacts);
        contactComboBox.getSelectionModel().select(0);

        scheduleLabel.setText(rb.getString("contact") + " " + rb.getString("schedule"));
        scheduleViewAll.setText(rb.getString("view") + " " + rb.getString("all"));
        scheduleByMonth.setText(rb.getString("by") + " " + rb.getString("month"));
        scheduleByWeek.setText(rb.getString("by") + " " + rb.getString("week"));
        scheduleDatePicker.setValue(LocalDate.now());

        scheduleIdCol.setText("ID");
        scheduleTitleCol.setText(rb.getString("title"));
        scheduleDescriptionCol.setText(rb.getString("desc"));
        scheduleTypeCol.setText(rb.getString("type"));
        scheduleStartCol.setText(rb.getString("start"));
        scheduleEndCol.setText(rb.getString("end"));
        scheduleCustIdCol.setText(rb.getString("cust") + " ID");

        scheduleIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        scheduleTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        scheduleDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        scheduleTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        scheduleStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        scheduleEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        scheduleCustIdCol.setCellValueFactory(new PropertyValueFactory<>("customer_id"));

        scheduleDetails.setText(rb.getString("view") + " " + rb.getString("detl"));
        reportLogout.setText(rb.getString("logout"));

        refreshMainTables();
        refreshRTTable();
        refreshRCTable();
        refreshSchedule();
    }

    /**
     * Method used to alert user whether there are upcoming appointments, lambda is used for efficient mapping of timestamps
     * from UTC to local and filtering by appointments within 15 minutes.  Uses earliest available appointment
     */
    public static void upcomingAppt(){
        ArrayList<Appointment> apptAL = SQLUtil.extractAppt();
        List<Appointment> apptL = apptAL.stream().map(appt -> appt.utcToLocal()).filter(appt ->
                (appt.getStart().equals(TimeUtil.localNow()) || appt.getStart().isAfter(TimeUtil.localNow()))
                        && appt.getStart().isBefore(TimeUtil.localNow().plusMinutes(15))).toList();
        Appointment earliestAppt = null;

        for (Appointment appt : apptL) {
            if (earliestAppt == null) earliestAppt = appt;
            else if (appt.getStart().isBefore(earliestAppt.getStart())) earliestAppt = appt;
        }

        if (earliestAppt != null) alert(0, earliestAppt);
        else alert(4);
    }

    /**
     * Method used to display customers and appointments, lambda used for efficient mapping of timestamps from UTC to local
     */
    private void refreshMainTables() {
        ArrayList<Customer> custAL = SQLUtil.extractCust();
        ObservableList<Customer> custOL = FXCollections.observableArrayList();
        custOL.addAll(custAL);
        custTV.setItems(custOL);

        ArrayList<Appointment> apptAL = SQLUtil.extractAppt();
        List<Appointment> apptL = apptAL.stream().map(appt -> appt.utcToLocal()).toList();
        ObservableList<Appointment> apptOL = FXCollections.observableArrayList();
        apptOL.addAll(apptL);
        apptTV.setItems(apptOL);
    }

    /**
     * Method used to display appointments, lambda is used for efficient mapping of timestamps from UTC to local
     * and filtering by selected contact
     */
    private void refreshSchedule(){
        ArrayList<Appointment> apptAL = SQLUtil.extractAppt();
        List<Appointment> apptL = apptAL.stream().map(appt -> appt.utcToLocal()).filter(appt ->
                contactComboBox.getSelectionModel().getSelectedItem().equals(appt.getContact())).toList();
        ObservableList<Appointment> apptOL = FXCollections.observableArrayList();
        apptOL.addAll(apptL);
        scheduleTV.setItems(apptOL);
    }

    /**
     * Method used to display appointments, lambda is used for efficient mapping of timestamps from UTC to local
     * and filtering by selected type and month
     */
    private void refreshRTTable() {
        ArrayList<Appointment> apptAL = SQLUtil.extractAppt();
        List<Appointment> apptL = apptAL.stream().map(appt -> appt.utcToLocal()).filter(appt -> appt.getType().equals(reportType.getSelectionModel().getSelectedItem()) &&
                appt.getMonth().equals(reportMonth.getSelectionModel().getSelectedItem())).toList();
        ObservableList<Appointment> apptOL = FXCollections.observableArrayList();
        apptOL.addAll(apptL);
        reportTotalTV.setItems(apptOL);

        ResourceBundle rb = ResourceBundle.getBundle("language", TimeUtil.getLoc());
        totalLabel.setText(rb.getString("total") + ": " + reportTotalTV.getItems().size());
    }

    /**
     * Method used to display changes made to the database
     */
    private void refreshRCTable() {
        if (changesToCust.isSelected()){
            rcCustIdCol.setVisible(false);
            rcContactCol.setVisible(false);
            rcTypeCol.setVisible(false);
            rcStartCol.setVisible(false);
            rcEndCol.setVisible(false);
            rcCustNameCol.setVisible(true);
            rcCustAddressCol.setVisible(true);
            rcCustPostCol.setVisible(true);
            rcCustPhoneCol.setVisible(true);
            rcCustDivCol.setVisible(true);

            if (add.isSelected()){
                reportChangesTV.setItems(Reports.getCustAdded());
            }else if (update.isSelected()){
                reportChangesTV.setItems(Reports.getCustUpdated());
            }else{
                reportChangesTV.setItems(Reports.getCustDeleted());
            }
        }else if (changesToAppt.isSelected()){
            rcCustNameCol.setVisible(false);
            rcCustAddressCol.setVisible(false);
            rcCustPostCol.setVisible(false);
            rcCustPhoneCol.setVisible(false);
            rcCustDivCol.setVisible(false);
            rcCustIdCol.setVisible(true);
            rcContactCol.setVisible(true);
            rcTypeCol.setVisible(true);
            rcStartCol.setVisible(true);
            rcEndCol.setVisible(true);

            if (add.isSelected()){
                reportChangesTV.setItems(Reports.getApptAdded());
            }else if (update.isSelected()){
                reportChangesTV.setItems(Reports.getApptUpdated());
            }else{
                reportChangesTV.setItems(Reports.getApptDeleted());
            }
        }
    }

    /**
     * Method used to navigate to the add customer form
     *
     * @param event the event to be handled
     * @throws IOException the exception to be handled
     */
    public void addCustHandler(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXUtil.loadView(stage, "addCustView.fxml");
    }

    /**
     * Method used to navigate to the update customer form, sets the customer to be updated
     *
     * @param event the event to be handled
     * @throws IOException the exception to be handled
     */
    public void updtCustHandler(ActionEvent event) throws IOException {
        if (custTV.getSelectionModel().getSelectedItem() != null) {
            CustController.setUpdtCust(custTV.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXUtil.loadView(stage, "updtCustView.fxml");
        } else alert(0);
    }

    /**
     * Method used to delete a customer, if successful updates report
     *
     */
    public void delCustHandler() {
        if (custTV.getSelectionModel().getSelectedItem() != null) {
            alert(1);
            if (del) {
                Customer cust = SQLUtil.delCustomer(custTV.getSelectionModel().getSelectedItem());
                if (cust != null) {
                    Reports.addCustDeleted(cust);
                    refreshMainTables();
                    alert(5);
                }
                del = false;
            }
        } else alert(2);
    }

    /**
     * Method used to filter by customer if byCustomer radio button is selected
     */
    public void custFilter() {
        if (byCustomer.isSelected()) byCustomerHandler();
    }

    /**
     * Method used to refresh main tables
     *
     */
    public void viewAllHandler(){ refreshMainTables(); }

    /**
     * Method used to display appointments, lambda is used for efficient filtering by customer
     */
    public void byCustomerHandler() {
        if (custComboBox.getSelectionModel().getSelectedItem() == null) refreshMainTables();
        else {
            ArrayList<Appointment> apptAL = SQLUtil.extractAppt();
            List<Appointment> apptL = apptAL.stream().filter(appt -> appt.getCustomer().equals(custComboBox.getSelectionModel().getSelectedItem())).toList();
            ObservableList<Appointment> apptOL = FXCollections.observableArrayList();
            apptOL.addAll(apptL);
            apptTV.setItems(apptOL);
        }
    }

    /**
     * Method used to display appointments, lambda is used for efficient mapping of timestamps from UTC to local
     * and filtering by month
     */
    public void byMonthHandler() {
        if (datePicker.getValue() == null) refreshMainTables();
        else {
            ArrayList<Appointment> apptAL = SQLUtil.extractAppt();
            List<Appointment> apptL = apptAL.stream().map(appt -> appt.utcToLocal()).filter(appt -> appt.getStart().toLocalDate().getMonth()
                    == datePicker.getValue().getMonth()).toList();
            ObservableList<Appointment> apptOL = FXCollections.observableArrayList();
            apptOL.addAll(apptL);
            apptTV.setItems(apptOL);
        }
    }

    /**
     * Method used to display appointments, lambda is used for efficient mapping of timestamps from UTC to local
     */
    public void byWeekHandler() {
        if (datePicker.getValue() == null) refreshMainTables();
        else {
            ArrayList<Appointment> apptAL = SQLUtil.extractAppt();
            List<Appointment> apptL = apptAL.stream().map(appt -> appt.utcToLocal()).toList();
            ObservableList<Appointment> apptOL = FXCollections.observableArrayList();
            for (Appointment appt : apptL) {
                LocalDate filter = datePicker.getValue();
                LocalDate start = appt.getStart().toLocalDate();

                if (start.equals(filter)) apptOL.add(appt);
                else {
                    switch (filter.getDayOfWeek()) {
                        case SUNDAY -> {
                            if (start.isAfter(filter) && start.isBefore(filter.plusDays(7))) apptOL.add(appt);
                        }
                        case MONDAY -> {
                            if (start.isBefore(filter) && start.isAfter(filter.minusDays(2))) apptOL.add(appt);
                            else if (start.isAfter(filter) && start.isBefore(filter.plusDays(6))) apptOL.add(appt);
                        }
                        case TUESDAY -> {
                            if (start.isBefore(filter) && start.isAfter(filter.minusDays(3))) apptOL.add(appt);
                            else if (start.isAfter(filter) && start.isBefore(filter.plusDays(5))) apptOL.add(appt);
                        }
                        case WEDNESDAY -> {
                            if (start.isBefore(filter) && start.isAfter(filter.minusDays(4))) apptOL.add(appt);
                            else if (start.isAfter(filter) && start.isBefore(filter.plusDays(4))) apptOL.add(appt);
                        }
                        case THURSDAY -> {
                            if (start.isBefore(filter) && start.isAfter(filter.minusDays(5))) apptOL.add(appt);
                            else if (start.isAfter(filter) && start.isBefore(filter.plusDays(3))) apptOL.add(appt);
                        }
                        case FRIDAY -> {
                            if (start.isBefore(filter) && start.isAfter(filter.minusDays(6))) apptOL.add(appt);
                            else if (start.isAfter(filter) && start.isBefore(filter.plusDays(2))) apptOL.add(appt);
                        }
                        case SATURDAY -> {
                            if (start.isBefore(filter) && start.isAfter(filter.minusDays(7))) apptOL.add(appt);
                        }
                    }
                }
            }
            apptTV.setItems(apptOL);
        }
    }

    /**
     * Method used to filter by month/week if byMonth/byWeek radio button is selected
     */
    public void datePicked() {
        if (byMonth.isSelected()) byMonthHandler();
        else if (byWeek.isSelected()) byWeekHandler();
    }

    /**
     * Method used to navigate to the add appointment form
     *
     * @param event the event to be handled
     * @throws IOException the exception to be handled
     */
    public void addApptHandler(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXUtil.loadView(stage, "addApptView.fxml");
    }

    /**
     * Method used to navigate to the update appointment form, sets the appointment to be updated
     *
     * @param event the event to be handled
     * @throws IOException the exception to be handled
     */
    public void updtApptHandler(ActionEvent event) throws IOException {
        if (apptTV.getSelectionModel().getSelectedItem() != null) {
            ApptController.setUpdtAppt(apptTV.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXUtil.loadView(stage, "updtApptView.fxml");
        } else alert(0);
    }

    /**
     * Method used to delete an appointment, if successful updates report
     */
    public void delApptHandler() {
        if (apptTV.getSelectionModel().getSelectedItem() != null) {
            alert(1);
            if (del) {
                Appointment appt = SQLUtil.delAppt(apptTV.getSelectionModel().getSelectedItem());
                if (appt != null) {
                    Reports.addApptDeleted(appt);
                    refreshMainTables();
                    alert(6);
                }
                del = false;
            }
        } else alert(2);
    }

    /**
     * Method used to view details of selected appointment
     */
    public void viewDetailsHandler() {
        if (apptTV.getSelectionModel().getSelectedItem() != null) alert(1, apptTV.getSelectionModel().getSelectedItem());
        else alert(3);
    }

    /**
     * Method used to filter appointments by calling refreshRTTable
     */
    public void reportTypeHandler() { refreshRTTable(); }

    /**
     * Method used to filter appointments by calling refreshRTTable
     */
    public void reportMonthHandler(){ refreshRTTable(); }

    /**
     * Method used to filter appointments by calling refreshRCTable
     */
    public void changesToCustHandler(){ refreshRCTable(); }

    /**
     * Method used to filter appointments by calling refreshRCTable
     */
    public void changesToApptHandler(){ refreshRCTable(); }

    /**
     * Method used to filter appointments by calling refreshRCTable
     */
    public void addHandler(){ refreshRCTable(); }

    /**
     * Method used to filter appointments by calling refreshRCTable
     *
     */
    public void updateHandler(){ refreshRCTable(); }

    /**
     * Method used to filter appointments by calling refreshRCTable
     *
     */
    public void deleteHandler(){ refreshRCTable(); }

    /**
     * Method used to filter appointments by calling refreshSchedule, reselects scheduleViewAll radio button
     *
     */
    public void contactFilter() {
        scheduleViewAll.setSelected(true);
        refreshSchedule();
    }

    /**
     * Method used to filter appointments by calling refreshSchedule
     *
     */
    public void reportViewAllHandler(){ refreshSchedule(); }

    /**
     * Method used to filter contact appointments by month, lambda is used for efficient mapping of timestamps from UTC to local
     */
    public void reportByMonthHandler() {
        if (scheduleDatePicker.getValue() == null) refreshSchedule();
        else {
            ArrayList<Appointment> apptAL = SQLUtil.extractAppt();
            List<Appointment> apptL = apptAL.stream().map(appt -> appt.utcToLocal()).toList();
            ObservableList<Appointment> apptOL = FXCollections.observableArrayList();
            for (Appointment appt : apptL) {
                LocalDate filter = scheduleDatePicker.getValue();
                LocalDate start = appt.getStart().toLocalDate();

                if (contactComboBox.getSelectionModel().getSelectedItem().equals(appt.getContact()) && (filter.getMonth() == start.getMonth())) apptOL.add(appt);
            }
            scheduleTV.setItems(apptOL);
        }
    }

    /**
     * Method used to filter contact appointments by week, lambda is used for efficient mapping of timestamps from UTC to local
     *
     */
    public void reportByWeekHandler() {
        if (scheduleDatePicker.getValue() == null) refreshSchedule();
        else {
            ArrayList<Appointment> apptAL = SQLUtil.extractAppt();
            List<Appointment> apptL = apptAL.stream().map(appt -> appt.utcToLocal()).toList();
            ObservableList<Appointment> apptOL = FXCollections.observableArrayList();
            for (Appointment appt : apptL) {
                LocalDate filter = scheduleDatePicker.getValue();
                LocalDate start = appt.getStart().toLocalDate();

                if (contactComboBox.getSelectionModel().getSelectedItem().equals(appt.getContact())) {
                    if (start.equals(filter)) apptOL.add(appt);
                    else {
                        switch (filter.getDayOfWeek()) {
                            case SUNDAY -> {
                                if (start.isAfter(filter) && start.isBefore(filter.plusDays(7))) apptOL.add(appt);
                            }
                            case MONDAY -> {
                                if (start.isBefore(filter) && start.isAfter(filter.minusDays(2))) apptOL.add(appt);
                                else if (start.isAfter(filter) && start.isBefore(filter.plusDays(6))) apptOL.add(appt);
                            }
                            case TUESDAY -> {
                                if (start.isBefore(filter) && start.isAfter(filter.minusDays(3))) apptOL.add(appt);
                                else if (start.isAfter(filter) && start.isBefore(filter.plusDays(5))) apptOL.add(appt);
                            }
                            case WEDNESDAY -> {
                                if (start.isBefore(filter) && start.isAfter(filter.minusDays(4))) apptOL.add(appt);
                                else if (start.isAfter(filter) && start.isBefore(filter.plusDays(4))) apptOL.add(appt);
                            }
                            case THURSDAY -> {
                                if (start.isBefore(filter) && start.isAfter(filter.minusDays(5))) apptOL.add(appt);
                                else if (start.isAfter(filter) && start.isBefore(filter.plusDays(3))) apptOL.add(appt);
                            }
                            case FRIDAY -> {
                                if (start.isBefore(filter) && start.isAfter(filter.minusDays(6))) apptOL.add(appt);
                                else if (start.isAfter(filter) && start.isBefore(filter.plusDays(2))) apptOL.add(appt);
                            }
                            case SATURDAY -> {
                                if (start.isBefore(filter) && start.isAfter(filter.minusDays(7))) apptOL.add(appt);
                            }
                        }
                    }
                }
            }
            scheduleTV.setItems(apptOL);
        }
    }

    /**
     * Method used to filter by month/week if scheduleByMonth/scheduleByWeek radio button is selected
     */
    public void reportDatePicked() {
        if (scheduleByMonth.isSelected()) reportByMonthHandler();
        else if (scheduleByWeek.isSelected()) reportByWeekHandler();
    }

    /**
     * Method used to view details of selected contact appointment
     */
    public void reportDetailsHandler() {
        if (scheduleTV.getSelectionModel().getSelectedItem() != null) alert(1, scheduleTV.getSelectionModel().getSelectedItem());
        else alert(3);
    }

    /**
     * Method used to return to the login form
     *
     * @param event the event to be handled
     * @throws Exception the exception to be handled
     */
    public void logoutHandler(ActionEvent event) throws Exception {
        JDBC.closeConnection();
        loggedIn = false;

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXUtil.loadView(stage, "loginView.fxml");
    }

    /**
     * Method used to inform user of alert cases
     *
     * @param alertCase the alert case to be handled
     */
    private static void alert(int alertCase) {
        ResourceBundle rb = ResourceBundle.getBundle("language", TimeUtil.getLoc());

        switch (alertCase) {
            case 0 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("at0"));
                alert.setHeaderText(rb.getString("ah5"));
                alert.setContentText(rb.getString("ac4"));
                alert.showAndWait();
            }
            case 1 -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(rb.getString("at1"));
                alert.setHeaderText(rb.getString("ah6"));
                alert.setContentText(rb.getString("ac7"));
                Optional<ButtonType> o = alert.showAndWait();
                if (o.isPresent() && o.get().equals(ButtonType.OK)) del = true;
            }
            case 2 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("at0"));
                alert.setHeaderText(rb.getString("ah5"));
                alert.setContentText(rb.getString("ac6"));
                alert.showAndWait();
            }
            case 3 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("at0"));
                alert.setHeaderText(rb.getString("ah5"));
                alert.setContentText(rb.getString("ac18"));
                alert.showAndWait();
            }
            case 4 -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(rb.getString("at2"));
                alert.setHeaderText(rb.getString("ah13"));
                alert.setContentText(rb.getString("ac20"));
                alert.showAndWait();
            }
            case 5 -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(rb.getString("at2"));
                alert.setHeaderText(rb.getString("ah11"));
                alert.setContentText(rb.getString("ac16"));
                alert.showAndWait();
            }
            case 6 -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(rb.getString("at2"));
                alert.setHeaderText(rb.getString("ah11"));
                alert.setContentText(rb.getString("ac17"));
                alert.showAndWait();
            }
            default -> throw new IllegalStateException("Unhandled case: " + alertCase);
        }
    }

    /**
     * Method used to inform user of alert cases
     *
     * @param alertCase the alert case to be handled
     */
    private static void alert(int alertCase, Appointment appt){
        ResourceBundle rb = ResourceBundle.getBundle("language", TimeUtil.getLoc());

        switch (alertCase) {
            case 0 -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(rb.getString("at2"));
                alert.setHeaderText(rb.getString("ah12"));
                alert.setContentText(appt.getContact() + " " + rb.getString("ac19") + ":\n"
                        + rb.getString("appt") + " ID: " + appt.getId() + "\n"
                        + rb.getString("date") + ": " + appt.getStart().toLocalDate() + "\n"
                        + rb.getString("time") + ": " + appt.getStart().toLocalTime());
                alert.showAndWait();
            }
            case 1 -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(rb.getString("at2"));
                alert.setHeaderText(rb.getString("ah10"));
                alert.setContentText(rb.getString("dt0") + ": " + appt.getCreated_by() + "\n"
                        + rb.getString("dt1") + ": " + appt.getCreate_date() + "\n"
                        + rb.getString("dt2") + ": " + appt.getLast_update_by() + "\n"
                        + rb.getString("dt3") + ": " + appt.getLast_update() + "\n");
                alert.showAndWait();
            }
            default -> throw new IllegalStateException("Unhandled case: " + alertCase);
        }
    }
}