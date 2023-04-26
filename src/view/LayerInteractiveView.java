package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 * Represents the interactive view. This view offers its functions through a menu. In the menu, you
 * can arrange layers, manipulate layers, and save/load layers. This view offers a visual way for
 * users to edit their images and possibly save and load them to work on later.
 */
public class LayerInteractiveView extends JFrame implements ILayerInteractiveView {

  private final JMenuBar menuBar;
  private JMenuItem createLayer;
  private JMenuItem removeLayer;
  private JMenuItem copyLayer;
  private JMenuItem setCurrentLayer;
  private JMenuItem visibleLayer;
  private JMenuItem invisibleLayer;
  private JMenuItem blur;
  private JMenuItem sharpen;
  private JMenuItem greyscale;
  private JMenuItem sepia;
  private JMenuItem downscale;
  private JMenuItem save;
  private JMenuItem multiSave;
  private JMenuItem load;
  private JMenuItem multiLoad;
  private JMenuItem checkerboard;
  private final JTextField inputField;
  private final JLabel errorField;
  private final JLabel image;
  private final JScrollPane imageScroll;

  /**
   * Creates a LayerInteractiveView object by initializing all necessary components, such as menu
   * items.
   */
  public LayerInteractiveView() {
    super();
    setTitle("CS 3500 LIME");
    setSize(500, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout(100, 50));

    image = new JLabel();
    imageScroll = new JScrollPane(image);
    imageScroll.setPreferredSize(new Dimension(1000,
        1500));

    //Build menu bar
    menuBar = new JMenuBar();

    buildArrangeMenuBar();
    buildManipulateMenuBar();
    buildSaveLoadMenuBar();

    inputField = new JTextField(20);
    inputField.setBorder(BorderFactory.createTitledBorder("Enter arguments"));

    errorField = new JLabel("Error: ");

    this.setJMenuBar(menuBar);
    this.add(inputField, BorderLayout.PAGE_END);
    this.add(errorField, BorderLayout.PAGE_START);
    setVisible(true);
  }

  //Build first menu for arranging the layers through create, set current, remove, and copy
  private void buildArrangeMenuBar() {
    JMenu arrangeLayer = new JMenu("Arrange Layers");
    createLayer = new JMenuItem("Create");
    createLayer.setActionCommand("Create");
    setCurrentLayer = new JMenuItem("Set Current");
    setCurrentLayer.setActionCommand("Set Current");
    removeLayer = new JMenuItem("Remove");
    removeLayer.setActionCommand("Remove");
    copyLayer = new JMenuItem("Copy");
    copyLayer.setActionCommand("Copy");
    visibleLayer = new JMenuItem("Visible");
    visibleLayer.setActionCommand("Visible");
    invisibleLayer = new JMenuItem("Invisible");
    invisibleLayer.setActionCommand("Invisible");

    arrangeLayer.add(createLayer);
    arrangeLayer.add(setCurrentLayer);
    arrangeLayer.add(removeLayer);
    arrangeLayer.add(copyLayer);
    arrangeLayer.add(visibleLayer);
    arrangeLayer.add(invisibleLayer);
    menuBar.add(arrangeLayer);
  }

  //Build second menu for manipulating the current layer's image
  //User can apply blur, sharpen, greyscale, or sepia
  //The current layer must be set for these options
  private void buildManipulateMenuBar() {
    JMenu layerManipulation = new JMenu("Layer Manipulation");
    blur = new JMenuItem("Blur");
    blur.setActionCommand("Blur");
    sharpen = new JMenuItem("Sharpen");
    sharpen.setActionCommand("Sharpen");
    greyscale = new JMenuItem("Greyscale");
    greyscale.setActionCommand("Greyscale");
    sepia = new JMenuItem("Sepia");
    sepia.setActionCommand("Sepia");
    downscale = new JMenuItem("Downscale");
    downscale.setActionCommand("Downscale");

    layerManipulation.add(blur);
    layerManipulation.add(sharpen);
    layerManipulation.add(greyscale);
    layerManipulation.add(sepia);
    layerManipulation.add(downscale);
    menuBar.add(layerManipulation);
  }

