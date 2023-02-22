package Tests;

import Game.Board;
import Game.Chessboard;
import Game.MoveResult;
import Game.Pieces.King;
import Game.Pieces.Knight;
import Game.Pieces.PieceInfo.PieceColor;
import Game.Pieces.PieceInfo.PieceType;
import Game.Pieces.PieceInfo.PieceTypeColor;
import Game.Pieces.Piece;
import Game.Pieces.Rook;
import Game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class KingTest {
    /* Initial board for test:
           a  b  c  d  e  f  g  h
        8 [R][ ][ ][ ][B][ ][ ][R]
        7 [ ][ ][ ][N][ ][ ][ ][ ]
        6 [ ][ ][ ][ ][ ][ ][ ][ ]
        5 [ ][ ][ ][ ][ ][ ][ ][ ]
        4 [ ][ ][ ][ ][ ][ ][ ][ ]
        3 [ ][ ][ ][ ][ ][ ][ ][ ]
        2 [ ][ ][ ][N][ ][ ][ ][ ]
        1 [R][ ][ ][ ][W][ ][ ][R]

        B = black king
        W = white king
        R = rooks
        N = knights
         */


    Board testBoard;

    @BeforeEach
    void setUp() {
        Map<Position, Piece> board = new HashMap<>();

        board.put(new Position(1, 1), new Rook(PieceColor.WHITE, new Position(1, 1)));
        board.put(new Position(1, 8), new Rook(PieceColor.WHITE, new Position(1, 8)));

        board.put(new Position(8, 1), new Rook(PieceColor.BLACK, new Position(8, 1)));
        board.put(new Position(8, 8), new Rook(PieceColor.BLACK, new Position(8, 8)));

        board.put(new Position(7, 4), new Knight(PieceColor.WHITE, new Position(7, 4)));
        board.put(new Position(2, 4), new Knight(PieceColor.BLACK, new Position(2, 4)));


        board.put(new Position(1, 5), new King(PieceColor.WHITE, new Position(1, 5)));
        board.put(new Position(8, 5), new King(PieceColor.BLACK, new Position(8, 5)));

        testBoard = new Chessboard(board);
    }

    @Test
    void singleLineMove() {
        MoveResult m;
        m = testBoard.move(new Position(1, 5), new Position(2, 5));
        assert (testBoard.getPieceTypeColor(new Position(2, 5)).equals(new PieceTypeColor(PieceType.KING, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 5)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(8, 5), new Position(7, 5));
        assert (testBoard.getPieceTypeColor(new Position(7, 5)).equals(new PieceTypeColor(PieceType.KING, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(8, 5)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void diagonalMove() {
        MoveResult m;
        m = testBoard.move(new Position(1, 5), new Position(2, 6));
        assert (testBoard.getPieceTypeColor(new Position(2, 6)).equals(new PieceTypeColor(PieceType.KING, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 5)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(8, 5), new Position(7, 6));
        assert (testBoard.getPieceTypeColor(new Position(7, 6)).equals(new PieceTypeColor(PieceType.KING, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(8, 5)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }


    @Test
    void invalidLongMove() {
        MoveResult m;
        m = testBoard.move(new Position(1, 5), new Position(3, 5));
        assert (testBoard.getPieceTypeColor(new Position(1, 5)).equals(new PieceTypeColor(PieceType.KING, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(3, 5)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);

        testBoard.move(new Position(1, 5), new Position(2, 6));

        m = testBoard.move(new Position(8, 5), new Position(6, 5));
        assert (testBoard.getPieceTypeColor(new Position(8, 5)).equals(new PieceTypeColor(PieceType.KING, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(6, 5)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);
    }

    @Test
    void invalidOutOfBoundsMove() {
        MoveResult m;
        m = testBoard.move(new Position(1, 5), new Position(0, 5));
        assert (testBoard.getPieceTypeColor(new Position(1, 5)).equals(new PieceTypeColor(PieceType.KING, PieceColor.WHITE)));
        assert (m == MoveResult.INVALID_OUT_OF_BOUNDS);

        testBoard.move(new Position(1, 5), new Position(1, 4));

        m = testBoard.move(new Position(8, 5), new Position(9, 5));
        assert (testBoard.getPieceTypeColor(new Position(8, 5)).equals(new PieceTypeColor(PieceType.KING, PieceColor.BLACK)));
        assert (m == MoveResult.INVALID_OUT_OF_BOUNDS);
    }

    @Test
    void capture() {
        MoveResult m;
        m = testBoard.move(new Position(1, 5), new Position(2, 4));
        assert (testBoard.getPieceTypeColor(new Position(2, 4)).equals(new PieceTypeColor(PieceType.KING, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 5)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(8, 5), new Position(7, 4));
        assert (testBoard.getPieceTypeColor(new Position(7, 4)).equals(new PieceTypeColor(PieceType.KING, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(8, 5)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void blockedMove() {
        testBoard.move(new Position(1, 8), new Position(1, 6));
        testBoard.move(new Position(8, 8), new Position(8, 6));

        MoveResult m;
        m = testBoard.move(new Position(1, 5), new Position(1, 6));
        assert (testBoard.getPieceTypeColor(new Position(1, 5)).equals(new PieceTypeColor(PieceType.KING, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 6)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.WHITE)));
        assert (m == MoveResult.INVALID);

        testBoard.move(new Position(1, 5), new Position(1, 4));

        m = testBoard.move(new Position(8, 5), new Position(8, 6));
        assert (testBoard.getPieceTypeColor(new Position(8, 5)).equals(new PieceTypeColor(PieceType.KING, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(8, 6)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.BLACK)));
        assert (m == MoveResult.INVALID);
    }

    @Test
    void rightCastling() {
        MoveResult m;
        m = testBoard.move(new Position(1, 5), new Position(1, 7));
        assert (testBoard.getPieceTypeColor(new Position(1, 7)).equals(new PieceTypeColor(PieceType.KING, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 6)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 5)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 8)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(8, 5), new Position(8, 7));
        assert (testBoard.getPieceTypeColor(new Position(8, 7)).equals(new PieceTypeColor(PieceType.KING, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(8, 6)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(8, 5)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(8, 8)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void leftCastling() {
        MoveResult m;
        m = testBoard.move(new Position(1, 5), new Position(1, 3));
        assert (testBoard.getPieceTypeColor(new Position(1, 3)).equals(new PieceTypeColor(PieceType.KING, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 4)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 5)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 1)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        testBoard.move(new Position(1, 5), new Position(1, 4));

        m = testBoard.move(new Position(8, 5), new Position(8, 3));
        assert (testBoard.getPieceTypeColor(new Position(8, 3)).equals(new PieceTypeColor(PieceType.KING, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(8, 4)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(8, 5)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(8, 1)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void invalidRightCastling_kingMoved() {
        testBoard.move(new Position(1, 5), new Position(2, 5));
        testBoard.move(new Position(8, 5), new Position(7, 5));
        testBoard.move(new Position(2, 5), new Position(1, 5));
        testBoard.move(new Position(7, 5), new Position(8, 5));

        MoveResult m;
        m = testBoard.move(new Position(1, 5), new Position(1, 7));
        assert (testBoard.getPieceTypeColor(new Position(1, 5)).equals(new PieceTypeColor(PieceType.KING, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 8)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 7)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 6)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);

        testBoard.move(new Position(1, 5), new Position(1, 4));

        m = testBoard.move(new Position(8, 5), new Position(8, 7));
        assert (testBoard.getPieceTypeColor(new Position(8, 5)).equals(new PieceTypeColor(PieceType.KING, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(8, 8)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(8, 7)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(8, 6)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);
    }

    @Test
    void invalidLeftCastling_kingMoved() {
        testBoard.move(new Position(1, 5), new Position(2, 5));
        testBoard.move(new Position(8, 5), new Position(7, 5));
        testBoard.move(new Position(2, 5), new Position(1, 5));
        testBoard.move(new Position(7, 5), new Position(8, 5));

        MoveResult m;
        m = testBoard.move(new Position(1, 5), new Position(1, 3));
        assert (testBoard.getPieceTypeColor(new Position(1, 5)).equals(new PieceTypeColor(PieceType.KING, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 1)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 3)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 4)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);

        testBoard.move(new Position(1, 5), new Position(1, 4));

        m = testBoard.move(new Position(8, 5), new Position(8, 3));
        assert (testBoard.getPieceTypeColor(new Position(8, 5)).equals(new PieceTypeColor(PieceType.KING, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(8, 1)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(8, 3)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(8, 4)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);
    }

    @Test
    void invalidRightCastling_rookMoved() {
        testBoard.move(new Position(1, 8), new Position(2, 8));
        testBoard.move(new Position(8, 8), new Position(7, 8));
        testBoard.move(new Position(2, 8), new Position(1, 8));
        testBoard.move(new Position(7, 8), new Position(8, 8));

        MoveResult m;
        m = testBoard.move(new Position(1, 5), new Position(1, 7));
        assert (testBoard.getPieceTypeColor(new Position(1, 5)).equals(new PieceTypeColor(PieceType.KING, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 8)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 7)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 6)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);

        testBoard.move(new Position(1, 5), new Position(1, 4));

        m = testBoard.move(new Position(8, 5), new Position(8, 7));
        assert (testBoard.getPieceTypeColor(new Position(8, 5)).equals(new PieceTypeColor(PieceType.KING, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(8, 8)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(8, 7)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(8, 6)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);
    }

    @Test
    void invalidLeftCastling_rookMoved() {
        testBoard.move(new Position(1, 1), new Position(2, 1));
        testBoard.move(new Position(8, 1), new Position(7, 1));
        testBoard.move(new Position(2, 1), new Position(1, 1));
        testBoard.move(new Position(7, 1), new Position(8, 1));

        MoveResult m;
        m = testBoard.move(new Position(1, 5), new Position(1, 3));
        assert (testBoard.getPieceTypeColor(new Position(1, 5)).equals(new PieceTypeColor(PieceType.KING, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 1)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 3)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 4)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);

        testBoard.move(new Position(1, 5), new Position(1, 4));

        m = testBoard.move(new Position(8, 5), new Position(8, 3));
        assert (testBoard.getPieceTypeColor(new Position(8, 5)).equals(new PieceTypeColor(PieceType.KING, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(8, 1)).equals(new PieceTypeColor(PieceType.ROOK, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(8, 3)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(8, 4)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);
    }
}
