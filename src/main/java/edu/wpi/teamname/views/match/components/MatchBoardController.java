package edu.wpi.teamname.views.match.components;

import edu.wpi.teamname.views.match.MatchController;
import edu.wpi.teamname.views.match.MatchScreenController;

public class MatchBoardController implements MatchController {
  MatchScreenController matchScreenController;

  @Override
  public void setMatchScreenController(MatchScreenController matchScreenController) {
    this.matchScreenController = matchScreenController;
  }
}
