package Tests;


import Game.Board;
import Game.Chessboard;
import Game.MoveResult;
import Game.Pieces.King;
import Game.Pieces.PieceInfo.PieceColor;
import Game.Pieces.PieceInfo.PieceType;
import Game.Pieces.PieceInfo.PieceTypeColor;
import Game.Pieces.Pawn;
import Game.Pieces.Piece;
import Game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class PawnTest {

    /* Initial board for test:
       a  b  c  d  e  f  g  h
    8 [K][ ][B][ ][ ][ ][B][ ]
    7 [B][B][B][B][ ][W][W][ ]
    6 [ ][W][ ][ ][ ][ ][ ][ ]
    5 [ ][ ][W][ ][W][ ][ ][ ]
    4 [ ][ ][B][ ][B][ ][ ][ ]
    3 [ ][B][ ][ ][ ][ ][ ][ ]
    2 [W][W][W][W][ ][B][B][ ]
    1 [K][ ][W][ ][ ][ ][W][ ]

    B = black pawn
    W = white pawn
    K = kings (not used on test)
     */

    Board testBoard;

    @BeforeEach
    void setUp() {
        Map<Position, Piece> board = new HashMap<>();

        board.put(new Position(2, 1), new Pawn(PieceColor.WHITE, new Position(2, 1)));
        board.put(new Position(2, 2), new Pawn(PieceColor.WHITE, new Position(2, 2)));
        board.put(new Position(2, 3), new Pawn(PieceColor.WHITE, new Position(2, 3)));
        board.put(new Position(2, 4), new Pawn(PieceColor.WHITE, new Position(2, 4)));
        board.put(new Position(6, 2), new Pawn(PieceColor.WHITE, new Position(6, 2)));
        board.put(new Position(5, 3), new Pawn(PieceColor.WHITE, new Position(5, 3)));
        board.put(new Position(5, 5), new Pawn(PieceColor.WHITE, new Position(5, 5)));
        board.put(new Position(7, 6), new Pawn(PieceColor.WHITE, new Position(7, 6)));
        board.put(new Position(7, 7), new Pawn(PieceColor.WHITE, new Position(7, 7)));
        board.put(new Position(1, 7), new Pawn(PieceColor.WHITE, new Position(1, 7)));
        board.put(new Position(1, 3), new Pawn(PieceColor.WHITE, new Position(1, 3)));

        board.put(new Position(3, 2), new Pawn(PieceColor.BLACK, new Position(3, 2)));
        board.put(new Position(4, 3), new Pawn(PieceColor.BLACK, new Position(4, 3)));
        board.put(new Position(4, 5), new Pawn(PieceColor.BLACK, new Position(4, 5)));
        board.put(new Position(7, 1), new Pawn(PieceColor.BLACK, new Position(7, 1)));
        board.put(new Position(7, 2), new Pawn(PieceColor.BLACK, new Position(7, 2)));
        board.put(new Position(7, 3), new Pawn(PieceColor.BLACK, new Position(7, 3)));
        board.put(new Position(7, 4), new Pawn(PieceColor.BLACK, new Position(7, 4)));
        board.put(new Position(2, 6), new Pawn(PieceColor.BLACK, new Position(2, 6)));
        board.put(new Position(2, 7), new Pawn(PieceColor.BLACK, new Position(2, 7)));
        board.put(new Position(8, 7), new Pawn(PieceColor.BLACK, new Position(8, 7)));
        board.put(new Position(8, 3), new Pawn(PieceColor.BLACK, new Position(8, 3)));


        board.put(new Position(1, 8), new King(PieceColor.WHITE, new Position(1, 1)));
        board.put(new Position(8, 8), new King(PieceColor.BLACK, new Position(8, 1)));

        testBoard = new Chessboard(board);
    }

    @Test
    void singleMove() {
        MoveResult m;
        m = testBoard.move(new Position(2, 1), new Position(3, 1));
        assert (testBoard.getPieceTypeColor(new Position(3, 1)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(2, 1)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(7, 1), new Position(6, 1));
        assert (testBoard.getPieceTypeColor(new Position(6, 1)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(7, 1)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }


    @Test
    void backwardsMove() {
        MoveResult m;
        m = testBoard.move(new Position(2, 2), new Position(1, 2));
        assert (testBoard.getPieceTypeColor(new Position(1, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(2, 2)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.WHITE)));
        assert (m == MoveResult.INVALID);

        testBoard.move(new Position(2, 1), new Position(3, 1));

        m = testBoard.move(new Position(7, 2), new Position(8, 2));
        assert (testBoard.getPieceTypeColor(new Position(8, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(7, 2)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.BLACK)));
        assert (m == MoveResult.INVALID);
    }

    @Test
    void doubleMove() {
        MoveResult m;
        m = testBoard.move(new Position(2, 1), new Position(4, 1));
        assert (testBoard.getPieceTypeColor(new Position(4, 1)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(2, 1)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(7, 1), new Position(5, 1));
        assert (testBoard.getPieceTypeColor(new Position(5, 1)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(7, 1)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void singleBlockedMove() {
        MoveResult m;
        m = testBoard.move(new Position(2, 2), new Position(3, 2));
        assert (testBoard.getPieceTypeColor(new Position(2, 2)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(3, 2)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.BLACK)));
        assert (m == MoveResult.INVALID);

        testBoard.move(new Position(2, 1), new Position(3, 1));

        m = testBoard.move(new Position(7, 2), new Position(6, 2));
        assert (testBoard.getPieceTypeColor(new Position(7, 2)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(6, 2)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.WHITE)));
        assert (m == MoveResult.INVALID);
    }

    @Test
    void doubleBlockedMove_blockOn1stSquare() {
        MoveResult m;
        m = testBoard.move(new Position(2, 2), new Position(4, 2));
        assert (testBoard.getPieceTypeColor(new Position(4, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(3, 2)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(2, 2)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.WHITE)));
        assert (m == MoveResult.INVALID);

        testBoard.move(new Position(2, 1), new Position(3, 1));

        m = testBoard.move(new Position(7, 2), new Position(5, 2));
        assert (testBoard.getPieceTypeColor(new Position(5, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(6, 2)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(7, 2)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.BLACK)));
        assert (m == MoveResult.INVALID);
    }

    @Test
    void doubleBlockedMove_blockOn2ndSquare() {
        MoveResult m;
        m = testBoard.move(new Position(2, 3), new Position(4, 3));
        assert (testBoard.getPieceTypeColor(new Position(4, 3)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(3, 3)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(2, 3)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.WHITE)));
        assert (m == MoveResult.INVALID);

        testBoard.move(new Position(2, 1), new Position(3, 1));

        m = testBoard.move(new Position(7, 2), new Position(5, 3));
        assert (testBoard.getPieceTypeColor(new Position(5, 3)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(6, 3)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(7, 3)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.BLACK)));
        assert (m == MoveResult.INVALID);
    }

    @Test
    void captureLeft() {
        MoveResult m;
        m = testBoard.move(new Position(2, 1), new Position(3, 2));
        assert (testBoard.getPieceTypeColor(new Position(3, 2)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(2, 1)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(7, 1), new Position(6, 2));
        assert (testBoard.getPieceTypeColor(new Position(6, 2)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(7, 1)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void captureRight() {
        MoveResult m;
        m = testBoard.move(new Position(2, 3), new Position(3, 2));
        assert (testBoard.getPieceTypeColor(new Position(3, 2)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(2, 3)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(7, 3), new Position(6, 2));
        assert (testBoard.getPieceTypeColor(new Position(6, 2)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(7, 3)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void enPassantLeft() {
        testBoard.move(new Position(2, 4), new Position(4, 4));
        testBoard.move(new Position(7, 4), new Position(5, 4));

        MoveResult m;
        m = testBoard.move(new Position(5, 3), new Position(6, 4));
        assert (testBoard.getPieceTypeColor(new Position(6, 4)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(5, 3)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(5, 4)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(4, 3), new Position(3, 4));
        assert (testBoard.getPieceTypeColor(new Position(3, 4)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(4, 3)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(4, 4)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

    }

    @Test
    void enPassantRight() {
        testBoard.move(new Position(2, 4), new Position(4, 4));
        testBoard.move(new Position(7, 4), new Position(5, 4));

        MoveResult m;
        m = testBoard.move(new Position(5, 5), new Position(6, 4));
        assert (testBoard.getPieceTypeColor(new Position(6, 4)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(5, 5)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(5, 4)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(4, 5), new Position(3, 4));
        assert (testBoard.getPieceTypeColor(new Position(3, 4)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(4, 5)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(4, 4)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void invalidEnPassant() {
        testBoard.move(new Position(2, 4), new Position(3, 4));
        testBoard.move(new Position(7, 4), new Position(6, 4));
        testBoard.move(new Position(3, 4), new Position(4, 4));
        testBoard.move(new Position(6, 4), new Position(5, 4));

        MoveResult m;
        m = testBoard.move(new Position(5, 5), new Position(6, 4));
        assert (testBoard.getPieceTypeColor(new Position(6, 4)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(5, 5)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(5, 4)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.BLACK)));
        assert (m == MoveResult.INVALID);

        testBoard.move(new Position(2, 1), new Position(3, 1));

        m = testBoard.move(new Position(4, 5), new Position(3, 4));
        assert (testBoard.getPieceTypeColor(new Position(3, 4)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(4, 5)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(4, 4)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.WHITE)));
        assert (m == MoveResult.INVALID);
    }

    @Test
    void promotion() {
        MoveResult m;
        m = testBoard.move(new Position(7, 6), new Position(8, 6));
        assert (m == MoveResult.PROMOTION);
        m = testBoard.promote(PieceType.QUEEN);
        assert (testBoard.getPieceTypeColor(new Position(8, 6)).equals(new PieceTypeColor(PieceType.QUEEN, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(7, 6)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(2, 6), new Position(1, 6));
        assert (m == MoveResult.PROMOTION);
        m = testBoard.promote(PieceType.QUEEN);
        assert (testBoard.getPieceTypeColor(new Position(1, 6)).equals(new PieceTypeColor(PieceType.QUEEN, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(2, 6)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

    }

    @Test
    void captureAndPromote() {
        MoveResult m;
        m = testBoard.move(new Position(7, 6), new Position(8, 7));
        assert (m == MoveResult.PROMOTION);
        m = testBoard.promote(PieceType.QUEEN);
        assert (testBoard.getPieceTypeColor(new Position(8, 7)).equals(new PieceTypeColor(PieceType.QUEEN, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(7, 6)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(2, 6), new Position(1, 7));
        assert (m == MoveResult.PROMOTION);
        m = testBoard.promote(PieceType.QUEEN);
        assert (testBoard.getPieceTypeColor(new Position(1, 7)).equals(new PieceTypeColor(PieceType.QUEEN, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(2, 6)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void blockedPromotion() {
        MoveResult m;
        m = testBoard.move(new Position(7, 7), new Position(8, 7));
        assert (testBoard.getPieceTypeColor(new Position(7, 7)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(8, 7)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.BLACK)));
        assert (m == MoveResult.INVALID);

        testBoard.move(new Position(2, 1), new Position(3, 1));

        m = testBoard.move(new Position(2, 7), new Position(1, 7));
        assert (testBoard.getPieceTypeColor(new Position(2, 7)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(1, 7)).equals(new PieceTypeColor(PieceType.PAWN, PieceColor.WHITE)));
        assert (m == MoveResult.INVALID);
    }
}