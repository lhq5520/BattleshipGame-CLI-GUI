package battleship;

import java.util.List;

/**
 * Represents a ship in the Battleship game.
 * Each ship has a specific type, a list of coordinates on the game board,
 * and tracks the number of hits it has sustained.
 */
public class Ship {
  private ShipType type;
  private List<int[]> coordinates;
  private int hits;

  /**
   * Constructs a new Ship instance with a specified type and list of coordinates.
   *
   * @param type the type of the ship (e.g., Aircraft Carrier, Battleship, etc.).
   * @param coordinates the list of coordinates occupied by this ship on the game board.
   */
  public Ship(ShipType type, List<int[]> coordinates) {
    this.type = type;
    this.coordinates = coordinates;
    this.hits = 0;
  }

  /**
   * Retrieves the type of this ship.
   *
   * @return the type of the ship.
   */
  public ShipType getType() {
    return type;
  }

  /**
   * Retrieves the list of coordinates occupied by this ship on the game board.
   *
   * @return a list of int arrays representing the coordinates of the ship.
   */
  public List<int[]> getCoordinates() {
    return coordinates;
  }

  /**
   * Checks if the ship is sunk based on the number of hits it has sustained.
   *
   * @return true if the ship is sunk (has sustained one or more hits), false otherwise.
   */
  public boolean isSunk() {
    if (hits > 0) {
      return true;
    }
    return false;
  }

  /**
   * Increments the hit count for this ship to record a hit.
   */
  public void markHits() {
    hits++;
  }

}

