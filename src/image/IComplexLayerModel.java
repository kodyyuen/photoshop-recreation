package image;

/**
 * Represents a layer model that has additional features. This new model can support downscaling
 * multi-layered images as well as its previous features such as image filtering and transforming
 * and manipulating layers.
 */
public interface IComplexLayerModel extends ILayerModel {

  /**
   * This method mutates the multi-layered image to downscale all layers. It sets the new default
   * width and height to the new downscaled dimensions.
   *
   * @param xScale the scale of how much the width will be reduced
   * @param yScale the scale of how much the width will be reduced
   * @throws IllegalStateException    if any of the layers do not have an image loaded into them
   * @throws IllegalArgumentException if the scales are not between 0 and 1, exclusive
   */
  void downscale(double xScale, double yScale);
}