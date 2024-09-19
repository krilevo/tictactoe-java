package src;

import java.util.Scanner;

public class Player {
  protected Mark mark;  // The mark (X or O) assigned to this player
  protected Scanner scanner;  // Scanner for reading user input

  // Contructor to initialize the player with a mark and input scanner
  public Player(Mark mark, Scanner scanner) {
    this.mark = mark;
    this.scanner = scanner;
  }

  // Getter method to retrieve the player's mark
  public Mark getMark() {
    return mark;
  }

  // Handles the player's move
  public void makeMove(Board board) {
    int row, col;

    // Loop until the player makes a valid move
    while (true) {
      // Prompt the player for a row and column
      System.out.println("Player " + mark + ", enter your move (row and column): ");

      // Get valid row and column inputs from the player and adjust for 0-indexing
      row = getValidInput("Enter row: ") - 1;
      col = getValidInput("Enter column: ") - 1;

      // Check if the move is within bounds and an empty space
      if (board.isValidMove(row, col)) {
        board.makeMove(row, col, mark.getSymbol()); // Make the move (add player's mark on the board)
        break;  // Exit the loop after a valid move
      } else {
        System.out.println("Invalid move!");  // The move is invalid, prompt again
      }
    }
  }

  // Validates user input (is an integer)
  protected int getValidInput(String prompt) {
    int input;

    // Loop until the player enters a valid integer
    while (true) {
      System.out.println(prompt); // Display the prompt to the player

      // Check if the next input is an integer
      if (scanner.hasNextInt()) {
        input = scanner.nextInt();
        break;  // Exit the loop
      } else {
        System.out.println("Invalid input. Please enter an integer.");  // The input is invalid, prompt again
        scanner.next(); // Discard invalid input
      }
    }
    return input; // Return the valid integer input
  }
}
