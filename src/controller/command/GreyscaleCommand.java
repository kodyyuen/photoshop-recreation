package controller.command;

import image.ILayerModel;

/**
 * Represents the greyscale color transformation as a command. This command applies the greyscale
 * transformation to the current layer's image of the given model. The image will appear in shades
 * of grey once this command is called.
 */
public class GreyscaleCommand implements LayerCommand {

  @Override
  public void apply(ILayerModel model) {
    model.greyscaleLayer();
  }
}