package Game.GameRules;

import Game.Pieces.*;
import Game.Pieces.PieceInfo.PieceColor;
import Game.Position;

import java.util.ArrayList;
import java.util.List;

public class BoardFactory {

    public static List<Piece> createDefaultBoard() {
        List<Piece> board = new ArrayList<>();
        for (int x = 1; x <= 8; x++) {
            PieceColor c = x <= 4 ? PieceColor.WHITE : PieceColor.BLACK;
            for (int y = 1; y <= 8; y++) {
                if (x == 1 || x == 8) {
                    if (y == 1 || y == 8) {
                        board.add(new Rook(c, new Position(x, y)));
                    } else if (y == 2 || y == 7) {
                        board.add(new Knight(c, new Position(x, y)));
                    } else if (y == 3 || y == 6) {
                        board.add(new Bishop(c, new Position(x, y)));
                    } else if (y == 4) {
                        board.add(new Queen(c, new Position(x, y)));
                    } else {
                        board.add(new King(c, new Position(x, y)));
                    }
                } else if (x == 2 || x == 7) {
                    board.add(new Pawn(c, new Position(x, y)));
                }
            }
        }
        return board;
    }

}
