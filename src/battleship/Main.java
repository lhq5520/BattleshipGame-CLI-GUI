package battleship;

import java.io.InputStreamReader;

/**
 * The Main class contains the main method that runs the Battleship game.
 */
public class Main {
  /**
   * The main method that runs the Battleship game.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    BattleshipModel model = new BattleshipModelImpl(30);
    SwingBattleshipView view = new SwingBattleshipView();
    SwingBattleshipController controller = new SwingBattleshipController(model, view);

    // Start the game
    controller.playGame(model);


  }
}
