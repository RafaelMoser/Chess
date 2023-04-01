package Tests;

import Game.GameRules.ChessRules;
import Game.GameRules.ModernChessRules;
import Game.MoveResult;
import Game.Pieces.King;
import Game.Pieces.Piece;
import Game.Pieces.PieceInfo.PieceColor;
import Game.Pieces.PieceInfo.PieceType;
import Game.Pieces.PieceInfo.PieceTypeColor;
import Game.Pieces.Rook;
import Game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class RookTest {

    /* Initial board for test:
           a  b  c  d  e  f  g  h
        8 [B][ ][ ][ ][ ][ ][ ][ ]
        7 [ ][ ][B][ ][ ][ ][ ][ ]
        6 [ ][ ][ ][ ][ ][ ][ ][K]
        5 [ ][ ][ ][ ][ ][ ][ ][ ]
        4 [ ][ ][ ][ ][ ][ ][ ][ ]
        3 [ ][ ][ ][ ][ ][ ][ ][K]
        2 [ ][ ][W][ ][ ][ ][ ][ ]
        1 [W][ ][ ][ ][ ][ ][ ][ ]

        B = black rook
        W = white rook
        K = kings (not used on test)
         */


    ChessRules testBoard;

    @BeforeEach
    void setUp() {
        List<Piece> board = new ArrayList<>();

        board.add(new Rook(PieceColor.WHITE, new Position(1, 1)));
        board.add(new Rook(PieceColor.WHITE, new Position(2, 3)));

        board.add(new Rook(PieceColor.BLACK, new Position(8, 1)));
        board.add(new Rook(PieceColor.BLACK, new Position(7, 3)));

        board.add(new King(PieceColor.WHITE, new Position(3, 8)));
        board.add(new King(PieceColor.BLACK, new Position(6, 8)));

        testBoard = new ModernChessRules(board);
    }

    @Test
    void singleLineMove() {
        MoveResult m;
        m = testBoard.move(new Position(1, 1), new Position(2, 1));
        assert (testBoard.getPieceTypeColor(new Position(2, 1)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 1)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(8, 1), new Position(8, 2));
        assert (testBoard.getPieceTypeColor(new Position(8, 2)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(8, 1)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void longLineMove() {
        MoveResult m;
        m = testBoard.move(new Position(1, 1), new Position(1, 8));
        assert (testBoard.getPieceTypeColor(new Position(1, 8)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 1)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(8, 1), new Position(8, 8));
        assert (testBoard.getPieceTypeColor(new Position(8, 8)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(8, 1)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void invalidDiagonalMove() {
        MoveResult m;
        m = testBoard.move(new Position(1, 1), new Position(3, 3));
        assert (testBoard.getPieceTypeColor(new Position(1, 1)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(3, 3)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);

        testBoard.move(new Position(1, 1), new Position(1, 2));

        m = testBoard.move(new Position(8, 1), new Position(6, 3));
        assert (testBoard.getPieceTypeColor(new Position(8, 1)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(6, 3)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);
    }

    @Test
    void invalidMove() {

        MoveResult m;
        m = testBoard.move(new Position(1, 1), new Position(4, 3));
        assert (testBoard.getPieceTypeColor(new Position(1, 1)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(4, 3)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);

        testBoard.move(new Position(1, 1), new Position(1, 2));

        m = testBoard.move(new Position(8, 1), new Position(5, 3));
        assert (testBoard.getPieceTypeColor(new Position(8, 1)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(5, 3)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);
    }

    @Test
    void invalidOutOfBoundsMove() {
        MoveResult m;
        m = testBoard.move(new Position(1, 1), new Position(1, 0));
        assert (testBoard.getPieceTypeColor(new Position(1, 1)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.WHITE)));
        assert (m == MoveResult.INVALID_OUT_OF_BOUNDS);

        testBoard.move(new Position(1, 1), new Position(1, 2));

        m = testBoard.move(new Position(8, 1), new Position(9, 1));
        assert (testBoard.getPieceTypeColor(new Position(8, 1)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.BLACK)));
        assert (m == MoveResult.INVALID_OUT_OF_BOUNDS);
    }

    @Test
    void capture() {
        MoveResult m;
        m = testBoard.move(new Position(1, 1), new Position(8, 1));
        assert (testBoard.getPieceTypeColor(new Position(8, 1)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 1)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(7, 3), new Position(2, 3));
        assert (testBoard.getPieceTypeColor(new Position(2, 3)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(7, 3)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void blockedMove() {
        MoveResult m;
        m = testBoard.move(new Position(2, 3), new Position(8, 3));
        assert (testBoard.getPieceTypeColor(new Position(2, 3)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(8, 3)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(7, 3)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.BLACK)));
        assert (m == MoveResult.INVALID);

        testBoard.move(new Position(1, 1), new Position(1, 4));

        m = testBoard.move(new Position(7, 3), new Position(1, 3));
        assert (testBoard.getPieceTypeColor(new Position(7, 3)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(1, 3)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(2, 3)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.WHITE)));
        assert (m == MoveResult.INVALID);
    }
}
