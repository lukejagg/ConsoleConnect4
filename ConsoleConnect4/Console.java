package ConsoleConnect4;
import java.util.Scanner;

class FourInARow {
    enum State {
        None,
        Player1,
        Player2,
        Draw,
    }

    final String Player1 = "\uD83D\uDD34";
    final String Player2 = "\uD83D\uDD35";
    final String Space = "⚪";

    int width;
    int height;
    int winLength;
    State[][] board;
    State turn;

    public FourInARow() {
        width = 7;
        height = 6;
        winLength = 4;
        board = new State[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                board[i][j] = State.None;
            }
        }
        turn = State.Player1;
    }

    public FourInARow(int width, int height, int winLength) {
        this.width = width;
        this.height = height;
        this.winLength = winLength;
        board = new State[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                board[i][j] = State.None;
            }
        }
        turn = State.Player1;
    }

    void printBoard() {
        System.out.println("Board:");
        for (int h = 0; h < height; h++) {
            String line = "";
            for (int x = 0; x < width; x++) {
                switch (board[x][h]) {
                    case None:
                        line += Space;
                        break;
                    case Player1:
                        line += Player1;
                        break;
                    case Player2:
                        line += Player2;
                        break;
                }
            }
            System.out.println(line);
        }
    }

    State checkWinner() {
        boolean draw = true;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                State current = board[x][y];
                if (current != State.None) {
                    // Horizontal
                    boolean canHorizontal = x <= width - winLength, canVertical = y <= height - winLength;
                    if (canHorizontal) {
                        for (int i = 1; i < winLength; i++) {
                            if (board[x + i][y] != current) {
                                break;
                            }
                            else if (i == winLength - 1) {
                                return current;
                            }
                        }
                    }
                    // Vertical
                    if (canVertical) {
                        for (int h = 1; h < winLength; h++) {
                            if (board[x][y + h] != current) {
                                break;
                            }
                            else if (h == winLength - 1) {
                                return current;
                            }
                        }
                    }
                    // Diagonal down right
                    if (canHorizontal && canVertical) {
                        for (int r = 1; r < winLength; r++) {
                            if (board[x + r][y + r] != current) {
                                break;
                            }
                            else if (r == winLength - 1) {
                                return current;
                            }
                        }
                    }
                    // Diagonal up right
                    if (canHorizontal && y >= winLength - 1) {
                        for (int r = 1; r < winLength; r++) {
                            if (board[x + r][y - r] != current) {
                                break;
                            }
                            else if (r == winLength - 1) {
                                return current;
                            }
                        }
                    }
                }
                else {
                    draw = false;
                }
            }
        }
        return draw ? State.Draw : State.None;
    }

    void move(int pos) {
        if (pos >= 0 && pos < width) {
            int h;
            boolean canMove = false;
            for (h = 0; h < height; h++) {
                if (board[pos][h] == State.None) {
                    canMove = true;
                    continue;
                }
                break;
            }
            if (canMove) {
                board[pos][--h] = turn;
                turn = turn == State.Player1 ? State.Player2 : State.Player1;
            }
        }
    }

    public static void play(int width, int height, int winLength) {
        Scanner scanner = new Scanner(System.in);
        FourInARow game = new FourInARow(width, height, winLength);
        game.printBoard();
        State ended = State.None;
        do {
            int move = -1;
            try {
                move = scanner.nextInt();
            }
            catch (Exception e) {
                System.out.println("Please enter a valid input!");
                scanner.nextLine();
            }
            game.move(move);
            game.printBoard();
            ended = game.checkWinner();
        }
        while (ended == State.None);

        if (ended == State.Draw) {
            System.out.println("Draw!");
        }
        else {
            System.out.println(ended + " won the game!");
        }
    }

    public static void play() {
        play(7,6,4);
    }
}

public class Console {
	public static void main(String[] args) {
		FourInARow.play();
	}
}