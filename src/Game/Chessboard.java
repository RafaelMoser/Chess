package Game;

import Game.Pieces.*;
import Game.Pieces.PieceInfo.PieceColor;
import Game.Pieces.PieceInfo.PieceType;
import Game.Pieces.PieceInfo.PieceTypeColor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Chessboard implements Board {

    private PieceColor currentTurn;
    private boolean playerIsInCheck;
    private King whiteKing, blackKing;
    private final Map<Position, Piece> pieces;
    private List<PieceTypeColor> takenPieces;
    private Pawn promotionTarget;

    public Chessboard(Map<Position, Piece> board) {
        pieces = new HashMap<>();
        pieces.putAll(board);
        for (Piece p : pieces.values()) {
            if (p.getType() == PieceType.KING) {
                if (p.getColor() == PieceColor.WHITE) {
                    whiteKing = (King) p;
                }
                if (p.getColor() == PieceColor.BLACK) {
                    blackKing = (King) p;
                }
            }
        }
        currentTurn = PieceColor.WHITE;
        playerIsInCheck = false;
        takenPieces = new LinkedList<>();
        promotionTarget = null;
    }

    @Override
    public MoveResult move(Position from, Position to) {
        Piece movingPiece = getPiece(from);
        MoveResult m = checkMoveValidity(movingPiece, to);
        if (m != MoveResult.VALID) {
            return m;
        }
        if (isCastling(from, to)) {
            executeCastling(movingPiece, to);
            return findValidMoveResult(movingPiece.getColor());
        } else {
            m = executeMovement(movingPiece, from, to);
            if (m != MoveResult.VALID) {
                return m;
            }
        }
        return finishMovement(movingPiece, to);
    }

    private MoveResult checkMoveValidity(Piece movingPiece, Position to) {
        if (!isInBounds(to)) {
            return MoveResult.INVALID_OUT_OF_BOUNDS;
        }
        if (movingPiece == null) {
            return MoveResult.INVALID_NO_PIECE;
        }

        if (movingPiece.getColor() != currentTurn) {
            return MoveResult.INVALID_WRONG_COLOR;
        }

        if (!movingPiece.isValidMove(this, to)) {
            return MoveResult.INVALID;
        }
        return MoveResult.VALID;
    }

    private void executeCastling(Piece movingPiece, Position to) {
        Direction d = Direction.findDirection(movingPiece.getCurrentPos(), to);
        Piece rook = pieces.remove(new Position(movingPiece.getColor() == PieceColor.WHITE ? 1 : 8, d == Direction.L ? 1 : 8));
        Position rookPos = new Position(to.x(), to.y() - d.y);
        pieces.remove(movingPiece.getCurrentPos());
        pieces.put(to, movingPiece);
        pieces.put(rookPos, rook);

        rook.afterMove(rookPos);
        movingPiece.afterMove(to);
        currentTurn = currentTurn.getOppositeColor();
    }

    private MoveResult executeMovement(Piece movingPiece, Position from, Position to) {
        Piece takenPiece = null;
        if (pieces.containsKey(to)) {
            takenPiece = pieces.remove(to);
        } else if (isEnPassant(to, movingPiece.getColor())) {
            takenPiece = pieces.remove(new Position(from.x(), to.y()));
        }

        pieces.remove(from);
        pieces.put(to, movingPiece);

        if (movingPiece.getType() == PieceType.KING ? isPositionUnderAttack(to, movingPiece.getColor()) : isInCheck(currentTurn)) {
            pieces.remove(to);
            pieces.put(from, movingPiece);

            if (takenPiece != null) {
                if (isEnPassant(to, movingPiece.getColor())) {
                    pieces.put(new Position(from.x(), to.y()), takenPiece);
                } else {
                    pieces.put(to, takenPiece);
                }
            }
            return MoveResult.INVALID_CHECKED;
        }
        if (takenPiece != null) {
            takenPieces.add(new PieceTypeColor(takenPiece.getType(), takenPiece.getColor()));
        }
        return MoveResult.VALID;
    }

    private MoveResult finishMovement(Piece movingPiece, Position to) {
        movingPiece.afterMove(to);

        if (playerIsInCheck) {
            playerIsInCheck = false;
        }

        if (movingPiece.getType() == PieceType.PAWN && to.x() == (movingPiece.getColor() == PieceColor.WHITE ? 8 : 1)) {
            promotionTarget = (Pawn) movingPiece;
            return MoveResult.PROMOTION;
        }
        currentTurn = currentTurn.getOppositeColor();

        playerIsInCheck = isInCheck(currentTurn);
        return findValidMoveResult(currentTurn);
    }

    @Override
    public boolean isInCheck(PieceColor color) {
        if (color == PieceColor.WHITE) {
            return isPositionUnderAttack(whiteKing.getCurrentPos(), PieceColor.WHITE);
        }
        return isPositionUnderAttack(blackKing.getCurrentPos(), PieceColor.BLACK);
    }

    private MoveResult findValidMoveResult(PieceColor color) {
        if (!playerIsInCheck) {
            return isStalemate(color);
        }
        Position kingPosition = (color == PieceColor.WHITE) ? whiteKing.getCurrentPos() : blackKing.getCurrentPos();
        for (Direction d : Direction.values()) {
            if (d == Direction.N) continue;
            Position p = new Position(kingPosition.x() + d.x, kingPosition.y() + d.y);
            if (!isInBounds(p)) continue;
            if (getPieceColor(p) == color) continue;
            if (!isPositionUnderAttack(p, color))
                return MoveResult.CHECK;
        }
        return findCheckmate(color);
    }

    private MoveResult findCheckmate(PieceColor color) {
        Piece attackingPiece = null;
        Position kingPosition = (color == PieceColor.WHITE) ? whiteKing.getCurrentPos() : blackKing.getCurrentPos();
        for (Piece p :
                pieces.values()) {
            if (p.getColor() == color.getOppositeColor() && p.isValidMove(this, kingPosition)) {
                if (attackingPiece != null) {
                    return MoveResult.CHECKMATE;
                }
                attackingPiece = p;
            }
        }
        if (isPositionUnderAttack(attackingPiece.getCurrentPos(), attackingPiece.getColor())) {
            if (Math.abs(attackingPiece.getCurrentPos().x() - kingPosition.x()) <= 1
                    && Math.abs(attackingPiece.getCurrentPos().y() - kingPosition.y()) <= 1) {
                return isPositionUnderAttack(attackingPiece.getCurrentPos(), attackingPiece.getColor()) ? MoveResult.CHECKMATE : MoveResult.CHECK;
            }
            return MoveResult.CHECK;
        }
        if (attackingPiece.getType() == PieceType.KNIGHT) {
            return MoveResult.CHECKMATE;
        }
        Direction d = Direction.findDirection(attackingPiece.getCurrentPos(), kingPosition);
        Position pos = new Position(attackingPiece.getCurrentPos().x() + d.x, attackingPiece.getCurrentPos().y() + d.y);
        while (!pos.equals(kingPosition)) {
            if (isPositionUnderAttack(pos, attackingPiece.getColor(), true)) {
                return MoveResult.CHECK;
            }
            pos = new Position(pos.x() + d.x, pos.y() + d.y);
        }
        return MoveResult.CHECKMATE;
    }

    private MoveResult isStalemate(PieceColor color) {
        for (Piece p :
                pieces.values()) {
            if (p.getColor() == color && p.hasValidMove(this)) {
                return MoveResult.VALID;
            }
        }
        return MoveResult.STALEMATE;
    }

    @Override
    public MoveResult promote(PieceType to) {
        if (promotionTarget == null) {
            return MoveResult.NO_PROMOTION;
        }
        switch (to) {
            case ROOK ->
                    pieces.replace(promotionTarget.getCurrentPos(), new Rook(promotionTarget.color, promotionTarget.getCurrentPos()));
            case BISHOP ->
                    pieces.replace(promotionTarget.getCurrentPos(), new Bishop(promotionTarget.color, promotionTarget.getCurrentPos()));
            case KNIGHT ->
                    pieces.replace(promotionTarget.getCurrentPos(), new Knight(promotionTarget.color, promotionTarget.getCurrentPos()));
            case QUEEN ->
                    pieces.replace(promotionTarget.getCurrentPos(), new Queen(promotionTarget.color, promotionTarget.getCurrentPos()));
            default -> {
                return MoveResult.INCORRECT_PROMOTION;
            }
        }
        currentTurn = currentTurn.getOppositeColor();
        promotionTarget = null;

        playerIsInCheck = isInCheck(currentTurn);
        return findValidMoveResult(currentTurn);
    }

    @Override
    public boolean isEnPassant(Position pos, PieceColor color) {

        if (pos.x() != (color == PieceColor.WHITE ? 6 : 3)) {
            return false;
        }

        Piece targetPiece = getPiece(new Position(color == PieceColor.WHITE ? 5 : 4, pos.y()));

        if (targetPiece == null) {
            return false;
        }

        if (targetPiece.getColor() == color || targetPiece.getType() != PieceType.PAWN) {
            return false;
        }

        return ((Pawn) targetPiece).canEnPassant();
    }

    @Override
    public PieceColor getPieceColor(Position pos) {
        if (getPiece(pos) == null) {
            return PieceColor.NONE;
        }
        return getPiece(pos).getColor();
    }

    @Override
    public PieceType getPieceType(Position pos) {
        if (getPiece(pos) == null) {
            return PieceType.NONE;
        }
        return getPiece(pos).getType();
    }

    @Override
    public boolean isInBounds(Position pos) {
        return (pos.x() >= 1) && (pos.x() <= 8) && (pos.y() >= 1) && (pos.y() <= 8);
    }

    @Override
    public PieceTypeColor getPieceTypeColor(Position pos) {
        return new PieceTypeColor(getPieceType(pos), getPieceColor(pos));
    }

    public boolean isPositionUnderAttack(Position pos, PieceColor attackedColor, boolean ignoreKing) {
        for (Piece p :
                pieces.values()) {
            if (p.getColor() == attackedColor) {
                continue;
            }
            if (ignoreKing && p.getType() == PieceType.KING) {
                continue;
            }
            if (p.isValidMove(this, pos, true)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isPositionUnderAttack(Position pos, PieceColor color) {
        return isPositionUnderAttack(pos, color, false);
    }

    @Override
    public boolean hasRookMoved(PieceColor color, Direction dir) {
        if (dir != Direction.R && dir != Direction.L) {
            return false;
        }

        Position pos = new Position(color == PieceColor.WHITE ? 1 : 8, dir == Direction.L ? 1 : 8);

        if (!pieces.containsKey(pos)) {
            return false;
        }
        if (getPieceType(pos) != PieceType.ROOK) {
            return false;
        }
        return ((Rook) getPiece(pos)).hasMoved();
    }

    @Override
    public PieceTypeColor[][] getVisualBoard() {
        PieceTypeColor[][] visualBoard = new PieceTypeColor[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Position pos = new Position(x + 1, y + 1);
                if (pieces.containsKey(pos)) {
                    visualBoard[x][y] = getPieceTypeColor(pos);
                } else {
                    visualBoard[x][y] = new PieceTypeColor(PieceType.NONE, PieceColor.NONE);
                }
            }
        }
        return visualBoard;
    }

    private Piece getPiece(Position pos) {
        return pieces.get(pos);
    }

    private boolean isCastling(Position from, Position to) {
        if (from.equals(1, 5)) {
            return to.equals(1, 3) || to.equals(1, 7);
        }
        if (from.equals(8, 5)) {
            return to.equals(8, 3) || to.equals(8, 7);
        }
        return false;
    }

    @Override
    public PieceColor getCurrentTurn() {
        return currentTurn;
    }
}
