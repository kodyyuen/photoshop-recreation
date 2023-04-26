import controller.ILayerController;
import controller.InteractiveController;
import controller.LayerController;
import image.IComplexLayerModel;
import image.ILayerModel;
import image.LayerModel;
import java.io.InputStreamReader;
import java.io.StringReader;
import view.ILayerInteractiveView;
import view.ILayerView;
import view.LayerInteractiveView;
import view.LayerTextView;

/**
 * Main class where the program is started. When run here, the program is displayed in the console.
 */
class Main {

  /**
   * Main method where you can run the program.
   *
   * @param args arguments entered in the terminal
   */
  public static void main(String[] args) {
    ILayerModel model = new LayerModel();
    Readable rd;
    ILayerView textView = new LayerTextView(System.out);
    ILayerController controller;

    String viewType = args[0];
    switch (viewType) {
      case "-script":
        if (args.length == 2) {
          rd = new StringReader(args[0] + " " + args[1]);
          controller = new LayerController(model, rd, textView);
          break;
        } else {
          throw new IllegalArgumentException(
              "You have entered too many arguments for the script command");
        }
      case "-text":
        rd = new InputStreamReader(System.in);
        controller = new LayerController(model, rd, textView);
        break;
      case "-interactive":
        ILayerInteractiveView interactive = new LayerInteractiveView();
        IComplexLayerModel complexModel = new LayerModel();
        controller = new InteractiveController(complexModel, interactive);
        break;
      default:
        throw new IllegalArgumentException("The input " + viewType + " is not valid");
    }
    controller.start();
  }
}