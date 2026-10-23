package pieces;

import javax.swing.*;
import java.io.IOException;

public class Queen extends Piece {
    public Queen(int targetY, int targetX, boolean isWhite) {
        super(targetY, targetX, isWhite);
    }

    @Override
    protected boolean isValidMoveInternal(int targetY, int targetX, Piece[][] board) {
        if (targetY < 0 || targetY >= 8 || targetX < 0 || targetX >= 8) {
            return false;
        }

        boolean isDiagonal = Math.abs(targetY - this.y) == Math.abs(targetX - this.x);
        boolean isStraight = targetY == this.y || targetX == this.x;

        if (!isDiagonal && !isStraight) {
            return false;
        }

        int stepY = Integer.compare(targetY, this.y);
        int stepX = Integer.compare(targetX, this.x);

        int currentX = this.x + stepX;
        int currentY = this.y + stepY;

        while (currentY != targetY || currentX != targetX) {
            if (board[currentY][currentX] != null) {
                return false;
            }
            currentX += stepX;
            currentY += stepY;
        }

        Piece targetPiece = board[targetY][targetX];
        return targetPiece == null || targetPiece.isWhite != this.isWhite;
    }

    @Override
    public Piece copy() {
        return new Queen(this.y, this.x, this.isWhite);
    }

    @Override
    public String getSymbol() {
        return isWhite ? "♕" : "♛";
    }

    @Override
    public Icon getIcon() throws IOException {
        return PieceIcons.getIcon(1, isWhite, 50);
    }
}
