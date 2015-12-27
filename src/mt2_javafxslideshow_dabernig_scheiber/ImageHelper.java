package mt2_javafxslideshow_dabernig_scheiber;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageHelper {

    public static void setUpImageViewWithImage(ImageView imageView, File imageFile, Rectangle2D alternativeBounds) {
        Image image = null;
        try {
            image = new Image(new FileInputStream(imageFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        imageView.setImage(image);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        if (getRatio(image) < 1) {
            double height = getSmallerHeight(image, alternativeBounds);
            imageView.setFitHeight(height);
            System.out.println("set height " +height);
        } else {
            double width = getSmallerWidth(image, alternativeBounds);
            imageView.setFitWidth(width);
            System.out.println("set width " +width);
        }
    }

    public static double getSmallerWidth(Image image, Rectangle2D alternativeBounds) {
        return image.getWidth() > alternativeBounds.getWidth() ? alternativeBounds.getWidth() : image.getWidth();
    }

    public static double getSmallerHeight(Image image, Rectangle2D alternativeBounds) {
        return image.getHeight() > alternativeBounds.getHeight() ? alternativeBounds.getHeight() : image.getHeight();
    }

    public static double getRatio(Image image) {
        double ratio = image.getWidth() / image.getHeight();
        return ratio;
    }

}
