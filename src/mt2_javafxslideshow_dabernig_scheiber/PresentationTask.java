package mt2_javafxslideshow_dabernig_scheiber;

import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.File;

public class PresentationTask extends Task<Void> {

    private static final long THREE_SECONDS = 3000;
    private File pictureDirectory;
    private PresentationRunnable switchPictures;
    private boolean paused = false;
    private boolean oldPictureRemoved = true;
    private boolean newPictureAdded = false;

    public PresentationTask(File pictureDirectory, PresentationRunnable switchPictures) {
        this.pictureDirectory = pictureDirectory;
        this.switchPictures = switchPictures;
    }

    @Override
    protected Void call() throws Exception {

        File[] picturesList = pictureDirectory.listFiles();

        for (final File picture : picturesList) {
            if (ImageHelper.isImage(picture) == false) {
                continue;
            }

            Platform.runLater(() -> switchPictures.run(picture));

            waitForCompletionOfPictureChange();

            pauseForViewing();

            pauseIfNeeded();
        }

        removeLastImage();

        waitForCompletionOfPictureRemoval();

        Platform.runLater(() -> PresentationStage.shutdownExistingInstance());

        return null;
    }

    private void waitForCompletionOfPictureRemoval() throws InterruptedException {
        while (oldPictureRemoved == false) {
            Thread.sleep(50);
        }
        oldPictureRemoved = false;
    }

    private void removeLastImage() {
        Platform.runLater(() -> switchPictures.run(null));
    }

    private void waitForCompletionOfPictureChange() throws InterruptedException {
        while (newPictureAdded == false || oldPictureRemoved == false) {
            Thread.sleep(50);
        }
        newPictureAdded = false;
        oldPictureRemoved = false;
    }

    private void pauseForViewing() throws InterruptedException {
        Thread.sleep(THREE_SECONDS);
    }

    private void pauseIfNeeded() throws InterruptedException {
        while(paused == true) {
            Thread.sleep(200);
        }
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void setOldPictureRemoved(boolean oldPictureRemoved) {
        this.oldPictureRemoved = oldPictureRemoved;
    }

    public void setNewPictureAdded(boolean newPictureAdded) {
        this.newPictureAdded = newPictureAdded;
    }
}
