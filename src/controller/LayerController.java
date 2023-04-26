package controller;

import controller.command.BlurCommand;
import controller.command.Checkerboard;
import controller.command.Copy;
import controller.command.Create;
import controller.command.Current;
import controller.command.GreyscaleCommand;
import controller.command.Invisible;
import controller.command.LayerCommand;
import controller.command.Load;
import controller.command.MultiSave;
import controller.command.Remove;
import controller.command.Save;
import controller.command.SepiaCommand;
import controller.command.SharpenCommand;
import controller.command.Visible;
import image.ILayerModel;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import view.ILayerView;

/**
 * Represents the controller for a LayerModel. The controller communicates with the LayerModel to
 * create and manipulate multi-layered images. It supports commands such as creating layers,
 * applying filters and transformations to layers, saving individual layers or a multi-layered
 * image, loading images into individual layers, and loading a multi-layered image. It communicates
 * with the view to display any error messages to the user.
 */
public class LayerController implements ILayerController {

  private final ILayerModel model;
  private final Readable rd;
  private final ILayerView view;
  private boolean pickMode;

  /**
   * Creates a controller so the user can use its commands.
   *
   * @param model the model where the layers will be manipulated
   * @param rd    a readable object to take user inputs
   * @param view  the view where messages will be outputted to the user
   * @throws IllegalArgumentException if any of the arguments are null
   */
  public LayerController(ILayerModel model, Readable rd, ILayerView view) {
    if (model == null || rd == null || view == null) {
      throw new IllegalArgumentException("The model, readable, or appendable cannot be null");
    }
    this.model = model;
    this.rd = rd;
    this.view = view;
    pickMode = true;
  }

  @Override
  public void start() {
    Scanner scan = new Scanner(this.rd);
    while (pickMode) {
      String token = scan.next().toLowerCase();
      switch (token) {
        case "-script":
          runScript(scan.next());
          pickMode = false;
          break;
        case "-text":
        default:
          runCommands(scan);
          break;
      }
    }
  }

  /**
   * EFFECT: Takes in a token and applies a command based on its input. Renders an error message if
   * the input was not supported / invalid.
   *
   * @param scan the scanner that holds the user inputs
   */
  private void commandInput(Scanner scan) {
    String token = scan.next();
    LayerCommand cmd = null;

    switch (token) {
      case "create":
        cmd = new Create(scan.next());
        break;
      case "current":
        cmd = new Current(scan.next());
        break;
      case "load":
        cmd = new Load(scan.next());
        break;
      case "checkerboard":
        try {
          cmd = new Checkerboard(scan.nextInt(), scan.nextInt());
        } catch (InputMismatchException e) {
          renderViewMessage(e.getMessage() + "\n");
        }
        break;
      case "save":
        cmd = new Save(scan.next(), scan.next());
        break;
      case "copy":
        cmd = new Copy(scan.next(), scan.next());
        break;
      case "remove":
        cmd = new Remove(scan.next());
        break;
      case "blur":
        cmd = new BlurCommand();
        break;
      case "sharpen":
        cmd = new SharpenCommand();
        break;
      case "greyscale":
        cmd = new GreyscaleCommand();
        break;
      case "sepia":
        cmd = new SepiaCommand();
        break;
      case "visible":
        cmd = new Visible(scan.next());
        break;
      case "invisible":
        cmd = new Invisible(scan.next());
        break;
      case "multiload":
        runScript(scan.next());
        break;
      case "multisave":
        cmd = new MultiSave(scan.next(), scan.next());
        break;
      default:
        cmd = null;
        renderViewMessage(token + " is not a valid command. Try again: ");
    }
    if (cmd != null) {
      try {
        cmd.apply(model);
      } catch (IllegalArgumentException | IllegalStateException e) {
        renderViewMessage(e.getMessage() + ". Try again: ");
      }
    }
  }

  /**
   * Runs a given script that has commands in it. It is used when loading in a multi-layer or
   * selecting the script option when the program starts.
   *
   * @param filePath filepath of the script
   */
  private void runScript(String filePath) {
    try {
      Scanner fileScan = new Scanner(new FileInputStream(filePath));
      runCommands(fileScan);
    } catch (IOException e) {
      renderViewMessage("The file location " + filePath + " for this script is invalid."
          + " Try again: ");
    }
  }

  /**
   * Runs the commands while the scanner still has input.
   *
   * @param scan the scanner object with commands
   */
  private void runCommands(Scanner scan) {
    while (scan.hasNext()) {
      commandInput(scan);
    }
    pickMode = false;
  }

  /**
   * Renders a message to the console. It is used to show users error messages.
   *
   * @param message the message to be shown
   * @throws IllegalStateException if writing to the appendable failed
   */
  private void renderViewMessage(String message)
      throws IllegalStateException {
    try {
      this.view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException("Writing to the Appendable message object in View failed");
    }
  }
}