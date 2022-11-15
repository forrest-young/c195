package control;
import qamtwo.c195.LoginApplication;
import helper.FXUtil;
import helper.Reports;
import helper.SQLUtil;
import helper.TimeUtil;
import model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CustController implements Initializable {
    /**
     * Local variables
     */
    private static Customer updtCust;
    ArrayList<Country> ctryAL = new ArrayList<>();
    ArrayList<Division> divAL = new ArrayList<>();
    @FXML private Tab loginTab;
    @FXML private Tab mainTab;
    @FXML private Tab addCustTab;
    @FXML private Tab updtCustTab;
    @FXML private Tab addApptTab;
    @FXML private Tab updtApptTab;
    @FXML private Tab reportsTab;
    @FXML private Label custLabel;
    @FXML private Label custIdLabel;
    @FXML private Label custNameLabel;
    @FXML private Label custAddrLabel;
    @FXML private Label custPostLabel;
    @FXML private Label custPhoneLabel;
    @FXML private TextField custId;
    @FXML private TextField custName;
    @FXML private TextField custAddr;
    @FXML private TextField custPost;
    @FXML private TextField custPhone;
    @FXML private Label ctryLabel;
    @FXML private ComboBox<String> ctryComboBox;
    @FXML private Label divLabel;
    @FXML private ComboBox<String> divComboBox;
    @FXML private Button cancel;
    @FXML private Button save;

    /**
     * Controller initializer, lambda used for efficient conversion of country and division objects to strings
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

        custIdLabel.setText(rb.getString("cust") + " ID");
        custNameLabel.setText(rb.getString("name"));
        custAddrLabel.setText(rb.getString("addr"));
        custPostLabel.setText(rb.getString("post"));
        custPhoneLabel.setText(rb.getString("phone"));
        ctryLabel.setText(rb.getString("ctry"));
        divLabel.setText(rb.getString("div"));
        cancel.setText(rb.getString("cacl"));
        save.setText(rb.getString("save"));

        ctryAL = SQLUtil.extractCtry();
        ObservableList<String> ctryOL = FXCollections.observableArrayList();
        List<String> countries = ctryAL.stream().map(ctry -> ctry.getCtry()).toList();
        ctryOL.addAll(countries);
        ctryComboBox.setItems(ctryOL);

        divAL = SQLUtil.extractDiv();
        ObservableList<String> divOL = FXCollections.observableArrayList();
        if (updtCust != null){
            custLabel.setText(rb.getString("updt") + " " + rb.getString("cust"));
            List<String> divs = divAL.stream().filter(div -> div.getCtryId() == updtCust.getCtryId()).map(div -> div.getDiv()).toList();
            divOL.addAll(divs);
            divComboBox.setItems(divOL);
            ctryComboBox.getSelectionModel().select(updtCust.getCtry());
            divComboBox.getSelectionModel().select(updtCust.getDiv());
            custId.setText(String.valueOf(updtCust.getId()));
            custName.setText(updtCust.getName());
            custAddr.setText(updtCust.getAddress());
            custPost.setText(updtCust.getPost());
            custPhone.setText(updtCust.getPhone());
        }else{
            custLabel.setText(rb.getString("add") + " " + rb.getString("cust"));
            ctryComboBox.getSelectionModel().clearAndSelect(0);

            Customer newCust = new Customer();
            List<String> divs = divAL.stream().filter(div -> div.getCtryId() == newCust.getCtryId()).map(div -> div.getDiv()).toList();
            divOL.addAll(divs);
            divComboBox.setItems(divOL);
            divComboBox.getSelectionModel().select(0);
            custId.setText(String.valueOf(SQLUtil.getNextCustId()));
        }
    }
    /**
     * Method used to filter divisions, lambda used for efficient conversion of division objects to strings
     */
    public void ctryComboHandler() {
        if (ctryComboBox.getSelectionModel().getSelectedItem() != null){
            ObservableList<String> divOL = FXCollections.observableArrayList();
            int id = SQLUtil.getCtryId(ctryComboBox.getSelectionModel().getSelectedItem());
            List<String> divL = divAL.stream().filter(div -> div.getCtryId() == id).map(div -> div.getDiv()).toList();
            divOL.addAll(divL);
            divComboBox.setItems(divOL);
            divComboBox.getSelectionModel().clearAndSelect(0);
        }
    }

    /**
     * Method used to save customer information, if successful updates report
     *
     * @param event the event to be handled
     * @throws IOException the exception to be handled
     */
    public void saveHandler(ActionEvent event) throws IOException {
        Division div = matchDiv(divComboBox.getSelectionModel().getSelectedItem());
        if (custName.getText().isEmpty() || custAddr.getText().isEmpty() || custPost.getText().isEmpty() || custPhone.getText().isEmpty()) alert(0);
        else if (custName.getLength() > LoginApplication.MAX_LENGTH || custAddr.getLength() > LoginApplication.MAX_ADDRESS_LENGTH
                || custPost.getLength() > LoginApplication.MAX_LENGTH || custPhone.getLength() > LoginApplication.MAX_LENGTH) alert(1);
        else{
            if (updtCust == null && div != null){
                Customer cust = (SQLUtil.addCustomer(Integer.parseInt(custId.getText()), custName.getText(), custAddr.getText(), custPost.getText(), custPhone.getText(), div.getDivId()));
                if (cust != null) Reports.addCustAdded(cust);
            }
            else if (div != null){
                Customer cust = SQLUtil.updtCustomer(Integer.parseInt(custId.getText()), custName.getText(), custAddr.getText(), custPost.getText(), custPhone.getText(), div.getDivId());
                if (cust != null) Reports.addCustUpdated(cust);
            }else alert(2);
            cancelHandler(event);
        }
    }

    /**
     * Method used to return to the main menu
     *
     * @param event the event to be handled
     * @throws IOException the exception to be handled
     */
    public void cancelHandler(ActionEvent event) throws IOException {
        updtCust = null;

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXUtil.loadView(stage, "mainView.fxml");
    }

    /**
     * Method used to set customer information for update
     *
     * @param cust the customer to be updated
     */
    public static void setUpdtCust(Customer cust){
        updtCust = cust;
    }

    /**
     * Method used to set division for saveHandler
     *
     * @param s the division selected from ComboBox
     * @return the division object to be saved
     */
    private Division matchDiv(String s) {
        for (Division div : divAL){
            if (div.getDiv().equals(s)){
                return div;
            }
        }
        return null;
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
                alert.setHeaderText(rb.getString("ah3"));
                alert.setContentText(rb.getString("ac2"));
                alert.showAndWait();
            }
            case 1 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("at0"));
                alert.setHeaderText(rb.getString("ah4"));
                alert.setContentText(rb.getString("ac3"));
                alert.showAndWait();
            }
            case 2 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("at0"));
                alert.setHeaderText(rb.getString("ah7"));
                alert.setContentText(rb.getString("ac21"));
                alert.showAndWait();
            }
            default -> throw new IllegalStateException("Unhandled case: " + alertCase);
        }
    }
}