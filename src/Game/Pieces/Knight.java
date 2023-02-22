package Game.Pieces;

import Game.Pieces.PieceInfo.PieceColor;
import Game.Pieces.PieceInfo.PieceType;
import Game.Position;
import Game.Board;

public class Knight extends GenericPiece {

    public Knight(PieceColor color, Position pos) {
        super(color, PieceType.KNIGHT, pos);
    }

    @Override
    public boolean isValidMove(Board board, Position to, boolean ignorePieceOnPosTo) {
        if (currentPos.equals(to)) {
            return false;
        }

        if ((Math.abs(currentPos.x() - to.x()) == 1 && Math.abs(currentPos.y() - to.y()) == 2) ||
                (Math.abs(currentPos.x() - to.x()) == 2 && Math.abs(currentPos.y() - to.y()) == 1)) {
            return ignorePieceOnPosTo || board.getPieceColor(to) != color;
        }
        return false;
    }

    @Override
    public boolean hasValidMove(Board board) {
        int[][] moves = {{2, 1}, {2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}, {-2, 1}, {-2, -1}};
        for (int[] move : moves) {
            Position pos = new Position(currentPos.x() + move[0], currentPos.y() + move[1]);
            if (!board.isInBounds(pos)) {
                continue;
            }
            if (board.getPieceColor(pos) != color) {
                return true;
            }
        }
        return false;
    }

}
