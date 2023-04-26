import java.io.IOException;
import org.junit.Test;

/**
 * Tests the functionality of the TestAppendable class which was created to ensure that the render
 * methods were properly throwing their intended IOExceptions.
 */
public class TestAppendableTest {

  @Test(expected = IOException.class)
  public void testAppend() throws IOException {
    TestAppendable ans = new TestAppendable();
    ans.append("abc");
  }
}