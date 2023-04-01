package Game.Pieces;

import Game.GameRules.ChessRules;
import Game.Direction;
import Game.Pieces.PieceInfo.PieceColor;
import Game.Pieces.PieceInfo.PieceType;
import Game.Position;


public class Pawn extends GenericPiece {

    private boolean hasMoved, enPassant;

    public Pawn(PieceColor color, Position pos) {
        super(color, PieceType.PAWN, pos);
        hasMoved = false;
        enPassant = false;
    }

    @Override
    public boolean isValidMove(ChessRules board, Position to, boolean ignorePieceOnPosTo) {
        if (currentPos.equals(to)) {
            return false;
        }

        if (currentPos.x() - to.x() == (color == PieceColor.WHITE ? -1 : 1)) {
            if (currentPos.y() == to.y()) {
                return board.getPieceColor(to) == PieceColor.NONE;
            }
            if (Math.abs(currentPos.y() - to.y()) == 1) {
                if (board.getPieceColor(to) == PieceColor.NONE) {
                    return board.isEnPassant(to, color);
                }
                return ignorePieceOnPosTo || board.getPieceColor(to) == color.getOppositeColor();
            }
        }
        if (!hasMoved) {
            if (currentPos.x() - to.x() == (color == PieceColor.WHITE ? -2 : 2)) {
                if (currentPos.y() == to.y()) {
                    Position p = new Position(currentPos.x() + (color == PieceColor.WHITE ? 1 : -1), currentPos.y());
                    return board.getPieceColor(p) == PieceColor.NONE && board.getPieceColor(to) == PieceColor.NONE;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasValidMove(ChessRules board) {
        Direction d = color == PieceColor.WHITE ? Direction.U : Direction.D;
        if (board.getPieceColor(new Position(currentPos.x() + d.x, currentPos.y())) == PieceColor.NONE) {
            return true;
        } else {
            return board.getPieceColor(new Position(currentPos.x() + d.x, currentPos.y() - 1)) == color.getOppositeColor()
                    || board.getPieceColor(new Position(currentPos.x() + d.x, currentPos.y() + 1)) == color.getOppositeColor();
        }
    }

    @Override
    public void afterMove(Position to) {
        enPassant = !hasMoved || Math.abs(getCurrentPos().x() - to.x()) == 2;
        hasMoved = true;
        super.afterMove(to);
    }

    public boolean canEnPassant() {
        return enPassant;
    }

}
