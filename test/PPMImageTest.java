import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import image.ImageState;
import image.Pixel;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing class that ensures that the read and write methods of the PPMImage class are being parsed
 * and formatted correctly.
 */
public class PPMImageTest {

  private MockPPMImage ppm = new MockPPMImage();
  private StringBuilder mockFile = new StringBuilder();
  private StringBuilder malformedPPM = new StringBuilder();
  private ImageState image;

  @Before
  public void initData() {
    mockFile.append("P3\n3 3\n255");
    for (int i = 0; i < 9; i++) {
      mockFile.append("\n150\n128\n200");
    }

    malformedPPM.append("P6\n3 3\n255");
    for (int i = 0; i < 9; i++) {
      malformedPPM.append("\n150\n128\n200");
    }
  }

  @Test
  //this test demonstrates that reading from the file will properly parse the PPM format and convert
  //its values into a correct image object
  public void readTest() {
    image = ppm.read(mockFile.toString());
    assertEquals(3, image.getWidth());
    assertEquals(3, image.getHeight());
    assertEquals(new Pixel(150, 128, 200), image.getPixelAt(1, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void readInvalidPPM() {
    image = ppm.read(malformedPPM.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void readInvalidWidth() {
    image = ppm.read("P3\n0 5\n255");
  }

  @Test(expected = IllegalArgumentException.class)
  public void readInvalidHeight() {
    image = ppm.read("P3\n5 -2\n255");
  }

  @Test
  //this test is demonstrating that writing to the file will properly format the image into the PPM
  //format where it begins with P3, width, height, max pixel value, then each channel value of
  //its pixels
  public void writeTest() {
    image = ppm.read(mockFile.toString());
    String log = ppm.write(image);
    assertTrue(log.contains("P3\n3 3\n255\n"));
    //11 characters from the beginning format, 12 characters per pixel and 9 pixels (3x3)
    assertEquals(119, log.length());
  }
}