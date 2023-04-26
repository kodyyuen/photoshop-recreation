import static org.junit.Assert.assertEquals;

import image.CreateCheckerboard;
import image.IPixel;
import image.Image;
import image.ImageState;
import image.Pixel;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing class to test the methods of the Image object.
 */
public class ImageTest {

  private ImageState board;
  private ImageState smallBoard;
  private ImageState rectangle;

  @Before
  public void initData() {
    board = new CreateCheckerboard(10, 10).create();
    smallBoard = new CreateCheckerboard(1, 2).create();

    //creating a rectangle image to test width and height
    List<List<IPixel>> rectangleTemplate = new ArrayList<List<IPixel>>();
    for (int i = 0; i < 5; i++) {
      rectangleTemplate.add(new ArrayList<>());
      for (int j = 0; j < 15; j++) {
        rectangleTemplate.get(i).add(new Pixel(255, 0, 0));
      }
    }

    rectangle = new Image(rectangleTemplate);

  }

  @Test
  public void getWidth() {
    assertEquals(100, board.getWidth());
    assertEquals(2, smallBoard.getWidth());
    assertEquals(15, rectangle.getWidth());
  }

  @Test
  public void getHeight() {
    assertEquals(100, board.getHeight());
    assertEquals(2, smallBoard.getWidth());
    assertEquals(5, rectangle.getHeight());
  }

  @Test
  public void getPixelAt() {
    assertEquals(new Pixel(220, 236, 253), board.getPixelAt(0, 0));
    assertEquals(new Pixel(253, 237, 220), board.getPixelAt(10, 0));
    assertEquals(new Pixel(220, 236, 253), smallBoard.getPixelAt(0, 0));
    assertEquals(new Pixel(253, 237, 220), smallBoard.getPixelAt(1, 0));
    assertEquals(new Pixel(253, 237, 220), smallBoard.getPixelAt(0, 1));
    assertEquals(new Pixel(220, 236, 253), smallBoard.getPixelAt(1, 1));

    for (int y = 0; y < rectangle.getHeight(); y++) {
      for (int x = 0; x < rectangle.getWidth(); x++) {
        assertEquals(new Pixel(255, 0, 0), rectangle.getPixelAt(x, y));
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetPixelAtSmall() {
    board.getPixelAt(-1, -2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetPixelAtBig() {
    board.getPixelAt(200, 300);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetPixelAtSmallAndBig() {
    board.getPixelAt(-200, 300);
  }

  @Test
  public void testGetTemplate() {
    for (int y = 0; y < board.getHeight(); y++) {
      for (int x = 0; x < board.getWidth(); x++) {
        assertEquals(board.getTemplate().get(y).get(x), board.getPixelAt(x, y));
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullTemplate() {
    new Image(null);
  }
}