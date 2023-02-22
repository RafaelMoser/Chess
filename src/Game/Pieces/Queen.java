package Game.Pieces;

import Game.Direction;
import Game.Pieces.PieceInfo.PieceColor;
import Game.Pieces.PieceInfo.PieceType;
import Game.Position;

public class Queen extends SlidingPiece {

    public Queen(PieceColor color, Position pos) {
        super(color, PieceType.QUEEN, pos,
                new Direction[]{Direction.U, Direction.R, Direction.D, Direction.L, Direction.UR, Direction.DR, Direction.DL, Direction.UL});
    }
}
