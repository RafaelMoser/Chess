package Game.Pieces;

import Game.GameRules.ChessRules;
import Game.Pieces.PieceInfo.PieceColor;
import Game.Pieces.PieceInfo.PieceType;
import Game.Pieces.PieceInfo.PieceTypeColor;
import Game.Position;

public interface Piece {
    boolean isValidMove(ChessRules board, Position to, boolean ignorePieceOnPosTo);

    boolean hasValidMove(ChessRules board);

    boolean isValidMove(ChessRules board, Position to);

    void afterMove(Position to);

    Position getCurrentPos();

    PieceType getType();

    PieceColor getColor();

    PieceTypeColor getTypeColor();
}
