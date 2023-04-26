package image;

import java.util.Objects;

/**
 * Represents a pixel in an image. A pixel has three channels and each can be from 0 to 255.
 */
public class Pixel implements IPixel {

  private final int red;
  private final int green;
  private final int blue;

  /**
   * Creates a pixel that is used in an image.
   *
   * @param red   red channel value
   * @param green green channel value
   * @param blue  blue channel value
   */
  public Pixel(int red, int green, int blue) {
    this.red = clamp(red);
    this.green = clamp(green);
    this.blue = clamp(blue);
  }

  //clamps the RGB values so that they cannot exceed 255 and cannot go below 0
  private int clamp(int value) {
    if (value < 0) {
      return 0;
    }
    return Math.min(value, 255);
  }

  @Override
  public int getRed() {
    return red;
  }

  @Override
  public int getGreen() {
    return green;
  }

  @Override
  public int getBlue() {
    return blue;
  }

  /**
   * Overriding equals so two pixels can be compared when looking at their channel values.
   *
   * @param that other pixel
   * @return if the two pixels are equal
   */
  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }

    if (!(that instanceof IPixel)) {
      return false;
    }

    return ((Pixel) that).red == this.red
        && ((Pixel) that).green == this.green
        && ((Pixel) that).blue == this.blue;
  }

  /**
   * Overriding hashcode so two pixels can be compared when looking at their channel values.
   *
   * @return hashcode value
   */
  @Override
  public int hashCode() {
    return Objects.hash(red, green, blue);
  }
}
