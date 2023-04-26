package image;

import java.util.List;

/**
 * Represents the state and information of an image, such as its dimensions, pixels, etc.
 */
public interface ImageState {

  /**
   * Retrieves the template of the image as a list of array lists of pixels.
   *
   * @return the template of the image
   */

  List<List<IPixel>> getTemplate();

  /**
   * Gets the width of the image.
   *
   * @return width of the image
   */
  int getWidth();

  /**
   * Gets the height of the image.
   *
   * @return height of the image
   */
  int getHeight();

  /**
   * Gets the pixel at the specified destination.
   *
   * @param x x coordinate of the image
   * @param y y coordinate of the image
   * @return pixel at the specified destination
   * @throws IllegalArgumentException if the destination entered is not within the image
   */
  IPixel getPixelAt(int x, int y) throws IllegalArgumentException;
}
