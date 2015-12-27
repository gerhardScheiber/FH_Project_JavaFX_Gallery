package mt2_javafxslideshow_dabernig_scheiber;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class GalleryController implements Initializable {

    private File pictureDirectory;
    @FXML
    private TilePane tilePane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void loadGalleryInDirectory(ActionEvent actionEvent) {
        pictureDirectory = getDirectoryFromUser();

        if(pictureDirectory == null) {
            return;
        }

        tilePane.getChildren().clear();

        final Task loadGalleryTask = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                File[] picturesList = pictureDirectory.listFiles();

                for (final File picture : picturesList) {
                    if (ImageHelper.isImage(picture) == false) {
                        continue;
                    }
                    final ImageView imageView = createImageView(picture);

                    Platform.runLater(() -> tilePane.getChildren().addAll(imageView));
                }

                return null;
            }
        };

        Thread loadGalleryThread = new Thread(loadGalleryTask);
        loadGalleryThread.setDaemon(true);
        loadGalleryThread.start();
    }

    public File getDirectoryFromUser() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Load Gallery from Directory");
        File defaultDirectory = new File(System.getProperty("user.home"));
        chooser.setInitialDirectory(defaultDirectory);
        return chooser.showDialog(null);
    }

    private ImageView createImageView(final File imageFile) {

        ImageView imageView = null;
        try {
            final Image image = new Image(new FileInputStream(imageFile), 150, 0, true, true);
            imageView = new ImageView(image);
            imageView.setFitWidth(150);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {

                    onImageClicked(mouseEvent, imageFile);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageView;
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
        if(pictureDirectory == null) {
            return;
        }

        PresentationStage.start(pictureDirectory);
    }
}
