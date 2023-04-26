import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import image.CreateCheckerboard;
import image.EmptyImage;
import image.ILayer;
import image.ImageState;
import image.Layer;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing class that tests the functionality of the Layer class.
 */
public class LayerTest {

  private ImageState board;
  private ImageState empty;

  @Before
  public void initData() {
    board = new CreateCheckerboard(1, 5).create();
    empty = new EmptyImage();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull() {
    new Layer(null, empty, false);
    new Layer("layer", null, true);
    new Layer(null, null, false);
  }

  @Test
  public void testName() {
    ILayer layer = new Layer("layer1", board, true);
    assertEquals("layer1", layer.getName());

    ILayer layer2 = new Layer("", board, true);
    assertEquals("", layer2.getName());
  }

  @Test
  public void testImage() {
    ILayer layer = new Layer("layer1", board, true);

    for (int y = 0; y < board.getHeight(); y++) {
      for (int x = 0; x < board.getWidth(); x++) {
        assertEquals(board.getPixelAt(x, y),
            layer.getImage().getPixelAt(x, y));
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyImage() {
    ILayer layer = new Layer("layer1", empty, false);
    assertEquals(empty, layer.getImage());
  }

  @Test
  public void testGetVis() {
    ILayer layer = new Layer("layer1", empty, false);
    ILayer layer2 = new Layer("layer2", board, true);
    ILayer layer3 = new Layer("layer3", empty, false);

    assertFalse(layer.getVisibility());
    assertTrue(layer2.getVisibility());
    assertFalse(layer3.getVisibility());
  }
}
