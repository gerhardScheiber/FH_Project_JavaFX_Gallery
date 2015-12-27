package mt2_javafxslideshow_dabernig_scheiber.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * @author Gerhard Scheiber
 */
public class MT2_JavaFXSlideshow_Dabernig_Scheiber extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
        stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());

        Parent root = FXMLLoader.load(getClass().getResource("../gallery/gallery.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        stage.show();

    }

}
