package pieces;

import javax.swing.*;
import java.io.IOException;

public class Elephant extends Piece {
    public Elephant(int targetY, int targetX, boolean isWhite) {
        super(targetY, targetX, isWhite);
    }

    @Override
    protected boolean isValidMoveInternal(int targetY, int targetX, Piece[][] board) {
        if (Math.abs(targetY - this.y) != Math.abs(targetX - this.x)) {
            return false;
        }

        int stepY = Integer.compare(targetY, this.y);
        int stepX = Integer.compare(targetX, this.x);

        int currentY = this.y + stepY;
        int currentX = this.x + stepX;

        while (currentY != targetY && currentX != targetX) {
            if (board[currentY][currentX] != null) {
                return false;
            }
            currentY += stepY;
            currentX += stepX;
        }

        Piece targetPiece = board[targetY][targetX];

        return targetPiece == null || targetPiece.isWhite != this.isWhite;

    }

    @Override
    public Piece copy() {
        return new Elephant(this.y, this.x, this.isWhite);
    }

    @Override
    public String getSymbol() {
        return isWhite ? "♗" : "♝";
    }

    @Override
    public Icon getIcon() throws IOException {
        return PieceIcons.elephant(isWhite, 50);
    }
}
