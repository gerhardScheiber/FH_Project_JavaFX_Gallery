package mt2_javafxslideshow_dabernig_scheiber;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class PictureController implements Initializable {

    final DoubleProperty zoomProperty = new SimpleDoubleProperty(1);
    @FXML
    private ImageView pictureView;

    @FXML
    private void zoom(ScrollEvent event) {
        if (event.getDeltaY() > 0) {
            zoomProperty.set(zoomProperty.get() * 1.11);
        } else if (event.getDeltaY() < 0) {
            zoomProperty.set(zoomProperty.get() / 1.11);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpPicture();
    }

    private void setUpPicture() {
        ImageHelper.setUpImageViewWithImage(pictureView, PictureStage.mostRecentImage, PictureStage.stageBounds);
        registerZoomListener(pictureView);
    }

    private void registerZoomListener(ImageView pictureView) {
        double height = ImageHelper.getSmallerHeight(pictureView.getImage(), PictureStage.stageBounds);
        double width = ImageHelper.getSmallerWidth(pictureView.getImage(), PictureStage.stageBounds);

        zoomProperty.addListener(arg0 -> {
            if (ImageHelper.getRatio(pictureView.getImage()) < 1) {
                pictureView.setFitHeight(height * zoomProperty.get());
            } else {
                pictureView.setFitWidth(width * zoomProperty.get());
            }
        });
    }
}
