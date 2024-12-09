package battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementation of the Battleship game model.
 * This class represents the model of a Battleship game,
 * including the game board, user guesses, and ship placements.
 * It provides methods to start the game, make guesses, check game status,
 * and retrieve game grid states.
 * The game board is represented by two grids:
 * <code>boardGrid</code>: Tracks the actual placement of ships.
 * <code>userGrid</code>: Tracks the player's guesses and the state of each cell
 * (hit, miss, unknown).
 * The class supports seeding for random placements,
 * making it useful for testing. Once the game is over,
 * the ship placements are revealed.
 */

public class BattleshipModelImpl implements BattleshipModel {
  private ShipType[][] boardGrid;
  private CellState[][] userGrid;
  private int guessCounts;
  private int maxCounts;
  private boolean isGameOver;
  private boolean areAllShipsDestroyed;
  private List<Ship> ships;
  private Random random; //test with seed

  /**
   * Constructor to initialize a new Battleship game model with a maximum number of allowed guesses.
   *
   * @param maxCounts the maximum number of guesses allowed in the game.
   */
  public BattleshipModelImpl(int maxCounts) {
    this.maxCounts = maxCounts;
    this.boardGrid = new ShipType[10][10];
    this.userGrid = new CellState[10][10];
    this.isGameOver = false;
    this.areAllShipsDestroyed = false;
    this.guessCounts = 0;
    this.ships = new ArrayList<>();
    this.random = new Random();

    for (int i = 0; i < userGrid.length; i++) {
      for (int j = 0; j < userGrid[0].length; j++) {
        userGrid[i][j] = CellState.UNKNOWN;
      }
    }
  }

  /**
   * Constructor to initialize a new Battleship game model with a maximum number of allowed guesses
   * and a specific seed for random placement. Useful for testing consistent ship placements.
   *
   * @param maxCounts the maximum number of guesses allowed in the game.
   * @param seed      the seed for random placement of ships.
   */
  public BattleshipModelImpl(int maxCounts, long seed) {
    this.maxCounts = maxCounts;
    this.boardGrid = new ShipType[10][10];
    this.userGrid = new CellState[10][10];
    this.isGameOver = false;
    this.areAllShipsDestroyed = false;
    this.guessCounts = 0;
    this.ships = new ArrayList<>();
    this.random = new Random(seed);

    for (int i = 0; i < userGrid.length; i++) {
      for (int j = 0; j < userGrid[0].length; j++) {
        userGrid[i][j] = CellState.UNKNOWN;
      }
    }
  }

  /**
   * Initializes the game by setting up the grids and randomly placing ships.
   */
  @Override
  public void startGame() {
    // Mark the start of the game
    isGameOver = false;
    guessCounts = 0;
    areAllShipsDestroyed = false;
    ships.clear();  // Clear any previously placed shipss

    // Reset grids
    for (int i = 0; i < boardGrid.length; i++) {
      for (int j = 0; j < boardGrid[i].length; j++) {
        boardGrid[i][j] = null; // Clear ship placements
        userGrid[i][j] = CellState.UNKNOWN; // Reset user guesses
      }
    }
    
    // Define ships
    ShipType[] ships = {
        ShipType.AIRCRAFT_CARRIER,
        ShipType.BATTLESHIP,
        ShipType.SUBMARINE,
        ShipType.DESTROYER,
        ShipType.PATROL_BOAT
    };

    // Place each ship
    for (ShipType ship : ships) {
      boolean placed = false;

      // Keep trying to place the ship until a valid position is found
      while (!placed) {
        int row = random.nextInt(10);        // Random row between 0 and 9
        int col = random.nextInt(10);        // Random column between 0 and 9
        boolean isVertical = random.nextBoolean(); // Random orientation

        if (canGenerateShip(row, col, ship, isVertical)) {
          generateShip(row, col, ship, isVertical);
          placed = true; // Ship placed successfully, exit loop
        }
      }
    }
  }

