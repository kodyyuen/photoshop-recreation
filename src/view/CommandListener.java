package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * Represents a class that listens to the view's components. It has a map of the view's commands and
 * runs the methods associated with it when it is interacted with.
 */
public class CommandListener implements ActionListener {

  private Map<String, Runnable> clickedActions;

  /**
   * Creates a CommandListener object to run commands on pressed components.
   */
  public CommandListener() {
    // no need for anything here
  }

  /**
   * Saves the map of commands created in the controller so it can be used.
   *
   * @param map list of commands and their methods
   */
  public void setClickedActions(Map<String, Runnable> map) {
    clickedActions = map;
  }

  @Override
  //performs the action given when the component is triggered
  public void actionPerformed(ActionEvent e) {
    if (clickedActions.containsKey(e.getActionCommand())) {
      clickedActions.get(e.getActionCommand()).run();
    }
  }
}
