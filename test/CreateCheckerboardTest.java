import static org.junit.Assert.assertEquals;

import image.CreateCheckerboard;
import image.IPixel;
import image.ImageState;
import image.Pixel;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing class to test that the checkerboard is being created correctly in terms of dimensions and
 * colors.
 */
public class CreateCheckerboardTest {

  private ImageState board;

  @Before
  public void initData() {
    board = new CreateCheckerboard(1, 5).create();
  }

  @Test
  public void testDimensions() {
    assertEquals(5, board.getHeight());
    assertEquals(5, board.getWidth());
  }

  @Test
  public void testAlternatingColors() {
    IPixel color1 = new Pixel(220, 236, 253);
    IPixel color2 = new Pixel(253, 237, 220);

    for (int y = 0; y < board.getHeight(); y++) {
      for (int x = 0; x < board.getWidth(); x++) {
        if (y % 2 == 0 && x % 2 == 0 || y % 2 != 0 && x % 2 != 0) {
          assertEquals(color1, board.getPixelAt(x, y));
        } else {
          assertEquals(color2, board.getPixelAt(x, y));
        }
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidTileSize() {
    new CreateCheckerboard(0, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidTileSizeNegative() {
    new CreateCheckerboard(-1, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidNumTiles() {
    new CreateCheckerboard(10, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidNumTilesZero() {
    new CreateCheckerboard(10, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidNumTilesNegative() {
    new CreateCheckerboard(10, -5);
  }
}