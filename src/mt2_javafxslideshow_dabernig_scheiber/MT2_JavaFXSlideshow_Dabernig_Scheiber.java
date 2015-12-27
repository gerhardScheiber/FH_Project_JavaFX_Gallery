package mt2_javafxslideshow_dabernig_scheiber;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * @author Gerhard Scheiber
 */
public class MT2_JavaFXSlideshow_Dabernig_Scheiber extends Application {

    Stage stage;

    @Override
    public void start(Stage pStage) throws Exception {

        stage = pStage;
//        ScrollPane root = new ScrollPane();
//        TilePane tp = new TilePane();

//        Button btnGalerie = new Button();
//        btnGalerie.setText("Gallerie");
//        tp.getChildren().add(btnGalerie);



        pStage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
        pStage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());

        Parent root = FXMLLoader.load(PictureStage.class.getResource("gallery.fxml"));

        Scene scene = new Scene(root);
        pStage.setScene(scene);
        pStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
