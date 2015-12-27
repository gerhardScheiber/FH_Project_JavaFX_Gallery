package mt2_javafxslideshow_dabernig_scheiber.picturedetail;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class PictureStage extends Stage {

    public static File mostRecentImage;
    public static Rectangle2D stageBounds;

    public PictureStage(File picture) {

        try {
            determineStageBounds();

            Parent root = FXMLLoader.load(PictureStage.class.getResource("picture.fxml"));

            this.setTitle(picture.getName());

            this.setX(stageBounds.getMinX());
            this.setY(stageBounds.getMinY());
            this.setScene(new Scene(root, stageBounds.getWidth(), stageBounds.getHeight()));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showWithPicture(File picture) {

        mostRecentImage = picture;
        Stage stage = new PictureStage(picture);
        stage.show();
    }

    private void determineStageBounds() {
        final double SIZE_OF_SCREEN_IN_PERCENT = 0.9;
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double height = screenBounds.getHeight() * SIZE_OF_SCREEN_IN_PERCENT;
        double width = screenBounds.getHeight() * SIZE_OF_SCREEN_IN_PERCENT;
        double x = screenBounds.getMinX() + (screenBounds.getWidth() * (1 - SIZE_OF_SCREEN_IN_PERCENT)) / 2d;
        double y = screenBounds.getMinY() + (screenBounds.getHeight() * (1 - SIZE_OF_SCREEN_IN_PERCENT)) / 2d;

        stageBounds = new Rectangle2D(x, y, width, height);
    }
}
