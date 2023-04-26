package image;

/**
 * Represents an interface that houses programmatically created images. It currently supports
 * creating a checkerboard.
 */
public interface CreateImage {

  /**
   * Creates a programmatic image based on its class.
   *
   * @return new image
   */
  ImageState create();
}