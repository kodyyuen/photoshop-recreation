import static org.junit.Assert.assertEquals;

import image.CreateCheckerboard;
import image.IComplexLayerModel;
import image.ILayer;
import image.IPixel;
import image.Image;
import image.ImageState;
import image.LayerModel;
import image.Pixel;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of the LayerModel.
 */
public class LayerModelTest {

  private IComplexLayerModel model;
  private ImageState board;
  private ImageState bigBoard;

  @Before
  public void initData() {
    model = new LayerModel();
    board = new CreateCheckerboard(1, 5).create();
    bigBoard = new CreateCheckerboard(1, 10).create();
  }

  @Test
  public void testCreateLayer() {
    model.createLayer("first");
    model.setCurrent("first");
    model.loadImageToLayer(board);

    ILayer curr = model.getMultiLayer().get(0);

    for (int y = 0; y < board.getHeight(); y++) {
      for (int x = 0; x < board.getWidth(); x++) {
        assertEquals(board.getPixelAt(x, y),
            curr.getImage().getPixelAt(x, y));
      }
    }
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
  public void testBlur() {
    model.createLayer("first");
    model.setCurrent("first");
    model.loadImageToLayer(board);
    model.blurLayer();
    double[][] kernel = new double[][]{
        {(1 / 16.0), (1 / 8.0), (1 / 16.0)},
        {(1 / 8.0), (1 / 4.0), (1 / 8.0)},
        {(1 / 16.0), (1 / 8.0), (1 / 16.0)}
    };

    ILayer curr = model.getMultiLayer().get(0);

    for (int y = 0; y < board.getHeight(); y++) {
      for (int x = 0; x < board.getWidth(); x++) {
        assertEquals(applyFilter(board, x, y, kernel),
            curr.getImage().getPixelAt(x, y));
      }
    }
  }

  @Test
  public void testSharpen() {
    model.createLayer("first");
    model.setCurrent("first");
    model.loadImageToLayer(board);
    model.sharpenLayer();
    double[][] kernel = new double[][]{
        {(-1 / 8.0), (-1 / 8.0), (-1 / 8.0), (-1 / 8.0), (-1 / 8.0)},
        {(-1 / 8.0), (1 / 4.0), (1 / 4.0), (1 / 4.0), (-1 / 8.0)},
        {(-1 / 8.0), (1 / 4.0), (1.0), (1 / 4.0), (-1 / 8.0)},
        {(-1 / 8.0), (1 / 4.0), (1 / 4.0), (1 / 4.0), (-1 / 8.0)},
        {(-1 / 8.0), (-1 / 8.0), (-1 / 8.0), (-1 / 8.0), (-1 / 8.0)}
    };

    ILayer curr = model.getMultiLayer().get(0);

    for (int y = 0; y < board.getHeight(); y++) {
      for (int x = 0; x < board.getWidth(); x++) {
        assertEquals(applyFilter(board, x, y, kernel),
            curr.getImage().getPixelAt(x, y));
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
  public void testGreyscale() {
    model.createLayer("first");
    model.setCurrent("first");
    model.loadImageToLayer(board);
    model.greyscaleLayer();
    double[][] matrix = {
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722}
    };

    ILayer curr = model.getMultiLayer().get(0);

    for (int y = 0; y < board.getHeight(); y++) {
      for (int x = 0; x < board.getWidth(); x++) {
        assertEquals(applyTransformation(board, x, y, matrix),
            curr.getImage().getPixelAt(x, y));
      }
    }
  }

  @Test
  public void testSepia() {
    model.createLayer("first");
    model.setCurrent("first");
    model.loadImageToLayer(board);
    model.sepiaLayer();
    double[][] matrix = {
        {0.393, 0.769, 0.189},
        {0.349, 0.686, 0.168},
        {0.272, 0.534, 0.131}
    };

    ILayer curr = model.getMultiLayer().get(0);

    for (int y = 0; y < board.getHeight(); y++) {
      for (int x = 0; x < board.getWidth(); x++) {
        assertEquals(applyTransformation(board, x, y, matrix),
            curr.getImage().getPixelAt(x, y));
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDoesNotExist() {
    model.createLayer("first");
    model.setCurrent("second");
    model.loadImageToLayer(board);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAlreadyExists() {
    model.createLayer("first");
    model.createLayer("first");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTooBigBoard() {
    model.createLayer("first");
    model.setCurrent("first");
    model.loadImageToLayer(board);
    model.createLayer("second");
    model.setCurrent("second");
    model.loadImageToLayer(bigBoard);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoImage() {
    model.createLayer("first");
    model.blurLayer();
    model.sharpenLayer();
    model.sepiaLayer();
    model.greyscaleLayer();
  }

  @Test
  public void testGetTopVisibleImage() {
    model.createLayer("first");
    model.setCurrent("first");
    model.loadImageToLayer(board);
    model.blurLayer();

    model.createLayer("second");
    model.setCurrent("second");
    model.loadImageToLayer(board);

    model.setVisible("first");
    model.setInvisible("second");

    double[][] kernel = new double[][]{
        {(1 / 16.0), (1 / 8.0), (1 / 16.0)},
        {(1 / 8.0), (1 / 4.0), (1 / 8.0)},
        {(1 / 16.0), (1 / 8.0), (1 / 16.0)}
    };

    ImageState curr = model.getTopVisibleImage();

    for (int y = 0; y < board.getHeight(); y++) {
      for (int x = 0; x < board.getWidth(); x++) {
        assertEquals(applyFilter(board, x, y, kernel),
            curr.getPixelAt(x, y));
      }
    }

    model.setVisible("second");
    model.setCurrent("second");
    model.sepiaLayer();
    curr = model.getTopVisibleImage();
    double[][] matrix = {
        {0.393, 0.769, 0.189},
        {0.349, 0.686, 0.168},
        {0.272, 0.534, 0.131}
    };

    for (int y = 0; y < board.getHeight(); y++) {
      for (int x = 0; x < board.getWidth(); x++) {
        assertEquals(applyTransformation(board, x, y, matrix),
            curr.getPixelAt(x, y));
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyMultiLayer() {
    model.createLayer("first");
    model.createLayer("second");
    model.getMultiLayer();
  }

  @Test
  public void testCopyLayer() {
    model.createLayer("first");
    model.setCurrent("first");
    model.loadImageToLayer(board);
    model.copyLayer("first", "second");
    ILayer first = model.getMultiLayer().get(0);
    ILayer second = model.getMultiLayer().get(1);

    assertEquals(first.getVisibility(), second.getVisibility());

    for (int y = 0; y < first.getImage().getHeight(); y++) {
      for (int x = 0; x < first.getImage().getWidth(); x++) {
        assertEquals(first.getImage().getPixelAt(x, y),
            second.getImage().getPixelAt(x, y));
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLayerDoesNotExist() {
    model.createLayer("first");
    model.setCurrent("first");
    model.loadImageToLayer(board);
    model.copyLayer("zero", "second");
  }

  @Test
  public void testRemoveLayer() {
    model.createLayer("first");
    model.setCurrent("first");
    model.loadImageToLayer(board);
    model.createLayer("second");
    model.setCurrent("second");
    model.loadImageToLayer(board);

    assertEquals(2, model.getMultiLayer().size());

    model.removeLayer("first");
    assertEquals(1, model.getMultiLayer().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRemove() {
    model.createLayer("first");
    model.setCurrent("first");
    model.loadImageToLayer(board);
    model.createLayer("second");
    model.setCurrent("second");
    model.loadImageToLayer(board);
    model.removeLayer("third");
  }

  @Test
  public void testDownscale() {
    model.createLayer("first");
    model.setCurrent("first");
    model.loadImageToLayer(board);

    double xScale = 0.5;
    double yScale = 0.5;
    int newWidth = (int) (board.getWidth() * xScale);
    int newHeight = (int) (board.getHeight() * yScale);

    //downscale an image first to compare equality with the model's downscaled image
    List<List<IPixel>> newTemplate = new ArrayList<List<IPixel>>();
    ImageState newImage;
    for (int y = 0; y < newHeight; y++) {
      newTemplate.add(new ArrayList<>());
      for (int x = 0; x < newWidth; x++) {
        newTemplate.get(y).add(pixelDownscale(board, x, y, newWidth, newHeight));
      }
    }
    newImage = new Image(newTemplate);

    model.downscale(xScale, yScale);
    ILayer curr = model.getMultiLayer().get(0);

    for (int y = 0; y < newImage.getHeight(); y++) {
      for (int x = 0; x < newImage.getWidth(); x++) {
        assertEquals(newImage.getPixelAt(x, y),
            curr.getImage().getPixelAt(x, y));
      }
    }
  }

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

  @Test(expected = IllegalStateException.class)
  public void invalidDownScaleModel() {
    model.downscale(0.2, 0.2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidDownScale() {
    model.createLayer("first");
    model.setCurrent("first");
    model.loadImageToLayer(board);
    model.createLayer("second");
    model.setCurrent("second");
    model.loadImageToLayer(board);

    model.downscale(-1, 0.4);
    model.downscale(0.7, -2);
    model.downscale(5, 0.5);
    model.downscale(0.2, 4);
  }

  @Test
  public void downscaleMultiTest() {
    model.createLayer("first");
    model.setCurrent("first");
    model.loadImageToLayer(board);
    model.createLayer("second");
    model.setCurrent("second");
    model.loadImageToLayer(board);

    double xScale = 0.3;
    double yScale = 0.4;
    int newWidth = (int) (board.getWidth() * xScale);
    int newHeight = (int) (board.getHeight() * yScale);

    //downscale an image first to compare equality with the model's downscaled image
    List<List<IPixel>> newTemplate = new ArrayList<List<IPixel>>();
    ImageState newImage;
    for (int y = 0; y < newHeight; y++) {
      newTemplate.add(new ArrayList<>());
      for (int x = 0; x < newWidth; x++) {
        newTemplate.get(y).add(pixelDownscale(board, x, y, newWidth, newHeight));
      }
    }
    newImage = new Image(newTemplate);
    model.downscale(xScale, yScale);

    ILayer first = model.getMultiLayer().get(0);
    ILayer second = model.getMultiLayer().get(1);

    //check to see if both images were downscaled
    for (int y = 0; y < newImage.getHeight(); y++) {
      for (int x = 0; x < newImage.getWidth(); x++) {
        assertEquals(newImage.getPixelAt(x, y),
            first.getImage().getPixelAt(x, y));
      }
    }
    for (int y = 0; y < newImage.getHeight(); y++) {
      for (int x = 0; x < newImage.getWidth(); x++) {
        assertEquals(newImage.getPixelAt(x, y),
            second.getImage().getPixelAt(x, y));
      }
    }
  }
}