package filter;

/**
 * This filter holds a kernel that will be applied to an image to create a new image with blurred
 * pixels.
 */
public class Blur implements IFilter {

  private final double[][] kernel;
  private static final double[][] blurKernel = new double[][]{
      {(1 / 16.0), (1 / 8.0), (1 / 16.0)},
      {(1 / 8.0), (1 / 4.0), (1 / 8.0)},
      {(1 / 16.0), (1 / 8.0), (1 / 16.0)}
  };

  /**
   * Creates a Blur object that holds its specific kernel.
   */
  public Blur() {
    this.kernel = blurKernel;
  }

  @Override
  public double[][] getKernel() {
    return this.kernel;
  }
}