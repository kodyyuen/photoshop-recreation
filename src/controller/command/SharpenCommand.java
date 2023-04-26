package controller.command;

import image.ILayerModel;

/**
 * Represents the sharpen filter as a command. This command applies the sharpen filter to the
 * current layer's image of the given model. The image will appear sharpened once this command is
 * called.
 */
public class SharpenCommand implements LayerCommand {

  @Override
  public void apply(ILayerModel model) {
    model.sharpenLayer();
  }
}