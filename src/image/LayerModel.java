package image;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a multi-layered model for an image. This model extends the simple image model, but
 * this model allows for multiple layers of an image. Each layer holds an image that can be
 * manipulated. Layers can be added, removed, manipulated, and saved through user interaction in the
 * controller.
 */
public class LayerModel extends ImageModel implements IComplexLayerModel {

  private final List<ILayer> multi;
  private String current;
  private boolean firstImage;
  private int defaultWidth;
  private int defaultHeight;

  /**
   * Constructs a LayerModel by initializing an empty list of layers. As the list of layers is
   * empty, the current layer is set to null.
   */
  public LayerModel() {
    this.multi = new ArrayList<ILayer>();
    this.current = null;
    this.firstImage = false;
  }

  @Override
  public void setCurrent(String name)
      throws IllegalArgumentException {
    for (ILayer layer : multi) {
      if (name.equalsIgnoreCase(layer.getName())) {
        this.current = name;
        return;
      }
    }
    throw new IllegalArgumentException("The layer " + name + " does not exist");
  }

  @Override
  public void createLayer(String name)
      throws IllegalArgumentException {
    if (multi.size() > 0) {
      for (ILayer layer : multi) {
        if (name.equalsIgnoreCase(layer.getName())) {
          throw new IllegalArgumentException("A layer " + name + " already exists");
        }
      }
    }
    multi.add(new Layer(name, new EmptyImage()));
  }

  @Override
  public void copyLayer(String originalLayerName, String name)
      throws IllegalArgumentException {
    ILayer originalLayer = getLayer(originalLayerName);

    for (ILayer layer : multi) {
      if (originalLayerName.equalsIgnoreCase(layer.getName())) {
        checkEmptyImage(layer, "The layer " + originalLayer
            + " you are trying to copy does not have an image loaded into it yet");
        multi.add(new Layer(name, originalLayer.getImage(), originalLayer.getVisibility()));
        return;
      }
    }
    throw new IllegalArgumentException(
        "The layer " + originalLayerName + " you are trying to copy does not exist");
  }

  @Override
  public void removeLayer(String name)
      throws IllegalArgumentException {
    int layerIndex = getLayerIndex(name);
    multi.remove(layerIndex);
  }

  @Override
  public void loadImageToLayer(ImageState image)
      throws IllegalArgumentException {
    checkImageDimensions(image);
    int layerIndex = getLayerIndex(current);
    ILayer layer = getLayer(current);
    this.multi.set(layerIndex, new Layer(current, image, layer.getVisibility()));
  }

  @Override
  public ImageState getTopVisibleImage()
      throws IllegalStateException {
    for (int i = multi.size() - 1; i >= 0; i--) {
      if (multi.get(i).getVisibility() && multi.get(i).getImage() != null) {
        return multi.get(i).getImage();
      }
    }
    throw new IllegalStateException("There are no visible layers available");
  }

  @Override
  public List<ILayer> getMultiLayer()
      throws IllegalArgumentException {
    List<ILayer> multiCopy = new ArrayList<ILayer>();
    for (int i = 0; i < multi.size(); i++) {
      checkEmptyImage(multi.get(i), "You cannot export an image with empty layers");
      multiCopy.add(new Layer(multi.get(i)));
    }
    return multiCopy;
  }

  @Override
  public void blurLayer() {
    ILayer layer = getLayer(current);
    checkEmptyImage(layer, "You cannot apply a blur to a layer with no image");
    super.loadImage(layer.getImage());
    super.blur();
    ImageState newImage = super.getNewImage();
    multi.set(getLayerIndex(current), new Layer(current, newImage, layer.getVisibility()));
  }

  @Override
  public void sharpenLayer() {
    ILayer layer = getLayer(current);
    checkEmptyImage(layer, "You cannot apply a sharpen to a layer with no image");
    super.loadImage(layer.getImage());
    super.sharpen();
    ImageState newImage = super.getNewImage();
    multi.set(getLayerIndex(current), new Layer(current, newImage, layer.getVisibility()));
  }

  @Override
  public void greyscaleLayer() {
    ILayer layer = getLayer(current);
    checkEmptyImage(layer, "You cannot apply a greyscale to a layer with no image");
    super.loadImage(layer.getImage());
    super.greyscale();
    ImageState newImage = super.getNewImage();
    multi.set(getLayerIndex(current), new Layer(current, newImage, layer.getVisibility()));
  }

  @Override
  public void sepiaLayer() {
    ILayer layer = getLayer(current);
    checkEmptyImage(layer, "You cannot apply a sepia to a layer with no image");
    super.loadImage(layer.getImage());
    super.sepia();
    ImageState newImage = super.getNewImage();
    multi.set(getLayerIndex(current), new Layer(current, newImage, layer.getVisibility()));
  }

  @Override
  public void setVisible(String name) {
    ILayer layer = getLayer(name);
    multi.set(getLayerIndex(name), new Layer(name, layer.getImage(), true));
  }

  @Override
  public void setInvisible(String name) {
    ILayer layer = getLayer(name);
    multi.set(getLayerIndex(name), new Layer(name, layer.getImage(), false));
  }

