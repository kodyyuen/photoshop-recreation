import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import view.ILayerInteractiveView;

/**
 * Mock class to make sure that the view arguments are correct.
 */
public class MockLayerInteractiveView implements ILayerInteractiveView {

  private final StringBuilder log;

  public MockLayerInteractiveView(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void addActionListener(ActionListener actionListener) {
    //substring 0, 19 so it only shows "MockCommandListener"
    log.append("addActionListener: ").append(actionListener.toString().substring(0, 19));
  }

  public void emitEvent(ActionListener actionListener) {
    actionListener.actionPerformed(new ActionEvent(new Object(), 0, "emit event worked"));
  }

  @Override
  public String getInputField() {
    return "getInputField successful";
  }

  @Override
  public String getLoadFile() {
    return "getLoadFile successful";
  }

  @Override
  public String getMultiLoadFile() {
    return "getMultiLoadFile successful";
  }

  @Override
  public List<String> getSaveFile() {
    List<String> ans = new ArrayList<>();
    ans.add("getSaveFile successful");
    return ans;
  }

  @Override
  public List<String> getSaveDirectory() {
    List<String> ans = new ArrayList<>();
    ans.add("getSaveDirectory successful");
    return ans;
  }

  @Override
  public void displayImage(BufferedImage image) {
    log.append("displayImage successful");
  }

  @Override
  public void renderMessage(String message) throws IOException {
    log.append("renderMessage: ").append(message);
  }
}