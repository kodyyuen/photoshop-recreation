package transformation;

/**
 * This interface represents the methods a transformation would need for it to be applied to an
 * image. Each transformation's kernel is unique and can be imagined as a 3x3 grid. A transformation
 * is currently either a greyscale or a sepia.
 */
public interface ITransformation {

  /**
   * Gets the matrix of the given transformation.
   *
   * @return a 2d array representation of the matrix
   */
  double[][] getMatrix();

}