package mt2_javafxslideshow_dabernig_scheiber;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.image.ImageView;

import java.io.File;

public class PresentationTask extends Task<Void> {

    private File pictureDirectory;
    private PresentationRunnable switchPictures;
    private boolean paused = false;

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

            pauseIfNeeded();

            Platform.runLater(() -> switchPictures.run(picture));
        }

        return null;
    }

    private void pauseIfNeeded() throws InterruptedException {
        while(paused == true) {
            Thread.sleep(200);
        }
    }


    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
