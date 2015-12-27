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
import javafx.geometry.Insets;
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
        ScrollPane root = new ScrollPane();
        TilePane tp = new TilePane();

        Button btnGalerie = new Button();
        btnGalerie.setText("Gallerie");
        tp.getChildren().add(btnGalerie);
        root.setStyle("-fx-background-color: GREY;");
        tp.setPadding(new Insets(25, 25, 25, 25));
        tp.setHgap(25);

        String path = "/home/mikevinmike/Pics/Istanbul-28-02-2015";

        File imgDir = new File(path);
        File[] picturesList = imgDir.listFiles();

        for (final File pic : picturesList) {
            if(pic.isDirectory() == true) {
                continue;
            }
            ImageView iv = createImageView(pic);
            tp.getChildren().addAll(iv);
        }

        root.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        root.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        root.setContent(tp);
        root.setFitToWidth(true);

        pStage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
        pStage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());

        Scene scene = new Scene(root);
        pStage.setScene(scene);
        pStage.show();

    }

    private ImageView createImageView(final File img) {

        ImageView iv = null;
        try {
            final Image image = new Image(new FileInputStream(img), 150, 0, true, true);
            iv = new ImageView(image);
            iv.setFitWidth(150);
            iv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {

                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

                        if (mouseEvent.getClickCount() == 1) {

                                PictureStage.showWithPicture(img);

                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iv;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
