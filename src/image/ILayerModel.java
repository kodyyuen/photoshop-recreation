package image;

import java.util.List;

/**
 * An interface that represents a model of a a multi-layered image. A layered model is initialized
 * with an empty list of layers. This interface allows for thr manipulation and enhancement of
 * images within layers. A simpler model is extended which handles the manipulation of an image
 * itself.
 */
public interface ILayerModel extends IModel {

  /**
   * EFFECT: Sets the current layer of the LayerModel. The current layer is the layer that will be
   * manipulated or enhanced.
   *
   * @param name the name of the current layer as a string.
   * @throws IllegalArgumentException if the layer with the given name does not exist
   */
  void setCurrent(String name);

  /**
   * EFFECT: Creates a layer using the given name and an empty image. The user will then upload an
   * image to this empty layer. An illegal argument exception is thrown if the given name already
   * exists in the multi-layer.
   *
   * @param name the name of the layer.
   * @throws IllegalArgumentException if the layer exists already
   */
  void createLayer(String name);

  /**
   * Adds a copy of the given name's layer to the top of the multi-layered image. The new layer's
   * name is the second name given. To copy a layer, the original layer must have an image already
   * loaded into it.
   *
   * @param originalLayerName the name of the layer that is going to be copied
   * @param name              the name of the new copied layer
   * @throws IllegalArgumentException if the layer's name does not exist or an image has not been
   *                                  loaded into the original layer
   */
  void copyLayer(String originalLayerName, String name);

  /**
   * EFFECT: Uploads the given image to the current layer of the multi-layered image.
   *
   * @param image the image to be uploaded.
   * @throws IllegalArgumentException if the loaded image does not have compatible dimensions
   */
  void loadImageToLayer(ImageState image) throws IllegalArgumentException;

  /**
   * Removes the layer of the given name.
   *
   * @param name name of the layer
   * @throws IllegalArgumentException if the layer does not exist
   */
  void removeLayer(String name);

  /**
   * Retrieves the top-most visible image of a multi-layered image. This is the layer containing the
   * image that the user will be able to see. An illegal state exception is thrown if there are no
   * visible layers.
   *
   * @return the top-most visible image.
   * @throws IllegalStateException if there are no visible layers
   */
  ImageState getTopVisibleImage();

  /**
   * Creates a copy of the list of layers of a multi-layered image. A copy is created to prevent
   * outside mutation.
   *
   * @return a copy of the list of layers of a multi-layered image.
   * @throws IllegalArgumentException if any of the layers have not had an image loaded into it yet
   *                                  (has an empty image)
   */
  List<ILayer> getMultiLayer();

  /**
   * EFFECT: Applies the blur filter to the image of the current layer.
   */
  void blurLayer();

  /**
   * EFFECT: Applies the sharpen filter to the image of the current layer.
   */
  void sharpenLayer();

  /**
   * EFFECT: Applies the greyscale color transformation to the image of the current layer.
   */
  void greyscaleLayer();

  /**
   * EFFECT: Applies the sepia color transformation to the image of the current layer.
   */
  void sepiaLayer();

  /**
   * EFFECT: Updates the given layer's visibility to true.
   *
   * @param name the name of the layer to be set visible.
   */
  void setVisible(String name);

  /**
   * EFFECT: Updates the given layer's visibility to false.
   *
   * @param name the name of the layer to be set invisible.
   */
  void setInvisible(String name);
}
