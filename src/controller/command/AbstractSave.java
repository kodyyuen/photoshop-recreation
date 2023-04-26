package controller.command;

import image.ILayerModel;
import image.ImageState;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Represents the abstract class for saving. To remove duplicated code from save and multisave, this
 * class was created to hold methods that save an image object to an image file for the supported
 * formats: PPM, JPG, PNG.
 */
public abstract class AbstractSave implements LayerCommand {

  @Override
  public abstract void apply(ILayerModel model);

  /**
   * Saves the image object as a PPM file. The method converts the image object information into the
   * necessary format that a PPM file requires.
   *
   * @param image    the image to be converted
   * @param filePath the file path where the PPM image will be saved
   * @throws IllegalArgumentException if the filepath cannot be created
   */
  protected void savePPMImage(ImageState image, String filePath)
      throws IllegalArgumentException {
    FileWriter out;

    try {
      out = new FileWriter(filePath + ".ppm");
      out.write("P3\n" + image.getWidth() + " " + image.getHeight() + "\n"
          + 255 + "\n");

      for (int y = 0; y < image.getHeight(); y++) {
        for (int x = 0; x < image.getWidth(); x++) {
          out.write(image.getPixelAt(x, y).getRed() + "\n"
              + image.getPixelAt(x, y).getGreen() + "\n"
              + image.getPixelAt(x, y).getBlue() + "\n");
        }
      }

      out.close();

    } catch (IOException e) {
      throw new IllegalArgumentException(
          filePath
              + " is not a valid file, cannot be created, or cannot be opened when trying to save"
              + " the PPM image to " + filePath);
    }
  }

  /**
   * Saves the given image by converting it to a buffered image and utilizing the ImageIO class to
   * write to the given filepath.
   *
   * @param image    the image to be saved
   * @param fileType the file type of the file to be saved
   * @param filePath the path where the file will be saved to
   * @throws IllegalArgumentException if the filepath cannot be created
   */
  protected void saveBufferedImage(ImageState image, String fileType, String filePath)
      throws IllegalArgumentException {
    try {
      ImageIO
          .write(ImageUtils.imageToBuffered(image), fileType, new File(filePath + "." + fileType));
    } catch (IOException e) {
      throw new IllegalArgumentException(filePath
          + " is not a valid file, cannot be created, or cannot be opened when trying to save the "
          + "JPG/PNG image");
    }
  }
}