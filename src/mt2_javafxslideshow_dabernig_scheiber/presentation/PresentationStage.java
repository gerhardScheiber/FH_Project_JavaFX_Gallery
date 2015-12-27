package mt2_javafxslideshow_dabernig_scheiber.presentation;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class PresentationStage extends Stage {

    public static Rectangle2D stageBounds;
    public static File mostRecentDirectory;
    private static PresentationStage instance;

    public PresentationStage(File pictureDirectory) {

        shutdownExistingInstance();
        instance = this;

        try {

            this.setOnCloseRequest(e -> PresentationController.shutdown());

            Parent root = FXMLLoader.load(PresentationStage.class.getResource("presentation.fxml"));

            this.setTitle("Presentation");

            Rectangle2D defaultBounds = getStageBounds();
            this.setX(defaultBounds.getMinX());
            this.setY(defaultBounds.getMinY());
            Scene scene = new Scene(root, defaultBounds.getWidth(), defaultBounds.getHeight());
            this.setScene(scene);
            this.setFullScreen(true);

            registerSceneEvents(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void shutdownExistingInstance() {
        if (instance == null) {
            return;
        }
        instance.close();
    }

    public static void start(File pictureDirectory) {

        mostRecentDirectory = pictureDirectory;
        Stage stage = new PresentationStage(pictureDirectory);
        stage.show();
    }

    private void registerSceneEvents(Scene scene) {
        scene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
            determineStageBounds();
        });
        scene.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> {
            determineStageBounds();
        });
        scene.setOnKeyReleased(event -> {
            if(event.getCode().getName().equals("Space")) {
                if(PresentationController.isPaused() == true) {
                    PresentationController.resume();
                } else {
                    PresentationController.pause();
                }
            }
        });
    }

    private void determineStageBounds() {
        if(this.isFullScreen() == true) {
            stageBounds = Screen.getPrimary().getVisualBounds();
            return;
        }
        stageBounds = getStageBounds();
    }

    private Rectangle2D getStageBounds() {
        final double SIZE_OF_SCREEN_IN_PERCENT = 0.9;
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double height = screenBounds.getHeight() * SIZE_OF_SCREEN_IN_PERCENT;
        double width = screenBounds.getHeight() * SIZE_OF_SCREEN_IN_PERCENT;
        double x = screenBounds.getMinX() + (screenBounds.getWidth() * (1 - SIZE_OF_SCREEN_IN_PERCENT)) / 2d;
        double y = screenBounds.getMinY() + (screenBounds.getHeight() * (1 - SIZE_OF_SCREEN_IN_PERCENT)) / 2d;

        return new Rectangle2D(x, y, width, height);
    }
}
