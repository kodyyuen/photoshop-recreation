import org.junit.Test;

/**
 * Testing class for the main method.
 */
public class MainTest {

  @Test(expected = IllegalArgumentException.class)
  public void testScriptErrorMain() {
    String[] args = {"-script", "test", "test2"};
    Main.main(args);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testErrorDefaultMain() {
    String[] args = {"-test"};
    Main.main(args);
  }

}
