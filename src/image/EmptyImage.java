package image;

import java.util.List;

/**
 * This class represents an empty image. This is used as a placeholder for when a layer is created,
 * but does not yet have an image loaded into it. Trying to do any operations on the layer while an
 * empty image is present will give errors.
 */
public class EmptyImage implements ImageState {

  /**
   * Returns null as an empty image has no template.
   *
   * @return null
   */
  @Override
  public List<List<IPixel>> getTemplate() {
    return null;
  }

  /**
   * Returns the width of an empty image, which is zero.
   *
   * @return zero
   */
  @Override
  public int getWidth() {
    return 0;
  }

  /**
   * Returns the height of an empty image, which is zero.
   *
   * @return zero
   */
  @Override
  public int getHeight() {
    return 0;
  }

  /**
   * Returns null as an empty image has no pixels.
   *
   * @param x x coordinate of the image
   * @param y y coordinate of the image
   * @return null
   */
  @Override
  public IPixel getPixelAt(int x, int y) {
    return null;
  }
}