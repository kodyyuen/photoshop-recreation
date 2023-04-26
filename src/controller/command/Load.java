package controller.command;

import image.ILayerModel;
import image.IPixel;
import image.Image;
import image.ImageState;
import image.Pixel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 * Represents the load command. This command loads an image object to the specified layer of the
 * given model. The image object is given as a file which will be processed to convert it to an
 * image object.
 */
public class Load implements LayerCommand {

  private final String name;

  /**
   * Constructs the Load command.
   *
   * @param name the name of the specified layer
   * @throws IllegalArgumentException if the filepath is null
   */
  public Load(String name)
      throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("The file path cannot be null when loading an image");
    }
    this.name = name;
  }

  @Override
  public void apply(ILayerModel model) {
    ImageState image = processFile(name);
    model.loadImageToLayer(image);
  }

  /**
   * Takes the file path and delegates the file to a specific method depending on the file type to
   * convert the file into an image object. Supported file types are PPM, PNG, and JPG.
   *
   * @param name name of the filepath
   * @return the image object
   * @throws IllegalStateException if the file type is not supported
   */
  private ImageState processFile(String name) {
    File loadFile = new File(name);
    BufferedImage in;
    ImageState image;

    try {
      in = ImageIO.read(loadFile);
    } catch (IOException e) {
      throw new IllegalStateException("Error during reading while trying to load the file " + name);
    }
    if (in == null) {
      image = convertPPMToImage(name);
    } else {
      image = ImageUtils.convertBufferedToImage(in);
    }
    return image;
  }

  /**
   * Converts a PPM file to an image object.
   *
   * @param fileName the name of the file
   * @return image object after it is converted
   * @throws IllegalArgumentException if the filename was not found
   */
  private ImageState convertPPMToImage(String fileName) throws IllegalArgumentException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(fileName));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + fileName + " not found!");
    }

    String convertedFile = formatFile(sc);

    //now set up the scanner to read from the string we just built
    sc = new Scanner(convertedFile);
    return convertFileToImage(sc);
  }

  // converts the file into a string so it can be parsed
  private String formatFile(Scanner sc)
      throws IllegalArgumentException {
    StringBuilder builder = new StringBuilder();

    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }
    return builder.toString();
  }

  // converts the scanned file into an image object by parsing and appropriately
  // formatting its contents
  private ImageState convertFileToImage(Scanner sc)
      throws IllegalArgumentException {
    String token;
    List<List<IPixel>> importedImageTemplate = new ArrayList<List<IPixel>>();

    token = sc.next();

    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }

    int width = 0;
    int height = 0;
    int maxValue = 0;

    try {
      width = sc.nextInt();
      height = sc.nextInt();
      maxValue = sc.nextInt();
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          "Malformed file: There is no proper width and height or max value");
    }

    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Image dimensions cannot be less than 1");
    }

    for (int i = 0; i < height; i++) {
      importedImageTemplate.add(new ArrayList<IPixel>());
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        importedImageTemplate.get(i).add(new Pixel(r, g, b));
      }
    }
    return new Image(importedImageTemplate);
  }
}