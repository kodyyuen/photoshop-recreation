import static org.junit.Assert.assertEquals;

import controller.command.ImageUtils;
import image.CreateCheckerboard;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing class for the Mock GUI.
 */
public class MockLayerInteractiveViewTest {

  private StringBuilder log;
  private MockLayerInteractiveView mock;
  private MockCommandListener commandListener;

  @Before
  public void initData() {
    log = new StringBuilder();
    mock = new MockLayerInteractiveView(log);
    commandListener = new MockCommandListener(log);
  }

  @Test
  public void addActionListenerTest() {
    mock.addActionListener(commandListener);
    assertEquals("addActionListener: MockCommandListener", log.toString());
  }

  @Test
  public void getInputFieldTest() {
    assertEquals("getInputField successful", mock.getInputField());
  }

  @Test
  public void getLoadFileTest() {
    assertEquals("getLoadFile successful", mock.getLoadFile());
  }

  @Test
  public void getMultiLoadFileTest() {
    assertEquals("getMultiLoadFile successful", mock.getMultiLoadFile());
  }

  @Test
  public void getSaveFileTest() {
    assertEquals("getSaveFile successful", mock.getSaveFile().get(0));
  }

  @Test
  public void getSaveDirectoryTest() {
    assertEquals("getSaveDirectory successful", mock.getSaveDirectory().get(0));
  }

  @Test
  public void displayImageTest() {
    mock.displayImage(ImageUtils.imageToBuffered(new CreateCheckerboard(5, 5).create()));
    assertEquals("displayImage successful", log.toString());
  }

  @Test
  public void renderMessageTest() throws IOException {
    mock.renderMessage("test");
    assertEquals("renderMessage: test", log.toString());
  }
}
