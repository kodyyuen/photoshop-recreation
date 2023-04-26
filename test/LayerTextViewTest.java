import static org.junit.Assert.assertEquals;

import java.io.IOException;
import org.junit.Test;
import view.ILayerView;
import view.LayerTextView;

/**
 * Testing class to test the view and its functionality.
 */
public class LayerTextViewTest {

  @Test
  public void renderMessageTest() throws IOException {
    StringBuilder ap = new StringBuilder();
    ILayerView view = new LayerTextView(ap);

    view.renderMessage("message");
    assertEquals("message", ap.toString());

    StringBuilder ap2 = new StringBuilder();
    view = new LayerTextView(ap2);
    view.renderMessage("hello world");
    assertEquals("hello world", ap2.toString());
  }

  @Test(expected = IOException.class)
  public void renderMessageThrowIO() throws IOException {
    TestAppendable fakeAP = new TestAppendable();
    ILayerView view = new LayerTextView(fakeAP);
    view.renderMessage("message");
    view.renderMessage("hello world");
  }

}
