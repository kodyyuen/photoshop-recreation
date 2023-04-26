package image;

/**
 * Represents a pixel of an image and its channels. Its channels consist of red, green, and blue and
 * when combined, create a color for that pixel. RGB values range from 0 to 255.
 */
public interface IPixel {

  /**
   * Gets the red channel value.
   *
   * @return channel value, from 0 to 255
   */
  int getRed();

  /**
   * Gets the green channel value.
   *
   * @return channel value, from 0 to 255
   */
  int getGreen();

  /**
   * Gets the blue channel value.
   *
   * @return channel value, from 0 to 255
   */
  int getBlue();
}