  /**
   * Attempts to place a ship on the board at the specified row, column, and orientation.
   *
   * @param row        the starting row for the ship placement.
   * @param col        the starting column for the ship placement.
   * @param shipType   the type of ship to be placed.
   * @param isVertical the orientation of the ship; true if vertical, false if horizontal.
   */

  private void generateShip(int row, int col, ShipType shipType, boolean isVertical) {
    int shipSize = shipType.getSize();
    List<int[]> coordinates = new ArrayList<>();

    for (int i = 0; i < shipSize; i++) {
      if (isVertical) {
        boardGrid[row + i][col] = shipType; // Place symbol vertically
        coordinates.add(new int[]{row + i, col});
      } else {
        boardGrid[row][col + i] = shipType; // Place symbol horizontally
        coordinates.add(new int[]{row, col + i});
      }
    }
    ships.add(new Ship(shipType, coordinates));
  }

  /**
   * Checks if a ship can be placed at the specified row, column, and orientation.
   *
   * @param row        the starting row for the ship placement.
   * @param col        the starting column for the ship placement.
   * @param shipType   the type of ship to be placed.
   * @param isVertical the orientation of the ship; true if vertical, false if horizontal.
   * @return true if the ship can be placed at the specified location, false otherwise.
   */
  private boolean canGenerateShip(int row, int col, ShipType shipType, boolean isVertical) {
    int shipSize = shipType.getSize();

    // Boundary check based on orientation
    if (isVertical) {
      if (row + shipSize > boardGrid.length) {
        return false; // Out of bounds vertically
      }
    } else {
      if (col + shipSize > boardGrid[0].length) {
        return false; // Out of bounds horizontally
      }
    }

    // Check each cell along the ship's path
    for (int i = 0; i < shipSize; i++) {
      if (isVertical) {
        if (isCellOccupied(row + i, col)) {
          return false; // Cell is occupied or out of bounds
        }
      } else {
        if (isCellOccupied(row, col + i)) {
          return false; // Cell is occupied or out of bounds
        }
      }
    }
    return true; // All checks passed; ship can be placed
  }

  /**
   * Checks if a specified cell is already occupied by another ship or is out of bounds.
   *
   * @param row the row index of the cell.
   * @param col the column index of the cell.
   * @return true if the cell is occupied or out of bounds, false otherwise.
   */
  // Helper method to check if a cell is occupied or out of bounds
  private boolean isCellOccupied(int row, int col) {
    return row <= 0
        || row >= boardGrid.length
        || col <= 0
        || col >= boardGrid[0].length
        || boardGrid[row][col] != null;
  }


  /**
   * Processes the player's guess at the specified coordinate.
   *
   * @param row the row index (0-based)
   * @param col the column index (0-based)
   * @return true if the guess was a hit, false otherwise
   * @throws IllegalArgumentException if the coordinates are out of bounds or the cell has already
   *                                  been guessed
   * @throws IllegalStateException    if the game is already over
   */
  @Override
  public boolean makeGuess(int row, int col) {
    if (isGameOver) {
      throw new IllegalStateException("The game is already over");
    }
    if (!isWithinBound(row, col)) {
      throw new IllegalArgumentException("Coordinates out of bounds");
    }
    if (userGrid[row][col] != CellState.UNKNOWN) {
      throw new IllegalArgumentException("Cell has already been guessed");
    }

    final CellState missingSymbol = CellState.MISS;
    final CellState hitSymbol = CellState.HIT;

    if (boardGrid[row][col] == null) {
      guessCounts++;
      userGrid[row][col] = missingSymbol;
      return false;
    } else {
      guessCounts++;
      userGrid[row][col] = hitSymbol;

      if (areAllShipsSunk()) {
        isGameOver = true;  // Mark game as over if all ships are sunk
      }
      return true;
    }
  }

  /**
   * Checks if a given coordinate is within the bounds of the game grid.
   *
   * @param row the row index of the cell.
   * @param col the column index of the cell.
   * @return true if the cell is within bounds, false otherwise.
   */
  // Helper method to check if a cell is within bounds
  private boolean isWithinBound(int row, int col) {
    return row >= 0
        && row < userGrid.length
        && col >= 0
        && col < userGrid[0].length;
  }


