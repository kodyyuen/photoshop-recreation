import controller.InteractiveController;
import org.junit.Test;

/**
 * Tests the interactive controller.
 */
public class LayerInteractiveControllerTest {

  @Test(expected = IllegalArgumentException.class)
  public void nullConstructor() {
    new InteractiveController(null, null);
  }
}
