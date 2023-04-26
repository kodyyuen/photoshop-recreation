import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Mock class to mimic the results of using the Command Listener.
 */
public class MockCommandListener implements ActionListener {

  private final StringBuilder log;

  /**
   * Creating a log to record the method inputs.
   *
   * @param log inputs
   */
  public MockCommandListener(StringBuilder log) {
    this.log = log;
  }

  public void setClickedActions(String map) {
    log.append("setClickedActions: " + map);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    log.append("actionPerformed: " + e.getActionCommand());
  }
}