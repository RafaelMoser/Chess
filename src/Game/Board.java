package Game;

import Game.Pieces.PieceInfo.PieceColor;
import Game.Pieces.PieceInfo.PieceType;
import Game.Pieces.PieceInfo.PieceTypeColor;

public interface Board {
    MoveResult move(Position from, Position to);

    boolean isInCheck(PieceColor color);

    MoveResult promote(PieceType to);

    boolean isEnPassant(Position pos, PieceColor color);

    PieceColor getPieceColor(Position pos);

    PieceType getPieceType(Position pos);

    boolean isInBounds(Position pos);

    PieceTypeColor getPieceTypeColor(Position pos);

    boolean isPositionUnderAttack(Position pos, PieceColor color);

    boolean hasRookMoved(PieceColor color, Direction dir);

    PieceTypeColor[][] getVisualBoard();

    PieceColor getCurrentTurn();
}
