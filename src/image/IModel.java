package image;

/**
 * This interface represents the model and its supporting features. Our model takes in an image
 * object and applies a feature to the image.
 */
public interface IModel {

  /**
   * EFFECT: Applies a blur filter to the image given to the model.
   */
  void blur();

  /**
   * EFFECT: Applies a sharpen filter to the image given to the model.
   */
  void sharpen();

  /**
   * EFFECT: Applies a greyscale color transformation to the image given to the model.
   */
  void greyscale();

  /**
   * EFFECT: Applies a sepia color transformation to the image given to the model.
   */
  void sepia();

  /**
   * Gets the original image given to the model before any features were applied.
   *
   * @return the original image
   */
  ImageState getOriginalImage();

  /**
   * Gets the new image after whatever features were applied, or the original image if no features
   * were applied.
   *
   * @return new image with features applied
   */
  ImageState getNewImage();
}
