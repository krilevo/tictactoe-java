package src;

import java.util.Scanner;

public class Game {
  // Instance variables for the game components
  private Board board;  // The game board
  private Player player1;  // Player 1 (X)
  private Player player2;  // Player 2 or CPU (O)
  private Player currentPlayer;  // The player whose turn it is
  private boolean isPlayingAgainstCPU;  // Flag to check if the opponent is CPU
  private Scanner scanner;  // Scanner for user input

  // Contructor to initialize the game
  public Game() {
    scanner = new Scanner(System.in);
    initializeGame(); // Set up the game components
  }
  
  // Method to initialize the game, including setting up players and the board
  private void initializeGame() {
    checkIfPlayingAgainstCPU();  // Determine if the opponent is CPU
    int boardSize = getBoardSize();  // Get the size of the board from the user
    board = new Board(boardSize);  // Create the board
    player1 = new Player(Mark.X, scanner);  // Initialize Player 1 with 'X'
    
    // Initialize Player 2 with 'O', either as a human or CPU player
    player2 = isPlayingAgainstCPU ? new CPUPlayer(Mark.O) : new Player(Mark.O, scanner);
    
    currentPlayer = player1;  // Start with Player 1's turn
  }

  // Main game loop to start and manage the game until it ends
  public void start() {
    board.print();  // Print the initial empty board

    // Loop until there is a winner or draw
    while (true) {
      currentPlayer.makeMove(board);  // Current player makes a move
      board.print();  // Print the updated board

      // Check if the current player has won
      if (board.checkForWin(currentPlayer.getMark().getSymbol())) {
        System.out.println("Player " + currentPlayer.getMark() + " wins!");
        break;
      // Check if the game is a draw
      } else if (board.checkForDraw()) {
        System.out.println("The game is a draw!");
        break;
      }
      switchPlayer(); // Switch to the other player for the next turn
    }
  }

  // Asks the user if they want to play against a CPU opponent
  private void checkIfPlayingAgainstCPU() {
    while (true) {
      System.out.println("Do you want to play against CPU? (yes/no): ");
      String input = scanner.next().toLowerCase();  // Get user input and convert it to lowercase

      // Set the flag based on user input and exit loop
      if (input.equals("yes") || input.equals("y")) {
        isPlayingAgainstCPU = true;
        break;
      } else if (input.equals("no") || input.equals("n")) {
        isPlayingAgainstCPU = false;
        break;
      } else {
        System.out.println("Invalid input."); // Prompt user again if input is invalid
      }
    }
  }

  // Gets the board size from the user
  private int getBoardSize() {
    int size;
    while (true) {
      System.out.println("Enter the board size (between 3 and 9): ");

      // Check if the input is an integer
      if (scanner.hasNextInt()) {
        size = scanner.nextInt();

        // Check if the size is within the allowed range
        if (size >= 3 && size <= 9) {
          break;  // Exit the loop if valid size is given
        } else {
          System.out.println("Invalid size. Please choose between 3 and 9.");
        }
      } else {
        System.out.println("Invalid input. Please enter an integer.");
        scanner.next(); // Clear invalid input
      }
    }
    return size;  // Return the given board size
  }

  // Switches the turn to the other player
  private void switchPlayer() {
    currentPlayer = (currentPlayer == player1) ? player2 : player1;
  }
}
