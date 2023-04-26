package controller.command;

import image.ILayerModel;

/**
 * Represents the sepia color transformation as a command. This command applies the sepia
 * transformation to the current layer's image of the given model. THe image will appear with the
 * respective tones once this command is called.
 */
public class SepiaCommand implements LayerCommand {

  @Override
  public void apply(ILayerModel model) {
    model.sepiaLayer();
  }
}