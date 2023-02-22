package Tests;

import Game.Board;
import Game.Chessboard;
import Game.MoveResult;
import Game.Pieces.*;
import Game.Pieces.PieceInfo.PieceColor;
import Game.Pieces.PieceInfo.PieceType;
import Game.Position;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class BoardTest {

    @Test
    void moveWrongColorPiece() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 5), new King(PieceColor.WHITE, new Position(1, 5)));
        board.put(new Position(8, 5), new King(PieceColor.BLACK, new Position(8, 5)));
        Board testBoard = new Chessboard(board);

        MoveResult m;
        m = testBoard.move(new Position(8, 5), new Position(7, 5));
        assert (m == MoveResult.INVALID_WRONG_COLOR);
    }

    @Test
    void checkByMoving() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 5), new King(PieceColor.WHITE, new Position(1, 5)));
        board.put(new Position(8, 5), new King(PieceColor.BLACK, new Position(8, 5)));
        board.put(new Position(1, 1), new Rook(PieceColor.WHITE, new Position(1, 1)));
        Board testBoard = new Chessboard(board);

        MoveResult m;
        m = testBoard.move(new Position(1, 1), new Position(8, 1));
        assert (m == MoveResult.CHECK);
        assert (testBoard.isInCheck(PieceColor.BLACK));
    }

    @Test
    void checkByCapture() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 5), new King(PieceColor.WHITE, new Position(1, 5)));
        board.put(new Position(8, 5), new King(PieceColor.BLACK, new Position(8, 5)));
        board.put(new Position(1, 1), new Rook(PieceColor.WHITE, new Position(1, 1)));
        board.put(new Position(8, 1), new Rook(PieceColor.BLACK, new Position(8, 1)));
        Board testBoard = new Chessboard(board);

        MoveResult m;
        m = testBoard.move(new Position(1, 1), new Position(8, 1));
        assert (m == MoveResult.CHECK);
        assert (testBoard.isInCheck(PieceColor.BLACK));
    }


    @Test
    void discoveryCheck() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 5), new King(PieceColor.WHITE, new Position(1, 5)));
        board.put(new Position(8, 5), new King(PieceColor.BLACK, new Position(8, 5)));

        board.put(new Position(8, 1), new Rook(PieceColor.WHITE, new Position(8, 1)));
        board.put(new Position(8, 2), new Bishop(PieceColor.WHITE, new Position(8, 2)));

        Board testBoard = new Chessboard(board);

        MoveResult m;
        m = testBoard.move(new Position(8, 2), new Position(7, 3));
        assert (m == MoveResult.CHECK);
        assert (testBoard.isInCheck(PieceColor.BLACK));
    }

    @Test
    void checkByPromotion() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 5), new King(PieceColor.WHITE, new Position(1, 5)));
        board.put(new Position(8, 5), new King(PieceColor.BLACK, new Position(8, 5)));

        board.put(new Position(7, 1), new Pawn(PieceColor.WHITE, new Position(7, 1)));

        Board testBoard = new Chessboard(board);

        MoveResult m;
        m = testBoard.move(new Position(7, 1), new Position(8, 1));
        assert (m == MoveResult.PROMOTION);
        m = testBoard.promote(PieceType.QUEEN);
        assert (m == MoveResult.CHECK);

        board = new HashMap<>();
        board.put(new Position(1, 5), new King(PieceColor.WHITE, new Position(1, 5)));
        board.put(new Position(8, 5), new King(PieceColor.BLACK, new Position(8, 5)));

        board.put(new Position(2, 1), new Pawn(PieceColor.BLACK, new Position(2, 1)));

        testBoard = new Chessboard(board);

        testBoard.move(new Position(1, 5), new Position(1, 4));

        m = testBoard.move(new Position(2, 1), new Position(1, 1));
        assert (m == MoveResult.PROMOTION);
        m = testBoard.promote(PieceType.QUEEN);
        assert (m == MoveResult.CHECK);
    }

    @Test
    void saveCheckByMoving() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 5), new King(PieceColor.WHITE, new Position(1, 5)));
        board.put(new Position(8, 5), new King(PieceColor.BLACK, new Position(8, 5)));
        board.put(new Position(8, 1), new Rook(PieceColor.BLACK, new Position(8, 1)));
        Board testBoard = new Chessboard(board);
        testBoard.move(new Position(1, 5), new Position(1, 4));
        testBoard.move(new Position(8, 1), new Position(1, 1));

        MoveResult m = testBoard.move(new Position(1, 4), new Position(2, 4));
        assert (m == MoveResult.VALID);
        assert (!testBoard.isInCheck(PieceColor.WHITE));
    }

    @Test
    void saveCheckByCapture() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 5), new King(PieceColor.WHITE, new Position(1, 5)));
        board.put(new Position(8, 5), new King(PieceColor.BLACK, new Position(8, 5)));
        board.put(new Position(8, 1), new Rook(PieceColor.BLACK, new Position(8, 1)));
        board.put(new Position(2, 2), new Bishop(PieceColor.WHITE, new Position(2, 2)));
        Board testBoard = new Chessboard(board);

        MoveResult m;
        testBoard.move(new Position(1, 5), new Position(1, 4));
        testBoard.move(new Position(8, 1), new Position(1, 1));

        m = testBoard.move(new Position(2, 2), new Position(1, 1));
        assert (m == MoveResult.VALID);
        assert (!testBoard.isInCheck(PieceColor.WHITE));
    }

    @Test
    void saveCheckByBlocking() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 5), new King(PieceColor.WHITE, new Position(1, 5)));
        board.put(new Position(8, 5), new King(PieceColor.BLACK, new Position(8, 5)));
        board.put(new Position(8, 1), new Rook(PieceColor.BLACK, new Position(8, 1)));
        board.put(new Position(2, 2), new Rook(PieceColor.WHITE, new Position(2, 2)));
        Board testBoard = new Chessboard(board);

        MoveResult m;
        testBoard.move(new Position(1, 5), new Position(1, 4));
        testBoard.move(new Position(8, 1), new Position(1, 1));

        m = testBoard.move(new Position(2, 2), new Position(1, 2));
        assert (m == MoveResult.VALID);
        assert (!testBoard.isInCheck(PieceColor.WHITE));
    }

    @Test
    void invalidCheckedMove_kingAttempts() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 5), new King(PieceColor.WHITE, new Position(1, 5)));
        board.put(new Position(8, 5), new King(PieceColor.BLACK, new Position(8, 5)));
        board.put(new Position(8, 1), new Rook(PieceColor.BLACK, new Position(8, 1)));
        Board testBoard = new Chessboard(board);
        testBoard.move(new Position(1, 5), new Position(1, 4));
        testBoard.move(new Position(8, 1), new Position(1, 1));

        MoveResult m = testBoard.move(new Position(1, 4), new Position(1, 3));
        assert (m == MoveResult.INVALID_CHECKED);
        assert (testBoard.isInCheck(PieceColor.WHITE));
    }

    @Test
    void invalidCheckedMove_otherPieceAttempts() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 5), new King(PieceColor.WHITE, new Position(1, 5)));
        board.put(new Position(8, 5), new King(PieceColor.BLACK, new Position(8, 5)));
        board.put(new Position(8, 1), new Rook(PieceColor.BLACK, new Position(8, 1)));
        board.put(new Position(2, 2), new Rook(PieceColor.WHITE, new Position(2, 2)));
        Board testBoard = new Chessboard(board);

        MoveResult m;
        testBoard.move(new Position(1, 5), new Position(1, 4));
        testBoard.move(new Position(8, 1), new Position(1, 1));

        m = testBoard.move(new Position(2, 2), new Position(3, 2));
        assert (m == MoveResult.INVALID_CHECKED);
        assert (testBoard.isInCheck(PieceColor.WHITE));
    }


    @Test
    void invalidCheckedMove_otherPieceAttemptsCapture() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 5), new King(PieceColor.WHITE, new Position(1, 5)));
        board.put(new Position(8, 5), new King(PieceColor.BLACK, new Position(8, 5)));
        board.put(new Position(8, 1), new Rook(PieceColor.BLACK, new Position(8, 1)));
        board.put(new Position(2, 2), new Rook(PieceColor.WHITE, new Position(2, 2)));
        board.put(new Position(3, 2), new Pawn(PieceColor.BLACK, new Position(3, 2)));
        Board testBoard = new Chessboard(board);

        MoveResult m;
        testBoard.move(new Position(1, 5), new Position(1, 4));
        testBoard.move(new Position(8, 1), new Position(1, 1));

        m = testBoard.move(new Position(2, 2), new Position(3, 2));
        assert (m == MoveResult.INVALID_CHECKED);
        assert (testBoard.isInCheck(PieceColor.WHITE));
        assert (testBoard.getPieceColor(new Position(3, 2)) == PieceColor.BLACK);
    }

    @Test
    void checkmate_kingTrapped() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 5), new King(PieceColor.WHITE, new Position(1, 5)));
        board.put(new Position(1, 4), new Queen(PieceColor.WHITE, new Position(1, 4)));
        board.put(new Position(8, 5), new King(PieceColor.BLACK, new Position(8, 5)));
        board.put(new Position(8, 6), new Pawn(PieceColor.BLACK, new Position(8, 6)));
        board.put(new Position(7, 6), new Pawn(PieceColor.BLACK, new Position(7, 6)));
        board.put(new Position(8, 4), new Pawn(PieceColor.BLACK, new Position(8, 4)));
        board.put(new Position(7, 4), new Pawn(PieceColor.BLACK, new Position(7, 4)));
        Board testBoard = new Chessboard(board);

        MoveResult m;
        m = testBoard.move(new Position(1, 4), new Position(2, 5));
        assert (m == MoveResult.CHECKMATE);
    }

    @Test
    void checkmate_pieceNextToKingDefended() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 5), new King(PieceColor.WHITE, new Position(1, 5)));
        board.put(new Position(2, 4), new Queen(PieceColor.WHITE, new Position(2, 4)));
        board.put(new Position(1, 4), new Queen(PieceColor.WHITE, new Position(1, 4)));
        board.put(new Position(8, 5), new King(PieceColor.BLACK, new Position(8, 5)));
        board.put(new Position(8, 6), new Pawn(PieceColor.BLACK, new Position(8, 6)));
        board.put(new Position(7, 6), new Pawn(PieceColor.BLACK, new Position(7, 6)));
        board.put(new Position(8, 4), new Pawn(PieceColor.BLACK, new Position(8, 4)));
        board.put(new Position(7, 4), new Pawn(PieceColor.BLACK, new Position(7, 4)));
        Board testBoard = new Chessboard(board);

        MoveResult m;
        m = testBoard.move(new Position(2, 4), new Position(7, 4));
        assert (m == MoveResult.CHECKMATE);
    }

    @Test
    void checkmateViaPromotion() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 5), new King(PieceColor.WHITE, new Position(1, 5)));
        board.put(new Position(8, 5), new King(PieceColor.BLACK, new Position(8, 5)));
        board.put(new Position(8, 6), new Pawn(PieceColor.BLACK, new Position(8, 6)));
        board.put(new Position(7, 6), new Pawn(PieceColor.BLACK, new Position(7, 6)));
        board.put(new Position(7, 5), new Pawn(PieceColor.BLACK, new Position(8, 4)));
        board.put(new Position(7, 4), new Pawn(PieceColor.BLACK, new Position(7, 5)));
        board.put(new Position(7, 1), new Pawn(PieceColor.WHITE, new Position(7, 1)));

        Board testBoard = new Chessboard(board);

        MoveResult m;
        testBoard.move(new Position(7, 1), new Position(8, 1));
        m = testBoard.promote(PieceType.QUEEN);
        assert (m == MoveResult.CHECKMATE);
    }

    @Test
    void notCheckmateViaPromotion_blockedByAnotherPiece() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 5), new King(PieceColor.WHITE, new Position(1, 5)));
        board.put(new Position(8, 5), new King(PieceColor.BLACK, new Position(8, 5)));
        board.put(new Position(8, 6), new Pawn(PieceColor.BLACK, new Position(8, 6)));
        board.put(new Position(7, 6), new Pawn(PieceColor.BLACK, new Position(7, 6)));
        board.put(new Position(7, 5), new Pawn(PieceColor.BLACK, new Position(8, 4)));
        board.put(new Position(7, 4), new Pawn(PieceColor.BLACK, new Position(7, 5)));
        board.put(new Position(7, 1), new Pawn(PieceColor.WHITE, new Position(7, 1)));
        board.put(new Position(7, 3), new Rook(PieceColor.BLACK, new Position(7, 3)));

        Board testBoard = new Chessboard(board);

        MoveResult m;
        testBoard.move(new Position(7, 1), new Position(8, 1));
        m = testBoard.promote(PieceType.QUEEN);
        assert (m == MoveResult.CHECK);
    }

    @Test
    void notCheckmate_otherPieceCanBlock() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 5), new King(PieceColor.WHITE, new Position(1, 5)));
        board.put(new Position(1, 4), new Queen(PieceColor.WHITE, new Position(1, 4)));
        board.put(new Position(8, 5), new King(PieceColor.BLACK, new Position(8, 5)));
        board.put(new Position(8, 6), new Pawn(PieceColor.BLACK, new Position(8, 6)));
        board.put(new Position(7, 6), new Pawn(PieceColor.BLACK, new Position(7, 6)));
        board.put(new Position(8, 4), new Pawn(PieceColor.BLACK, new Position(8, 4)));
        board.put(new Position(7, 4), new Pawn(PieceColor.BLACK, new Position(7, 4)));
        board.put(new Position(6, 1), new Rook(PieceColor.BLACK, new Position(6, 1)));
        Board testBoard = new Chessboard(board);

        MoveResult m;
        m = testBoard.move(new Position(1, 4), new Position(2, 5));
        assert (m == MoveResult.CHECK);
    }

    @Test
    void discoveryCheckmate() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 5), new King(PieceColor.WHITE, new Position(1, 5)));
        board.put(new Position(2, 5), new Queen(PieceColor.WHITE, new Position(2, 5)));
        board.put(new Position(3, 5), new Bishop(PieceColor.WHITE, new Position(3, 5)));
        board.put(new Position(8, 5), new King(PieceColor.BLACK, new Position(8, 5)));
        board.put(new Position(8, 6), new Pawn(PieceColor.BLACK, new Position(8, 6)));
        board.put(new Position(7, 6), new Pawn(PieceColor.BLACK, new Position(7, 6)));
        board.put(new Position(8, 4), new Pawn(PieceColor.BLACK, new Position(8, 4)));
        board.put(new Position(7, 4), new Pawn(PieceColor.BLACK, new Position(7, 4)));
        Board testBoard = new Chessboard(board);

        MoveResult m;
        m = testBoard.move(new Position(3, 5), new Position(4, 6));
        assert (m == MoveResult.CHECKMATE);
    }

    @Test
    void checkmate_twoAttackers() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 5), new King(PieceColor.WHITE, new Position(1, 5)));
        board.put(new Position(2, 5), new Queen(PieceColor.WHITE, new Position(4, 5)));
        board.put(new Position(4, 5), new Bishop(PieceColor.WHITE, new Position(4, 5)));
        board.put(new Position(8, 5), new King(PieceColor.BLACK, new Position(8, 5)));
        board.put(new Position(8, 6), new Pawn(PieceColor.BLACK, new Position(8, 6)));
        board.put(new Position(8, 4), new Pawn(PieceColor.BLACK, new Position(8, 4)));
        board.put(new Position(7, 4), new Pawn(PieceColor.BLACK, new Position(7, 4)));
        Board testBoard = new Chessboard(board);

        MoveResult m;
        m = testBoard.move(new Position(4, 5), new Position(6, 7));
        assert (m == MoveResult.CHECKMATE);
    }

    @Test
    void notCheckmate_twoAttackersButCanEscape() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 5), new King(PieceColor.WHITE, new Position(1, 5)));
        board.put(new Position(2, 5), new Queen(PieceColor.WHITE, new Position(4, 5)));
        board.put(new Position(4, 5), new Bishop(PieceColor.WHITE, new Position(4, 5)));
        board.put(new Position(8, 5), new King(PieceColor.BLACK, new Position(8, 5)));
        board.put(new Position(8, 4), new Pawn(PieceColor.BLACK, new Position(8, 4)));
        board.put(new Position(7, 4), new Pawn(PieceColor.BLACK, new Position(7, 4)));
        Board testBoard = new Chessboard(board);

        MoveResult m;
        m = testBoard.move(new Position(4, 5), new Position(6, 7));
        assert (m == MoveResult.CHECK);
    }

    @Test
    void stalemate() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 5), new King(PieceColor.WHITE, new Position(1, 5)));
        board.put(new Position(1, 6), new Rook(PieceColor.WHITE, new Position(1, 6)));
        board.put(new Position(1, 4), new Queen(PieceColor.WHITE, new Position(1, 4)));
        board.put(new Position(8, 5), new King(PieceColor.BLACK, new Position(8, 5)));
        board.put(new Position(5, 8), new Pawn(PieceColor.BLACK, new Position(5, 8)));
        board.put(new Position(4, 8), new Pawn(PieceColor.WHITE, new Position(4, 8)));
        Board testBoard = new Chessboard(board);

        MoveResult m;
        m = testBoard.move(new Position(1, 4), new Position(6, 4));
        assert (m == MoveResult.STALEMATE);
    }
}
