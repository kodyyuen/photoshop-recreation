package controller.command;

import image.ILayerModel;

/**
 * Represents the copy command. This command gets the given layer name and creates a new layer with
 * the given name. The new layer is a copy of the original layer. The new layer is added to the
 * end.
 */
public class Copy implements LayerCommand {

  private final String original;
  private final String name;

  /**
   * Constructs the Copy command.
   *
   * @param original the name of the layer to be copied
   * @param name     the name of the copied layer
   */
  public Copy(String original, String name) {
    this.original = original;
    this.name = name;
  }

  @Override
  public void apply(ILayerModel model) {
    model.copyLayer(original, name);
  }
}