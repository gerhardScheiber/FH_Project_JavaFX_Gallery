package mt2_javafxslideshow_dabernig_scheiber;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class PresentationController implements Initializable {

    private static PresentationTask presentationTask;
    private static Thread presentationThread;
    private static BooleanProperty paused = new SimpleBooleanProperty(false);
    @FXML
    private ImageView pictureView;
//    private ImageView pictureView2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        paused.addListener((observable, oldValue, newValue) -> {
            presentationTask.setPaused(newValue);
        });
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

    public static void pause() {
        paused.set(true);
    }

    public static void resume() {
        paused.set(false);
    }

    public static boolean isPaused() {
        return paused.get();
    }
}