  /**
   * Checks if the game is over.
   *
   * @return true if all ships are sunk or the maximum number of guesses is reached, false otherwise
   */
  @Override
  public boolean isGameOver() {
    if (!isGameOver) {
      if (guessCounts >= maxCounts || areAllShipsDestroyed) {
        isGameOver = true;
      }
    }
    return isGameOver;
  }


  /**
   * Checks if all ships have been sunk.
   *
   * @return true if all ships are sunk, false otherwise
   */
  @Override
  public boolean areAllShipsSunk() {
    for (Ship ship : ships) {
      for (int[] coordinate : ship.getCoordinates()) {
        if (userGrid[coordinate[0]][coordinate[1]] == CellState.HIT) {
          ship.markHits();
        }
      }
    }

    for (Ship ship : ships) {
      if (!ship.isSunk()) {
        return false;
      }
    }
    isGameOver = true;
    return true;
  }

  /**
   * Gets the number of guesses the player has made so far.
   *
   * @return the number of guesses made
   */
  @Override
  public int getGuessCount() {
    return guessCounts;
  }

  /**
   * Gets the maximum number of guesses allowed.
   *
   * @return the maximum number of guesses
   */
  @Override
  public int getMaxGuesses() {
    return maxCounts;
  }

  /**
   * Retrieves the current state of the cell grid for display purposes.
   *
   * @return a deep copy of the 2D array representing the cell grid state
   */
  @Override
  public CellState[][] getCellGrid() {

    CellState[][] copy = new CellState[userGrid.length][userGrid[0].length];
    for (int i = 0; i < userGrid.length; i++) {
      for (int j = 0; j < userGrid[i].length; j++) {
        copy[i][j] = userGrid[i][j];
      }
    }
    return copy;
  }

  /**
   * Retrieves the current state of the ship grid after the game is over. The ship grid should not
   * be revealed during the game.
   *
   * @return a deep copy of the 2D array representing the ship grid state
   * @throws IllegalStateException if the game is not over
   */
  @Override
  public ShipType[][] getShipGrid() {

    if (!isGameOver) {
      throw new IllegalStateException("The ship grid can only be accessed after the game is over.");
    }

    ShipType[][] copy = new ShipType[boardGrid.length][boardGrid[0].length];
    for (int i = 0; i < boardGrid.length; i++) {
      for (int j = 0; j < boardGrid[i].length; j++) {
        copy[i][j] = boardGrid[i][j];
      }
    }
    return copy;
  }

  /**
   * Returns a string representation of the user grid, showing the player's view of the game.
   * If the game is over, it also displays the ship grid.
   *
   * @return a string representing the state of the game grids.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("User Grid:\n");

    // Display the user grid (player's view of the game)
    for (int i = 0; i < userGrid.length; i++) {
      for (int j = 0; j < userGrid[i].length; j++) {
        if (userGrid[i][j] == CellState.UNKNOWN) {
          sb.append("_ "); // Unknown cells
        } else if (userGrid[i][j] == CellState.MISS) {
          sb.append("MISS "); // Missed guesses
        } else if (userGrid[i][j] == CellState.HIT) {
          sb.append("HIT "); // Hit cells
        }
      }
      sb.append("\n");
    }

    // Display the board grid (ship placements) if the game is over
    if (isGameOver) {
      sb.append("\nShip Grid:\n");
      for (int i = 0; i < boardGrid.length; i++) {
        for (int j = 0; j < boardGrid[i].length; j++) {
          if (boardGrid[i][j] == null) {
            sb.append("_ "); // Empty cells
          } else {
            sb.append(boardGrid[i][j].getSymbol() + " "); // Display ship symbols
          }
        }
        sb.append("\n");
      }
    } else {
      sb.append("\n(Ship grid is hidden until the game is over.)\n");
    }

    return sb.toString();
  }

}
