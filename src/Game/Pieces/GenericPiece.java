package Game.Pieces;

import Game.GameRules.ChessRules;
import Game.Pieces.PieceInfo.PieceColor;
import Game.Pieces.PieceInfo.PieceType;
import Game.Pieces.PieceInfo.PieceTypeColor;
import Game.Position;

public abstract class GenericPiece implements Piece {

    public final PieceColor color;
    public final PieceType type;
    protected Position currentPos;

    public GenericPiece(PieceColor color, PieceType type, Position pos) {
        this.color = color;
        this.type = type;
        this.currentPos = pos;
    }

    @Override
    public boolean isValidMove(ChessRules board, Position to) {
        return isValidMove(board, to, false);
    }

    @Override
    public void afterMove(Position to) {
        currentPos = to;
    }

    @Override
    public Position getCurrentPos() {
        return currentPos;
    }

    @Override
    public PieceType getType() {
        return type;
    }

    @Override
    public PieceColor getColor() {
        return color;
    }

    @Override
    public PieceTypeColor getTypeColor() {
        return new PieceTypeColor(type, color);
    }
}
