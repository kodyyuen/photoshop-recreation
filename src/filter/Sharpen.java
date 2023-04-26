package filter;

/**
 * This filter holds a kernel that will be applied to an image to create a new image with
 * accentuated edges.
 */
public class Sharpen implements IFilter {

  private final double[][] kernel;
  private static final double[][] sharpenKernel = new double[][]{
      {(-1 / 8.0), (-1 / 8.0), (-1 / 8.0), (-1 / 8.0), (-1 / 8.0)},
      {(-1 / 8.0), (1 / 4.0), (1 / 4.0), (1 / 4.0), (-1 / 8.0)},
      {(-1 / 8.0), (1 / 4.0), (1.0), (1 / 4.0), (-1 / 8.0)},
      {(-1 / 8.0), (1 / 4.0), (1 / 4.0), (1 / 4.0), (-1 / 8.0)},
      {(-1 / 8.0), (-1 / 8.0), (-1 / 8.0), (-1 / 8.0), (-1 / 8.0)}
  };

  /**
   * Creates a Sharpen object that holds its specific kernel.
   */
  public Sharpen() {
    this.kernel = sharpenKernel;
  }

  @Override
  public double[][] getKernel() {
    return this.kernel;
  }
}