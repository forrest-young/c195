package helper;
import model.User;

import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ResourceBundle;

public abstract class JDBC {
    /**
     * Local variables, con is used by SQLUtil to interface with the database
     */
    public static Connection con;
    public static User user;
    public static boolean openConnection(String usr, String pw) {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("DBProperties");
            Class.forName(rb.getString("driver"));
            String url = rb.getString("protocol") + rb.getString("vendor") + rb.getString("location") + rb.getString("dbname");
            con = DriverManager.getConnection(url, rb.getString("user"), rb.getString("pw"));

            if (con != null) {
                try {
                    Statement stm = con.createStatement();
                    String qry = "SELECT * FROM users WHERE User_Name = \"" + usr + "\" AND Password = \"" + pw + "\"";
                    ResultSet rs = stm.executeQuery(qry);

                    if (rs.next()) {
                        user = new User(rs.getInt("User_ID"), rs.getString("User_Name"));
                        return true;
                    }
                } catch (SQLException e) {
                    alert(0);
                    e.printStackTrace();
                }
            }
        }catch(Exception e) {
            alert(1);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method used to disconnect from the database
     *
     * @throws SQLException the exception to be handled
     */
    public static void closeConnection() throws SQLException {
        if (con != null) {
            con.close();
            con = null;
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
                alert.setHeaderText(rb.getString("ah1"));
                alert.setContentText(rb.getString("ac1"));
                alert.showAndWait();
            }
            case 1 -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("at0"));
                alert.setHeaderText(rb.getString("ah2"));
                alert.setContentText(rb.getString("ac1"));
                alert.showAndWait();
            }
            default -> throw new IllegalStateException("Unhandled case: " + alertCase);
        }
    }
}