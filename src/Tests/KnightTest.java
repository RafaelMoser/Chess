package Tests;

import Game.GameRules.ChessRules;
import Game.GameRules.ModernChessRules;
import Game.MoveResult;
import Game.Pieces.King;
import Game.Pieces.Knight;
import Game.Pieces.Pawn;
import Game.Pieces.Piece;
import Game.Pieces.PieceInfo.PieceColor;
import Game.Pieces.PieceInfo.PieceType;
import Game.Pieces.PieceInfo.PieceTypeColor;
import Game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class KnightTest {
/* Initial board for test:
       a  b  c  d  e  f  g  h
    8 [ ][B][ ][ ][K][ ][B][ ]
    7 [X][X][x][ ][ ][X][X][X]
    6 [ ][ ][ ][ ][ ][B][ ][W]
    5 [ ][ ][ ][ ][ ][ ][ ][ ]
    4 [ ][ ][ ][ ][ ][ ][ ][ ]
    3 [ ][ ][ ][ ][ ][W][ ][B]
    2 [X][X][X][ ][ ][X][X][X]
    1 [ ][W][ ][ ][K][ ][W][ ]

    B = black knight
    W = white knight
    K = kings (not used on test)
    more pawns added based on the test done at positions marked X
     */

    ChessRules testBoard, testBoardMoveOver;


    @BeforeEach
    void setUp() {
        List<Piece> board = new ArrayList<>();

        board.add(new Knight(PieceColor.WHITE, new Position(1, 2)));
        board.add(new Knight(PieceColor.WHITE, new Position(1, 7)));
        board.add(new Knight(PieceColor.WHITE, new Position(3, 6)));
        board.add(new Knight(PieceColor.WHITE, new Position(6, 8)));

        board.add(new Knight(PieceColor.BLACK, new Position(8, 2)));
        board.add(new Knight(PieceColor.BLACK, new Position(8, 7)));
        board.add(new Knight(PieceColor.BLACK, new Position(6, 6)));
        board.add(new Knight(PieceColor.BLACK, new Position(3, 8)));

        board.add(new King(PieceColor.WHITE, new Position(1, 5)));
        board.add(new King(PieceColor.BLACK, new Position(8, 5)));

        testBoard = new ModernChessRules(board);

        board.add(new Pawn(PieceColor.WHITE, new Position(2, 1)));
        board.add(new Pawn(PieceColor.WHITE, new Position(2, 2)));
        board.add(new Pawn(PieceColor.WHITE, new Position(2, 3)));
        board.add(new Pawn(PieceColor.WHITE, new Position(2, 6)));
        board.add(new Pawn(PieceColor.WHITE, new Position(2, 7)));
        board.add(new Pawn(PieceColor.WHITE, new Position(2, 8)));

        board.add(new Pawn(PieceColor.BLACK, new Position(7, 1)));
        board.add(new Pawn(PieceColor.BLACK, new Position(7, 2)));
        board.add(new Pawn(PieceColor.BLACK, new Position(7, 3)));
        board.add(new Pawn(PieceColor.BLACK, new Position(7, 6)));
        board.add(new Pawn(PieceColor.BLACK, new Position(7, 7)));
        board.add(new Pawn(PieceColor.BLACK, new Position(7, 8)));

        testBoardMoveOver = new ModernChessRules(board);
    }

    @Test
    void twoXoneYMove() {
        MoveResult m;
        m = testBoard.move(new Position(1, 2), new Position(3, 3));
        assert (testBoard.getPieceTypeColor(new Position(3, 3)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(8, 2), new Position(6, 3));
        assert (testBoard.getPieceTypeColor(new Position(6, 3)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(8, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void oneXtwoYMove() {
        MoveResult m;
        m = testBoard.move(new Position(1, 2), new Position(2, 4));
        assert (testBoard.getPieceTypeColor(new Position(2, 4)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(8, 2), new Position(7, 4));
        assert (testBoard.getPieceTypeColor(new Position(7, 4)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(8, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void invalidLineMove() {
        MoveResult m;
        m = testBoard.move(new Position(1, 2), new Position(3, 2));
        assert (testBoard.getPieceTypeColor(new Position(1, 2)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(3, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);

        testBoard.move(new Position(1, 2), new Position(3, 3));

        m = testBoard.move(new Position(8, 2), new Position(6, 2));
        assert (testBoard.getPieceTypeColor(new Position(8, 2)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(6, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);
    }

    @Test
    void invalidDiagonalMove() {
        MoveResult m;
        m = testBoard.move(new Position(1, 2), new Position(3, 4));
        assert (testBoard.getPieceTypeColor(new Position(1, 2)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(3, 4)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);

        testBoard.move(new Position(1, 2), new Position(3, 3));

        m = testBoard.move(new Position(8, 2), new Position(6, 4));
        assert (testBoard.getPieceTypeColor(new Position(8, 2)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(6, 4)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);
    }

    @Test
    void invalidMove() {
        MoveResult m;
        m = testBoard.move(new Position(1, 2), new Position(4, 4));
        assert (testBoard.getPieceTypeColor(new Position(1, 2)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(4, 4)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);

        testBoard.move(new Position(1, 2), new Position(3, 3));

        m = testBoard.move(new Position(8, 2), new Position(5, 4));
        assert (testBoard.getPieceTypeColor(new Position(8, 2)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(5, 4)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.INVALID);
    }

    @Test
    void invalidOutOfBoundsMove() {
        MoveResult m;
        m = testBoard.move(new Position(1, 2), new Position(0, 4));
        assert (testBoard.getPieceTypeColor(new Position(1, 2)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.WHITE)));
        assert (m == MoveResult.INVALID_OUT_OF_BOUNDS);

        testBoard.move(new Position(1, 2), new Position(3, 3));

        m = testBoard.move(new Position(8, 2), new Position(9, 4));
        assert (testBoard.getPieceTypeColor(new Position(8, 2)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.BLACK)));
        assert (m == MoveResult.INVALID_OUT_OF_BOUNDS);
    }

    @Test
    void capture() {
        MoveResult m;
        m = testBoard.move(new Position(1, 7), new Position(3, 8));
        assert (testBoard.getPieceTypeColor(new Position(3, 8)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(1, 7)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoard.move(new Position(8, 7), new Position(6, 8));
        assert (testBoard.getPieceTypeColor(new Position(6, 8)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(8, 7)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void blockedMove() {
        MoveResult m;
        m = testBoard.move(new Position(1, 7), new Position(3, 6));
        assert (testBoard.getPieceTypeColor(new Position(1, 7)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.WHITE)));
        assert (testBoard.getPieceTypeColor(new Position(3, 6)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.WHITE)));
        assert (m == MoveResult.INVALID);

        testBoard.move(new Position(1, 2), new Position(3, 3));

        m = testBoard.move(new Position(8, 7), new Position(6, 6));
        assert (testBoard.getPieceTypeColor(new Position(8, 7)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.BLACK)));
        assert (testBoard.getPieceTypeColor(new Position(6, 6)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.BLACK)));
        assert (m == MoveResult.INVALID);
    }

    @Test
    void moveOverPiece() {
        MoveResult m;
        m = testBoardMoveOver.move(new Position(1, 2), new Position(3, 3));
        assert (testBoardMoveOver.getPieceTypeColor(new Position(3, 3)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.WHITE)));
        assert (testBoardMoveOver.getPieceTypeColor(new Position(1, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoardMoveOver.move(new Position(8, 2), new Position(6, 3));
        assert (testBoardMoveOver.getPieceTypeColor(new Position(6, 3)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.BLACK)));
        assert (testBoardMoveOver.getPieceTypeColor(new Position(8, 2)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void moveOverPieceAndCapture() {
        MoveResult m;
        m = testBoardMoveOver.move(new Position(1, 7), new Position(3, 8));
        assert (testBoardMoveOver.getPieceTypeColor(new Position(3, 8)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.WHITE)));
        assert (testBoardMoveOver.getPieceTypeColor(new Position(1, 7)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);

        m = testBoardMoveOver.move(new Position(8, 7), new Position(6, 8));
        assert (testBoardMoveOver.getPieceTypeColor(new Position(6, 8)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.BLACK)));
        assert (testBoardMoveOver.getPieceTypeColor(new Position(8, 7)).equals(new PieceTypeColor(PieceType.NONE, PieceColor.NONE)));
        assert (m == MoveResult.VALID);
    }

    @Test
    void blockedMoveOverPiece() {
        MoveResult m;
        m = testBoardMoveOver.move(new Position(1, 7), new Position(3, 6));
        assert (testBoardMoveOver.getPieceTypeColor(new Position(1, 7)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.WHITE)));
        assert (testBoardMoveOver.getPieceTypeColor(new Position(3, 6)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.WHITE)));
        assert (m == MoveResult.INVALID);

        testBoardMoveOver.move(new Position(1, 2), new Position(3, 3));

        m = testBoardMoveOver.move(new Position(8, 7), new Position(6, 6));
        assert (testBoardMoveOver.getPieceTypeColor(new Position(8, 7)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.BLACK)));
        assert (testBoardMoveOver.getPieceTypeColor(new Position(6, 6)).equals(new PieceTypeColor(PieceType.KNIGHT, PieceColor.BLACK)));
        assert (m == MoveResult.INVALID);
    }


}
