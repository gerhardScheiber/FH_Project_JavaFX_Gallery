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
    final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);

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

        String path = "E:\\Studium\\FH WCISbbM14\\3. Semester\\Medientechnik 2\\Ressourcen\\simplicity50\\";

        File imgDir = new File(path);
        File[] picturesList = imgDir.listFiles();

        for (final File pic : picturesList) {
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
                            try {

                                ImageView iv = new ImageView();
                                Image image = new Image(new FileInputStream(img));
                                iv.setImage(image);
                                iv.setStyle("-fx-background-color: DARKGREY;");
                                iv.setFitHeight(stage.getHeight() - 550);
                                iv.setFitWidth(stage.getWidth() - 150);
                                iv.setPreserveRatio(true);
                                iv.setSmooth(true);
                                iv.setCache(true);

                                BorderPane bp = new BorderPane();
                                bp.setCenter(iv);
                                bp.setStyle("-fx-background-color: DARKGREY;");
                                
                                zoomProperty.addListener(new InvalidationListener() {
                                    @Override
                                    public void invalidated(Observable arg0) {
                                        iv.setFitWidth(zoomProperty.get() * 4);
                                        iv.setFitHeight(zoomProperty.get() * 3);
                                    }
                                });

                                bp.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
                                    @Override
                                    public void handle(ScrollEvent event) {
                                        if (event.getDeltaY() > 0) {
                                            zoomProperty.set(zoomProperty.get() * 1.11);
                                        } else if (event.getDeltaY() < 0) {
                                            zoomProperty.set(zoomProperty.get() / 1.11);
                                        }
                                    }
                                });
                                
                                Stage stage = new Stage();
                                stage.setTitle(img.getName());
                                stage.setWidth(stage.getWidth());
                                stage.setHeight(stage.getHeight());

                                Scene scene = new Scene(bp, Color.BLACK);
                                stage.setScene(scene);
                                stage.show();

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

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
