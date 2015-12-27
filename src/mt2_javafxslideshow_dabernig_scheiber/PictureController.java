package mt2_javafxslideshow_dabernig_scheiber;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        File pictureFile = PictureStage.mostRecentImage;
        Image picture = null;
        try {
            picture = new Image(new FileInputStream(pictureFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        pictureView.setImage(picture);
        pictureView.setPreserveRatio(true);
        pictureView.setSmooth(true);
        pictureView.setCache(true);

        double ratio = picture.getWidth() / picture.getHeight();
        double height = 0;
        double width = 0;

        if (ratio < 1) {
            height = picture.getHeight() > PictureStage.stageBounds.getHeight() ? PictureStage.stageBounds.getHeight() : picture.getHeight();
            pictureView.setFitHeight(height);
        } else {
            width = picture.getWidth() > PictureStage.stageBounds.getWidth() ? PictureStage.stageBounds.getWidth() : picture.getWidth();
            pictureView.setFitWidth(width);
        }

        registerZoomListener(ratio, width, height);
    }

    private void registerZoomListener(final double ratio, final double width, final double height) {
        zoomProperty.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                if (ratio < 1) {
                    System.out.println(height);
                    System.out.println(zoomProperty.get());
                    System.out.println(height * zoomProperty.get());
                    pictureView.setFitHeight(height * zoomProperty.get());
                } else {
                    pictureView.setFitWidth(width * zoomProperty.get());
                }
            }
        });
    }
}
