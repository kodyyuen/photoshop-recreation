import static org.junit.Assert.assertEquals;

import image.IPixel;
import image.Pixel;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing class to test the Pixel methods.
 */
public class PixelTest {

  private IPixel zeroPixel;
  private IPixel maxPixel;
  private IPixel randomPixel;

  @Before
  public void initData() {
    zeroPixel = new Pixel(0, 0, 0);
    maxPixel = new Pixel(255, 255, 255);
    randomPixel = new Pixel(12, 25, 56);
  }

  @Test
  public void getRed() {
    assertEquals(0, zeroPixel.getRed());
    assertEquals(255, maxPixel.getRed());
    assertEquals(12, randomPixel.getRed());
  }

  @Test
  public void getGreen() {
    assertEquals(0, zeroPixel.getGreen());
    assertEquals(255, maxPixel.getGreen());
    assertEquals(25, randomPixel.getGreen());
  }

  @Test
  public void getBlue() {
    assertEquals(0, zeroPixel.getBlue());
    assertEquals(255, maxPixel.getBlue());
    assertEquals(56, randomPixel.getBlue());
  }

  @Test
  public void clampTestRedLow() {
    IPixel clampPixel = new Pixel(-5, 25, 15);
    assertEquals(new Pixel(0, 25, 15), clampPixel);
  }

  @Test
  public void clampTestGreenLow() {
    IPixel clampPixel = new Pixel(25, -5, 15);
    assertEquals(new Pixel(25, 0, 15), clampPixel);
  }

  @Test
  public void clampTestBlueLow() {
    IPixel clampPixel = new Pixel(5, 25, -15);
    assertEquals(new Pixel(5, 25, 0), clampPixel);
  }

  @Test
  public void clampTestPixelLow() {
    IPixel clampPixel = new Pixel(-5, -25, -15);
    assertEquals(new Pixel(0, 0, 0), clampPixel);
  }

  @Test
  public void clampTestRedHigh() {
    IPixel clampPixel = new Pixel(256, 25, 15);
    assertEquals(new Pixel(255, 25, 15), clampPixel);
  }

  @Test
  public void clampTestGreenHigh() {
    IPixel clampPixel = new Pixel(25, 300, 15);
    assertEquals(new Pixel(25, 255, 15), clampPixel);
  }

  @Test
  public void clampTestBlueHigh() {
    IPixel clampPixel = new Pixel(5, 25, 1010);
    assertEquals(new Pixel(5, 25, 255), clampPixel);
  }

  @Test
  public void clampTestPixelHigh() {
    IPixel clampPixel = new Pixel(555, 625, 915);
    assertEquals(new Pixel(255, 255, 255), clampPixel);
  }

  @Test
  public void clampTestPixelCombo() {
    IPixel clampPixel = new Pixel(555, -625, 915);
    assertEquals(new Pixel(255, 0, 255), clampPixel);
  }

  @Test
  public void equalsTest() {
    assertEquals(new Pixel(12, 24, 36), new Pixel(12, 24, 36));
  }

  @Test
  public void hashCodeTest() {
    assertEquals(new Pixel(12, 24, 36).hashCode(),
        new Pixel(12, 24, 36).hashCode());
  }
}