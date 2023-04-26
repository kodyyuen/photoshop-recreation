import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import image.CreateCheckerboard;
import image.IModel;
import image.IPixel;
import image.ImageModel;
import image.ImageState;
import image.Pixel;
import org.junit.Before;
import org.junit.Test;
import transformation.Greyscale;
import transformation.ITransformation;
import transformation.Sepia;

/**
 * This class represents the tests for the transformations available: greyscale and sepia.
 */
public class TransformationTests {

  private IModel model;

  @Before
  public void initData() {
    ImageState board = new CreateCheckerboard(1, 5).create();
    model = new ImageModel(board);
  }

  @Test
  public void testPixelGreyscale() {
    ITransformation greyscale = new Greyscale();
    double[][] matrix = {
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722}
    };

    //tests retrieving the kernel of the greyscale transformation
    assertArrayEquals(greyscale.getMatrix(), matrix);

    int x = 0;
    int y = 0;

    int[] pixelColors = {model.getOriginalImage().getPixelAt(x, y).getRed(),
        model.getOriginalImage().getPixelAt(x, y).getGreen(),
        model.getOriginalImage().getPixelAt(x, y).getBlue()};

    int[] newPixelColors = new int[3];
    double newColor = 0;

    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix.length; j++) {
        newColor += matrix[i][j] * pixelColors[j];
      }
      newPixelColors[i] = (int) newColor;
      newColor = 0;
    }

    IPixel result = new Pixel(newPixelColors[0], newPixelColors[1], newPixelColors[2]);
    model.greyscale();
    assertEquals(result, model.getNewImage().getPixelAt(x, y));

  }

  @Test
  public void testPixelSepia() {
    ITransformation sepia = new Sepia();
    double[][] matrix = {
        {0.393, 0.769, 0.189},
        {0.349, 0.686, 0.168},
        {0.272, 0.534, 0.131}
    };

    //tests retrieving the kernel of the greyscale transformation
    assertArrayEquals(sepia.getMatrix(), matrix);

    int x = 0;
    int y = 0;

    int[] pixelColors = {model.getOriginalImage().getPixelAt(x, y).getRed(),
        model.getOriginalImage().getPixelAt(x, y).getGreen(),
        model.getOriginalImage().getPixelAt(x, y).getBlue()};

    int[] newPixelColors = new int[3];
    double newColor = 0;

    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix.length; j++) {
        newColor += matrix[i][j] * pixelColors[j];
      }
      newPixelColors[i] = (int) newColor;
      newColor = 0;
    }

    IPixel result = new Pixel(newPixelColors[0], newPixelColors[1], newPixelColors[2]);
    model.sepia();
    assertEquals(result, model.getNewImage().getPixelAt(x, y));

  }
}
