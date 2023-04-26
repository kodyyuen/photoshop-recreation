package view;

import java.io.IOException;

/**
 * An interface used to represent different views of images and interactions with the user. This
 * interface renders messages by writing to an Appendable object in the Controller class.
 */
public interface ILayerView {

  /**
   * EFFECT: Renders a specific message to the provided data destination.
   *
   * @param message the message to be transmitted
   * @throws IOException if transmission to the provided data destination fails.
   */
  void renderMessage(String message) throws IOException;
}
