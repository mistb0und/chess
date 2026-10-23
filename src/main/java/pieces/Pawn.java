package pieces;

import javax.swing.*;
import java.io.IOException;

public class Pawn extends Piece {
    public Pawn(int y, int x, boolean isWhite) {
        super(y, x, isWhite);
    }

    @Override
    protected boolean isValidMoveInternal(int targetY, int targetX, Piece[][] board) {
        int direction = isWhite ? -1 : 1;
        int startRow = isWhite ? 6 : 1;

        if (targetY == this.y + direction && targetX == this.x) {
            Piece targetPiece = board[targetY][targetX];
            return targetPiece == null;
        }
        if (this.y == startRow && targetY == this.y + 2 * direction && this.x == targetX) {
            if (board[this.y + direction][this.x] != null) {
                return false;
            }
            Piece targePiece = board[targetY][targetX];
            return targePiece == null;
        }
        if (targetY == this.y + direction && Math.abs(targetX - this.x) == 1) {
            Piece targePiece = board[targetY][targetX];
            return targePiece != null && targePiece.isWhite != this.isWhite;
        }
        return false;

    }

    @Override
    public Piece copy() {
        return new Pawn(this.y, this.x, this.isWhite);
    }

    @Override
    public String getSymbol() {
        return isWhite ? "♙" : "♟";
    }

    @Override
    public Icon getIcon() throws IOException {
        return PieceIcons.pawn(isWhite, 50);
    }

}
