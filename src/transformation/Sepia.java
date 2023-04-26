package transformation;

/**
 * The color transformation sepia tone has the characteristics of photographs taken in the 19th and
 * early 20th century with a reddish brown tone. This color transformation uses a matrix that will
 * be applied to each pixel of an image in order to transform its colors to sepia-toned hues.
 */
public class Sepia implements ITransformation {

  private final double[][] matrix;
  private static final double[][] sepiaMatrix = {
      {0.393, 0.769, 0.189},
      {0.349, 0.686, 0.168},
      {0.272, 0.534, 0.131}
  };

  /**
   * Creates a Sepia object that holds its specific matrix.
   */
  public Sepia() {
    this.matrix = sepiaMatrix;
  }

  @Override
  public double[][] getMatrix() {
    return this.matrix;
  }
}
