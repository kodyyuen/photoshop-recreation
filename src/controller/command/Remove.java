package controller.command;

import image.ILayerModel;

/**
 * Represents the remove command. This command removes the given layer from the multi-layered
 * image.
 */
public class Remove implements LayerCommand {

  private final String name;

  /**
   * Constructs the Remove command.
   *
   * @param name the name of the given layer
   */
  public Remove(String name) {
    this.name = name;
  }

  @Override
  public void apply(ILayerModel model) {
    model.removeLayer(name);
  }
}