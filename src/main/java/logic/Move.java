package logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pieces.Piece;

public class Move {
    Piece piece;
    int fromX, fromY, toY, toX;
    Piece capturedPiece;

    public Move(Piece piece, int fromX, int fromY, int toY, int toX, Piece capturedPiece) {
        this.piece = piece;
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        Logger logger = LoggerFactory.getLogger(Move.class);
        if (capturedPiece != null) {
            logger.info("Взято фігуру {}", capturedPiece.getSymbol());
        }
        this.capturedPiece = capturedPiece;
        logger.debug("Створено хід: {}", this.getDetailInfo());
    }

    public String getDetailInfo() {
        String color = piece.isWhite() ? "White" : "Black";
        String pieceType = piece.getClass().getSimpleName();
        String fromSquare = (char) ('a' + fromX) + "" + (8 - fromY);
        String toSquare = (char) ('a' + toX) + "" + (8 - toY);

        String captureInfo = "";
        if (capturedPiece != null) {
            captureInfo = " (captures" + capturedPiece.getClass().getSimpleName() + ")";
        }

        return color + " " + " " + pieceType + " " + fromSquare + " " + toSquare + " " + captureInfo;
    }

    @Override
    public String toString() {
        return getDetailInfo();
    }
}
