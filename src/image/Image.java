package image;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an image object. An image object takes in an arraylist which holds all of the image's
 * pixels. This image object can have features applied to it.
 */
public class Image implements ImageState {

  private final List<List<IPixel>> imageTemplate;

  /**
   * Creates an image object, taking in an arraylist of arraylist of pixels to represent a grid of
   * pixels.
   *
   * @param template grid template of the image
   * @throws IllegalArgumentException if the template or its pixels are null
   */
  public Image(List<List<IPixel>> template)
      throws IllegalArgumentException {
    if (template == null) {
      throw new IllegalArgumentException("The image cannot be null");
    }
    this.imageTemplate = template;
  }

  @Override
  public List<List<IPixel>> getTemplate() {
    List<List<IPixel>> templateCopy = new ArrayList<List<IPixel>>();
    int red = 0;
    int green = 0;
    int blue = 0;

    for (int y = 0; y < imageTemplate.size(); y++) {
      templateCopy.add(new ArrayList<IPixel>());
      for (int x = 0; x < imageTemplate.get(0).size(); x++) {
        red = imageTemplate.get(y).get(x).getRed();
        green = imageTemplate.get(y).get(x).getGreen();
        blue = imageTemplate.get(y).get(x).getBlue();
        templateCopy.get(y).add(new Pixel(red, green, blue));
      }
    }
    return templateCopy;
  }

  @Override
  public int getWidth() {
    return imageTemplate.get(0).size();
  }

  @Override
  public int getHeight() {
    return imageTemplate.size();
  }


  @Override
  public IPixel getPixelAt(int x, int y)
      throws IllegalArgumentException {
    if (x < 0 || x >= this.getWidth() || y < 0 || y >= this.getHeight()) {
      throw new IllegalArgumentException("The bounds you entered for the pixel are invalid");
    }
    int red = imageTemplate.get(y).get(x).getRed();
    int green = imageTemplate.get(y).get(x).getGreen();
    int blue = imageTemplate.get(y).get(x).getBlue();
    return new Pixel(red, green, blue);
  }
}
