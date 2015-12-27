package mt2_javafxslideshow_dabernig_scheiber;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.TilePane;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class GalleryController implements Initializable {

    @FXML
    private TilePane tilePane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void loadGalleryInDirectory(ActionEvent actionEvent) {
        final String path = "/home/mikevinmike/Pics/Istanbul-28-02-2015";

        final Task loadGalleryTask = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                File imgDir = new File(path);
                File[] picturesList = imgDir.listFiles();

                for (final File pic : picturesList) {
                    if(pic.isDirectory() == true) {
                        continue;
                    }
                    final ImageView imageView = createImageView(pic);

                    Platform.runLater(() -> tilePane.getChildren().addAll(imageView));
                }

                return null;
            }
        };

        Thread loadGalleryThread = new Thread(loadGalleryTask);
        loadGalleryThread.setDaemon(true);
        loadGalleryThread.start();
    }

    private ImageView createImageView(final File imageFile) {

        ImageView iv = null;
        try {
            final Image image = new Image(new FileInputStream(imageFile), 150, 0, true, true);
            iv = new ImageView(image);
            iv.setFitWidth(150);
            iv.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {

                    onImageClicked(mouseEvent, imageFile);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iv;
    }

    private void onImageClicked(MouseEvent mouseEvent, File imageFile) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

            if (mouseEvent.getClickCount() == 1) {

                PictureStage.showWithPicture(imageFile);

            }
        }
    }

    @FXML
    public void startPresentation(ActionEvent actionEvent) {
    }
}
