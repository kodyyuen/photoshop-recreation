package filter;

/**
 * This interface represents the methods a filter would need for it to be applied to an image. Each
 * filter's kernel is unique and can be imagined as a 3x3 grid. A filter is currently either a blur
 * or a sharpen.
 */
public interface IFilter {

  /**
   * Gets the kernel of the given filter.
   *
   * @return a 2d array representation of the kernel
   */
  double[][] getKernel();

}