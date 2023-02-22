package Tests;

import Game.Board;
import Game.Chessboard;
import Game.MoveResult;
import Game.Pieces.Bishop;
import Game.Pieces.King;
import Game.Pieces.PieceInfo.PieceColor;
import Game.Pieces.PieceInfo.PieceType;
import Game.Pieces.PieceInfo.PieceTypeColor;
import Game.Pieces.Piece;
import Game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class BishopTest {

   /* Initial board for test:
       a  b  c  d  e  f  g  h
    8 [ ][ ][B][ ][ ][W][ ][ ]
    7 [ ][B][ ][ ][ ][ ][ ][K]
    6 [W][ ][ ][ ][W][ ][ ][ ]
    5 [ ][ ][ ][ ][ ][ ][ ][ ]
    4 [ ][ ][ ][ ][ ][ ][ ][ ]
    3 [B][ ][ ][ ][B][ ][ ][ ]
    2 [ ][W][ ][ ][ ][ ][ ][K]
    1 [ ][ ][W][ ][ ][B][ ][ ]

    B = black bishop
    W = white bishop
    K = kings (not used on test)
     */

    Board testBoard;

    @BeforeEach
    void setUp() {
        Map<Position, Piece> board = new HashMap<>();

        board.put(new Position(6, 1), new Bishop(PieceColor.WHITE, new Position(6, 1)));
        board.put(new Position(2, 2), new Bishop(PieceColor.WHITE, new Position(2, 2)));
        board.put(new Position(1, 3), new Bishop(PieceColor.WHITE, new Position(1, 3)));
        board.put(new Position(6, 5), new Bishop(PieceColor.WHITE, new Position(6, 5)));
        board.put(new Position(8, 6), new Bishop(PieceColor.WHITE, new Position(8, 6)));

        board.put(new Position(3, 1), new Bishop(PieceColor.BLACK, new Position(3, 1)));
        board.put(new Position(7, 2), new Bishop(PieceColor.BLACK, new Position(7, 2)));
        board.put(new Position(8, 3), new Bishop(PieceColor.BLACK, new Position(8, 3)));
        board.put(new Position(3, 5), new Bishop(PieceColor.BLACK, new Position(3, 5)));
        board.put(new Position(1, 6), new Bishop(PieceColor.BLACK, new Position(2, 6)));


        board.put(new Position(2, 8), new King(PieceColor.WHITE, new Position(2, 8)));
        board.put(new Position(7, 8), new King(PieceColor.BLACK, new Position(7, 8)));

        testBoard = new Chessboard(board);
    }


    @Test
    void singleMove() {
        MoveResult m;
        m = testBoard.move(new Position(2, 2), new Position(1, 1));
        assert (testBoard.getPieceTypeColor(new Position(1, 1)).equals(new PieceTypeColor(PieceType.BISHOP, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(2, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(7, 2), new Position(8, 1));
        assert (testBoard.getPieceTypeColor(new Position(8, 1)).equals(new PieceTypeColor(PieceType.BISHOP, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(7, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void singleLongMove() {
        MoveResult m;
        m = testBoard.move(new Position(2, 2), new Position(8, 8));
        assert (testBoard.getPieceTypeColor(new Position(8, 8)).equals(new PieceTypeColor(PieceType.BISHOP, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(2, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(7, 2), new Position(1, 8));
        assert (testBoard.getPieceTypeColor(new Position(1, 8)).equals(new PieceTypeColor(PieceType.BISHOP, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(7, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void invalidLineMove() {
        MoveResult m;
        m = testBoard.move(new Position(2, 2), new Position(4, 2));
        assert (testBoard.getPieceTypeColor(new Position(2, 2)).equals(new PieceTypeColor(PieceType.BISHOP, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(4, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);

        testBoard.move(new Position(2, 2), new Position(1, 1));

        m = testBoard.move(new Position(7, 2), new Position(5, 2));
        assert (testBoard.getPieceTypeColor(new Position(7, 2)).equals(new PieceTypeColor(PieceType.BISHOP, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(5, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);
    }

    @Test
    void invalidMove() {
        MoveResult m;
        m = testBoard.move(new Position(2, 2), new Position(4, 3));
        assert (testBoard.getPieceTypeColor(new Position(2, 2)).equals(new PieceTypeColor(PieceType.BISHOP, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(4, 3)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);

        testBoard.move(new Position(2, 2), new Position(1, 1));

        m = testBoard.move(new Position(7, 2), new Position(5, 1));
        assert (testBoard.getPieceTypeColor(new Position(7, 2)).equals(new PieceTypeColor(PieceType.BISHOP, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(5, 3)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);
    }

    @Test
    void invalidOutOfBoundsMove() {
        MoveResult m;
        m = testBoard.move(new Position(2, 2), new Position(0, 0));
        assert (testBoard.getPieceTypeColor(new Position(2, 2)).equals(new PieceTypeColor(PieceType.BISHOP, PieceColor.WHITE)));
        assert (m == MoveResult.INVALID_OUT_OF_BOUNDS);

        testBoard.move(new Position(2, 2), new Position(1, 1));

        m = testBoard.move(new Position(7, 2), new Position(9, 0));
        assert (testBoard.getPieceTypeColor(new Position(7, 2)).equals(new PieceTypeColor(PieceType.BISHOP, PieceColor.BLACK)));
        assert (m == MoveResult.INVALID_OUT_OF_BOUNDS);
    }

    @Test
    void capture() {
        MoveResult m;
        m = testBoard.move(new Position(2, 2), new Position(3, 1));
        assert (testBoard.getPieceTypeColor(new Position(3, 1)).equals(new PieceTypeColor(PieceType.BISHOP, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(2, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(7, 2), new Position(6, 1));
        assert (testBoard.getPieceTypeColor(new Position(6, 1)).equals(new PieceTypeColor(PieceType.BISHOP, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(7, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void farCapture() {
        MoveResult m;
        m = testBoard.move(new Position(6, 1), new Position(1, 6));
        assert (testBoard.getPieceTypeColor(new Position(1, 6)).equals(new PieceTypeColor(PieceType.BISHOP, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(6, 1)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(3, 1), new Position(8, 6));
        assert (testBoard.getPieceTypeColor(new Position(8, 6)).equals(new PieceTypeColor(PieceType.BISHOP, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(3, 1)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void occupiedSquareMove() {
        MoveResult m;
        m = testBoard.move(new Position(2, 2), new Position(1, 3));
        assert (testBoard.getPieceTypeColor(new Position(1, 3)).equals(new PieceTypeColor(PieceType.BISHOP, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(2, 2)).equals(new PieceTypeColor(PieceType.BISHOP, PieceColor.WHITE)));
        assert (m == MoveResult.INVALID);

        testBoard.move(new Position(2, 2), new Position(1, 1));

        m = testBoard.move(new Position(7, 2), new Position(8, 3));
        assert (testBoard.getPieceTypeColor(new Position(8, 3)).equals(new PieceTypeColor(PieceType.BISHOP, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(7, 2)).equals(new PieceTypeColor(PieceType.BISHOP, PieceColor.BLACK)));
        assert (m == MoveResult.INVALID);
    }

    @Test
    void blockedMove() {
        MoveResult m;
        m = testBoard.move(new Position(1, 3), new Position(6, 8));
        assert (testBoard.getPieceTypeColor(new Position(1, 3)).equals(new PieceTypeColor(PieceType.BISHOP, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(6, 8)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(3, 5)).equals(new PieceTypeColor(PieceType.BISHOP, PieceColor.BLACK)));
        assert (m == MoveResult.INVALID);

        testBoard.move(new Position(2, 2), new Position(1, 1));

        m = testBoard.move(new Position(8, 3), new Position(3, 8));
        assert (testBoard.getPieceTypeColor(new Position(8, 3)).equals(new PieceTypeColor(PieceType.BISHOP, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(3, 8)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(6, 5)).equals(new PieceTypeColor(PieceType.BISHOP, PieceColor.WHITE)));
        assert (m == MoveResult.INVALID);
    }
}
