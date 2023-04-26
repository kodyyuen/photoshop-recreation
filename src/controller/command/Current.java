package controller.command;

import image.ILayerModel;

/**
 * Represents the current command. This command sets the current layer of the model. The current
 * layer is the layer that will be manipulated or enhanced.
 */
public class Current implements LayerCommand {

  private final String name;

  /**
   * Constructs the Current command.
   *
   * @param name the name of the current layer
   */
  public Current(String name) {
    this.name = name;
  }

  @Override
  public void apply(ILayerModel model) {
    model.setCurrent(name);
  }
}