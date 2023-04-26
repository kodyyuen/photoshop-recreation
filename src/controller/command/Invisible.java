package controller.command;

import image.ILayerModel;

/**
 * Represents the invisible command. This command sets the specified layer of the given model's
 * visibility to invisible.
 */
public class Invisible implements LayerCommand {

  private final String name;

  /**
   * Constructs the Invisible command.
   *
   * @param name the name of the layer to be set invisible
   */
  public Invisible(String name) {
    this.name = name;
  }

  @Override
  public void apply(ILayerModel model) {
    model.setInvisible(name);
  }
}