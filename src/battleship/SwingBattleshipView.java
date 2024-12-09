package battleship;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Represents the GUI view for the Battleship game using Java Swing.
 * This class provides the graphical interface for the game,
 * including the game board, messages, and buttons for user interaction.
 */
public class SwingBattleshipView extends JFrame implements BattleshipViewGui {

  private JButton[][] gridButtons; // Buttons representing the game grid
  private JLabel messageCenter; // Label to display central messages
  private JLabel promptCenter; // Label to display prompts to the player
  private JLabel guessCount; // Label to display the number of guesses
  private JButton newGameButton; // Button to start a new game

  /**
   * Constructs the Swing-based GUI for the Battleship game.
   * Sets up the main frame, game board, and control buttons.
   */
  public SwingBattleshipView() {
    super("Battleship Game");
    this.gridButtons = new JButton[10][10];

    // Setup the main frame
    setupMainFrame();

    // Add components
    addGameBoard(); // Center
    addMessageCenter(); // North
    addPromptCenterAndButtons(); // South

    // Display the frame
    this.setVisible(true);
  }

  /**
   * Sets up the main frame for the application.
   * Configures the window's properties and layout.
   */
  private void setupMainFrame() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(620, 670);
    this.setLayout(new BorderLayout());
  }

  /**
   * Creates and adds the game board to the GUI.
   * The game board includes row and column labels and buttons for each grid cell.
   */
  private void addGameBoard() {
    JPanel gameBoardPanel = new JPanel();
    gameBoardPanel.setLayout(new BorderLayout());

    // Create column labels
    JPanel columnLabels = new JPanel(new GridLayout(1, 11));
    columnLabels.add(new JLabel("")); // Empty corner for alignment
    for (int col = 0; col < 10; col++) {
      columnLabels.add(new JLabel(String.valueOf(col), SwingConstants.CENTER));
    }
    gameBoardPanel.add(columnLabels, BorderLayout.NORTH);

    // Create the game grid with row labels
    JPanel gridPanel = new JPanel(new GridLayout(10, 11));
    for (int row = 0; row < 10; row++) {
      // Add row labels (A-J)
      gridPanel.add(new JLabel(String.valueOf((char) (row + 65)), SwingConstants.CENTER));
      for (int col = 0; col < 10; col++) {
        JButton cell = new JButton();
        cell.setFocusPainted(false);
        this.gridButtons[row][col] = cell;
        gridPanel.add(cell);
      }
    }
    gameBoardPanel.add(gridPanel, BorderLayout.CENTER);

    // Add the game board panel to the main frame
    this.add(gameBoardPanel, BorderLayout.CENTER);
  }

  /**
   * Creates and adds the prompt center, guess count, and control buttons.
   */
  private void addPromptCenterAndButtons() {
    promptCenter = new JLabel("Welcome to BattleShip", SwingConstants.CENTER);
    promptCenter.setFont(new Font("Arial", Font.PLAIN, 14));
    promptCenter.setPreferredSize(new Dimension(600, 30));

    JPanel messagePanel = new JPanel(new BorderLayout());
    messagePanel.add(promptCenter, BorderLayout.EAST);

    guessCount = new JLabel("Click to Start", SwingConstants.RIGHT);
    guessCount.setFont(new Font("Arial", Font.PLAIN, 14));
    guessCount.setPreferredSize(new Dimension(600, 30));
    messagePanel.add(guessCount, BorderLayout.WEST);

    JPanel buttonPanel = new JPanel(new FlowLayout());

    newGameButton = new JButton("New Game");
    newGameButton.setPreferredSize(new Dimension(100, 25));
    buttonPanel.add(newGameButton);

    JButton exitButton = new JButton("Exit");
    exitButton.setPreferredSize(new Dimension(100, 25));
    exitButton.addActionListener(e -> System.exit(0));
    buttonPanel.add(exitButton);

    buttonPanel.setPreferredSize(new Dimension(600, 40));
    messagePanel.add(buttonPanel, BorderLayout.SOUTH);

    this.add(messagePanel, BorderLayout.SOUTH);
  }

  /**
   * Resets the view to its initial state.
   * Clears the game board, re-enables buttons, and resets messages.
   */
  public void resetView() {
    for (int row = 0; row < 10; row++) {
      for (int col = 0; col < 10; col++) {
        JButton button = gridButtons[row][col];

        // Remove all existing action listeners
        for (ActionListener al : button.getActionListeners()) {
          button.removeActionListener(al);
        }

        // Reset button state
        button.setText("");
        button.setBackground(null);
        button.setEnabled(true);
      }
    }

    // Reset display messages
    displayWelcomeMessage();
  }

  /**
   * Adds an ActionListener to the "New Game" button.
   *
   * @param listener the ActionListener to add
   */
  public void addNewGameListener(ActionListener listener) {
    newGameButton.addActionListener(listener);
  }

  /**
   * Adds the message center label to display key game messages.
   */
  private void addMessageCenter() {
    messageCenter = new JLabel("Welcome to Battleship!", SwingConstants.CENTER);
    messageCenter.setFont(new Font("Arial", Font.BOLD, 18));
    messageCenter.setPreferredSize(new Dimension(600, 50));

    this.add(messageCenter, BorderLayout.NORTH);
  }

  /**
   * Displays the welcome message to the player.
   */
  @Override
  public void displayWelcomeMessage() {
    if (messageCenter != null) {
      messageCenter.setText("Welcome to Battleship! Click a cell to make a guess.");
    }

    if (promptCenter != null) {
      promptCenter.setText("Welcome to Battleship!");
    }

    if (guessCount != null) {
      guessCount.setText("Click to Start");
    }
  }

  /**
   * Displays a prompt message to the player.
   */
  @Override
  public void displayPromptMessage() {
    if (promptCenter != null) {
      promptCenter.setText("Make your next guess by clicking a cell.");
    }
  }

  /**
   * Displays a hit message when the player hits a ship.
   */
  @Override
  public void displayHitMessage() {
    if (messageCenter != null) {
      messageCenter.setText("Hit! Great job!");
    }

    if (promptCenter != null) {
      promptCenter.setText("Got a Hit. Check Your Next Guesses Carefully.");
    }
  }

  /**
   * Displays a miss message when the player misses a ship.
   */
  @Override
  public void displayMissMessage() {
    if (messageCenter != null) {
      messageCenter.setText("Miss! Try again.");
    }

    if (promptCenter != null) {
      promptCenter.setText("Missed! You May Get Lucky Next Guesses.");
    }
  }

  /**
   * Displays the game-over message when the game ends.
   *
   * @param win true if the player wins; false otherwise
   */
  @Override
  public void displayGameOver(boolean win) {
    String message = win ? "Congratulations! You sank all the ships!"
        : "Game Over! You've used all your guesses.";
    if (messageCenter != null) {
      messageCenter.setText(message);
    }

    if (promptCenter != null) {
      promptCenter.setText("Game over!");
    }
    JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Updates the cell's display state based on the game state.
   *
   * @param row   the row index of the cell
   * @param col   the column index of the cell
   * @param state the new state of the cell (e.g., HIT, MISS, UNKNOWN)
   */
  @Override
  public void updateCell(int row, int col, CellState state) {
    JButton button = gridButtons[row][col];

    switch (state) {
      case HIT:
        button.setText("O");
        break;
      case MISS:
        button.setText("X");
        break;
      case UNKNOWN:
        button.setBackground(Color.LIGHT_GRAY);
        button.setText("");
        break;
      default:
        throw new IllegalArgumentException("Unexpected CellState: " + state);
    }

    button.setEnabled(state == CellState.UNKNOWN);
    button.repaint();
  }

  /**
   * Displays the current state of the game grid.
   *
   * @param cellGrid the 2D array representing the cell states
   */
  @Override
  public void displayCellGrid(CellState[][] cellGrid) {
    for (int row = 0; row < 10; row++) {
      for (int col = 0; col < 10; col++) {
        updateCell(row, col, cellGrid[row][col]);
      }
    }
  }

  /**
   * Displays the ship grid in a separate window.
   * Reveals ship placements after the game ends.
   *
   * @param shipGrid the 2D array representing the ship placements
   */
  @Override
  public void displayShipGrid(ShipType[][] shipGrid) {
    JFrame shipGridFrame = new JFrame("Ship Grid");
    shipGridFrame.setSize(500, 550);
    shipGridFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    shipGridFrame.setLayout(new BorderLayout());

    JPanel shipGridPanel = new JPanel(new GridLayout(11, 11));
    shipGridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    shipGridPanel.add(new JLabel(""));
    for (int col = 0; col < 10; col++) {
      JLabel columnLabel = new JLabel(String.valueOf(col), SwingConstants.CENTER);
      columnLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      shipGridPanel.add(columnLabel);
    }

    for (int row = 0; row < 10; row++) {
      JLabel rowLabel = new JLabel(String.valueOf((char) (row + 65)), SwingConstants.CENTER);
      rowLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      shipGridPanel.add(rowLabel);

      for (int col = 0; col < 10; col++) {
        JLabel cellLabel = new JLabel("", SwingConstants.CENTER);
        cellLabel.setOpaque(true);
        cellLabel.setPreferredSize(new Dimension(40, 40));
        cellLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        if (shipGrid[row][col] != null) {
          cellLabel.setBackground(Color.GRAY);
          cellLabel.setText(shipGrid[row][col].toString().substring(0, 1));
          cellLabel.setForeground(Color.WHITE);
        } else {
          cellLabel.setBackground(Color.LIGHT_GRAY);
        }
        shipGridPanel.add(cellLabel);
      }
    }

    shipGridFrame.add(shipGridPanel, BorderLayout.CENTER);
    shipGridFrame.setVisible(true);
  }

  /**
   * Displays the current guess count and maximum allowed guesses.
   *
   * @param currentGuesses the current number of guesses made
   * @param maxGuesses     the maximum number of guesses allowed
   */
  @Override
  public void displayGuessCount(int currentGuesses, int maxGuesses) {
    if (guessCount != null) {
      guessCount.setText("Guess: " + currentGuesses + " / " + maxGuesses);
    }
  }

  /**
   * Displays an error message in a dialog box.
   *
   * @param message the error message to display
   */
  @Override
  public void displayErrorMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Adds an ActionListener to a specific cell in the game grid.
   *
   * @param row      the row index of the cell
   * @param col      the column index of the cell
   * @param listener the ActionListener to add
   */
  @Override
  public void addCellClickListener(int row, int col, ActionListener listener) {
    gridButtons[row][col].addActionListener(listener);
  }
}
