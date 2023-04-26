import image.IPixel;
import image.Image;
import image.ImageState;
import image.Pixel;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a mock class that can convert between files and image objects for PPM files.
 */
public class MockPPMImage {

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   */
  public ImageState read(String filename)
      throws IllegalArgumentException {
    Scanner sc = new Scanner(new StringReader(filename));

    String convertedFile = convertFileToString(sc);

    //now set up the scanner to read from the string we just built
    sc = new Scanner(convertedFile);
    return convertToImage(sc);
  }

  // converts the file into a string so it can be parsed
  private String convertFileToString(Scanner sc)
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
  private ImageState convertToImage(Scanner sc)
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
      throw new IllegalArgumentException("Malformed file: There is no proper width and height");
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

  /**
   * Converts the given image object into a valid format and writes its content to the output file.
   */
  public String write(ImageState image) {

    StringBuilder log = new StringBuilder();

    log.append("P3\n").append(image.getWidth()).append(" ").append(image.getHeight()).append("\n")
        .append(255).append("\n");

    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        log.append(image.getPixelAt(x, y).getRed()).append("\n")
            .append(image.getPixelAt(x, y).getGreen()).append("\n")
            .append(image.getPixelAt(x, y).getBlue()).append("\n");
      }
    }
    return log.toString();
  }
}

