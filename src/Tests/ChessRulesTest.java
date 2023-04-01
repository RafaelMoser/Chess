package Tests;

import Game.GameRules.ChessRules;
import Game.GameRules.ModernChessRules;
import Game.MoveResult;
import Game.Pieces.*;
import Game.Pieces.PieceInfo.PieceColor;
import Game.Pieces.PieceInfo.PieceType;
import Game.Position;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ChessRulesTest {

    @Test
    void moveWrongColorPiece() {
        List<Piece> board = new ArrayList<>();
        board.add(new King(PieceColor.WHITE, new Position(1, 5)));
        board.add(new King(PieceColor.BLACK, new Position(8, 5)));
        ChessRules testBoard = new ModernChessRules(board);

        MoveResult m;
        m = testBoard.move(new Position(8, 5), new Position(7, 5));
        assert (m == MoveResult.INVALID_WRONG_COLOR);
    }

    @Test
    void checkByMoving() {
        List<Piece> board = new ArrayList<>();
        board.add(new King(PieceColor.WHITE, new Position(1, 5)));
        board.add(new King(PieceColor.BLACK, new Position(8, 5)));
        board.add(new Rook(PieceColor.WHITE, new Position(1, 1)));
        ChessRules testBoard = new ModernChessRules(board);

        MoveResult m;
        m = testBoard.move(new Position(1, 1), new Position(8, 1));
        assert (m == MoveResult.CHECK);
        assert (testBoard.isInCheck(PieceColor.BLACK));
    }

    @Test
    void checkByCapture() {
        List<Piece> board = new ArrayList<>();
        board.add(new King(PieceColor.WHITE, new Position(1, 5)));
        board.add(new King(PieceColor.BLACK, new Position(8, 5)));
        board.add(new Rook(PieceColor.WHITE, new Position(1, 1)));
        board.add(new Rook(PieceColor.BLACK, new Position(8, 1)));
        ChessRules testBoard = new ModernChessRules(board);

        MoveResult m;
        m = testBoard.move(new Position(1, 1), new Position(8, 1));
        assert (m == MoveResult.CHECK);
        assert (testBoard.isInCheck(PieceColor.BLACK));
    }


    @Test
    void discoveryCheck() {
        List<Piece> board = new ArrayList<>();
        board.add(new King(PieceColor.WHITE, new Position(1, 5)));
        board.add(new King(PieceColor.BLACK, new Position(8, 5)));

        board.add(new Rook(PieceColor.WHITE, new Position(8, 1)));
        board.add(new Bishop(PieceColor.WHITE, new Position(8, 2)));

        ChessRules testBoard = new ModernChessRules(board);

        MoveResult m;
        m = testBoard.move(new Position(8, 2), new Position(7, 3));
        assert (m == MoveResult.CHECK);
        assert (testBoard.isInCheck(PieceColor.BLACK));
    }

    @Test
    void checkByPromotion() {
        List<Piece> board = new ArrayList<>();
        board.add(new King(PieceColor.WHITE, new Position(1, 5)));
        board.add(new King(PieceColor.BLACK, new Position(8, 5)));

        board.add(new Pawn(PieceColor.WHITE, new Position(7, 1)));

        ChessRules testBoard = new ModernChessRules(board);

        MoveResult m;
        m = testBoard.move(new Position(7, 1), new Position(8, 1));
        assert (m == MoveResult.PROMOTION);
        m = testBoard.promote(PieceType.QUEEN);
        assert (m == MoveResult.CHECK);

        board = new ArrayList<>();
        board.add(new King(PieceColor.WHITE, new Position(1, 5)));
        board.add(new King(PieceColor.BLACK, new Position(8, 5)));

        board.add(new Pawn(PieceColor.BLACK, new Position(2, 1)));

        testBoard = new ModernChessRules(board);

        testBoard.move(new Position(1, 5), new Position(1, 4));

        m = testBoard.move(new Position(2, 1), new Position(1, 1));
        assert (m == MoveResult.PROMOTION);
        m = testBoard.promote(PieceType.QUEEN);
        assert (m == MoveResult.CHECK);
    }

    @Test
    void saveCheckByMoving() {
        List<Piece> board = new ArrayList<>();
        board.add(new King(PieceColor.WHITE, new Position(1, 5)));
        board.add(new King(PieceColor.BLACK, new Position(8, 5)));
        board.add(new Rook(PieceColor.BLACK, new Position(8, 1)));
        ChessRules testBoard = new ModernChessRules(board);
        testBoard.move(new Position(1, 5), new Position(1, 4));
        testBoard.move(new Position(8, 1), new Position(1, 1));

        MoveResult m = testBoard.move(new Position(1, 4), new Position(2, 4));
        assert (m == MoveResult.VALID);
        assert (!testBoard.isInCheck(PieceColor.WHITE));
    }

    @Test
    void saveCheckByCapture() {
        List<Piece> board = new ArrayList<>();
        board.add(new King(PieceColor.WHITE, new Position(1, 5)));
        board.add(new King(PieceColor.BLACK, new Position(8, 5)));
        board.add(new Rook(PieceColor.BLACK, new Position(8, 1)));
        board.add(new Bishop(PieceColor.WHITE, new Position(2, 2)));
        ChessRules testBoard = new ModernChessRules(board);

        MoveResult m;
        testBoard.move(new Position(1, 5), new Position(1, 4));
        testBoard.move(new Position(8, 1), new Position(1, 1));

        m = testBoard.move(new Position(2, 2), new Position(1, 1));
        assert (m == MoveResult.VALID);
        assert (!testBoard.isInCheck(PieceColor.WHITE));
    }

    @Test
    void saveCheckByBlocking() {
        List<Piece> board = new ArrayList<>();
        board.add(new King(PieceColor.WHITE, new Position(1, 5)));
        board.add(new King(PieceColor.BLACK, new Position(8, 5)));
        board.add(new Rook(PieceColor.BLACK, new Position(8, 1)));
        board.add(new Rook(PieceColor.WHITE, new Position(2, 2)));
        ChessRules testBoard = new ModernChessRules(board);

        MoveResult m;
        testBoard.move(new Position(1, 5), new Position(1, 4));
        testBoard.move(new Position(8, 1), new Position(1, 1));

        m = testBoard.move(new Position(2, 2), new Position(1, 2));
        assert (m == MoveResult.VALID);
        assert (!testBoard.isInCheck(PieceColor.WHITE));
    }

    @Test
    void invalidCheckedMove_kingAttempts() {
        List<Piece> board = new ArrayList<>();
        board.add(new King(PieceColor.WHITE, new Position(1, 5)));
        board.add(new King(PieceColor.BLACK, new Position(8, 5)));
        board.add(new Rook(PieceColor.BLACK, new Position(8, 1)));
        ChessRules testBoard = new ModernChessRules(board);
        testBoard.move(new Position(1, 5), new Position(1, 4));
        testBoard.move(new Position(8, 1), new Position(1, 1));

        MoveResult m = testBoard.move(new Position(1, 4), new Position(1, 3));
        assert (m == MoveResult.INVALID_CHECKED);
        assert (testBoard.isInCheck(PieceColor.WHITE));
    }

    @Test
    void invalidCheckedMove_otherPieceAttempts() {
        List<Piece> board = new ArrayList<>();
        board.add(new King(PieceColor.WHITE, new Position(1, 5)));
        board.add(new King(PieceColor.BLACK, new Position(8, 5)));
        board.add(new Rook(PieceColor.BLACK, new Position(8, 1)));
        board.add(new Rook(PieceColor.WHITE, new Position(2, 2)));
        ChessRules testBoard = new ModernChessRules(board);

        MoveResult m;
        testBoard.move(new Position(1, 5), new Position(1, 4));
        testBoard.move(new Position(8, 1), new Position(1, 1));

        m = testBoard.move(new Position(2, 2), new Position(3, 2));
        assert (m == MoveResult.INVALID_CHECKED);
        assert (testBoard.isInCheck(PieceColor.WHITE));
    }


    @Test
    void invalidCheckedMove_otherPieceAttemptsCapture() {
        List<Piece> board = new ArrayList<>();
        board.add(new King(PieceColor.WHITE, new Position(1, 5)));
        board.add(new King(PieceColor.BLACK, new Position(8, 5)));
        board.add(new Rook(PieceColor.BLACK, new Position(8, 1)));
        board.add(new Rook(PieceColor.WHITE, new Position(2, 2)));
        board.add(new Pawn(PieceColor.BLACK, new Position(3, 2)));
        ChessRules testBoard = new ModernChessRules(board);

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
        List<Piece> board = new ArrayList<>();
        board.add(new King(PieceColor.WHITE, new Position(1, 5)));
        board.add(new Queen(PieceColor.WHITE, new Position(1, 4)));
        board.add(new King(PieceColor.BLACK, new Position(8, 5)));
        board.add(new Pawn(PieceColor.BLACK, new Position(8, 6)));
        board.add(new Pawn(PieceColor.BLACK, new Position(7, 6)));
        board.add(new Pawn(PieceColor.BLACK, new Position(8, 4)));
        board.add(new Pawn(PieceColor.BLACK, new Position(7, 4)));
        ChessRules testBoard = new ModernChessRules(board);

        MoveResult m;
        m = testBoard.move(new Position(1, 4), new Position(2, 5));
        assert (m == MoveResult.CHECKMATE);
    }

    @Test
    void checkmate_pieceNextToKingDefended() {
        List<Piece> board = new ArrayList<>();
        board.add(new King(PieceColor.WHITE, new Position(1, 5)));
        board.add(new Queen(PieceColor.WHITE, new Position(2, 4)));
        board.add(new Queen(PieceColor.WHITE, new Position(1, 4)));
        board.add(new King(PieceColor.BLACK, new Position(8, 5)));
        board.add(new Pawn(PieceColor.BLACK, new Position(8, 6)));
        board.add(new Pawn(PieceColor.BLACK, new Position(7, 6)));
        board.add(new Pawn(PieceColor.BLACK, new Position(8, 4)));
        board.add(new Pawn(PieceColor.BLACK, new Position(7, 4)));
        ChessRules testBoard = new ModernChessRules(board);

        MoveResult m;
        m = testBoard.move(new Position(2, 4), new Position(7, 4));
        assert (m == MoveResult.CHECKMATE);
    }

    @Test
    void checkmateViaPromotion() {
        List<Piece> board = new ArrayList<>();
        board.add(new King(PieceColor.WHITE, new Position(1, 5)));
        board.add(new King(PieceColor.BLACK, new Position(8, 5)));
        board.add(new Pawn(PieceColor.BLACK, new Position(8, 6)));
        board.add(new Pawn(PieceColor.BLACK, new Position(7, 6)));
        board.add(new Pawn(PieceColor.BLACK, new Position(7, 4)));
        board.add(new Pawn(PieceColor.BLACK, new Position(7, 5)));
        board.add(new Pawn(PieceColor.WHITE, new Position(7, 1)));

        ChessRules testBoard = new ModernChessRules(board);

        MoveResult m;
        testBoard.move(new Position(7, 1), new Position(8, 1));
        m = testBoard.promote(PieceType.QUEEN);
        assert (m == MoveResult.CHECKMATE);
    }

    @Test
    void notCheckmateViaPromotion_blockedByAnotherPiece() {
        List<Piece> board = new ArrayList<>();
        board.add(new King(PieceColor.WHITE, new Position(1, 5)));
        board.add(new King(PieceColor.BLACK, new Position(8, 5)));
        board.add(new Pawn(PieceColor.BLACK, new Position(8, 6)));
        board.add(new Pawn(PieceColor.BLACK, new Position(7, 6)));
        board.add(new Pawn(PieceColor.BLACK, new Position(7, 4)));
        board.add(new Pawn(PieceColor.BLACK, new Position(7, 5)));
        board.add(new Pawn(PieceColor.WHITE, new Position(7, 1)));
        board.add(new Rook(PieceColor.BLACK, new Position(7, 3)));

        ChessRules testBoard = new ModernChessRules(board);

        MoveResult m;
        testBoard.move(new Position(7, 1), new Position(8, 1));
        m = testBoard.promote(PieceType.QUEEN);
        assert (m == MoveResult.CHECK);
    }

    @Test
    void notCheckmate_otherPieceCanBlock() {
        List<Piece> board = new ArrayList<>();
        board.add(new King(PieceColor.WHITE, new Position(1, 5)));
        board.add(new Queen(PieceColor.WHITE, new Position(1, 4)));
        board.add(new King(PieceColor.BLACK, new Position(8, 5)));
        board.add(new Pawn(PieceColor.BLACK, new Position(8, 6)));
        board.add(new Pawn(PieceColor.BLACK, new Position(7, 6)));
        board.add(new Pawn(PieceColor.BLACK, new Position(8, 4)));
        board.add(new Pawn(PieceColor.BLACK, new Position(7, 4)));
        board.add(new Rook(PieceColor.BLACK, new Position(6, 1)));
        ChessRules testBoard = new ModernChessRules(board);

        MoveResult m;
        m = testBoard.move(new Position(1, 4), new Position(2, 5));
        assert (m == MoveResult.CHECK);
    }

    @Test
    void discoveryCheckmate() {
        List<Piece> board = new ArrayList<>();
        board.add(new King(PieceColor.WHITE, new Position(1, 5)));
        board.add(new Queen(PieceColor.WHITE, new Position(2, 5)));
        board.add(new Bishop(PieceColor.WHITE, new Position(3, 5)));
        board.add(new King(PieceColor.BLACK, new Position(8, 5)));
        board.add(new Pawn(PieceColor.BLACK, new Position(8, 6)));
        board.add(new Pawn(PieceColor.BLACK, new Position(7, 6)));
        board.add(new Pawn(PieceColor.BLACK, new Position(8, 4)));
        board.add(new Pawn(PieceColor.BLACK, new Position(7, 4)));
        ChessRules testBoard = new ModernChessRules(board);

        MoveResult m;
        m = testBoard.move(new Position(3, 5), new Position(4, 6));
        assert (m == MoveResult.CHECKMATE);
    }

    @Test
    void checkmate_twoAttackers() {
        List<Piece> board = new ArrayList<>();
        board.add(new King(PieceColor.WHITE, new Position(1, 5)));
        board.add(new Queen(PieceColor.WHITE, new Position(2, 5)));
        board.add(new Bishop(PieceColor.WHITE, new Position(4, 5)));
        board.add(new King(PieceColor.BLACK, new Position(8, 5)));
        board.add(new Pawn(PieceColor.BLACK, new Position(8, 6)));
        board.add(new Pawn(PieceColor.BLACK, new Position(8, 4)));
        board.add(new Pawn(PieceColor.BLACK, new Position(7, 4)));
        ChessRules testBoard = new ModernChessRules(board);

        MoveResult m;
        m = testBoard.move(new Position(4, 5), new Position(6, 7));
        assert (m == MoveResult.CHECKMATE);
    }

    @Test
    void notCheckmate_twoAttackersButCanEscape() {
        List<Piece> board = new ArrayList<>();
        board.add(new King(PieceColor.WHITE, new Position(1, 5)));
        board.add(new Queen(PieceColor.WHITE, new Position(4, 5)));
        board.add(new Bishop(PieceColor.WHITE, new Position(4, 5)));
        board.add(new King(PieceColor.BLACK, new Position(8, 5)));
        board.add(new Pawn(PieceColor.BLACK, new Position(8, 4)));
        board.add(new Pawn(PieceColor.BLACK, new Position(7, 4)));
        ChessRules testBoard = new ModernChessRules(board);

        MoveResult m;
        m = testBoard.move(new Position(4, 5), new Position(6, 7));
        assert (m == MoveResult.CHECK);
    }

    @Test
    void stalemate() {
        List<Piece> board = new ArrayList<>();
        board.add(new King(PieceColor.WHITE, new Position(1, 5)));
        board.add(new Rook(PieceColor.WHITE, new Position(1, 6)));
        board.add(new Queen(PieceColor.WHITE, new Position(1, 4)));
        board.add(new King(PieceColor.BLACK, new Position(8, 5)));
        board.add(new Pawn(PieceColor.BLACK, new Position(5, 8)));
        board.add(new Pawn(PieceColor.WHITE, new Position(4, 8)));
        ChessRules testBoard = new ModernChessRules(board);

        MoveResult m;
        m = testBoard.move(new Position(1, 4), new Position(6, 4));
        assert (m == MoveResult.STALEMATE);
    }
}
