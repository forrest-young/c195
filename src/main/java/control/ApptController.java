package control;
import qamtwo.c195.LoginApplication;
import helper.*;
import model.Appointment;
import model.Customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ApptController implements Initializable {
    /**
     * Local variables
     */
    private static Appointment updtAppt;
    @FXML private Label apptLabel;
    @FXML private DatePicker startDate;
    @FXML private DatePicker endDate;
    @FXML private Text timePrompt;
    @FXML private TextField apptId;
    @FXML private ComboBox<String> startHour;
    @FXML private ComboBox<String> startMin;
    @FXML private ComboBox<String> endHour;
    @FXML private ComboBox<String> endMin;
    @FXML private TextField title;
    @FXML private TextField location;
    @FXML private TextField type;
    @FXML private TextField description;
    @FXML private Label contactLabel;
    @FXML private Label idLabel;
    @FXML private Label startLabel;
    @FXML private Label endLabel;
    @FXML private Label titleLabel;
    @FXML private Label locationLabel;
    @FXML private Label typeLabel;
    @FXML private Label descriptionLabel;
    @FXML private ComboBox<String> contactComboBox;
    @FXML private ComboBox<String> custComboBox;
    @FXML private Label startEST;
    @FXML private Label endEST;
    @FXML private Label custLabel;
    @FXML private Button cancel;
    @FXML private Button save;
    @FXML private Tab loginTab;
    @FXML private Tab mainTab;
    @FXML private Tab addCustTab;
    @FXML private Tab updtCustTab;
    @FXML private Tab addApptTab;
    @FXML private Tab updtApptTab;
    @FXML private Tab reportsTab;

    /**
     * Controller initializer, lambda used for efficient conversion of customer objects to strings
     *
     * @param url default
     * @param rb default
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rb = ResourceBundle.getBundle("language", TimeUtil.getLoc());

        loginTab.setText(rb.getString("login"));
        mainTab.setText(rb.getString("main"));
        addCustTab.setText(rb.getString("add") + " " + rb.getString("cust"));
        updtCustTab.setText(rb.getString("updt") + " " + rb.getString("cust"));
        addApptTab.setText(rb.getString("add") + " " + rb.getString("appt"));
        updtApptTab.setText(rb.getString("updt") + " " + rb.getString("appt"));
        reportsTab.setText(rb.getString("report"));

        custLabel.setText(rb.getString("cust"));
        contactLabel.setText(rb.getString("contact"));
        idLabel.setText("ID");
        startLabel.setText(rb.getString("start"));
        endLabel.setText(rb.getString("end"));
        titleLabel.setText(rb.getString("title"));
        locationLabel.setText(rb.getString("location"));
        typeLabel.setText(rb.getString("type"));
        descriptionLabel.setText(rb.getString("desc"));
        timePrompt.setText(rb.getString("tprompt"));
        cancel.setText(rb.getString("cacl"));
        save.setText(rb.getString("save"));

        ArrayList<Customer> custAL = SQLUtil.extractCust();
        List<String> customers = custAL.stream().map(cust -> cust.getName()).toList();
        ObservableList<String> custOL = FXCollections.observableArrayList();
        custOL.addAll(customers);
        custComboBox.setItems(custOL);

        contactComboBox.setItems(SQLUtil.extractContacts());

        ObservableList<String> hourOL = FXCollections.observableArrayList();
        ObservableList<String> minOL = FXCollections.observableArrayList();
        for (int x=0;x<24;x++){
            String s = String.format("%02d", x);
            hourOL.add(s);
        }
        startHour.setItems(hourOL);
        endHour.setItems(hourOL);

        for (int x=0;x<60;x++){
            String s = String.format("%02d", x);
            minOL.add(s);
        }
        startMin.setItems(minOL);
        endMin.setItems(minOL);

        if (updtAppt != null){
            apptLabel.setText(rb.getString("updt") + " " + rb.getString("appt"));
            contactComboBox.getSelectionModel().select(updtAppt.getContact());
            custComboBox.getSelectionModel().select(updtAppt.getCustomer());
            apptId.setText(String.valueOf(updtAppt.getId()));
            startHour.getSelectionModel().select(TimeUtil.getStartHour(updtAppt));
            startMin.getSelectionModel().select(TimeUtil.getStartMin(updtAppt));
            endHour.getSelectionModel().select(TimeUtil.getEndHour(updtAppt));
            endMin.getSelectionModel().select(TimeUtil.getEndMin(updtAppt));
            startDate.setValue(updtAppt.getStart().toLocalDate());
            endDate.setValue(updtAppt.getEnd().toLocalDate());
            startEST.setText(rb.getString("sest") + " " + TimeUtil.localToEst(LocalDateTime.of(LocalDate.from(updtAppt.getStart().toLocalDate()),
                    LocalTime.of(TimeUtil.getStartHour(updtAppt), TimeUtil.getStartMin(updtAppt)))).toLocalTime());
            endEST.setText(rb.getString("eest") + " " + TimeUtil.localToEst(LocalDateTime.of(LocalDate.from(updtAppt.getEnd().toLocalDate()),
                    LocalTime.of(TimeUtil.getEndHour(updtAppt), TimeUtil.getEndMin(updtAppt)))).toLocalTime());
            title.setText(updtAppt.getTitle());
            location.setText(updtAppt.getLocation());
            type.setText(updtAppt.getType());
            description.setText(updtAppt.getDescription());
        }else{
            Appointment appt = new Appointment();

            apptLabel.setText(rb.getString("add") + " " + rb.getString("appt"));
            contactComboBox.getSelectionModel().select(0);
            custComboBox.getSelectionModel().select(0);
            apptId.setText(String.valueOf(SQLUtil.getNextApptId()));
            startHour.getSelectionModel().select(appt.getStart().toLocalTime().getHour());
            startMin.getSelectionModel().select(appt.getStart().toLocalTime().getMinute());
            endHour.getSelectionModel().select(appt.getEnd().toLocalTime().getHour());
            endMin.getSelectionModel().select(appt.getEnd().toLocalTime().getMinute());
            startDate.setValue(appt.getStart().toLocalDate());
            endDate.setValue(appt.getEnd().toLocalDate());
            startEST.setText(rb.getString("sest") + " " + TimeUtil.localToEst(LocalDateTime.of(LocalDate.from(appt.getStart().toLocalDate()),
                    LocalTime.of(TimeUtil.getStartHour(appt), TimeUtil.getStartMin(appt)))).toLocalTime());
            endEST.setText(rb.getString("eest") + " " + TimeUtil.localToEst(LocalDateTime.of(LocalDate.from(appt.getEnd().toLocalDate()),
                    LocalTime.of(TimeUtil.getEndHour(appt), TimeUtil.getEndMin(appt)))).toLocalTime());
        }
    }

    /**
     * Method used to display EST and end date
     */
    public void timeHandler() {
        ResourceBundle rb = ResourceBundle.getBundle("language", TimeUtil.getLoc());
        startEST.setText(rb.getString("sest") + " " + TimeUtil.localToEst(LocalDateTime.of(LocalDate.parse(String.valueOf(startDate.getValue())),
                LocalTime.of(Integer.parseInt(startHour.getSelectionModel().getSelectedItem()), Integer.parseInt(startMin.getSelectionModel().getSelectedItem())))).toLocalTime());
        endEST.setText(rb.getString("eest") + " " + TimeUtil.localToEst(LocalDateTime.of(LocalDate.parse(String.valueOf(endDate.getValue())),
                LocalTime.of(Integer.parseInt(endHour.getSelectionModel().getSelectedItem()), Integer.parseInt(endMin.getSelectionModel().getSelectedItem())))).toLocalTime());

        if (startDate.getValue() != null) {
            LocalDate end = TimeUtil.getEndDate(startDate.getValue(), startHour.getSelectionModel().getSelectedItem(), startMin.getSelectionModel().getSelectedItem(),
                    endHour.getSelectionModel().getSelectedItem(), endMin.getSelectionModel().getSelectedItem());
            endDate.setValue(end);
        }
    }

    /**
     * Method used to return to the main menu
     *
     * @param event the event to be handled
     * @throws IOException the exception to be handled
     */
    public void cancelHandler(ActionEvent event) throws IOException {
        updtAppt = null;

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXUtil.loadView(stage, "mainView.fxml");
    }

    /**
     * Method used to save appointment information, if successful updates report
     *
     * @param event the event to be handled
     * @throws IOException the exception to be handled
     */
    public void saveHandler(ActionEvent event) throws IOException {
        if (startDate.getValue() == null || endDate.getValue() == null || title.getText().isEmpty() || location.getText().isEmpty() ||
                type.getText().isEmpty() || description.getText().isEmpty()) alert(0);
        else if (title.getLength() > LoginApplication.MAX_LENGTH || location.getLength() > LoginApplication.MAX_LENGTH ||
                type.getLength() > LoginApplication.MAX_LENGTH || description.getLength() > LoginApplication.MAX_LENGTH) alert(1);
        else if (custComboBox.getSelectionModel().isEmpty()) alert(2);
        else if (contactComboBox.getSelectionModel().isEmpty()) alert(3);
        else{
            LocalDateTime start = LocalDateTime.of(startDate.getValue(), LocalTime.of(Integer.parseInt(startHour.getSelectionModel().getSelectedItem()),
                    Integer.parseInt(startMin.getSelectionModel().getSelectedItem()), 0));
            LocalDateTime end = LocalDateTime.of(endDate.getValue(), LocalTime.of(Integer.parseInt(endHour.getSelectionModel().getSelectedItem()),
                    Integer.parseInt(endMin.getSelectionModel().getSelectedItem()), 0));

            if (TimeUtil.isValid(Integer.parseInt(apptId.getText()), start, end)) {
                if (updtAppt == null){
                    Appointment appt = SQLUtil.addAppt(Integer.parseInt(apptId.getText()), title.getText(), description.getText(), location.getText(), type.getText(),
                            start, end, SQLUtil.getCustomerId(custComboBox.getSelectionModel().getSelectedItem()), JDBC.user.getId(),
                            SQLUtil.getContactId(contactComboBox.getSelectionModel().getSelectedItem()));
                    if (appt != null) Reports.addApptAdded(appt);
                }
                else{
                    Appointment appt = SQLUtil.updtAppt(Integer.parseInt(apptId.getText()), title.getText(), description.getText(), location.getText(), type.getText(),
                            start, end, SQLUtil.getCustomerId(custComboBox.getSelectionModel().getSelectedItem()), JDBC.user.getId(),
                            SQLUtil.getContactId(contactComboBox.getSelectionModel().getSelectedItem()));
                    if (appt != null) Reports.addApptUpdated(appt);
                }
                cancelHandler(event);
            }
        }
    }

    /**
     * Method used to set appointment information for update
     *
     * @param appt the appointment to be updated
     */
    public static void setUpdtAppt(Appointment appt) { updtAppt = appt; }

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
                alert.setHeaderText(rb.getString("ah3"));
                alert.setContentText(rb.getString("ac8"));
                alert.showAndWait();
            }
            case 1 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("at0"));
                alert.setHeaderText(rb.getString("ah4"));
                alert.setContentText(rb.getString("ac9"));
                alert.showAndWait();
            }
            case 2 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("at0"));
                alert.setHeaderText(rb.getString("ah7"));
                alert.setContentText(rb.getString("ac10"));
                alert.showAndWait();
            }
            case 3 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("at0"));
                alert.setHeaderText(rb.getString("ah8"));
                alert.setContentText(rb.getString("ac11"));
                alert.showAndWait();
            }
            default -> throw new IllegalStateException("Unhandled case: " + alertCase);
        }
    }
}
