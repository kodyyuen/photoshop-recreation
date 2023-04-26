import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import filter.Blur;
import filter.IFilter;
import filter.Sharpen;
import image.CreateCheckerboard;
import image.IModel;
import image.IPixel;
import image.ImageModel;
import image.ImageState;
import image.Pixel;
import org.junit.Before;
import org.junit.Test;

/**
 * This class represents the tests for the filters available: blur and sharpen.
 */
public class FilterTests {

  private ImageState board;
  private IModel model;

  @Before
  public void initData() {
    board = new CreateCheckerboard(1, 5).create();
    model = new ImageModel(board);
  }

  private int redAt(int x, int y) {
    return board.getPixelAt(x, y).getRed();
  }

  private int greenAt(int x, int y) {
    return board.getPixelAt(x, y).getGreen();
  }

  private int blueAt(int x, int y) {
    return board.getPixelAt(x, y).getBlue();
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullModel() {
    new ImageModel(null);
  }

  @Test
  public void testPixelBlur() {
    IFilter blur = new Blur();

    double[][] kernel = new double[][]{{(1 / 16.0), (1 / 8.0), (1 / 16.0)},
        {(1 / 8.0), (1 / 4.0), (1 / 8.0)},
        {(1 / 16.0), (1 / 8.0), (1 / 16.0)}};

    //tests retrieving the kernel of the blur filter
    assertArrayEquals(blur.getKernel(),
        kernel);

    int kernelSize = kernel.length / 2;
    int scaledXKernel;
    int scaledYKernel;
    double red = 0;
    double green = 0;
    double blue = 0;
    int x = 0;
    int y = 0;

    //applies the blur class's kernel to the top left pixel of the board image
    for (int i = 0; i < kernel.length; i++) {
      for (int j = 0; j < kernel.length; j++) {
        scaledXKernel = (j - kernelSize + x);
        scaledYKernel = (i - kernelSize + y);
        if (scaledXKernel >= 0 && scaledXKernel < model.getOriginalImage().getWidth()
            && scaledYKernel >= 0 && scaledYKernel < model.getOriginalImage().getHeight()) {
          red += (kernel[i][j] * model.getOriginalImage().getPixelAt(scaledXKernel, scaledYKernel)
              .getRed());
          green += (kernel[i][j] * model.getOriginalImage().getPixelAt(scaledXKernel, scaledYKernel)
              .getGreen());
          blue += (kernel[i][j] * model.getOriginalImage().getPixelAt(scaledXKernel, scaledYKernel)
              .getBlue());
        }
      }
    }
    IPixel result = new Pixel((int) red, (int) green, (int) blue);

    double newRed = 0.25 * redAt(0, 0)
        + 0.125 * redAt(1, 0)
        + 0.125 * redAt(0, 1)
        + 0.0625 * redAt(1, 1);

    double newGreen = 0.25 * greenAt(0, 0)
        + 0.125 * greenAt(1, 0)
        + 0.125 * greenAt(0, 1)
        + 0.0625 * greenAt(1, 1);

    double newBlue = 0.25 * blueAt(0, 0)
        + 0.125 * blueAt(1, 0)
        + 0.125 * blueAt(0, 1)
        + 0.0625 * blueAt(1, 1);

    assertEquals(new Pixel((int) newRed, (int) newGreen, (int) newBlue),
        result);

  }

  @Test
  public void testPixelSharpen() {
    IFilter sharpen = new Sharpen();
    double[][] kernel = new double[][]{
        {(-1 / 8.0), (-1 / 8.0), (-1 / 8.0), (-1 / 8.0), (-1 / 8.0)},
        {(-1 / 8.0), (1 / 4.0), (1 / 4.0), (1 / 4.0), (-1 / 8.0)},
        {(-1 / 8.0), (1 / 4.0), (1.0), (1 / 4.0), (-1 / 8.0)},
        {(-1 / 8.0), (1 / 4.0), (1 / 4.0), (1 / 4.0), (-1 / 8.0)},
        {(-1 / 8.0), (-1 / 8.0), (-1 / 8.0), (-1 / 8.0), (-1 / 8.0)}
    };

    //tests retrieving the kernel of the sharpen filter
    assertArrayEquals(sharpen.getKernel(),
        kernel);

    int kernelSize = kernel.length / 2;
    int scaledXKernel;
    int scaledYKernel;
    double red = 0;
    double green = 0;
    double blue = 0;
    int x = 0;
    int y = 0;

    //applies the sharpen class's kernel to the top left pixel of the board image
    for (int i = 0; i < kernel.length; i++) {
      for (int j = 0; j < kernel.length; j++) {
        scaledXKernel = (j - kernelSize + x);
        scaledYKernel = (i - kernelSize + y);
        if (scaledXKernel >= 0 && scaledXKernel < model.getOriginalImage().getWidth()
            && scaledYKernel >= 0 && scaledYKernel < model.getOriginalImage().getHeight()) {
          red += (kernel[i][j] * model.getOriginalImage().getPixelAt(scaledXKernel, scaledYKernel)
              .getRed());
          green += (kernel[i][j] * model.getOriginalImage().getPixelAt(scaledXKernel, scaledYKernel)
              .getGreen());
          blue += (kernel[i][j] * model.getOriginalImage().getPixelAt(scaledXKernel, scaledYKernel)
              .getBlue());
        }
      }
    }
    IPixel result = new Pixel((int) red, (int) green, (int) blue);

    double newRed = redAt(0, 0)
        + 0.25 * redAt(1, 0)
        + 0.25 * redAt(0, 1)
        + 0.25 * redAt(1, 1)
        - 0.125 * redAt(2, 0)
        - 0.125 * redAt(2, 1)
        - 0.125 * redAt(2, 2)
        - 0.125 * redAt(1, 2)
        - 0.125 * redAt(0, 2);

    double newGreen = greenAt(0, 0)
        + 0.25 * greenAt(1, 0)
        + 0.25 * greenAt(0, 1)
        + 0.25 * greenAt(1, 1)
        - 0.125 * greenAt(2, 0)
        - 0.125 * greenAt(2, 1)
        - 0.125 * greenAt(2, 2)
        - 0.125 * greenAt(1, 2)
        - 0.125 * greenAt(0, 2);

    double newBlue = blueAt(0, 0)
        + 0.25 * blueAt(1, 0)
        + 0.25 * blueAt(0, 1)
        + 0.25 * blueAt(1, 1)
        - 0.125 * blueAt(2, 0)
        - 0.125 * blueAt(2, 1)
        - 0.125 * blueAt(2, 2)
        - 0.125 * blueAt(1, 2)
        - 0.125 * blueAt(0, 2);

    assertEquals(new Pixel((int) newRed, (int) newGreen, (int) newBlue),
        result);
  }
}
