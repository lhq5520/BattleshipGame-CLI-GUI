package battleship;

import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * The BattleshipView interface defines the methods required for displaying the game state and
 * interacting with the player. It handles all user interface aspects, such as rendering the game
 * grid, capturing user input, and displaying messages.
 */
public interface BattleshipViewGui {

  /**
   * Displays a welcome message to the player at the start of the game.
   *
   * @throws IOException if an I/O error occurs
   */
  void displayWelcomeMessage() throws IOException;

  /**
   * Displays a prompt message to the player, indicating that they should enter a guess.
   *
   * @throws IOException if an I/O error occurs
   */
  void displayPromptMessage() throws IOException;

  /**
   * Displays the current state of the player's grid, showing hits, misses, and unknown cells.
   *
   * @param cellGrid a 2D array representing the cell grid state
   * @throws IOException if an I/O error occurs
   */
  void displayCellGrid(CellState[][] cellGrid) throws IOException;

  /**
   * Displays the ship grid after the game has ended, revealing the positions of all ships.
   *
   * @param shipGrid a 2D array representing the ship grid state
   * @throws IOException if an I/O error occurs
   */
  void displayShipGrid(ShipType[][] shipGrid) throws IOException;

  /**
   * Displays the current number of guesses the player has made.
   *
   * @param currentGuesses the current number of guesses made
   * @param maxGuesses     the max number of guesses made
   * @throws IOException if an I/O error occurs
   */
  void displayGuessCount(int currentGuesses, int maxGuesses) throws IOException;


  void displayErrorMessage(String message) throws IOException;

  /**
   * Displays a message indicating that the game is over.
   *
   * @param win true if the player won the game, false if the player lost
   * @throws IOException if an I/O error occurs
   */
  void displayGameOver(boolean win) throws IOException;

  /**
   * Displays a message indicating that the player made a hit.
   *
   * @throws IOException if an I/O error occurs
   */
  void displayHitMessage() throws IOException;

  /**
   * Displays a message indicating that the player made a miss.
   *
   * @throws IOException if an I/O error occurs
   */
  void displayMissMessage() throws IOException;

  void updateCell(int row, int col, CellState state);

  void addCellClickListener(int row, int col, ActionListener listener);

}
