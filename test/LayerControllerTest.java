import static org.junit.Assert.assertEquals;

import controller.ILayerController;
import controller.LayerController;
import image.ILayerModel;
import image.LayerModel;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;
import view.ILayerView;
import view.LayerTextView;

/**
 * Tests the controller of the LayerModel and if its inputs are correctly corresponding to the
 * intended commands and if the commands are working properly.
 */
public class LayerControllerTest {

  private ILayerController controller;
  private ILayerModel model;
  private StringReader in;
  private StringBuilder out;
  private ILayerView view;

  @Before
  public void initData() {
    in = new StringReader("");
    out = new StringBuilder();
    view = new LayerTextView(out);
    model = new LayerModel();
    controller = new LayerController(model, in, view);
  }

  @Test
  //an invalid input will never be possible as it is handled by the main method
  public void invalidModeTest() {
    in = new StringReader("a");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("", out.toString());
  }

  @Test
  public void invalidScriptTest() {
    in = new StringReader("-script test -script test");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("The file location test for this script is invalid. Try again: ", out.toString());
  }

  @Test
  public void createTest() {
    in = new StringReader("-text create first");
    controller = new LayerController(model, in, view);
    controller.start();
    //only errors are displayed, if there are no errors then only the opening message
    //will be displayed
    assertEquals("", out.toString());
  }

  @Test
  public void createErrorTest() {
    in = new StringReader("-text create first create first");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("A layer first already exists. Try again: ", out.toString());
  }

  @Test
  public void currentTest() {
    in = new StringReader("-text create first current first");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("", out.toString());
  }

  @Test
  public void currentErrorTest() {
    in = new StringReader("-text current first");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("The layer first does not exist. Try again: ", out.toString());
  }

  @Test
  public void savePPMTest() {
    in = new StringReader("-text create first current first checkerboard 50 5 save abcd ppm");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals(
        "",
        out.toString());
  }

  @Test
  public void saveJPGTest() {
    in = new StringReader("-text create first current first checkerboard 50 5 save test png");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("", out.toString());
  }

  @Test
  public void savePNGErrorTest() {
    in = new StringReader("-text create first current first checkerboard 50 5 save test png");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("", out.toString());
  }

  @Test
  public void loadTest() {
    in = new StringReader("-text create first current first load test.png");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals(
        "",
        out.toString());
  }

  @Test
  public void checkerboardTest() {
    in = new StringReader("-text create first current first");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("", out.toString());
  }

  @Test
  public void checkerboardErrorTest() {
    in = new StringReader("-text create first current first checkerboard 1 1");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("Tile size must be at least 1 pixel"
        + " and number of tiles must be at least 2. Try again: ", out.toString());
  }

  @Test
  public void checkerboardErrorLetterTest() {
    in = new StringReader("-text create first current first checkerboard a a");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("null\n"
            + "a is not a valid command. Try again: a is not a valid command. Try again: ",
        out.toString());
  }

  @Test
  public void blurTest() {
    in = new StringReader("-text create first current first checkerboard 5 5 blur");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("", out.toString());
  }

  @Test
  public void sharpenTest() {
    in = new StringReader("-text create first current first checkerboard 5 5 sharpen");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("", out.toString());
  }

  @Test
  public void greyscaleTest() {
    in = new StringReader("-text create first current first checkerboard 5 5 greyscale");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("", out.toString());
  }

  @Test
  public void sepiaTest() {
    in = new StringReader("-text create first current first checkerboard 5 5 sepia");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("", out.toString());
  }

  @Test
  public void blurErrorTest() {
    in = new StringReader("-text create first current first blur");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("The image cannot be null. Try again: ",
        out.toString());
  }

  @Test
  public void sharpenErrorTest() {
    in = new StringReader("-text create first current first sharpen");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("The image cannot be null. Try again: ",
        out.toString());
  }

  @Test
  public void greyscaleErrorTest() {
    in = new StringReader("-text create first current first greyscale");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("The image cannot be null. Try again: ",
        out.toString());
  }

  @Test
  public void sepiaErrorTest() {
    in = new StringReader("-text create first current first sepia");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("The image cannot be null. Try again: ",
        out.toString());
  }

  @Test
  public void visibleTest() {
    in = new StringReader("-text create first current first checkerboard 50 5 invisible"
        + " first visible first");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("", out.toString());
  }

  @Test
  public void invisibleTest() {
    in = new StringReader("-text create first current first checkerboard 50 5 invisible first");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("", out.toString());
  }

  @Test
  public void visibleErrorTest() {
    in = new StringReader("-text create first current first visible second");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("The layer second does not exist."
        + " Try again: ", out.toString());
  }

  @Test
  public void invisibleErrorTest() {
    in = new StringReader("-text create first current first invisible first");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("The image cannot be null."
        + " Try again: ", out.toString());
  }

  @Test
  public void multiLoadErrorTest() {
    in = new StringReader("-text multiload test.txt");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("The file location test.txt for this"
        + " script is invalid. Try again: ", out.toString());
  }

  @Test
  public void multiSaveErrorTest() {
    in = new StringReader("-text create first current first checkerboard 50 5 create second"
        + " multisave test jpg");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("The image cannot be null. Try again: ",
        out.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullInputs() {
    new LayerController(null, in, view);
    new LayerController(model, null, view);
    new LayerController(model, in, null);
  }

  @Test(expected = IllegalStateException.class)
  public void failAppendable() {
    new LayerController(model, new StringReader("-text current first"),
        new LayerTextView(new TestAppendable()))
        .start();
  }

  @Test
  public void invalidCommand() {
    in = new StringReader("-text something");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("something is not a valid command. Try again: ",
        out.toString());
  }

  @Test
  public void successfulCommand() {
    in = new StringReader("-text create ex current ex checkerboard 1 5 sharpen");
    controller = new LayerController(model, in, view);
    controller.start();
    //only errors are displayed, if there are no errors then only the opening message
    //will be displayed
    assertEquals("", out.toString());
  }

  @Test
  public void removeTest() {
    //this should show nothing as it was a successful command, so no errors will be outputted
    in = new StringReader(
        "-text create first current first checkerboard 5 5 remove first");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("", out.toString());
  }

  @Test
  public void removeErrorTest() {
    in = new StringReader(
        "-text create first current first checkerboard 5 5 remove second");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("The layer second does not exist. Try again: ", out.toString());
  }

  @Test
  public void copyTest() {
    //this should show nothing as it was a successful command, so no errors will be outputted
    in = new StringReader(
        "-text create first current first checkerboard 5 5 copy first second");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals("", out.toString());
  }

  @Test
  public void copyError() {
    in = new StringReader(
        "-text create first current first checkerboard 5 5 copy second first");
    controller = new LayerController(model, in, view);
    controller.start();
    assertEquals(
        "The layer second does not exist. Try again: ",
        out.toString());
  }
}