import java.util.Scanner;

public class TicTacToe {
  private static int boardSize;
  private static char[][] board;
  private static char currentPlayer = 'X';
  public static void main(String[] args) {
    getBoardSize();
    initializeBoard();
    printBoard();
    playGame();
  }

  // Get the board size from the user
  public static void getBoardSize() {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.println("Give the board size (between 3 and 9): ");
      boardSize = scanner.nextInt();

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
    Scanner scanner = new Scanner(System.in);

    while (!gameEnded) {
      int row, col;

      while (true) {
        System.out.println("Player " + currentPlayer + ", enter your move (row and column): ");
        row = scanner.nextInt() - 1;
        col = scanner.nextInt() - 1;

        if (row >= 0 && row < boardSize && col >= 0 && col < boardSize && board[row][col] == '-') {
          board[row][col] = currentPlayer;
          break;
        } else {
          System.out.println("Invalid move!");
        }
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

    scanner.close();
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
