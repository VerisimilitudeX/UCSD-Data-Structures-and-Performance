package application;

import java.io.IOException;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {
  private Stage primaryStage;
  private BorderPane rootLayout;

  // called at start of application
  @Override
  public void start(final Stage primaryStage) {

    this.primaryStage = primaryStage;

    this.primaryStage.setTitle("VerisiNotes");

    try {
      // Load root layout from fxml
      final FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/RootLayout.fxml"));
      rootLayout = (BorderPane) loader.load();
      final Scene scene = new Scene(rootLayout);
      primaryStage.setScene(scene);

      // min height and width calculated from components in TextAppLayout
      primaryStage.setMinHeight(430);
      primaryStage.setMinWidth(334);
      primaryStage.show();

    } catch (final Exception e) {
      e.printStackTrace();
    }

    showTextProApp();
  }

  /** Shows the main TextApplication scene */
  public void showTextProApp() {
    try {
      // Load the fxml file and set into the center of the main layout
      final FXMLLoader loader = new FXMLLoader(getClass().getResource("view/TextAppLayout.fxml"));

      final HBox textProPage = (HBox) loader.load();
      rootLayout.setCenter(textProPage);

      // Connect controller and main app
      final TextProController controller = loader.getController();
      controller.setMainApp(this);

    } catch (final IOException e) {
      // Exception gets thrown if the fxml file could not be loaded
      e.printStackTrace();
    }
  }

  // SHOW NEW STAGE METHODS

  /**
   * Shows dialog for user input error
   *
   * @param inErr - message to dispaly
   */
  public void showInputErrorDialog(final String inErr) {
    final Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("Input Error");
    alert.setContentText(inErr);

    alert.showAndWait();
  }

  /**
   * Displays dialog that allows user to select local text file to display in TextArea
   *
   * @param ta - reference to TextArea to display loaded text file
   */
  public void showLoadFileDialog(final AutoSpellingTextArea ta) {
    try {
      // Load the fxml file and create a new stage for the popup
      final FXMLLoader loader =
          new FXMLLoader(MainApp.class.getResource("view/LoadFileLayout.fxml"));
      final VBox page = (VBox) loader.load();
      final Stage dialogStage = new Stage();
      dialogStage.setTitle("Load File");
      dialogStage.initModality(Modality.WINDOW_MODAL);
      dialogStage.initOwner(primaryStage);
      final Scene scene = new Scene(page);
      dialogStage.setScene(scene);

      // Set reference to stage in controller
      final LoadFileDialogController controller = loader.getController();
      controller.setDialogStage(dialogStage);

      // give controller reference to text area to load file into
      controller.setTextArea(ta);

      // Show the dialog and wait until the user closes it
      dialogStage.showAndWait();

    } catch (final IOException e) {
      // Exception gets thrown if the fxml file could not be loaded
      e.printStackTrace();
    }
  }

  public void showEditDistanceDialog(final String selectedText) {
    try {
      // Load the fxml file and create a new stage for the popup
      final FXMLLoader loader =
          new FXMLLoader(MainApp.class.getResource("view/EditDistanceLayout.fxml"));
      final VBox page = (VBox) loader.load();
      final Stage dialogStage = new Stage();
      dialogStage.setTitle("Calculate Edit Distance");
      dialogStage.initModality(Modality.WINDOW_MODAL);
      dialogStage.initOwner(primaryStage);
      final Scene scene = new Scene(page);
      dialogStage.setScene(scene);

      // Set reference to stage in controller
      final EditDistanceDialogController controller = loader.getController();
      controller.setDialogStage(dialogStage);
      controller.setMainApp(this);
      controller.setField(selectedText);

      // give controller reference to scene (cursor)

      // Show the dialog and wait until the user closes it
      dialogStage.showAndWait();

    } catch (final IOException e) {
      // Exception gets thrown if the fxml file could not be loaded
      e.printStackTrace();
    }
  }

  public void showEDResult(final List<String> path) {
    // intialize alert/dialog to display edit distance result
    final Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Result");
    alert.setHeaderText("Word Path : ");
    alert.initModality(Modality.NONE);
    alert.setResizable(true);

    // create layout for content
    final VBox box = new VBox();
    final HBox midBox = new HBox();
    box.setPadding(new Insets(35, 0, 35, 0));
    box.setSpacing(35);
    midBox.setSpacing(15);

    final Label pathLabel = new Label();
    final Label numStepsLabel = new Label("Number of steps : ");
    final Label numSteps = new Label();
    final Font font = new Font(14);
    pathLabel.setFont(font);
    numStepsLabel.setFont(font);
    numSteps.setFont(Font.font(font.getFamily(), FontWeight.BOLD, 14));

    midBox.getChildren().add(numStepsLabel);
    midBox.getChildren().add(numSteps);
    midBox.setAlignment(Pos.CENTER);

    box.getChildren().add(pathLabel);
    box.getChildren().add(midBox);
    box.setAlignment(Pos.CENTER);
    alert.getDialogPane().setPrefWidth(300);

    // check for path
    if (path != null) {
      numSteps.setText(Integer.toString(path.size() - 1));
      pathLabel.setText(String.join(" -> ", path));

      final Text text = new Text(pathLabel.getText());
      text.setFont(font);
      if (text.getLayoutBounds().getWidth() > 200) {
        alert.getDialogPane().setPrefWidth(text.getLayoutBounds().getWidth() + 100);
      }

    }
    // no path found
    else {
      pathLabel.setText("No Path Found.");
      numSteps.setText("N/A");
    }

    // set content and styling
    alert.getDialogPane().setContent(box);
    alert
        .getDialogPane()
        .getStylesheets()
        .add(getClass().getResource("application.css").toExternalForm());
    alert.getDialogPane().getStyleClass().add("myDialog");
    alert.showAndWait();
  }

  public void showMarkovDialog(final textgen.MarkovTextGenerator mtg) {
    try {
      // Load the fxml file and create a new stage for the popup
      final FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/MarkovLayout.fxml"));
      final BorderPane page = (BorderPane) loader.load();
      final Stage dialogStage = new Stage();
      dialogStage.setTitle("Markov Text Generator");
      // dialogStage.initModality(Modality.WINDOW_MODAL);
      dialogStage.initOwner(primaryStage);
      final Scene scene = new Scene(page);
      dialogStage.setScene(scene);

      // Set reference to stage in controller
      // BUG -- when first displayed results don't show up until resize window
      final MarkovController controller = loader.getController();
      // controller.setDialogStage(dialogStage);
      controller.setMainApp(this);
      controller.setMTG(mtg);

      // Show the dialog and wait until the user closes it
      dialogStage.showAndWait();

    } catch (final IOException e) {
      // Exception gets thrown if the fxml file could not be loaded
      e.printStackTrace();
    }
  }

  public void showLoadStage(final Stage loadStage, final String text) {
    loadStage.initModality(Modality.APPLICATION_MODAL);
    loadStage.initOwner(primaryStage);
    final VBox loadVBox = new VBox(20);
    loadVBox.setAlignment(Pos.CENTER);
    final Text tNode = new Text(text);
    tNode.setFont(new Font(16));
    loadVBox.getChildren().add(new HBox());
    loadVBox.getChildren().add(tNode);
    loadVBox.getChildren().add(new HBox());
    final Scene loadScene = new Scene(loadVBox, 300, 200);
    loadStage.setScene(loadScene);
    loadStage.show();
  }

  // MAIN
  public static void main(final String[] args) {
    launch(args);
  }

  public Stage getStage() {
    return this.primaryStage;
  }
}
