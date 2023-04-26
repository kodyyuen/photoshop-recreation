import static org.junit.Assert.assertEquals;

import image.CreateCheckerboard;
import image.ILayer;
import image.ImageState;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the functionality of the mock model which was created to ensure the validity of the
 * controller.
 */
public class MockLayerModelTest {

  private StringBuilder log;
  private MockLayerModel mock;

  @Before
  public void initData() {
    log = new StringBuilder();
    mock = new MockLayerModel(log);
  }

  @Test
  public void setCurrentTest() {
    mock.setCurrent("first");
    assertEquals("setCurrent: first\n", log.toString());
  }

  @Test
  public void createLayerTest() {
    mock.createLayer("first");
    assertEquals("createLayer: first\n", log.toString());
  }

  @Test
  public void loadImageToLayerTest() {
    mock.loadImageToLayer(new CreateCheckerboard(5, 5).create());
    assertEquals("loadImageToLayer width and height: " + 25 + " " + 25 + "\n", log.toString());
  }

  @Test
  public void getTopVisibleImageTest() {
    ImageState image = mock.getTopVisibleImage();
    assertEquals("getTopVisibleImage\n", log.toString());
    assertEquals(25, image.getWidth());
    assertEquals(25, image.getHeight());
  }

  @Test
  public void getMultiLayerTest() {
    List<ILayer> list = mock.getMultiLayer();
    assertEquals("getMultiLayer\n", log.toString());
    assertEquals(1, list.size());
  }

  @Test
  public void blurLayerTest() {
    mock.blurLayer();
    assertEquals("blurLayer\n", log.toString());
  }

  @Test
  public void sharpenLayerTest() {
    mock.sharpenLayer();
    assertEquals("sharpenLayer\n", log.toString());
  }

  @Test
  public void greyscaleLayerTest() {
    mock.greyscaleLayer();
    assertEquals("greyscaleLayer\n", log.toString());
  }

  @Test
  public void sepiaLayerTest() {
    mock.sepiaLayer();
    assertEquals("sepiaLayer\n", log.toString());
  }

  @Test
  public void setVisibleTest() {
    mock.setVisible("first");
    assertEquals("setVisible: first\n", log.toString());
  }

  @Test
  public void setInvisibleTest() {
    mock.setInvisible("first");
    assertEquals("setInvisible: first\n", log.toString());
  }

  @Test
  public void removeLayerTest() {
    mock.removeLayer("first");
    assertEquals("removeLayer: " + "first" + "\n", log.toString());
  }

  @Test
  public void copyLayerTest() {
    mock.copyLayer("first", "second");
    assertEquals("copyLayer: " + "first" + " " + "second" + "\n", log.toString());
  }

  @Test
  public void downscaleTest() {
    mock.downscale(0.5, 0.7);
    assertEquals("downscale by: " + "0.5" + "and " + "0.7" + "\n", log.toString());
  }
}