  //Build third menu for saving and loading layers
  //User can save the top-most visible layer, save all the layers,
  //load an image to the specified layer, or load a multi-layered image
  private void buildSaveLoadMenuBar() {
    JMenu saveLoad = new JMenu("Save/Load");
    save = new JMenuItem("Save");
    save.setActionCommand("Save");
    multiSave = new JMenuItem("Multi-Save");
    multiSave.setActionCommand("Multi-Save");
    load = new JMenuItem("Load");
    load.setActionCommand("Load");
    multiLoad = new JMenuItem("Multi-Load");
    multiLoad.setActionCommand("Multi-Load");
    checkerboard = new JMenuItem("Checkerboard");
    checkerboard.setActionCommand("Checkerboard");

    saveLoad.add(save);
    saveLoad.add(multiSave);
    saveLoad.add(load);
    saveLoad.add(multiLoad);
    saveLoad.add(checkerboard);
    menuBar.add(saveLoad);
  }

  @Override
  public void addActionListener(ActionListener actionListener) {
    createLayer.addActionListener(actionListener);
    setCurrentLayer.addActionListener(actionListener);
    removeLayer.addActionListener(actionListener);
    copyLayer.addActionListener(actionListener);
    invisibleLayer.addActionListener(actionListener);
    visibleLayer.addActionListener(actionListener);
    blur.addActionListener(actionListener);
    sharpen.addActionListener(actionListener);
    greyscale.addActionListener(actionListener);
    sepia.addActionListener(actionListener);
    downscale.addActionListener(actionListener);
    save.addActionListener(actionListener);
    multiSave.addActionListener(actionListener);
    load.addActionListener(actionListener);
    multiLoad.addActionListener(actionListener);
    checkerboard.addActionListener(actionListener);
  }

  @Override
  public void renderMessage(String message) {
    errorField.setText("Error: " + message);
  }

  @Override
  public String getInputField() {
    return inputField.getText();
  }

  @Override
  public String getLoadFile() {
    JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView());

    if (j.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
      return j.getSelectedFile().getAbsolutePath();
    } else {
      return null;
    }
  }

  @Override
  public String getMultiLoadFile() {
    JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView());
    j.setFileFilter(new FileNameExtensionFilter(
        "When loading a multi-layered image, the file must be a text file", "txt"));

    if (j.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
      return j.getSelectedFile().getAbsolutePath();
    } else {
      return null;
    }
  }

  @Override
  public List<String> getSaveFile() {
    JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView());
    String path;
    String fileType;
    List<String> saveFile = new ArrayList<>();

    if (j.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
      path = j.getSelectedFile().getAbsolutePath();
    } else {
      return null;
    }
    fileType = JOptionPane
        .showInputDialog("Enter a file type for your image (PPM, PNG, default JPG)");
    saveFile.add(path);
    saveFile.add(fileType);
    return saveFile;
  }

  @Override
  public List<String> getSaveDirectory() {
    JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView());
    j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    String path;
    String fileType;
    List<String> saveDirectory = new ArrayList<>();

    if (j.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
      path = j.getSelectedFile().getAbsolutePath();
    } else {
      return null;
    }
    fileType = JOptionPane
        .showInputDialog("Enter a file type for your images (PPM, PNG, default JPG)");
    saveDirectory.add(path);
    saveDirectory.add(fileType);
    return saveDirectory;
  }

  @Override
  public void displayImage(BufferedImage bufferedImage) {
    image.setIcon(new ImageIcon(bufferedImage));
    this.add(imageScroll, BorderLayout.CENTER);
    this.setVisible(true);
    this.repaint();
  }
}