package image;

/**
 * Represents a layer of an image. Layers are stacked on each other to produce a multi-layered
 * image. A layer holds a single image that can be empty or manipulated. Each layer has a name and a
 * state of visibility or invisibility; in a multi-layered image, the user sees the top-most visible
 * image.
 */
public class Layer implements ILayer {

  private final String name;
  private ImageState image;
  private boolean visibility;

  /**
   * Constructs a layer of a multi-layered image.
   *
   * @param name       the name of a layer as a string.
   * @param image      the image the layer holds, can be either empty or uploaded from the user.
   * @param visibility the state of visibility.
   */
  public Layer(String name, ImageState image, boolean visibility) {
    if (name == null || image == null) {
      throw new IllegalArgumentException("Name or image for a layer cannot be null");
    }
    this.name = name;
    this.image = image;
    this.visibility = visibility;
  }

  /**
   * Convenience constructor used when creating a new, visible layer. Its default visibility is
   * true.
   *
   * @param name  the name of a layer as a string.
   * @param image the image of the layer.
   */
  public Layer(String name, ImageState image) {
    this(name, image, true);
  }

  /**
   * Copy convenience constructor used when generating a copy of the multi-layered image.
   *
   * @param layer a layer of the multi-layered image to be copied.
   */
  public Layer(ILayer layer) {
    this.name = layer.getName();
    this.image = layer.getImage();
    this.visibility = layer.getVisibility();
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public ImageState getImage() {
    return new Image(this.image.getTemplate());
  }

  @Override
  public boolean getVisibility() {
    return this.visibility;
  }
}
