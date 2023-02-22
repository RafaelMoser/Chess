package Game.Pieces;

import Game.Direction;
import Game.Pieces.PieceInfo.PieceColor;
import Game.Pieces.PieceInfo.PieceType;
import Game.Position;

public class Bishop extends SlidingPiece {

    public Bishop(PieceColor color, Position pos) {
        super(color, PieceType.BISHOP, pos,
                new Direction[]{Direction.UR, Direction.DR, Direction.DL, Direction.UL});
    }
}
