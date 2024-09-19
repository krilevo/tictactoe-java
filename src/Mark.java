package src;

// Enum representing the different marks that can be used on the board
public enum Mark {
  X('X'),     // Mark for player 1
  O('O'),     // Mark for player 2
  EMPTY('-'); // Mark for an empty spot

  private final char symbol;

  // Contrcutor for the Mark enum
  Mark(char symbol) {
    this.symbol = symbol;
  }

  // Getter for the symbol associated with this mark
  public char getSymbol() {
    return symbol;
  }
}