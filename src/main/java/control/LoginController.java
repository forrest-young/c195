package control;
import qamtwo.c195.LoginApplication;
import helper.FXUtil;
import helper.JDBC;
import helper.TimeUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    /**
     * Local variables
     */
    @FXML private Label prompt;
    @FXML private Label loc;
    @FXML private Tab loginTab;
    @FXML private Tab mainTab;
    @FXML private Tab addCustTab;
    @FXML private Tab updtCustTab;
    @FXML private Tab addApptTab;
    @FXML private Tab updtApptTab;
    @FXML private Tab reportsTab;
    @FXML private Label zoneId;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Button login;

    /**
     * Controller initializer
     *
     * @param url default
     * @param rb default
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rb = ResourceBundle.getBundle("language", TimeUtil.getLoc());
        loc.setText(rb.getString("loc") + ": " + TimeUtil.getLoc());
        zoneId.setText(rb.getString("zone") + ": " + TimeUtil.getLocalZone());
        prompt.setText(rb.getString("prompt"));
        login.setText(rb.getString("login"));
        username.setPromptText(rb.getString("un"));
        password.setPromptText(rb.getString("pw"));
        loginTab.setText(rb.getString("login"));
        mainTab.setText(rb.getString("main"));
        addCustTab.setText(rb.getString("add") + " " + rb.getString("cust"));
        updtCustTab.setText(rb.getString("updt") + " " + rb.getString("cust"));
        addApptTab.setText(rb.getString("add") + " " + rb.getString("appt"));
        updtApptTab.setText(rb.getString("updt") + " " + rb.getString("appt"));
        reportsTab.setText(rb.getString("report"));

        username.setText("admin");
        password.setText("admin");
    }

    /**
     * Method used to verify login credentials, updates login activity
     *
     * @param event the event to be handled
     * @throws Exception the exception to be handled
     */
    @FXML public void loginHandler(ActionEvent event) throws Exception {
        String user = username.getText();
        String pw = password.getText();

        if (user.length() == 0 || pw.length() == 0){
            alert(0);
        }else if (user.length() > LoginApplication.MAX_LENGTH || pw.length() > LoginApplication.MAX_LENGTH) {
            alert(1);
        }else{
            if (JDBC.openConnection(user, pw)) {
                try {
                    final DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
                    final String time = dtf.format(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                    final FileWriter fwr = new FileWriter("login_activity.txt", true);
                    final BufferedWriter bwr = new BufferedWriter(fwr);
                    bwr.write("LOGIN SUCCESS by " + user + " @" + time + "(" + TimeUtil.getLocalZone() + "+-UTC Offset)\n");
                    bwr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXUtil.loadView(stage, "mainView.fxml");
            }else{
                final DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
                final String time = dtf.format(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                final FileWriter fwr = new FileWriter("login_activity.txt", true);
                final BufferedWriter bwr = new BufferedWriter(fwr);
                bwr.write("LOGIN FAILURE by " + user + " @" + time + "(" + TimeUtil.getLocalZone() + "+-UTC Offset)\n");
                bwr.close();

                alert(2);
            }
        }
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
                alert.setContentText(rb.getString("ac0"));
                alert.showAndWait();
            }
            case 1 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("at0"));
                alert.setHeaderText(rb.getString("ah4"));
                alert.setContentText(rb.getString("ac5"));
                alert.showAndWait();
            }
            case 2 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("at0"));
                alert.setHeaderText(rb.getString("ah0"));
                alert.setContentText(rb.getString("ac0"));
                alert.showAndWait();
            }
            default -> throw new IllegalStateException("Unhandled case: " + alertCase);
        }
    }
}