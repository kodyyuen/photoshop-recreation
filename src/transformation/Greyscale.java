package transformation;

/**
 * The color transformation greyscale is composed only of shades of grey. This color transformation
 * uses a matrix that will be applied to each pixel of an image in order to transform its colors to
 * shades of grey.
 */
public class Greyscale implements ITransformation {

  private final double[][] matrix;
  private static final double[][] greyscaleMatrix = {
      {0.2126, 0.7152, 0.0722},
      {0.2126, 0.7152, 0.0722},
      {0.2126, 0.7152, 0.0722}
  };

  /**
   * Creates a Greyscale object that holds its specific matrix.
   */
  public Greyscale() {
    this.matrix = greyscaleMatrix;
  }

  @Override
  public double[][] getMatrix() {
    return this.matrix;
  }
}
