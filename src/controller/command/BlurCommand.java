package controller.command;

import image.ILayerModel;

/**
 * Represents the blur filter as a command. This command applies the blur filter to the current
 * layer's image of the given model. The image will appear blurred once this command is called.
 */
public class BlurCommand implements LayerCommand {

  @Override
  public void apply(ILayerModel model) {
    model.blurLayer();
  }
}