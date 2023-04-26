package controller.command;

import image.ILayerModel;

/**
 * Represents the visible command. This command sets the specified layer of the given model's
 * visibility to visible.
 */
public class Visible implements LayerCommand {

  private final String name;

  /**
   * Constructs the Visible command.
   *
   * @param name the name of the layer ot be set visible
   */
  public Visible(String name) {
    this.name = name;
  }

  @Override
  public void apply(ILayerModel model) {
    model.setVisible(name);
  }
}