package qamtwo.c195;

import helper.FXUtil;
import helper.JDBC;

import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.SQLException;

public class LoginApplication extends Application {
    /**
     * Global variables restricting input from end user
     */
    public static int MAX_LENGTH = 50;
    public static int MAX_ADDRESS_LENGTH = 100;

    /**
     * Function called by main, loads login view
     *
     * @param stage Primary stage, to be used throughout program
     * @throws Exception the exception to be handled
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXUtil.loadView(stage, "loginView.fxml");
    }

    /**
     * Main function, calls start and closes connection after program exits
     *
     * @param args Arguments (unused) to be used by main
     * @throws SQLException the SQL exception to be handled
     */
    public static void main(String[] args) throws SQLException {
        launch();
        JDBC.closeConnection();
    }
}