  /**
   * Retrieves the specified layer from the list of layers according to its name.
   *
   * @param name the name of the layer
   * @return the specified layer
   */
  private ILayer getLayer(String name) {
    int layerIndex = getLayerIndex(name);
    return multi.get(layerIndex);
  }

  /**
   * Retrieves the index of a layer in the list of layers according to its name.
   *
   * @param name the name of the layer
   * @return the index of a specified layer
   */
  private int getLayerIndex(String name) {
    if (current == null) {
      throw new IllegalArgumentException("You have not set a current layer yet");
    }
    int index = 0;
    for (ILayer layer : multi) {
      if (name.equalsIgnoreCase(layer.getName())) {
        return index;
      }
      index++;
    }
    throw new IllegalArgumentException("The layer " + name + " does not exist");
  }

  /**
   * EFFECT: Checks the image dimensions of the given image to make sure that the multi-layered
   * image is a consistent size.
   *
   * @param image the given image to be checked
   * @throws IllegalArgumentException if the given image does not have compatible dimensions
   */
  private void checkImageDimensions(ImageState image)
      throws IllegalArgumentException {
    if (!firstImage) {
      defaultWidth = image.getWidth();
      defaultHeight = image.getHeight();
      firstImage = true;
    } else {
      if (!(image.getWidth() == defaultWidth
          && image.getHeight() == defaultHeight)) {
        throw new IllegalArgumentException(
            "The image you are trying to load does not have compatible dimensions");
      }
    }
  }

  /**
   * Checks to see if the given layer has an empty image.
   *
   * @param layer   the layer to be checked
   * @param message the error message
   * @throws IllegalStateException if the layer has an empty image
   */
  private void checkEmptyImage(ILayer layer, String message) {
    if (layer.getImage() == null) {
      throw new IllegalStateException(
          message);
    }
  }

  @Override
  public void downscale(double xScale, double yScale)
      throws IllegalStateException, IllegalArgumentException {
    if (xScale < 0 || xScale > 1 || yScale < 0 || yScale > 1) {
      throw new IllegalArgumentException("Invalid scale factor");
    }
    if (multi.size() == 0) {
      throw new IllegalStateException("You cannot downscale an image with no layers");
    }
    for (ILayer layer : multi) {
      checkEmptyImage(layer, "You cannot downscale a layer with an empty image");
    }

    ImageState firstImage = multi.get(0).getImage();
    int newWidth = (int) (firstImage.getWidth() * xScale);
    int newHeight = (int) (firstImage.getHeight() * yScale);

    for (ILayer layer : multi) {
      imageDownscale(layer, newWidth, newHeight);
    }

    defaultWidth = newWidth;
    defaultHeight = newHeight;
  }

  /**
   * Helper method that downscales the given image. It sends pixels to another method to be
   * downscaled and reconstructs the downscaled image.
   *
   * @param layer     layer to be downscaled
   * @param newWidth  width of the image after it has been downscaled
   * @param newHeight height of the image after it has been downscaled
   */
  private void imageDownscale(ILayer layer, int newWidth, int newHeight) {
    ImageState image = layer.getImage();
    ImageState newImage;

    List<List<IPixel>> newTemplate = new ArrayList<List<IPixel>>();

    for (int y = 0; y < newHeight; y++) {
      newTemplate.add(new ArrayList<>());
      for (int x = 0; x < newWidth; x++) {
        newTemplate.get(y).add(pixelDownscale(image, x, y, newWidth, newHeight));
      }
    }

    newImage = new Image(newTemplate);
    multi.set(getLayerIndex(layer.getName()), new Layer(layer.getName(), newImage,
        layer.getVisibility()));
  }

  /**
   * Helper method that downscales the pixel in the image and returns the new pixel.
   *
   * @param image     image to be downscaled
   * @param x         x coordinate of pixel
   * @param y         y coordinate of pixel
   * @param newWidth  the new width of the downscaled image
   * @param newHeight the new height of the downscaled image
   * @return the downscaled pixel
   */
  private IPixel pixelDownscale(ImageState image, int x, int y, int newWidth, int newHeight) {

    double newX = ((x) / (double) newWidth) * image.getWidth();
    double newY = ((y) / (double) newHeight) * image.getHeight();
    int ceilX = (int) Math.ceil(newX);
    int ceilY = (int) Math.ceil(newY);
    int floorX = (int) Math.floor(newX);
    int floorY = (int) Math.floor(newY);

    IPixel topLeft = image.getPixelAt(floorX, floorY);
    IPixel topRight = image.getPixelAt(ceilX, floorY);
    IPixel bottomLeft = image.getPixelAt(floorX, ceilY);
    IPixel bottomRight = image.getPixelAt(ceilX, ceilY);

    int red =
        (topLeft.getRed() + topRight.getRed() + bottomLeft.getRed() + bottomRight.getRed()) / 4;
    int green =
        (topLeft.getGreen() + topRight.getGreen() + bottomLeft.getGreen() + bottomRight
            .getGreen()) / 4;
    int blue =
        (topLeft.getBlue() + topRight.getBlue() + bottomLeft.getBlue() + bottomRight.getBlue())
            / 4;

    return new Pixel(red, green, blue);
  }
}