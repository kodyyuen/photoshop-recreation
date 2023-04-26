package image;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a class that creates a custom checkerboard with varying tile sizes and number of
 * tiles. The checkerboard has a preset color, but the user can customize how large each of its
 * tiles are and how many tiles are on each side of the board.
 */
public class CreateCheckerboard implements CreateImage {

  private final int tileSize;
  private final int numTiles;

  /**
   * Creates a checkerboard object with its given specifications. Its default colors are blue and
   * yellow.
   *
   * @param tileSize length of pixels of one side as the tile is a square
   * @param numTiles number of tiles in one side of the checkerboard
   */
  public CreateCheckerboard(int tileSize, int numTiles)
      throws IllegalArgumentException {
    if (tileSize < 1 || numTiles < 2) {
      throw new IllegalArgumentException(
          "Tile size must be at least 1 pixel and number of tiles must be at least 2");
    }
    this.tileSize = tileSize;
    this.numTiles = numTiles;
  }

  @Override
  public ImageState create() {
    List<List<IPixel>> newTemplate = new ArrayList<List<IPixel>>();

    IPixel color1 = new Pixel(220, 236, 253);
    IPixel color2 = new Pixel(253, 237, 220);

    for (int rowStart = 0; rowStart < numTiles; rowStart++) {
      createRow(rowStart, tileSize, numTiles, newTemplate, color1, color2);
    }
    return new Image(newTemplate);
  }

  //creates the row based on its given row starting number
  private void createRow(int rowStart, int tileSize, int numTiles, List<List<IPixel>> template,
      IPixel color1, IPixel color2) {

    IPixel[] even = {color1, color2};
    IPixel[] odd = {color2, color1};

    for (int k = rowStart * tileSize; k < (rowStart + 1) * tileSize; k++) {
      template.add(new ArrayList<IPixel>());
      for (int j = 0; j < numTiles; j++) {
        for (int i = 0; i < tileSize; i++) {
          if (rowStart % 2 == 0) {
            template.get(k).add(even[j % 2]);
          } else {
            template.get(k).add(odd[j % 2]);
          }
        }
      }
    }
  }
}