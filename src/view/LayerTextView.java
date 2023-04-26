package view;

import java.io.IOException;

/**
 * An interface that represents the text view of tbe user interaction to manipulate images. This
 * provides a way for the controller to communicate with the user with any errors they are making.a
 */
public class LayerTextView implements ILayerView {

  private final Appendable ap;

  /**
   * Constructs the text view of the user interaction.
   *
   * @param ap the appendable object that is written to.
   */
  public LayerTextView(Appendable ap) {
    if (ap == null) {
      throw new IllegalArgumentException("The appendable cannot be null");
    }
    this.ap = ap;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    ap.append(message);
  }
}