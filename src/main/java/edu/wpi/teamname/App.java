package edu.wpi.teamname;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {
  private Stage primaryStage;

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    // Setup primary stage
    this.primaryStage = primaryStage;
    this.primaryStage.setTitle("Exciting Chess");
    // Load game board
    // TODO create path
    switchScene("views/match/MatchBoard.fxml");
  }

  public void switchScene(String path) throws IOException {
    // Load scene pane from FXML
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource(path));

    Pane pane = loader.load();
    Scene scene = new Scene(pane);

    // Display primary stage
    primaryStage.setScene(scene);
    this.primaryStage.setMaximized(true);
    this.primaryStage.show();
  }
}
