package Tests;

import Game.GameRules.ChessRules;
import Game.GameRules.ModernChessRules;
import Game.MoveResult;
import Game.Pieces.King;
import Game.Pieces.Piece;
import Game.Pieces.PieceInfo.PieceColor;
import Game.Pieces.PieceInfo.PieceType;
import Game.Pieces.PieceInfo.PieceTypeColor;
import Game.Pieces.Queen;
import Game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class QueenTest {

    /* Initial board for test:
           a  b  c  d  e  f  g  h
        8 [ ][ ][ ][ ][K][ ][ ][ ]
        7 [ ][B][ ][ ][ ][ ][B][ ]
        6 [ ][ ][ ][ ][ ][ ][ ][ ]
        5 [ ][ ][ ][ ][ ][ ][ ][ ]
        4 [ ][ ][ ][ ][ ][ ][ ][ ]
        3 [ ][ ][ ][ ][ ][ ][ ][ ]
        2 [ ][W][ ][ ][ ][ ][ ][ ]
        1 [ ][ ][ ][ ][K][ ][ ][ ]

        B = black queen
        W = white queen
        K = kings (not used on test)
         */


    ChessRules testBoard;

    @BeforeEach
    void setUp() {
        List<Piece> board = new ArrayList<>();

        board.add(new Queen(PieceColor.WHITE, new Position(2, 2)));

        board.add(new Queen(PieceColor.BLACK, new Position(7, 2)));
        board.add(new Queen(PieceColor.BLACK, new Position(7, 7)));


        board.add(new King(PieceColor.WHITE, new Position(1, 5)));
        board.add(new King(PieceColor.BLACK, new Position(8, 5)));

        testBoard = new ModernChessRules(board);
    }

    @Test
    void singleLineMove() {
        MoveResult m;
        m = testBoard.move(new Position(2, 2), new Position(3, 2));
        assert (testBoard.getPieceTypeColor(new Position(3, 2)).equals(new PieceTypeColor(PieceType.QUEEN, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(2, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(7, 2), new Position(6, 2));
        assert (testBoard.getPieceTypeColor(new Position(6, 2)).equals(new PieceTypeColor(PieceType.QUEEN, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(7, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void longLineMove() {
        testBoard.move(new Position(1, 5), new Position(1, 4));
        testBoard.move(new Position(7, 7), new Position(8, 7));

        MoveResult m;
        m = testBoard.move(new Position(2, 2), new Position(2, 8));
        assert (testBoard.getPieceTypeColor(new Position(2, 8)).equals(new PieceTypeColor(PieceType.QUEEN, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(2, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(7, 2), new Position(7, 8));
        assert (testBoard.getPieceTypeColor(new Position(7, 8)).equals(new PieceTypeColor(PieceType.QUEEN, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(7, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void singleDiagonalMove() {
        MoveResult m;
        m = testBoard.move(new Position(2, 2), new Position(3, 3));
        assert (testBoard.getPieceTypeColor(new Position(3, 3)).equals(new PieceTypeColor(PieceType.QUEEN, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(2, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(7, 2), new Position(6, 3));
        assert (testBoard.getPieceTypeColor(new Position(6, 3)).equals(new PieceTypeColor(PieceType.QUEEN, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(7, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void longDiagonalMove() {
        testBoard.move(new Position(1, 5), new Position(2, 5));
        testBoard.move(new Position(7, 7), new Position(8, 7));

        MoveResult m;
        m = testBoard.move(new Position(2, 2), new Position(8, 8));
        assert (testBoard.getPieceTypeColor(new Position(8, 8)).equals(new PieceTypeColor(PieceType.QUEEN, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(2, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(7, 2), new Position(1, 8));
        assert (testBoard.getPieceTypeColor(new Position(1, 8)).equals(new PieceTypeColor(PieceType.QUEEN, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(7, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void invalidMove() {
        MoveResult m;
        m = testBoard.move(new Position(2, 2), new Position(4, 3));
        assert (testBoard.getPieceTypeColor(new Position(2, 2)).equals(new PieceTypeColor(PieceType.QUEEN, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(4, 3)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);

        testBoard.move(new Position(2, 2), new Position(3, 2));

        m = testBoard.move(new Position(7, 2), new Position(5, 3));
        assert (testBoard.getPieceTypeColor(new Position(7, 2)).equals(new PieceTypeColor(PieceType.QUEEN, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(5, 3)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);
    }

    @Test
    void invalidOutOfBoundsMove() {
        MoveResult m;
        m = testBoard.move(new Position(2, 2), new Position(0, 2));
        assert (testBoard.getPieceTypeColor(new Position(2, 2)).equals(new PieceTypeColor(PieceType.QUEEN, PieceColor.WHITE)));
        assert (m == MoveResult.INVALID_OUT_OF_BOUNDS);

        testBoard.move(new Position(2, 2), new Position(3, 2));

        m = testBoard.move(new Position(7, 2), new Position(9, 2));
        assert (testBoard.getPieceTypeColor(new Position(7, 2)).equals(new PieceTypeColor(PieceType.QUEEN, PieceColor.BLACK)));
        assert (m == MoveResult.INVALID_OUT_OF_BOUNDS);
    }

    @Test
    void capture() {
        MoveResult m;
        m = testBoard.move(new Position(2, 2), new Position(7, 7));
        assert (testBoard.getPieceTypeColor(new Position(7, 7)).equals(new PieceTypeColor(PieceType.QUEEN, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(2, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(7, 2), new Position(7, 7));
        assert (testBoard.getPieceTypeColor(new Position(7, 7)).equals(new PieceTypeColor(PieceType.QUEEN, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(7, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void blockedMove() {
        MoveResult m;
        m = testBoard.move(new Position(2, 2), new Position(8, 2));
        assert (testBoard.getPieceTypeColor(new Position(2, 2)).equals(new PieceTypeColor(PieceType.QUEEN, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(8, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(7, 2)).equals(new PieceTypeColor(PieceType.QUEEN, PieceColor.BLACK)));
        assert (m == MoveResult.INVALID);

        testBoard.move(new Position(1, 5), new Position(1, 4));

        m = testBoard.move(new Position(7, 7), new Position(1, 1));
        assert (testBoard.getPieceTypeColor(new Position(7, 7)).equals(new PieceTypeColor(PieceType.QUEEN, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(1, 1)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (testBoard.getPieceTypeColor(new Position(2, 2)).equals(new PieceTypeColor(PieceType.QUEEN, PieceColor.WHITE)));
        assert (m == MoveResult.INVALID);
    }
}
