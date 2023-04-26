import static org.junit.Assert.assertEquals;

import java.awt.event.ActionEvent;
import org.junit.Before;
import org.junit.Test;

/**
 * Test to test the mock command listener.
 */
public class MockCommandListenerTest {

  private StringBuilder log;
  private MockCommandListener mock;

  @Before
  public void initData() {
    log = new StringBuilder();
    mock = new MockCommandListener(log);
  }

  @Test
  public void setClickedActionsTest() {
    mock.setClickedActions("test map");
    assertEquals("setClickedActions: test map", log.toString());
  }

  @Test
  public void actionPerformedTest() {
    mock.actionPerformed(new ActionEvent(new Object(), 0, "test event"));
    assertEquals("actionPerformed: test event", log.toString());
  }
}
