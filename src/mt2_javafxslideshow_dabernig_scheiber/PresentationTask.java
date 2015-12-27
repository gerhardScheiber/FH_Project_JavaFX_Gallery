package mt2_javafxslideshow_dabernig_scheiber;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.image.ImageView;

import java.io.File;

public class PresentationTask extends Task<Void> {

    private File pictureDirectory;
    private PresentationRunnable switchPictures;

    public PresentationTask(File pictureDirectory, PresentationRunnable switchPictures) {
        this.pictureDirectory = pictureDirectory;
        this.switchPictures = switchPictures;
    }

    @Override
    protected Void call() throws Exception {

        File[] picturesList = pictureDirectory.listFiles();

        for (final File picture : picturesList) {
            if(picture.isDirectory() == true) {
                continue;
            }

            Thread.sleep(3000);

            Platform.runLater(() -> switchPictures.run(picture));
        }

        return null;
    }


}
