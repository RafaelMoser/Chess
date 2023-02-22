package Game.Pieces;

import Game.Board;
import Game.Direction;
import Game.Pieces.PieceInfo.PieceColor;
import Game.Pieces.PieceInfo.PieceType;
import Game.Position;

public class King extends GenericPiece {

    private boolean hasMoved;

    public King(PieceColor color, Position pos) {
        super(color, PieceType.KING, pos);
    }

    @Override
    public boolean isValidMove(Board board, Position to, boolean ignorePieceOnPosTo) {
        if (currentPos.equals(to)) {
            return false;
        }

        if (!hasMoved && currentPos.equals(color == PieceColor.WHITE ? 1 : 8, 5) && (to.y() == 3 || to.y() == 7)) {
            if (board.isInCheck(color)) {
                return false;
            }
            Direction d = Direction.findDirection(currentPos, to);
            if (!board.hasRookMoved(color, d)) {
                Position p = new Position(currentPos.x(), currentPos.y() + d.y);

                while (!p.equals(to)) {
                    if (board.getPieceType(p) != PieceType.NONE && !board.isPositionUnderAttack(p, color)) {
                        return false;
                    }
                    p = new Position(p.x(), p.y() + d.y);
                }
                return true;
            }
        }

        if (Math.abs(currentPos.x() - to.x()) > 1 || Math.abs(currentPos.y() - to.y()) > 1) {
            return false;
        }
        return ignorePieceOnPosTo || board.getPieceColor(to) != color;
    }

    @Override
    public boolean hasValidMove(Board board) {
        for (Direction d : Direction.values()) {
            if (d == Direction.N) {
                continue;
            }
            Position pos = new Position(currentPos.x() + d.x, currentPos.y() + d.y);
            if (!board.isInBounds(pos)) {
                continue;
            }
            if (!board.isPositionUnderAttack(pos, color) && (board.getPieceColor(pos) != color)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterMove(Position to) {
        super.afterMove(to);
        hasMoved = true;
    }


}
