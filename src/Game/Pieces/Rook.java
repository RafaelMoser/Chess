package Game.Pieces;

import Game.Direction;
import Game.Pieces.PieceInfo.PieceColor;
import Game.Pieces.PieceInfo.PieceType;
import Game.Position;

public class Rook extends SlidingPiece {

    private boolean hasMoved;

    public Rook(PieceColor color, Position pos) {
        super(color, PieceType.ROOK, pos,
                new Direction[]{Direction.U, Direction.R, Direction.D, Direction.L});
        hasMoved = false;
    }

    @Override
    public void afterMove(Position to) {
        super.afterMove(to);
        hasMoved = true;
    }

    public boolean hasMoved() {
        return hasMoved;
    }
}
