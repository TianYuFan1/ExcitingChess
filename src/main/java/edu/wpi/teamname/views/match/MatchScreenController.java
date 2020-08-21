package edu.wpi.teamname.views.match;

import edu.wpi.teamname.Client.Client;
import edu.wpi.teamname.views.match.components.MatchBoardController;
import edu.wpi.teamname.views.match.components.MatchChatController;
import edu.wpi.teamname.views.match.components.MatchControlsController;
import edu.wpi.teamname.views.match.components.MatchMovesController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class MatchScreenController {
  @FXML AnchorPane pn_chat;
  @FXML AnchorPane pn_moves;
  @FXML AnchorPane pn_board;
  @FXML AnchorPane pn_controls;

  public MatchChatController getMatchChatController() {
    return matchChatController;
  }

  public MatchMovesController getMatchMovesController() {
    return matchMovesController;
  }

  public MatchBoardController getMatchBoardController() {
    return matchBoardController;
  }

  public MatchControlsController getMatchControlsController() {
    return matchControlsController;
  }

  public Client getClient() {
    return client;
  }

  MatchChatController matchChatController;
  MatchMovesController matchMovesController;
  MatchBoardController matchBoardController;
  MatchControlsController matchControlsController;
  Client client;

  /**
   * Initializes chat, board, moves list, and controls of match screen
   *
   * @throws IOException when FXML file is not found
   */
  public void initialize() throws IOException {
    matchBoardController = (MatchBoardController) loadComponent(pn_board, "MatchBoard.fxml");
    matchChatController = (MatchChatController) loadComponent(pn_chat, "MatchChat.fxml");
    matchMovesController = (MatchMovesController) loadComponent(pn_moves, "MatchMoves.fxml");
    matchControlsController =
        (MatchControlsController) loadComponent(pn_controls, "MatchControls.fxml");
    client = new Client("username", this);
    client.connect("username");
  }

  /**
   * Loads FXML file into appropriate pane and returns controller
   *
   * @param host pane hosting the FXML
   * @param path FXML file path
   * @return controller of the FXML
   * @throws IOException when FXML file is not found
   */
  public MatchController loadComponent(Pane host, String path) throws IOException {
    // Load FXML
    FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
    Pane childPane = loader.load();
    host.getChildren().add(childPane);
    // Load Controller
    MatchController controller = loader.getController();
    controller.setMatchScreenController(this);
    return controller;
  }
}
