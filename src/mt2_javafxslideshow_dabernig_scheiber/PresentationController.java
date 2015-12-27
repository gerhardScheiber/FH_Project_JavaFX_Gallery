package mt2_javafxslideshow_dabernig_scheiber;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class PresentationController implements Initializable {

    private static PresentationTask presentationTask;
    private static Thread presentationThread;
    @FXML
    private ImageView pictureView;
//    private ImageView pictureView2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        startPresentation(PresentationStage.mostRecentDirectory);

    }

    private void startPresentation(File mostRecentDirectory) {

        System.out.println("start presentation");
        presentationTask = new PresentationTask(mostRecentDirectory, new PresentationRunnable() {
            @Override
            public void run(File picture) {
                ImageHelper.setUpImageViewWithImage(pictureView, picture, PresentationStage.stageBounds);
            }
        });

        presentationThread = new Thread(presentationTask);
        presentationThread.setDaemon(false);
        presentationThread.start();
    }

    public static void shutdown() {
        if(presentationTask != null) {
            presentationTask.cancel();
        }
    }

}
