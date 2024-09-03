import java.util.ArrayList;
import java.util.List;
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

    // Try to win
    if (attemptCriticalMove('O')) {
      return; // Winning move made, exit
    }

    // Try to block the player from winning
    if (attemptCriticalMove('X')) {
      return; // Blocking move made, exit
    }

    // Fallback to a random move
    makeRandomMove();
  }

  // Attempts to make a winning or blocking move based on the specified playerMark
  public static boolean attemptCriticalMove(char playerMark) {
    // Check rows
    for (int i = 0; i < boardSize; i++) {
      int indexOfEmptySpot = findCriticalMove(i, 0, 0, 1, playerMark);
      if (indexOfEmptySpot != -1) {
        board[i][indexOfEmptySpot] = 'O';
        return true;
      }
    }

    // Check columns
    for (int j = 0; j < boardSize; j++) {
      int indexOfEmptySpot = findCriticalMove(0, j, 1, 0, playerMark);
      if (indexOfEmptySpot != -1) {
        board[indexOfEmptySpot][j] = 'O';
        return true;
      }
    }

    // Check main diagonal
    int mainDiagIndex = findCriticalMove(0, 0, 1, 1, playerMark);
    if (mainDiagIndex != -1) {
      board[mainDiagIndex][mainDiagIndex] = 'O';
      return true;
    }

    // Check anti-diagonal
    int antiDiagIndex = findCriticalMove(0, boardSize - 1, 1, -1, playerMark);
    if (antiDiagIndex != -1) {
      board[antiDiagIndex][boardSize - antiDiagIndex - 1] = 'O';
      return true;
    }

    return false;
  }

  // Finds a critical move on a line (row, column, or diagonal)
  public static int findCriticalMove(int startRow, int startCol, int rowIncrement, int colIncrement, char playerMark) {
    int marks = 0;
    int emptyRow = -1;
    int emptyCol = -1;

    for (int i = 0; i < boardSize; i++) {
      int row = startRow + i * rowIncrement;
      int col = startCol + i * colIncrement;

      if (board[row][col] == playerMark) {
        marks++;
      } else if (board[row][col] == '-') {
        emptyRow = row;
        emptyCol = col;
      }
    }

    // Ensure only one empty spot exists and we're about to win/block
    if (marks == boardSize - 1 && emptyRow != -1 && emptyCol != -1) {
      return rowIncrement == 0 ? emptyCol : emptyRow;
    }

    return -1; // No critical move found
  }

  // Makes a random move
  public static void makeRandomMove() {
    List<int[]> emptySpots = new ArrayList<>();
    for (int i = 0; i < boardSize; i++) {
      for (int j = 0; j < boardSize; j++) {
        if (board[i][j] == '-') {
          emptySpots.add(new int[]{i, j});
        }
      }
    }

    if (!emptySpots.isEmpty()) {
      int[] spot = emptySpots.get((int) (Math.random() * emptySpots.size()));
      board[spot[0]][spot[1]] = 'O';
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
