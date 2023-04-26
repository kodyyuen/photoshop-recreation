import java.io.IOException;

/**
 * Represents an Appendable class that throws an IOException when append() is used. Used for testing
 * purposes.
 */
public class TestAppendable implements Appendable {

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException();
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException();
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException();
  }
}
