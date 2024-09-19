package src;

public class Board {
  // 2D array representing the game board
  private char[][] board;

  // Size of the board (e.g., 3 for 3x3 board)
  private int size;

  // Contructor to initialize the board with the given size
  public Board(int size) {
    this.size = size;
    board = new char[size][size];
    initializeBoard();
  }

  // Initializes the board by setting all cells to the empty mark ('-')
  private void initializeBoard() {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        board[i][j] = Mark.EMPTY.getSymbol(); // Set each cell to empty
      }
    }
  }

  // Prints the current state of the board
  public void print() {
    System.out.println("Current board:");
    printTopRow();  // Print the top row numbers for column labels

    // Print each row of the board
    for (int i = 0; i < size; i++) {
      System.out.print((i + 1) + " |"); // Row number label
      for (int j = 0; j < size; j++) {
        System.out.print(board[i][j] + " ");  // Print each cell
      }
      System.out.println(); // Newline after each row
    }
  }

  // Prints the top row of column numbers and underscores for visual separation
  private void printTopRow() {
    System.out.print("   ");
    for (int i = 0; i < size; i++) {
      System.out.print((i + 1) + " ");  // Print column number
    }
    System.out.println();
    System.out.print("  ");
    for (int i = 0; i < size; i++) {
      System.out.print("__"); // Print underscore below column numbers
    }
    System.out.println();
  }

  // Checks if a move is valid (within bounds and on an empty cell)
  public boolean isValidMove(int row, int col) {
    return row >= 0 && row < size && col >= 0 && col < size && board[row][col] == Mark.EMPTY.getSymbol();
  }

  // Places an mark ('X' or 'O') on the board at the given row and column
  public void makeMove(int row, int col, char mark) {
    board[row][col] = mark;
  }

  // Checks if the given mark has won
  public boolean checkForWin(char mark) {
    return checkRows(mark) || checkColumns(mark) || checkDiagonals(mark);
  }

  // Check if the given mark has a winning combination in any row
  private boolean checkRows(char mark) {
    for (int i = 0; i < size; i++) {
      boolean win = true;
      for (int j = 0; j < size; j++) {
        if (board[i][j] != mark) {
          win = false; // If any cell in the row doesn't match, no win
          break;
        }
      }
      if (win) return true; // If all cells in the row match, mark wins
    }
    return false; // No winning row found
  }

  // Checks if the given mark has a winning combination in any column
  private boolean checkColumns(char mark) {
    for (int i = 0; i < size; i++) {
      boolean win = true;
      for (int j = 0; j < size; j++) {
        if (board[j][i] != mark) {
          win = false; // If any cell in the column doesn't match, no win
          break;
        }
      }
      if (win) return true; // If all cells in the column match, mark wins
    }
    return false; // No winning column found
  }

  // Checks if the given mark has a winning combination in either diagonal
  private boolean checkDiagonals(char mark) {
    boolean win = true;

    // Check the main diagonal (top-left to bottom-right)
    for (int i = 0; i < size; i++) {
      if (board[i][i] != mark) {
        win = false;  // If any cell in the main diagonal doesn't match, no win
        break;
      }
    }
    if (win) return true;  // If all cells in the main diagonal match, mark wins

    win = true;  // Reset win flag for anti-diagonal check
    
    // Check the anti-diagonal (top-right to bottom-left)
    for (int i = 0; i < size; i++) {
      if (board[i][size - i - 1] != mark) {
        win = false;  // If any cell in the anti-diagonal doesn't match, no win
        break;
      }
    }
    return win;  // If all cells in the anti-diagonal match, mark wins
  }

  // Checks if the board is full without any empty cells, indicating a draw
  public boolean checkForDraw() {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (board[i][j] == Mark.EMPTY.getSymbol()) {
          return false; // If any cell is empty, it's not a draw
        }
      }
    }
    return true; // No empty cells found, it's a draw
  }

  // Getter for the board size
  public int getSize() {
    return size;
  }

  // Getter for the value in a specific cell
  public char getCell(int row, int col) {
    return board[row][col];
  }
}
