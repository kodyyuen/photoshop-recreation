package controller.command;

import image.ILayerModel;

/**
 * An interface that utilizes the command design pattern to represent the commands that can be
 * applied to the model. Each class that implements this interface represents a command; these
 * commands are called accordingly in the controller.
 */
public interface LayerCommand {

  /**
   * EFFECT: Applies the respective command to the given model.
   *
   * @param model the multi-layer model that is being manipulated
   */
  void apply(ILayerModel model);

}
