package image;

import filter.Blur;
import filter.IFilter;
import filter.Sharpen;
import java.util.ArrayList;
import java.util.List;
import transformation.Greyscale;
import transformation.ITransformation;
import transformation.Sepia;

/**
 * This model represents an object that takes in an image which is manipulated through its pixel
 * sequence. This model can have its image filtered or transformed using the desired feature's
 * kernel or matrix.
 */
public class ImageModel implements IModel, ImageState {

  private ImageState image;
  private ImageState newImage;

  /**
   * Constructor for the model. It takes in an image object to be used in its methods.
   *
   * @param image image that the model will apply methods to
   * @throws IllegalArgumentException if the image is null
   */
  public ImageModel(ImageState image)
      throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }
    this.image = image;
    this.newImage = new Image(image.getTemplate());
  }

  /**
   * Constructs an ImageModel with empty images to have images manually loaded into it.
   */
  public ImageModel() {
    this.image = new EmptyImage();
    this.newImage = new EmptyImage();
  }

  @Override
  public void blur() {
    applyFilter(new Blur());
  }

  @Override
  public void sharpen() {
    applyFilter(new Sharpen());
  }

  @Override
  public void greyscale() {
    applyTransformation(new Greyscale());
  }

  @Override
  public void sepia() {
    applyTransformation(new Sepia());
  }

  /**
   * Applies the given filter to the image.
   *
   * @param filter the given filter. A filter is one of: Blur, Sharpen. EFFECT: mutates newImage
   *               after the feature is applied
   * @throws IllegalArgumentException if the provided filter is invalid or null
   */
  protected void applyFilter(IFilter filter) {
    if (filter == null) {
      throw new IllegalArgumentException("The filter cannot be null");
    }
    List<List<IPixel>> newTemplate = new ArrayList<List<IPixel>>();

    for (int y = 0; y < this.image.getHeight(); y++) {
      newTemplate.add(new ArrayList<IPixel>());
      for (int x = 0; x < this.image.getWidth(); x++) {
        newTemplate.get(y).add(pixelFilter(filter, x, y));
      }
    }
    newImage = new Image(newTemplate);
  }

  /**
   * Applies the given filter to the specified pixel.
   *
   * @param filter the given filter. A filter is one of: Blur, Sharpen.
   * @param x      the x coordinate of the pixel
   * @param y      the y coordinate of the pixel
   * @return the modified pixel
   */
  private IPixel pixelFilter(IFilter filter, int x, int y) {
    double[][] kernel = filter.getKernel();
    int kernelSize = kernel.length / 2;
    int scaledXKernel;
    int scaledYKernel;
    double red = 0;
    double green = 0;
    double blue = 0;

    for (int i = 0; i < kernel.length; i++) {
      for (int j = 0; j < kernel.length; j++) {
        scaledXKernel = (j - kernelSize + x);
        scaledYKernel = (i - kernelSize + y);
        if (scaledXKernel >= 0 && scaledXKernel < this.image.getWidth()
            && scaledYKernel >= 0 && scaledYKernel < this.image.getHeight()) {
          red += (kernel[i][j] * this.newImage.getPixelAt(scaledXKernel, scaledYKernel).getRed());
          green += (kernel[i][j] * this.newImage.getPixelAt(scaledXKernel, scaledYKernel)
              .getGreen());
          blue += (kernel[i][j] * this.newImage.getPixelAt(scaledXKernel, scaledYKernel).getBlue());
        }
      }
    }
    return new Pixel((int) red, (int) green, (int) blue);
  }

  /**
   * Applies the given transformation to the image.
   *
   * @param transformation the given transformation. A transformation is one of: Greyscale, Sepia.
   *                       EFFECT: mutates newImage after the feature is applied
   * @throws IllegalArgumentException if the provided transformation is invalid or null
   */
  protected void applyTransformation(ITransformation transformation) {
    if (transformation == null) {
      throw new IllegalArgumentException("The filter cannot be null");
    }
    List<List<IPixel>> newTemplate = new ArrayList<List<IPixel>>();

    for (int y = 0; y < this.image.getHeight(); y++) {
      newTemplate.add(new ArrayList<IPixel>());
      for (int x = 0; x < this.image.getWidth(); x++) {
        newTemplate.get(y).add(pixelTransformation(transformation, x, y));
      }
    }
    newImage = new Image(newTemplate);
  }


  /**
   * Applies the given color transformation to the specified pixel.
   *
   * @param transformation the given transformation. A transformation is one of: Greyscale, Sepia.
   * @param x              the x coordinate of the pixel
   * @param y              the y coordinate of the pixel
   * @return the modified pixel
   */
  private IPixel pixelTransformation(ITransformation transformation, int x, int y) {
    double[][] matrix = transformation.getMatrix();
    int[] pixelColors = {this.newImage.getPixelAt(x, y).getRed(),
        this.newImage.getPixelAt(x, y).getGreen(),
        this.newImage.getPixelAt(x, y).getBlue()};

    int[] newPixelColors = new int[3];
    double newColor = 0;

    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix.length; j++) {
        newColor += matrix[i][j] * pixelColors[j];
      }
      newPixelColors[i] = (int) newColor;
      newColor = 0;
    }
    return new Pixel(newPixelColors[0], newPixelColors[1], newPixelColors[2]);
  }

  @Override
  public ImageState getOriginalImage() {
    return new Image(image.getTemplate());
  }

  @Override
  public ImageState getNewImage() {
    return new Image(newImage.getTemplate());
  }

  @Override
  public List<List<IPixel>> getTemplate() {
    return this.newImage.getTemplate();
  }

  @Override
  public int getWidth() {
    return this.image.getWidth();
  }

  @Override
  public int getHeight() {
    return this.image.getHeight();
  }

  @Override
  public IPixel getPixelAt(int x, int y) {
    return this.newImage.getPixelAt(x, y);
  }

  /**
   * EFFECT: Sets the image of the model to the given image.
   *
   * @param image the given image of the model
   */
  protected void loadImage(ImageState image) {
    this.image = image;
    this.newImage = new Image(image.getTemplate());
  }
}

