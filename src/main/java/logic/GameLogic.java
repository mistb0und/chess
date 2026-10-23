package logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pieces.King;
import pieces.Piece;

public class GameLogic {
    private static final Logger logger = LoggerFactory.getLogger(GameLogic.class);

    public boolean isKingInCheck(Piece[][] board, boolean isWhiteKing) {
        int kingX = -1, kingY = -1;
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board.length; x++) {
                if (board[y][x] instanceof King && board[y][x].isWhite() == isWhiteKing) {
                    logger.info("{} король знайдено на позиції: {}, {}", isWhiteKing ? "Білий" : "Чорний", y, x);
                    kingX = x;
                    kingY = y;
                    break;
                }
            }
        }

        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board.length; x++) {
                if (board[y][x] != null && board[y][x].isWhite() != isWhiteKing) {
                    if (board[y][x].isValidMove(kingY, kingX, board)) {
                        logger.info("{} атакує короля на позиції {}, {}", board[y][x].getSymbol(), kingY, kingX);
                        return true;
                    }
                }
            }
        }
        logger.debug("Король не під шахом");
        return false;
    }

    public boolean isCheckMate(Piece[][] board, boolean isWhiteKing) {
        logger.info("Перевірка мату для {} короля", isWhiteKing ? "білого" : "чорного");
        if (!isKingInCheck(board, isWhiteKing)) {
            logger.info("{} король не під шахом, мат не можливий", isWhiteKing ? "Білий" : "Чорний");
            return false;
        }
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board.length; x++) {
                if (hasAnyValidMove(board[y][x], y, x, board, isWhiteKing)) {
                    logger.info("{} король може втекти з шаху", isWhiteKing ? "Білий" : "Чорний");
                    return false;
                }
            }
        }
        logger.warn("МАТ! {} король не може втекти", isWhiteKing ? "Білий" : "Чорний");
        return true;
    }

    private boolean hasAnyValidMove(Piece piece, int fromY, int fromX, Piece[][] board, boolean isWhiteKing) {
        if (piece == null || piece.isWhite() != isWhiteKing) {
            return false;
        }
        logger.debug("Перевіряємо чи має {} на позиції ({}, {}) валідні ходи", piece.getClass().getSimpleName(), fromY, fromX);
        for (int toY = 0; toY < board.length; toY++) {
            for (int toX = 0; toX < board.length; toX++) {
                if (piece.isValidMove(toY, toX, board)) {
                    if (wouldMoveBeLegal(fromY, fromX, toY, toX, board)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean wouldMoveBeLegal(int fromY, int fromX, int toY, int toX, Piece[][] board) {
        Piece[][] tempBoard = copyBoard(board);
        Piece movingPiece = tempBoard[fromY][fromX];

        int originalX = movingPiece.getX();
        int originalY = movingPiece.getY();

        tempBoard[toY][toX] = movingPiece;
        tempBoard[fromY][fromX] = null;
        movingPiece.setX(toX);
        movingPiece.setY(toY);

        boolean isWhite = movingPiece.isWhite();
        boolean legal = !isKingInCheck(tempBoard, isWhite);

        movingPiece.setX(originalX);
        movingPiece.setY(originalY);

        return legal;
    }

    private Piece[][] copyBoard(Piece[][] board) {
        Piece[][] result = new Piece[board.length][board.length];
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board.length; x++) {
                if (board[y][x] != null) {
                    result[y][x] = board[y][x].copy();
                }
            }
        }
        return result;
    }
}
