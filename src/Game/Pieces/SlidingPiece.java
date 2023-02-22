package Game.Pieces;

import Game.Board;
import Game.Direction;
import Game.Pieces.PieceInfo.PieceColor;
import Game.Pieces.PieceInfo.PieceType;
import Game.Position;

import java.util.Arrays;
import java.util.List;

public abstract class SlidingPiece extends GenericPiece implements Piece {

    private List<Direction> movementDirections;

    public SlidingPiece(PieceColor color, PieceType type, Position pos, Direction[] directionArray) {
        super(color, type, pos);
        this.movementDirections = Arrays.asList(directionArray);
    }

    @Override
    public boolean hasValidMove(Board board) {
        for (Direction d : movementDirections) {
            if (d == Direction.N) continue;
            Position pos = new Position(currentPos.x() + d.x, currentPos.y() + d.y);
            if (!board.isInBounds(pos)) continue;
            if (board.getPieceColor(pos) != color) return true;
        }
        return false;
    }

    @Override
    public boolean isValidMove(Board board, Position to, boolean ignorePieceOnPosTo) {
        if (!Direction.isSlidingMove(currentPos, to)) {
            return false;
        }
        Direction d = Direction.findDirection(currentPos, to);
        if (!movementDirections.contains(d)) {
            return false;
        }
        Position p = new Position(currentPos.x() + d.x, currentPos.y() + d.y);

        while (!p.equals(to)) {
            if (board.getPieceType(p) != PieceType.NONE) {
                return false;
            }
            p = new Position(p.x() + d.x, p.y() + d.y);
        }
        return ignorePieceOnPosTo || board.getPieceColor(to) != color;
    }
}