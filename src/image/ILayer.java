package image;

/**
 * Represents an interface of the layer of an image. Each layer can only hold one image which can
 * then be manipulated and/or enhanced.
 */
public interface ILayer {

  /**
   * Retrieves the name of the layer.
   *
   * @return the name of the layer as a string
   */
  String getName();

  /**
   * Retrieves the image of the layer.
   *
   * @return the image of the layer as an ImageState
   */
  ImageState getImage();

  /**
   * Returns whether the layer is visible or invisible.
   *
   * @return whether the layer is visible or invisible as a boolean
   */
  boolean getVisibility();
}
