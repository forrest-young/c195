package helper;
import qamtwo.c195.LoginApplication;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class FXUtil {
    /**
     * Method used to switch between forms, lambda is used as an easier method of setting form dimensions
     *
     * @param stage the stage to be loaded
     * @param view the form to be loaded
     * @throws IOException the exception to be handled
     */
    public static void loadView(Stage stage, String view) throws IOException {
        stage.hide();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(LoginApplication.class.getResource(view));
        fxmlLoader.load();
        Scene scene = new Scene(fxmlLoader.getRoot());
        stage.setScene(scene);

        stage.setOnShown( event -> {
            stage.setMinHeight(stage.getHeight());
            stage.setMaxHeight(stage.getHeight());
            stage.setMinWidth(stage.getWidth());
            stage.setMaxWidth(stage.getWidth());
        });

        stage.show();
    }
}