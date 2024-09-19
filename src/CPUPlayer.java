package src;

import java.util.ArrayList;
import java.util.List;

// Extend the Player class
public class CPUPlayer extends Player {

  // Contructor for CPUPlayer, no need for scanner
  public CPUPlayer(Mark mark) {
    // Call the constructor of the superclass Player with the given mark
    // Passing 'null' for scanner as CPU does not require user input
    super(mark, null); 
  }

  @Override
  // Override the 'makeMove'method in the Player superclass
  public void makeMove(Board board) {

    // Try to win
    if (attemptCriticalMove(board, mark.getSymbol())) {
      System.out.println("CPU is making a move...");
      return; // Exit if a move was made
    }

    // If there is no winning move, try to block the player from winning instead
    if (attemptCriticalMove(board, Mark.X.getSymbol())) {
      return; // Exit if a move was made
    }

    // If cannot win or block, make a random move
    makeRandomMove(board);
  }

  // Attempts to make a critical move (either winning or blocking)
  private boolean attemptCriticalMove(Board board, char mark) {

    // Check each row for a critical move
    for (int i = 0; i < board.getSize(); i++) {
      int indexOfEmptySpot = findCriticalMove(board, i, 0, 0, 1, mark);
      if (indexOfEmptySpot != -1) {
        board.makeMove(i, indexOfEmptySpot, this.mark.getSymbol());
        return true;  // Critical move found and executed
      }
  }

  // Check each column for a critical move
  for (int j = 0; j < board.getSize(); j++) {
    int indexOfEmptySpot = findCriticalMove(board, 0, j, 1, 0, mark);
    if (indexOfEmptySpot != -1) {
      board.makeMove(indexOfEmptySpot, j, this.mark.getSymbol());
      return true;  // Critical move found and executed
    }
  }

  // Check the main diagonal for a critical move
  int mainDiagIndex = findCriticalMove(board, 0, 0, 1, 1, mark);
  if (mainDiagIndex != -1) {
    board.makeMove(mainDiagIndex, mainDiagIndex, this.mark.getSymbol());
    return true;
  }

  // Check the secondary diagonal for a critical move
  int secDiagIndex = findCriticalMove(board, 0, board.getSize() - 1, 1, -1, mark);
  if (secDiagIndex != -1) {
    board.makeMove(secDiagIndex, board.getSize() - secDiagIndex - 1, this.mark.getSymbol());
    return true;  // Critical move found and executed
  }

  return false; // No critical move found
  }

  // Finds a critical move (winning or blocking) in a given direction (row, column, diagonal)
  private int findCriticalMove(Board board, int row, int col, int rowInc, int colInc, char mark) {
    List<Integer> emptySpots = new ArrayList<>(); // List to store empty spots in the line
    int markCount = 0;  // Counter for the number of marks in the line

    // Iterate through the line (row, column, or diagonal)
    for (int i = 0; i < board.getSize(); i++) {
      if (board.getCell(row, col) == mark) {
        markCount++;  // Count how many cells in the line have the required mark
      } else if (board.getCell(row, col) == Mark.EMPTY.getSymbol()) {
        emptySpots.add(i);  // Keep track of empty spots
      }
      row += rowInc;  // Move to the next cell in the line
      col += colInc;  // Move to the next cell in the line
    }

    // Check if the line has all but one cell filled with the required mark and there's an empty spot
    if (markCount == board.getSize() - 1 && !emptySpots.isEmpty()) {
        return emptySpots.get(0); // Return the index of the empty spot
    }
    return -1;  // No critical move found
  }

  // Makes a random move if no critical move is possible
  private void makeRandomMove(Board board) {
    List<int[]> availableMoves = new ArrayList<>(); // List to store all available moves

    // Iterate through the board to find all valid (empty) spots
    for (int i = 0; i < board.getSize(); i++) {
      for (int j = 0; j < board.getSize(); j++) {
        if (board.isValidMove(i, j)) {
          availableMoves.add(new int[]{i, j});  // Add valid move to the list
        }
      }
    }

    // Check if there are any valid moves available
    if (!availableMoves.isEmpty()) {
      // Choose a random move from the list and make it
      int[] move = availableMoves.get((int) (Math.random() * availableMoves.size()));
      board.makeMove(move[0], move[1], this.mark.getSymbol());
    }
  }
}
