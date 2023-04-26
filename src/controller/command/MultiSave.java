package controller.command;

import image.ILayer;
import image.ILayerModel;
import image.ImageState;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Represents the multisave command. This command saves the image as a collection of files with one
 * for each layer (as regular images) and one (text) file that stores the locations of all the layer
 * files.
 */
public class MultiSave extends AbstractSave {

  private String folderpath;
  private String fileType;
  private List<ILayer> multi;

  /**
   * Constructs the MultiSave command.
   *
   * @param fileType the file type of the layer files
   */
  public MultiSave(String folderpath, String fileType) {
    this.folderpath = folderpath;
    this.fileType = fileType;
  }

  @Override
  public void apply(ILayerModel model) {
    this.multi = model.getMultiLayer();
    File folder = new File(folderpath);

    try {
      folder.mkdir();
    } catch (SecurityException e) {
      throw new IllegalArgumentException("Folder path cannot be created");
    }

    switch (fileType) {
      case "ppm":
        saveMultiPPM();
        break;
      case "png":
      case "jpg":
        saveMultiBufferedImage();
        break;
      default:
        //defaults the file type to jpg
        fileType = "jpg";
        saveMultiBufferedImage();
        break;
    }
    saveScript(folder);
  }

  /**
   * EFFECT: Saves all layers' image objects of the multi-layered image. Each layer will be saved as
   * an individual PPM file.
   */
  private void saveMultiPPM() {
    ImageState image;
    for (int i = 0; i < multi.size(); i++) {
      image = multi.get(i).getImage();
      super.savePPMImage(image, folderpath + "/layer-" + (i + 1));
    }
  }

  /**
   * EFFECT: Saves all layers' image objects of the multi-layered image. Each layer will be saved as
   * an individual PNG/JPG file.
   */
  private void saveMultiBufferedImage() {
    ImageState image;
    for (int i = 0; i < multi.size(); i++) {
      image = multi.get(i).getImage();
      super.saveBufferedImage(image, fileType, folderpath + "/layer-" + (i + 1));
    }
  }

  /**
   * EFFECT: Saves the script for the multi-layered image to be used when using multiload. Each
   * layer has its state saved, including its name, image, and visibility status. A multisave cannot
   * be completed if any of the layers have not had an image loaded into it / has an empty image.
   *
   * @throws IllegalArgumentException if the filepath is invalid
   */
  private void saveScript(File folder)
      throws IllegalArgumentException {
    String textPath = "multi-layer.txt";
    FileWriter out;
    String layerName;
    String layerPath;
    boolean layerVisibility;

    try {
      out = new FileWriter(new File(folder, textPath));

      for (int i = 0; i < multi.size(); i++) {
        layerName = multi.get(i).getName();
        layerPath = folderpath + "/layer-" + (i + 1) + "." + fileType;
        layerVisibility = multi.get(i).getVisibility();

        out.write("create " + layerName + "\n"
            + "current " + layerName + "\n"
            + "load " + layerPath + "\n");

        if (!layerVisibility) {
          out.write("invisible " + layerName + "\n");
        }
      }

      out.close();

    } catch (IOException e) {
      throw new IllegalArgumentException(
          folderpath + " is not a valid file, cannot be created, or cannot be opened");
    }
  }
}