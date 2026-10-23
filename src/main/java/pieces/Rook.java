package pieces;

import javax.swing.*;
import java.io.IOException;

public class Rook extends Piece {
    public Rook(int y, int x, boolean isWhite) {
        super(y, x, isWhite);
    }

    @Override
    protected boolean isValidMoveInternal(int targetY, int targetX, Piece[][] board) {
        if (targetY != this.y && targetX != this.x) {
            return false;
        }

        int stepY = Integer.compare(targetY, this.y);
        int stepX = Integer.compare(targetX, this.x);

        int currentY = this.y + stepY;
        int currentX = this.x + stepX;

        while (currentY != targetY || currentX != targetX) {
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
        return new Rook(this.y, this.x, this.isWhite);
    }

    @Override
    public String getSymbol() {
        return isWhite ? "♖" : "♜";
    }

    @Override
    public Icon getIcon() throws IOException {
        return PieceIcons.rook(isWhite, 50);
    }
}
