package pieces;

import javax.swing.*;
import java.io.IOException;

public class Knight extends  Piece
{
    public Knight(int targetY, int targetX, boolean isWhite) {
        super(targetY, targetX, isWhite);
    }

    @Override
    protected boolean isValidMoveInternal(int targetY, int targetX, Piece[][] board) {
        if (targetY < 0 || targetY >= 8 || targetX < 0 || targetX >= 8) {
            return false;
        }
        int dx = Math.abs(targetX - this.x);
        int dy = Math.abs(targetY - this.y);

        if (!(dx == 2 && dy == 1 || dy == 2 && dx == 1)) {
            return false;
        }

        Piece targetPiece = board[targetY][targetX];
        return targetPiece == null || targetPiece.isWhite != this.isWhite;
    }

    @Override
    public Piece copy() {
        return new Knight(this.y, this.x, this.isWhite);
    }

    @Override
    public String getSymbol() {
        return isWhite ? "♘" : "♞";
    }

    @Override
    public Icon getIcon() throws IOException {
        return PieceIcons.getIcon(3, isWhite, 50);
    }
}
