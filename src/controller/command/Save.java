package controller.command;

import image.ILayerModel;
import image.ImageState;

/**
 * Represents the save command. This command saves the top visible layer of the given multi-layered
 * image model.
 */
public class Save extends AbstractSave {

  private final String fileType;
  private final String filePath;

  /**
   * Constructs the Save command.
   *
   * @param filePath the file path of the layer
   * @param fileType the type of file the image object will be written to
   */
  public Save(String filePath, String fileType) {
    this.filePath = filePath;
    this.fileType = fileType;
  }

  @Override
  public void apply(ILayerModel model) throws IllegalStateException {
    ImageState currentImage = model.getTopVisibleImage();

    switch (fileType) {
      case "ppm":
        super.savePPMImage(currentImage, filePath);
        break;
      case "png":
      case "jpg":
        super.saveBufferedImage(currentImage, fileType, filePath);
        break;
      default:
        //defaults to jpg if no valid option was picked
        super.saveBufferedImage(currentImage, "jpg", filePath);
        break;
    }
  }
}
