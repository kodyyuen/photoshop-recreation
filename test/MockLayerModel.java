import image.CreateCheckerboard;
import image.IComplexLayerModel;
import image.ILayer;
import image.ImageState;
import image.Layer;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a mock layer model. It will take in inputs and hold them so that it confirms that the
 * controller works properly.
 */
public class MockLayerModel implements IComplexLayerModel {

  private final StringBuilder log;

  public MockLayerModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void setCurrent(String name) {
    log.append("setCurrent: " + name + "\n");
  }

  @Override
  public void createLayer(String name) {
    log.append("createLayer: " + name + "\n");
  }

  @Override
  public void copyLayer(String originalLayerName, String name) {
    log.append("copyLayer: " + originalLayerName + " " + name + "\n");
  }

  @Override
  public void loadImageToLayer(ImageState image) {
    log.append(
        "loadImageToLayer width and height: " + image.getWidth() + " " + image.getHeight() + "\n");
  }

  @Override
  public void removeLayer(String name) {
    log.append("removeLayer: " + name + "\n");
  }

  @Override
  public ImageState getTopVisibleImage() {
    log.append("getTopVisibleImage\n");
    return new CreateCheckerboard(5, 5).create();
  }

  @Override
  public List<ILayer> getMultiLayer() {
    log.append("getMultiLayer\n");
    List<ILayer> list = new ArrayList<>();
    list.add(new Layer("first", new CreateCheckerboard(5, 5).create(), true));
    return list;
  }

  @Override
  public void blurLayer() {
    log.append("blurLayer\n");
  }

  @Override
  public void sharpenLayer() {
    log.append("sharpenLayer\n");
  }

  @Override
  public void greyscaleLayer() {
    log.append("greyscaleLayer\n");
  }

  @Override
  public void sepiaLayer() {
    log.append("sepiaLayer\n");
  }

  @Override
  public void setVisible(String name) {
    log.append("setVisible: " + name + "\n");
  }

  @Override
  public void setInvisible(String name) {
    log.append("setInvisible: " + name + "\n");
  }

  @Override
  public void downscale(double xScale, double yScale) {
    log.append("downscale by: " + xScale + "and " + yScale + "\n");
  }

  @Override
  public void blur() {
    //uses ImageModel for this
  }

  @Override
  public void sharpen() {
    //uses ImageModel for this
  }

  @Override
  public void greyscale() {
    //uses ImageModel for this
  }

  @Override
  public void sepia() {
    //uses ImageModel for this
  }

  @Override
  public ImageState getOriginalImage() {
    //uses ImageModel for this
    return null;
  }

  @Override
  public ImageState getNewImage() {
    //uses ImageModel for this
    return null;
  }
}