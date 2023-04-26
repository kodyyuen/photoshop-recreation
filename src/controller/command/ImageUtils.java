package controller.command;

import image.IPixel;
import image.Image;
import image.ImageState;
import image.Pixel;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a utility class that can be used anywhere. These methods were deemed useful enough to
 * be applicable anywhere in the program.
 */
public class ImageUtils {

  /**
   * Converts the given image object to a buffered image so it can be written to a file.
   *
   * @param image image to be saved
   * @return the converted buffered image object
   */
  public static BufferedImage imageToBuffered(ImageState image) {
    BufferedImage buffered = new BufferedImage(image.getWidth(), image.getHeight(),
        BufferedImage.TYPE_INT_RGB);
    int rgb = 0;
    int red = 0;
    int green = 0;
    int blue = 0;

    //converts the current image to a buffered image
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        red = image.getPixelAt(x, y).getRed();
        green = image.getPixelAt(x, y).getGreen();
        blue = image.getPixelAt(x, y).getBlue();
        rgb = (red << 16 | green << 8 | blue);
        buffered.setRGB(x, y, rgb);
      }
    }
    return buffered;
  }

  /**
   * Converts a buffered image to an image object. This method is used when the file type is a
   * PNG/JPG.
   *
   * @param in the buffered image after reading the file
   * @return the image object after it was converted
   */
  public static ImageState convertBufferedToImage(BufferedImage in) {
    int red;
    int green;
    int blue;
    List<List<IPixel>> template = new ArrayList<List<IPixel>>();

    for (int y = 0; y < in.getHeight(); y++) {
      template.add(new ArrayList<>());
      for (int x = 0; x < in.getWidth(); x++) {
        red = (in.getRGB(x, y) & 0xff0000) >> 16;
        green = (in.getRGB(x, y) & 0xff00) >> 8;
        blue = in.getRGB(x, y) & 0xff;
        template.get(y).add(new Pixel(red, green, blue));
      }
    }
    return new Image(template);
  }
}