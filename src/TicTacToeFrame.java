import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame {
    private static final int ROW = 3;
    private static final int COL = 3;
    private final JButton[][] buttons = new JButton[ROW][COL];
    private final String[][] board = new String[ROW][COL];
    private String currentPlayer = "X";
    private int moveCount = 0;

    public TicTacToeFrame() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize board
        clearBoard();

        // Create game panel
        JPanel gamePanel = new JPanel(new GridLayout(ROW, COL));
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                JButton button = createButton(row, col);
                buttons[row][col] = button;
                gamePanel.add(button);
            }
        }

        // Create quit button
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // Add components to frame
        add(gamePanel, BorderLayout.CENTER);
        add(quitButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createButton(int row, int col) {
        JButton button = new JButton("");
        button.setFont(new Font("Arial", Font.BOLD, 40));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick(row, col, button);
            }
        });
        return button;
    }

    private void clearBoard() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                board[row][col] = " ";
            }
        }
    }

    private boolean isValidMove(int row, int col) {
        return board[row][col].equals(" ");
    }

    private boolean isWin(String player) {
        return isRowWin(player) || isColWin(player) || isDiagonalWin(player);
    }

    private boolean isRowWin(String player) {
        for (int row = 0; row < ROW; row++) {
            if (board[row][0].equals(player) && board[row][1].equals(player) && board[row][2].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private boolean isColWin(String player) {
        for (int col = 0; col < COL; col++) {
            if (board[0][col].equals(player) && board[1][col].equals(player) && board[2][col].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDiagonalWin(String player) {
        return (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) ||
                (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player));
    }

    private boolean isTie() {
        return moveCount >= 7 && !isWin("X") && !isWin("O");
    }

    private void handleButtonClick(int row, int col, JButton button) {
        if (!isValidMove(row, col)) {
            JOptionPane.showMessageDialog(this, "Invalid move! Try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Update board and button
        board[row][col] = currentPlayer;
        button.setText(currentPlayer);
        moveCount++;

        // Check for win or tie
        if (moveCount >= 5 && isWin(currentPlayer)) {
            JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
            return;
        } else if (isTie()) {
            JOptionPane.showMessageDialog(this, "It's a tie!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
            return;
        }

        // Switch player
        currentPlayer = currentPlayer.equals("X") ? "O" : "X";
    }

    private void resetGame() {
        clearBoard();
        currentPlayer = "X";
        moveCount = 0;
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setEnabled(true);
            }
        }
    }
}