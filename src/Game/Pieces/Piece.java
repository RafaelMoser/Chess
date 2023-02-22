package Game.Pieces;

import Game.Board;
import Game.Pieces.PieceInfo.PieceColor;
import Game.Pieces.PieceInfo.PieceType;
import Game.Position;

public interface Piece {
    boolean isValidMove(Board board, Position to, boolean ignorePieceOnPosTo);

    boolean hasValidMove(Board board);

    boolean isValidMove(Board board, Position to);

    void afterMove(Position to);

    Position getCurrentPos();

    PieceType getType();

    PieceColor getColor();
}
