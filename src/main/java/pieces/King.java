package pieces;

import javax.swing.*;
import java.io.IOException;

public class King extends Piece {

    public King(int targetY, int targetX, boolean isWhite) {
        super(targetY, targetX, isWhite);
    }

    @Override
    protected boolean isValidMoveInternal(int targetY, int targetX, Piece[][] board) {
        if (targetY < 0 || targetY >= 8 || targetX < 0 || targetX >= 8 ) {
            return false;
        }

        if (Math.abs(targetX - this.x) > 1 || Math.abs(targetY - this.y) > 1) {
            return false;
        }

        Piece targetPiece = board[targetY][targetX];
        return targetPiece == null || targetPiece.isWhite != this.isWhite;
    }

    @Override
    public Piece copy() {
        return new King(this.y, this.x, this.isWhite);
    }

    @Override
    public String getSymbol() {
        return isWhite ? "♔" : "♚";
    }

    @Override
    public Icon getIcon() throws IOException {
        return PieceIcons.king(isWhite, 50);
    }
}
