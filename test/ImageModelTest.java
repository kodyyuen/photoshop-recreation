import static org.junit.Assert.assertEquals;

import image.CreateCheckerboard;
import image.IModel;
import image.IPixel;
import image.Image;
import image.ImageModel;
import image.ImageState;
import image.Pixel;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the ImageModel class. These tests make sure that the applyFeature method applies the
 * given feature correctly.
 */
public class ImageModelTest {

  private ImageState board;
  private IModel model;

  @Before
  public void initData() {
    board = new CreateCheckerboard(1, 5).create();
    model = new ImageModel(board);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullTemplate() {
    model = new ImageModel(null);
  }

  private IPixel applyFilter(ImageState image, int x, int y, double[][] kernel) {
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
        if (scaledXKernel >= 0 && scaledXKernel < image.getWidth()
            && scaledYKernel >= 0 && scaledYKernel < image.getHeight()) {
          red += (kernel[i][j] * image.getPixelAt(scaledXKernel, scaledYKernel).getRed());
          green += (kernel[i][j] * image.getPixelAt(scaledXKernel, scaledYKernel).getGreen());
          blue += (kernel[i][j] * image.getPixelAt(scaledXKernel, scaledYKernel).getBlue());
        }
      }
    }
    return new Pixel((int) red, (int) green, (int) blue);
  }

  @Test
  public void testApplyFeatureBlur() {
    model.blur();
    double[][] kernel = new double[][]{
        {(1 / 16.0), (1 / 8.0), (1 / 16.0)},
        {(1 / 8.0), (1 / 4.0), (1 / 8.0)},
        {(1 / 16.0), (1 / 8.0), (1 / 16.0)}
    };

    for (int y = 0; y < board.getHeight(); y++) {
      for (int x = 0; x < board.getWidth(); x++) {
        assertEquals(applyFilter(board, x, y, kernel),
            model.getNewImage().getPixelAt(x, y));
      }
    }
  }

  @Test
  public void testApplyFeatureSharpen() {
    model.sharpen();
    double[][] kernel = {
        {(-1 / 8.0), (-1 / 8.0), (-1 / 8.0), (-1 / 8.0), (-1 / 8.0)},
        {(-1 / 8.0), (1 / 4.0), (1 / 4.0), (1 / 4.0), (-1 / 8.0)},
        {(-1 / 8.0), (1 / 4.0), (1.0), (1 / 4.0), (-1 / 8.0)},
        {(-1 / 8.0), (1 / 4.0), (1 / 4.0), (1 / 4.0), (-1 / 8.0)},
        {(-1 / 8.0), (-1 / 8.0), (-1 / 8.0), (-1 / 8.0), (-1 / 8.0)}
    };

    for (int y = 0; y < board.getHeight(); y++) {
      for (int x = 0; x < board.getWidth(); x++) {
        assertEquals(applyFilter(board, x, y, kernel),
            model.getNewImage().getPixelAt(x, y));
      }
    }
  }

  private IPixel applyTransformation(ImageState imageState, int x, int y, double[][] matrix) {
    int[] pixelColors = {imageState.getPixelAt(x, y).getRed(),
        imageState.getPixelAt(x, y).getGreen(),
        imageState.getPixelAt(x, y).getBlue()};

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

  @Test
  public void testApplyFeatureGreyscale() {
    model.greyscale();
    double[][] matrix = {
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722}
    };

    for (int y = 0; y < board.getHeight(); y++) {
      for (int x = 0; x < board.getWidth(); x++) {
        assertEquals(applyTransformation(board, x, y, matrix),
            model.getNewImage().getPixelAt(x, y));
      }
    }
  }

  @Test
  public void testApplyFeatureSepia() {
    model.sepia();
    double[][] matrix = {
        {0.393, 0.769, 0.189},
        {0.349, 0.686, 0.168},
        {0.272, 0.534, 0.131}
    };

    for (int y = 0; y < board.getHeight(); y++) {
      for (int x = 0; x < board.getWidth(); x++) {
        assertEquals(applyTransformation(board, x, y, matrix),
            model.getNewImage().getPixelAt(x, y));
      }
    }
  }

  @Test
  public void testApplyFilterAndTransformation() {
    model.sharpen();
    model.sepia();

    double[][] kernel = {
        {(-1 / 8.0), (-1 / 8.0), (-1 / 8.0), (-1 / 8.0), (-1 / 8.0)},
        {(-1 / 8.0), (1 / 4.0), (1 / 4.0), (1 / 4.0), (-1 / 8.0)},
        {(-1 / 8.0), (1 / 4.0), (1.0), (1 / 4.0), (-1 / 8.0)},
        {(-1 / 8.0), (1 / 4.0), (1 / 4.0), (1 / 4.0), (-1 / 8.0)},
        {(-1 / 8.0), (-1 / 8.0), (-1 / 8.0), (-1 / 8.0), (-1 / 8.0)}
    };

    double[][] matrix = {
        {0.393, 0.769, 0.189},
        {0.349, 0.686, 0.168},
        {0.272, 0.534, 0.131}
    };

    List<List<IPixel>> appliedFeatures = new ArrayList<>();

    for (int y = 0; y < board.getHeight(); y++) {
      appliedFeatures.add(new ArrayList<>());
      for (int x = 0; x < board.getWidth(); x++) {
        appliedFeatures.get(y).add(applyFilter(board, x, y, kernel));
      }
    }

    for (int y = 0; y < board.getHeight(); y++) {
      for (int x = 0; x < board.getWidth(); x++) {
        assertEquals(applyTransformation(new Image(appliedFeatures), x, y, matrix),
            model.getNewImage().getPixelAt(x, y));
      }
    }
  }

  @Test
  public void testApplyTransformationAndFeature() {
    model.greyscale();
    model.blur();

    double[][] kernel = {
        {(1 / 16.0), (1 / 8.0), (1 / 16.0)},
        {(1 / 8.0), (1 / 4.0), (1 / 8.0)},
        {(1 / 16.0), (1 / 8.0), (1 / 16.0)}
    };

    double[][] matrix = {
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722}
    };

    List<List<IPixel>> appliedFeatures = new ArrayList<>();

    for (int y = 0; y < board.getHeight(); y++) {
      appliedFeatures.add(new ArrayList<>());
      for (int x = 0; x < board.getWidth(); x++) {
        appliedFeatures.get(y).add(applyTransformation(board, x, y, matrix));
      }
    }

    ImageState result = new Image(appliedFeatures);

    for (int y = 0; y < board.getHeight(); y++) {
      for (int x = 0; x < board.getWidth(); x++) {
        assertEquals(applyFilter(result, x, y, kernel),
            model.getNewImage().getPixelAt(x, y));
      }
    }
  }

  @Test
  public void testGetOriginalImage() {
    model.blur();

    for (int y = 0; y < board.getHeight(); y++) {
      for (int x = 0; x < board.getWidth(); x++) {
        assertEquals(board.getPixelAt(x, y),
            model.getOriginalImage().getPixelAt(x, y));
      }
    }
  }

  @Test
  public void testGetNewImage() {
    model.sepia();
    double[][] matrix = {
        {0.393, 0.769, 0.189},
        {0.349, 0.686, 0.168},
        {0.272, 0.534, 0.131}
    };

    for (int y = 0; y < board.getHeight(); y++) {
      for (int x = 0; x < board.getWidth(); x++) {
        assertEquals(applyTransformation(board, x, y, matrix),
            model.getNewImage().getPixelAt(x, y));
      }
    }
  }
}