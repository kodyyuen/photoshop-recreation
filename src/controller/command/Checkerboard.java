package controller.command;

import image.CreateCheckerboard;
import image.ILayerModel;

/**
 * Represents the checkerboard command. This command creates an image object of a checkerboard and
 * loads the image object to a new layer of the image model.
 */
public class Checkerboard implements LayerCommand {

  private final int tileSize;
  private final int numTiles;

  /**
   * Constructs the Checkerboard command; the given integers are used to construct the image object
   * of a checkerboard.
   *
   * @param tileSize the size of the tiles of the checkerboard image object
   * @param numTiles the number of tiles of the checkerboard image object
   */
  public Checkerboard(int tileSize, int numTiles) {
    this.tileSize = tileSize;
    this.numTiles = numTiles;
  }

  @Override
  public void apply(ILayerModel model) {
    model.loadImageToLayer(new CreateCheckerboard(tileSize, numTiles).create());
  }
}
