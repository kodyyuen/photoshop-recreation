package controller.command;

import image.ILayerModel;

/**
 * Represents the create command. This command creates a new layer with an empty image in the image
 * model.
 */
public class Create implements LayerCommand {

  private final String name;

  /**
   * Constructs the create command.
   *
   * @param name the name of the new layer
   */
  public Create(String name) {
    this.name = name;
  }

  @Override
  public void apply(ILayerModel model) {
    model.createLayer(name);
  }
}