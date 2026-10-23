import logic.GameLogic;
import logic.Move;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ChessBoard extends JFrame {
    private static final Logger logger = LoggerFactory.getLogger(ChessBoard.class);
    private Piece selectedPiece = null;
    private final int SIZE = 8;
    private final JButton[][] squares = new JButton[SIZE][SIZE];
    private final Piece[][] board = new Piece[SIZE][SIZE];
    private boolean currentTurnIsWhite = true;
    private final ArrayList<Move> moveHistory = new ArrayList<>();
    private final GameLogic gameLogic = new GameLogic();

    public ChessBoard() throws IOException {
        setTitle("Java Chess");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(SIZE, SIZE));
        initializeBoard();
        setVisible(true);
    }

    static void main() throws IOException {
        logger.info("Запуск гри Шахи");
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.printMoveHistory();
    }

    private void initializeBoard() throws IOException {
        board[6][0] = new Pawn(6, 0, true);
        board[6][1] = new Pawn(6, 1, true);
        board[6][2] = new Pawn(6, 2, true);
        board[6][3] = new Pawn(6, 3, true);
        board[6][4] = new Pawn(6, 4, true);
        board[6][5] = new Pawn(6, 5, true);
        board[6][6] = new Pawn(6, 6, true);
        board[6][7] = new Pawn(6, 7, true);

        board[1][0] = new Pawn(1, 0, false);
        board[1][1] = new Pawn(1, 1, false);
        board[1][2] = new Pawn(1, 2, false);
        board[1][3] = new Pawn(1, 3, false);
        board[1][4] = new Pawn(1, 4, false);
        board[1][5] = new Pawn(1, 5, false);
        board[1][6] = new Pawn(1, 6, false);
        board[1][7] = new Pawn(1, 7, false);

        board[7][2] = new Elephant(7, 2, true);
        board[0][2] = new Elephant(0, 2, false);
        board[7][5] = new Elephant(7, 5, true);
        board[0][5] = new Elephant(0, 5, false);

        board[7][0] = new Rook(7, 0, true);
        board[7][7] = new Rook(7, 7, true);
        board[0][0] = new Rook(0, 0, false);
        board[0][7] = new Rook(0, 7, false);

        board[7][3] = new Queen(7, 3, true);
        board[0][3] = new Queen(0, 3, false);

        board[7][4] = new King(7, 4, true);
        board[0][4] = new King(0, 4, false);

        board[7][1] = new Knight(7, 1, true);
        board[7][6] = new Knight(7, 6, true);
        board[0][1] = new Knight(0, 1, false);
        board[0][6] = new Knight(0, 6, false);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JButton jButton = getJButton(row, col);
                squares[row][col] = jButton;
                add(jButton);

                final int currentRow = row;
                final int currentCol = col;

                jButton.addActionListener(_ -> {

                    if (selectedPiece == null) {
                        if (board[currentRow][currentCol] != null
                                && board[currentRow][currentCol].isWhite() == currentTurnIsWhite) {
                            selectedPiece = board[currentRow][currentCol];
                        }
                    } else {
                        logger.info("{} намагається зробити хід з ({},{}) на ({},{})", 
                            selectedPiece.getSymbol(), selectedPiece.getY(), selectedPiece.getX(), currentRow, currentCol);
                        
                        if (selectedPiece.isValidMove(currentRow, currentCol, board)) {
                            Piece capturedPiece = board[currentRow][currentCol];
                            Move move = new Move(selectedPiece, selectedPiece.getX(), selectedPiece.getY(),
                                    currentCol, currentRow, capturedPiece);
                            moveHistory.add(move);
                            logger.info("Хід виконано: {}", move);

                            board[selectedPiece.getY()][selectedPiece.getX()] = null;
                            selectedPiece.setY(currentRow);
                            selectedPiece.setX(currentCol);
                            board[currentRow][currentCol] = selectedPiece;

                            boolean opponentInCheck = gameLogic.isKingInCheck(board, !currentTurnIsWhite);
                            if (opponentInCheck) {
                                logger.warn("CHECK!");
                                if (gameLogic.isCheckMate(board, !currentTurnIsWhite)) {
                                    logger.warn("CHECKMATE! {} wins!", currentTurnIsWhite ? "White" : "Black");
                                }
                            }

                            currentTurnIsWhite = !currentTurnIsWhite;
                            logger.debug("Current turn: {}", currentTurnIsWhite ? "White" : "Black");
                        } else {
                            logger.debug("Invalid move");
                        }
                        selectedPiece = null;
                        refreshBoard();
                    }

                });
            }
        }
    }

    private JButton getJButton(int row, int col) throws IOException {
        JButton jButton = new JButton();
        jButton.setFont(new Font("Serif", Font.BOLD, 30));
        if ((row + col) % 2 == 0) {
            jButton.setBackground(Color.LIGHT_GRAY);
        } else {
            jButton.setBackground(Color.DARK_GRAY);
        }
        if (board[row][col] != null) {
            Icon icon = board[row][col].getIcon();
            if (icon != null) {
                jButton.setIcon(icon);
                jButton.setText("");
            } else {
                jButton.setIcon(null);
                jButton.setText(board[row][col].getSymbol());
            }
        } else {
            jButton.setIcon(null);
            jButton.setText("");
        }
        return jButton;
    }

    private void refreshBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] != null) {
                    Icon icon;
                    try {
                        icon = board[row][col].getIcon();
                    } catch (IOException e) {
                        logger.error("Помилка завантаження іконки для фігури на позиції ({}, {})", row, col, e);
                        throw new RuntimeException(e);
                    }
                    if (icon != null) {
                        squares[row][col].setIcon(icon);
                        squares[row][col].setText("");
                    } else {
                        squares[row][col].setIcon(null);
                        squares[row][col].setText(board[row][col].getSymbol());
                    }
                } else {
                    squares[row][col].setIcon(null);
                    squares[row][col].setText("");
                }
            }
        }
    }

    public void printMoveHistory() {
        logger.info("=== logic.Move history ===");
        for (int i = 0; i < moveHistory.size(); i++) {
            logger.info("{}. {}", i + 1, moveHistory.get(i));
        }
    }
}