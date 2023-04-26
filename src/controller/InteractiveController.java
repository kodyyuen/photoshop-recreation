package controller;

import controller.command.BlurCommand;
import controller.command.Checkerboard;
import controller.command.Copy;
import controller.command.Create;
import controller.command.Current;
import controller.command.GreyscaleCommand;
import controller.command.ImageUtils;
import controller.command.Invisible;
import controller.command.LayerCommand;
import controller.command.Load;
import controller.command.MultiSave;
import controller.command.Remove;
import controller.command.Save;
import controller.command.SepiaCommand;
import controller.command.SharpenCommand;
import controller.command.Visible;
import image.IComplexLayerModel;
import image.ImageState;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import view.CommandListener;
import view.ILayerInteractiveView;

/**
 * Represents a controller that deals with the GUI inputs. The controller creates the methods used
 * for the view listener and runs the methods to update the model when the listener methods are
 * triggered. It also renders any error messages to the GUI for the user to see.
 */
public class InteractiveController implements ILayerController {

  private final IComplexLayerModel model;
  private final ILayerInteractiveView view;
  private ILayerController delegate;
  private LayerCommand cmd;

  /**
   * Creates a controller that the interactive view can communicate with.
   *
   * @param model the model where the layers will be manipulated
   * @param view  the view where messages will be outputted to the user
   * @throws IllegalArgumentException if any of the arguments are null
   */
  public InteractiveController(IComplexLayerModel model, ILayerInteractiveView view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("The model or view cannot be null.");
    }
    this.model = model;
    this.view = view;
  }

  @Override
  public void start() {
    configureButtonListener();
  }

  /**
   * Configures each of the listeners to be added to the ActionListener class. This is so the user
   * can interact with the view and have the controller mutate the model so the view can stay
   * updated.
   */
  private void configureButtonListener() {
    Map<String, Runnable> buttonClickedMap = new HashMap<String, Runnable>();
    CommandListener commandListener = new CommandListener();

    buttonClickedMap.put("Create", () -> {
      cmd = new Create(view.getInputField());
      runCommand();
    });

    buttonClickedMap.put("Set Current", () -> {
      cmd = new Current(view.getInputField());
      runCommand();
    });

    buttonClickedMap.put("Load", () -> {
      String loadFile = view.getLoadFile();
      if (loadFile == null) {
        return;
      }
      cmd = new Load(loadFile);
      runCommand();
      refreshViewImage();
    });

    buttonClickedMap.put("Multi-Load", () -> {
      String multiFile = view.getMultiLoadFile();
      if (multiFile == null) {
        return;
      }
      StringReader script = new StringReader("-script " + multiFile);
      delegate = new LayerController(model, script, view);
      delegate.start();
      refreshViewImage();
    });

    buttonClickedMap.put("Checkerboard", () -> {
      String input = view.getInputField();
      String[] inputList = input.split(" ");
      int tileSize;
      int numTiles;

      if (inputList.length != 2) {
        try {
          view.renderMessage("The number of arguments to create a checkerboard image are invalid");
          return;
        } catch (IOException e) {
          e.printStackTrace();
          return;
        }
      }

      try {
        tileSize = Integer.parseInt(inputList[0]);
        numTiles = Integer.parseInt(inputList[1]);
      } catch (NumberFormatException e) {
        try {
          view.renderMessage("The values you entered to create a checkerboard image are invalid");
          return;
        } catch (IOException ioException) {
          ioException.printStackTrace();
          return;
        }
      }

      cmd = new Checkerboard(tileSize, numTiles);
      runCommand();
      refreshViewImage();
    });

    buttonClickedMap.put("Save", () -> {
      List<String> saveFile = view.getSaveFile();
      if (saveFile == null) {
        return;
      }
      cmd = new Save(saveFile.get(0), saveFile.get(1));
      runCommand();
    });

    buttonClickedMap.put("Multi-Save", () -> {
      List<String> saveDirectory = view.getSaveDirectory();
      if (saveDirectory == null) {
        return;
      }
      cmd = new MultiSave(saveDirectory.get(0), saveDirectory.get(1));
      runCommand();
    });

    buttonClickedMap.put("Copy", () -> {
      String input = view.getInputField();
      String[] inputList = input.split(" ");
      if (inputList.length != 2) {
        try {
          view.renderMessage("Your inputs to copy a layer are invalid");
          return;
        } catch (IOException e) {
          e.printStackTrace();
          return;
        }
      }
      cmd = new Copy(inputList[0], inputList[1]);
      runCommand();
      refreshViewImage();
    });

    buttonClickedMap.put("Remove", () -> {
      cmd = new Remove(view.getInputField());
      runCommand();
      refreshViewImage();
    });

    buttonClickedMap.put("Visible", () -> {
      cmd = new Visible(view.getInputField());
      runCommand();
      refreshViewImage();
    });

    buttonClickedMap.put("Invisible", () -> {
      cmd = new Invisible(view.getInputField());
      runCommand();
      refreshViewImage();
    });

    buttonClickedMap.put("Blur", () -> {
      cmd = new BlurCommand();
      runCommand();
      refreshViewImage();
    });

    buttonClickedMap.put("Sharpen", () -> {
      cmd = new SharpenCommand();
      runCommand();
      refreshViewImage();
    });

    buttonClickedMap.put("Greyscale", () -> {
      cmd = new GreyscaleCommand();
      runCommand();
      refreshViewImage();
    });

    buttonClickedMap.put("Sepia", () -> {
      cmd = new SepiaCommand();
      runCommand();
      refreshViewImage();
    });

    buttonClickedMap.put("Downscale", () -> {
      String input = view.getInputField();
      String[] inputList = input.split(" ");
      double x;
      double y;

      if (inputList.length != 2) {
        try {
          view.renderMessage("The number of arguments to downscale an image are invalid");
          return;
        } catch (IOException e) {
          e.printStackTrace();
          return;
        }
      }

      try {
        x = Double.parseDouble(inputList[0]);
        y = Double.parseDouble(inputList[1]);
      } catch (NumberFormatException e) {
        try {
          view.renderMessage("The values you entered to downscale an image are invalid");
          return;
        } catch (IOException ioException) {
          ioException.printStackTrace();
          return;
        }
      }

      try {
        model.downscale(x, y);
      } catch (IllegalStateException | IllegalArgumentException e) {
        try {
          view.renderMessage(e.getMessage());
          return;
        } catch (IOException ioException) {
          ioException.printStackTrace();
          return;
        }
      }
      refreshViewImage();
    });

    commandListener.setClickedActions(buttonClickedMap);
    view.addActionListener(commandListener);
  }

  /**
   * EFFECT: Runs the command that the view has triggered.
   */
  private void runCommand() {
    try {
      cmd.apply(model);
    } catch (IllegalArgumentException | IllegalStateException e) {
      try {
        view.renderMessage(e.getMessage());
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
    }
  }

  /**
   * EFFECT: Gives the view the most recent top visible image and calls on the view to refresh its
   * state so it shows the image.
   */
  private void refreshViewImage() {
    try {
      ImageState image = model.getTopVisibleImage();
      view.displayImage(ImageUtils.imageToBuffered(image));
    } catch (IllegalArgumentException | IllegalStateException e) {
      try {
        view.renderMessage(e.getMessage());
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
    }
  }
}