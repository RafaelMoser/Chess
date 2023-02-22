package Game;

import Game.Pieces.*;
import Game.Pieces.PieceInfo.PieceColor;

import java.util.HashMap;
import java.util.Map;

public class BoardCreator {

    public static Map<Position, Piece> createDefaultBoard() {
        Map<Position, Piece> board = new HashMap<>();
        for (int x = 1; x <= 8; x++) {
            PieceColor c = x <= 4 ? PieceColor.WHITE : PieceColor.BLACK;
            for (int y = 1; y <= 8; y++) {
                if (x == 1 || x == 8) {
                    if (y == 1 || y == 8) {
                        board.put(new Position(x, y), new Rook(c, new Position(x, y)));
                    } else if (y == 2 || y == 7) {
                        board.put(new Position(x, y), new Knight(c, new Position(x, y)));
                    } else if (y == 3 || y == 6) {
                        board.put(new Position(x, y), new Bishop(c, new Position(x, y)));
                    } else if (y == 4) {
                        board.put(new Position(x, y), new Queen(c, new Position(x, y)));
                    } else board.put(new Position(x, y), new King(c, new Position(x, y)));
                } else if (x == 2 || x == 7) {
                    board.put(new Position(x, y), new Pawn(c, new Position(x, y)));
                }
            }
        }
        return board;
    }

}
