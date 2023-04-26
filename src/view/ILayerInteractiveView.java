package view;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * An interface used to represent an interactive view of images and commands. This interface uses
 * methods to extract user input from the view for other parts of the program to use.
 */
public interface ILayerInteractiveView extends ILayerView {

  /**
   * Assigns an ActionListener for each menu item. The listeners will react accordingly depending on
   * what item is pressed.
   *
   * @param actionListener the listener that will be assigned to the given component
   */
  void addActionListener(ActionListener actionListener);

  /**
   * Gets the text from the input field. The input field is used for any necessary arguments.
   *
   * @return text from field
   */
  String getInputField();

  /**
   * Prompts the view to open a FileChooser and returns the selected file.
   *
   * @return file path of selected file
   */
  String getLoadFile();

  /**
   * Prompts the view to open a FileChooser and returns the selected file. The file can only be a
   * text file.
   *
   * @return path of the text file
   */
  String getMultiLoadFile();

  /**
   * Prompts the view to open a FileChooser and returns the selected save location. Also prompts the
   * user to give their preferred file type for the file to be saved.
   *
   * @return file path of the file to be saved
   */
  List<String> getSaveFile();

  /**
   * Prompts the view to open a FileChooser and returns the selected save location. Also prompts the
   * user to give their preferred file type for the file(s) and text file to be saved.
   *
   * @return file directory of the images and text file to be saved
   */
  List<String> getSaveDirectory();

  /**
   * Refreshes the GUI to show the most recent topmost visible image.
   *
   * @param image most recent top most visible image
   */
  void displayImage(BufferedImage image);
}
