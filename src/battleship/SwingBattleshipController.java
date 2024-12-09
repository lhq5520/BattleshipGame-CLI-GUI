package battleship;

/**
 * Controller for the Battleship game using the Swing-based GUI.
 * This class connects the model and view, handling user input
 * and updating the view based on the model's state.
 */
public class SwingBattleshipController implements BattleshipController {

  private final BattleshipModel model; // The model representing the game's logic and state
  private final SwingBattleshipView view; // The Swing-based GUI for the game

  /**
   * Constructs the SwingBattleshipController with the specified model and view.
   * Initializes the controller and sets up the "New Game" button listener.
   *
   * @param model the BattleshipModel implementation
   * @param view  the SwingBattleshipView implementation
   */
  public SwingBattleshipController(BattleshipModel model, SwingBattleshipView view) {
    this.model = model;
    this.view = view;

    // Attach listener for the "New Game" button
    view.addNewGameListener(e -> playGame(model));
  }

  /**
   * Starts or restarts the game.
   * Resets the model and view states, reattaches cell listeners, and displays a welcome message.
   *
   * @param model the BattleshipModel implementation to be reset
   */
  @Override
  public void playGame(BattleshipModel model) {
    model.startGame();           // Reset the model state
    view.resetView();            // Reset the view state
    initializeGrid();            // Attach listeners to grid cells
    view.displayWelcomeMessage(); // Show the welcome message
  }

  /**
   * Initializes the grid by attaching listeners to each cell in the game grid.
   * Listeners handle user clicks on grid cells and update the game state accordingly.
   */
  private void initializeGrid() {
    for (int row = 0; row < 10; row++) {
      for (int col = 0; col < 10; col++) {
        int finalRow = row;
        int finalCol = col;

        // Attach a listener to each cell button in the view
        view.addCellClickListener(row, col, e -> {
          handleGuess(finalRow, finalCol);
        });
      }
    }
  }

  /**
   * Handles the player's guess at a specific grid cell.
   * Updates the model and view based on whether the guess is a hit or miss,
   * and checks if the game is over.
   *
   * @param row the row index of the guessed cell
   * @param col the column index of the guessed cell
   */
  private void handleGuess(int row, int col) {
    try {
      // Process the guess and determine if it's a hit or miss
      boolean isHit = model.makeGuess(row, col);
      CellState state = isHit ? CellState.HIT : CellState.MISS;

      // Update the view with the result
      view.updateCell(row, col, state);
      if (isHit) {
        view.displayHitMessage();
      } else {
        view.displayMissMessage();
      }

      // Update the guess count in the view
      try {
        int guessCount = model.getGuessCount();
        int maxGuesses = model.getMaxGuesses();
        view.displayGuessCount(guessCount, maxGuesses);
      } catch (IllegalStateException e) {
        view.displayErrorMessage("Error displaying guess count due to an invalid state: "
            + e.getMessage());
      }

      // Check if the game is over
      if (model.isGameOver()) {
        boolean win = model.areAllShipsSunk();
        view.displayGameOver(win);

        // Display the ship grid at the end of the game
        try {
          view.displayShipGrid(model.getShipGrid());
        } catch (IllegalStateException e) {
          view.displayErrorMessage("Error displaying ship grid: " + e.getMessage());
        }
      }
    } catch (IllegalArgumentException ex) {
      // Handle invalid guesses (e.g., out-of-bounds or already guessed cells)
      view.displayErrorMessage("Invalid guess! " + ex.getMessage());
    } catch (IllegalStateException ex) {
      // Handle guesses after the game is over
      view.displayErrorMessage("The game is over. Restart to play again.");
    }
  }
}
