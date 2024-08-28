import java.util.Scanner;

public class TicTacToe {
  private static int boardSize;
  private static char[][] board;
  private static char currentPlayer = 'X';
  private static boolean isPlayingAgainstCPU = false;
  private static Scanner scanner = new Scanner(System.in);
  public static void main(String[] args) {
    
    checkIfPlayingAgainstCPU();
    getBoardSize();
    initializeBoard();
    printBoard();
    playGame();
    scanner.close();
  }

  public static void checkIfPlayingAgainstCPU() {
    while (true) {
      System.out.println("Do you want to play against CPU? (yes/no): ");
      String opponentChoice = scanner.next().toLowerCase();

      if (opponentChoice.equals("yes") || opponentChoice.equals("y")) {
        isPlayingAgainstCPU = true;
        break;
      } else if (opponentChoice.equals("no") || opponentChoice.equals("n")) {
        isPlayingAgainstCPU = false;
        break;
      } else {
        System.out.println("Invalid input.");
      }
    }
  }

  public static int getValidInput(String prompt) {
    int input;
    while (true) {
      System.out.print(prompt);

      if (scanner.hasNextInt()) {
        input = scanner.nextInt();
        break;
      } else {
        System.out.println("Invalid input. Please enter an integer.");
        scanner.next(); // Discard invalid input
      }
    }
    return input;
  }

  // Get the board size from the user
  public static void getBoardSize() {
    while (true) {
      boardSize = getValidInput("Give the board size (between 3 and 9): ");

      if (boardSize < 3 || boardSize > 9) {
        System.out.println("The size is too small or too big.");
      } else {
        break;
      }
    }
  }

  // Initialize the game board with empty spaces
  public static void initializeBoard() {
    board = new char[boardSize][boardSize];

    for (int i = 0; i < boardSize; i++) {
      for (int j = 0; j < boardSize; j++) {
        board[i][j] = '-';
      }
    }
  }

  // Print the board
  public static void printBoard() {
    System.out.println("Current board:");
    printTopRow();

    for (int i = 0; i < boardSize; i++) {
      System.out.print((i + 1) + "|");
      for (int j = 0; j < boardSize; j++) {
        System.out.print(board[i][j] + " ");
      }
      System.out.println();
    }
  }

  public static void printTopRow() {
    System.out.print("  ");
    for (int i = 0; i < boardSize; i++) {
      System.out.print(i + 1 + " ");
    }
    System.out.println();
    System.out.print("  ");
    for (int i = 0; i < boardSize; i++) {
      System.out.print("_ ");
    }
    System.out.println();
  }

  // Switch the player
  public static void switchPlayer() {
    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
  }

  public static void playGame() {
    boolean gameEnded = false;

    while (!gameEnded) {
      if (isPlayingAgainstCPU && currentPlayer == 'O') {
        makeCPUMove();
      } else {
        makeHumanMove();
      }

      printBoard();

      if (checkForWin()) {
        System.out.println("Player " + currentPlayer + " wins!");
        gameEnded = true;
      } else if (checkForDraw()) {
        System.out.println("The game is a draw!");
        gameEnded = true;
      } else {
        switchPlayer();
      }
    }
  }

  public static void makeHumanMove() {
    int row, col;

    while (true) {
      System.out.println("Player " + currentPlayer + ", enter your move (row and column): ");
      row = getValidInput("Enter row: ") - 1;
      col = getValidInput("Enter column: ") - 1;

      if (row >= 0 && row < boardSize && col >= 0 && col < boardSize && board[row][col] == '-') {
        board[row][col] = currentPlayer;
        break;
      } else {
        System.out.println("Invalid move!");
      }
    }
  }

  public static void makeCPUMove() {
    System.out.println("CPU is making a move...");
    int row, col;

    while (true) {
      row = (int) (Math.random() * boardSize);
      col = (int) (Math.random() * boardSize);

      if (board[row][col] == '-') {
        board[row][col] = currentPlayer;
        break;
      }
    }
  }

  public static boolean checkForWin() {
    return (checkRows() || checkColumns() || checkDiagonals());
  }

  public static boolean checkRows() {
    for (int i = 0; i < boardSize; i++) {
      boolean win = true;
      for (int j = 1; j < boardSize; j++) {
        if (board[i][j] != board[i][0] || board[i][j] == '-') {
          win = false;
          break;
        }
      }
      if (win) return true;
    }
    return false;
  }

  public static boolean checkColumns() {
    for (int i = 0; i < boardSize; i++) {
      boolean win = true;
      for (int j = 1; j < boardSize; j++) {
        if (board[j][i] != board[0][i] || board[j][i] == '-') {
          win = false;
          break;
        }
      }
      if (win) return true;
    }
    return false;
  }

  public static boolean checkDiagonals() {
    // Check the main diagonal
    boolean win = true;
    for (int i = 1; i < boardSize; i++) {
      if (board[i][i] != board[0][0] || board[i][i] == '-') {
        win = false;
        break;
      }
    }
    if (win) return true;

    // Check the anti-diagonal
    win = true;
    for (int i = 1; i < boardSize; i++) {
      if (board[i][boardSize - i - 1] != board[0][boardSize - 1] || board[i][boardSize - i - 1] == '-') {
        win = false;
        break;
      }
    }
    return win;
  }

  public static boolean checkForDraw() {
    for (int i = 0; i < boardSize; i++) {
      for (int j = 0; j < boardSize; j++) {
        if (board[i][j] == '-') {
          return false;
        }
      }
    }
    return true;
  }
}
