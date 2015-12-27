package mt2_javafxslideshow_dabernig_scheiber;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class PresentationController implements Initializable {

    private static PresentationTask presentationTask;
    private static Thread presentationThread;
    private static BooleanProperty paused = new SimpleBooleanProperty(false);
    final long DURATION_OF_TRANSITION_IN_MS = 750;
    @FXML
    private Pane presentationContainer;

    public static void shutdown() {
        if (presentationTask != null) {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        paused.addListener((observable, oldValue, newValue) -> {
            presentationTask.setPaused(newValue);
        });
        startPresentation(PresentationStage.mostRecentDirectory);

    }

    private void startPresentation(File mostRecentDirectory) {

        System.out.println("start presentation");
        presentationTask = new PresentationTask(mostRecentDirectory, picture -> {
            final ImageView oldImageView = getOldImageView();
            System.out.println(picture);
            final ImageView newImageView = getAndAddNewImageView(picture);
            System.out.println(picture);

            hideOldImage(oldImageView);
            showNewImage(newImageView);
        });

        presentationThread = new Thread(presentationTask);
        presentationThread.setDaemon(false);
        presentationThread.start();
    }

    private void showNewImage(ImageView newImageView) {
        RotateTransition rotate = new RotateTransition(Duration.millis(DURATION_OF_TRANSITION_IN_MS));
        rotate.setToAngle(360);
        FadeTransition fade = new FadeTransition(Duration.millis(DURATION_OF_TRANSITION_IN_MS));
        fade.setFromValue(0);
        fade.setToValue(1);
        ParallelTransition transition = new ParallelTransition(newImageView, rotate, fade);
        transition.setCycleCount(1);
        transition.setOnFinished(event -> {
            presentationTask.setNewPictureAdded(true);
        });

        transition.play();
    }

    private void hideOldImage(ImageView oldImageView) {
        if (oldImageView != null) {
            ScaleTransition scale = new ScaleTransition(Duration.millis(DURATION_OF_TRANSITION_IN_MS));
            scale.setNode(oldImageView);
            scale.setToX(0);
            scale.setToY(0);

            scale.setOnFinished(event -> {
                removeOldImageView(oldImageView);
                presentationTask.setOldPictureRemoved(true);
            });

            scale.play();
        }
    }

    private void removeOldImageView(ImageView oldImageView) {
        presentationContainer.getChildren().remove(oldImageView);
    }

    public ImageView getOldImageView() {
        if (presentationContainer.getChildren().isEmpty() == false) {
            return (ImageView) presentationContainer.getChildren().get(0);
        }
        return null;
    }

    public ImageView getAndAddNewImageView(File picture) {
        ImageView newImageView = new ImageView();
        if (picture != null) {
            ImageHelper.setUpImageViewWithImage(newImageView, picture, PresentationStage.stageBounds);
            presentationContainer.getChildren().add(newImageView);
        }
        return newImageView;
    }
}
