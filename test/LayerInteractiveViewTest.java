import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Testing class for the GUI.
 */
public class LayerInteractiveViewTest {

  private MockLayerInteractiveView view;
  private MockCommandListener listener;
  private StringBuilder log;

  @Before
  public void initData() {
    log = new StringBuilder();
    view = new MockLayerInteractiveView(log);
    listener = new MockCommandListener(log);
  }

  @Test
  public void addActionListenerViewTest() {
    view.addActionListener(listener);
    assertEquals("addActionListener: MockCommandListener", log.toString());
  }

  @Test
  public void emitEventTest() {
    view.emitEvent(listener);
    assertEquals("actionPerformed: emit event worked", log.toString());
  }
}
