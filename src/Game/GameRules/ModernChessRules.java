package Game.GameRules;

import Game.Direction;
import Game.MoveResult;
import Game.Pieces.*;
import Game.Pieces.PieceInfo.PieceColor;
import Game.Pieces.PieceInfo.PieceType;
import Game.Pieces.PieceInfo.PieceTypeColor;
import Game.Position;

import java.util.List;

public class ModernChessRules implements ChessRules {

    public static final int BOARD_LOWER_BOUND = 1;
    private static final int BOARD_UPPER_BOUND = 8;
    public static final int CASTLING_KING_ORIGIN_COLUMN = 5;
    public static final int CASTLING_KING_LEFT_Y = 3;
    public static final int CASTLING_KING_RIGHT_Y = 7;




    private final BoardState currentBoardState;
    private boolean currentPlayerIsInCheck;

    public ModernChessRules(List<Piece> startingPieceLayout) {
        currentBoardState = new BoardState(startingPieceLayout);
        currentPlayerIsInCheck = false;
    }

    @Override
    public MoveResult move(Position from, Position to) {
        Piece movingPiece = currentBoardState.getPiece(from);
        MoveResult currentMoveResult = checkMoveValidity(movingPiece, to);
        if(currentMoveResult != MoveResult.VALID){
            return currentMoveResult;
        } else if (isCastling(from,to)) {
            executeCastling(from, to);
            return findValidMoveResult(movingPiece.getColor());
        } else {
            currentMoveResult = executeMovement(movingPiece, from,to);
            if(currentMoveResult != MoveResult.VALID){
                return currentMoveResult;
            }
        }
        return finishMovement(movingPiece, to);
    }


    private MoveResult checkMoveValidity(Piece movingPiece, Position to) {
        if (!isInBounds(to)) {
            return MoveResult.INVALID_OUT_OF_BOUNDS;
        } else if (movingPiece == null) {
            return MoveResult.INVALID_NO_PIECE;
        } else if (movingPiece.getColor() != currentBoardState.getCurrentTurn()) {
            return MoveResult.INVALID_WRONG_COLOR;
        } else if (!movingPiece.isValidMove(this, to)) {
            return MoveResult.INVALID;
        } else {
            return MoveResult.VALID;
        }
    }

    private void executeCastling(Position from, Position to) {
        Direction castlingDirection = Direction.findDirection(from, to);
        Position rookFrom = new Position(from.x(),
                castlingDirection == Direction.L ? BOARD_LOWER_BOUND : BOARD_UPPER_BOUND);
        Position rookTo = new Position(to.x(), to.y() - castlingDirection.y);
        currentBoardState.movePiece(from, to);
        currentBoardState.movePiece(rookFrom, rookTo);
        currentBoardState.finishMove(to);
        currentBoardState.finishMove(rookTo);
        currentBoardState.changeTurn();
    }

    private MoveResult executeMovement(Piece movingPiece, Position from, Position to) {
        currentBoardState.movePiece(from, to);
        if(isEnPassant(to, movingPiece.getColor())){
            currentBoardState.enPassantCapture(new Position(from.x(), to.y()));
        }
        if (movingPiece.getType() == PieceType.KING ?
                isPositionUnderAttack(to, movingPiece.getColor()) : isInCheck(currentBoardState.getCurrentTurn())){
            currentBoardState.revertLastMove();
            return MoveResult.INVALID_CHECKED;
        }

        return MoveResult.VALID;
    }

    private MoveResult finishMovement(Piece movingPiece, Position to) {
        currentBoardState.finishMove(to);
        if(currentPlayerIsInCheck){
            currentPlayerIsInCheck = false;
        }
        if(currentBoardState.getPieceType(to) == PieceType.PAWN
                && to.x() == (movingPiece.getColor() == PieceColor.WHITE ? BOARD_UPPER_BOUND : BOARD_LOWER_BOUND)) {
            currentBoardState.setPromotionTarget(movingPiece);
            return MoveResult.PROMOTION;
        }
        currentBoardState.changeTurn();
        currentPlayerIsInCheck = isInCheck(currentBoardState.getCurrentTurn());
        return findValidMoveResult(currentBoardState.getCurrentTurn());
    }

    @Override
    public boolean isInCheck(PieceColor colorBeingAttacked) {
        Piece king = currentBoardState.getKing(colorBeingAttacked);
        return isPositionUnderAttack(king.getCurrentPos(), colorBeingAttacked);
    }

    private MoveResult findValidMoveResult(PieceColor movingColor) {
        if (!currentPlayerIsInCheck) {
            return isStalemate(movingColor);
        }
        Position kingPosition = currentBoardState.getKing(movingColor).getCurrentPos();
        for (Direction d : Direction.values()) {
            if (d == Direction.N){
                continue;
            }
            Position positionAroundKing = new Position(kingPosition.x() + d.x, kingPosition.y() + d.y);
            if(isInBounds(positionAroundKing)
                && currentBoardState.getPieceColor(positionAroundKing) != movingColor
                && !isPositionUnderAttack(positionAroundKing,movingColor)){
                return MoveResult.CHECK;
            }
        }
        return findCheckmate(movingColor);
    }

    private MoveResult findCheckmate(PieceColor color) {
        Piece attackingPiece = null;
        Position kingPosition = currentBoardState.getKing(color).getCurrentPos();
        for (Piece piece :
                currentBoardState.getPieceCollection()) {
            if (piece.getColor() == color.getOppositeColor() && piece.isValidMove(this, kingPosition)) {
                if (attackingPiece != null) { //2 pieces attacking the king = checkmate
                    return MoveResult.CHECKMATE;
                }
                attackingPiece = piece;
            }
        }
        assert attackingPiece != null;
        if (isPositionUnderAttack(attackingPiece.getCurrentPos(), attackingPiece.getColor())) {
            if (Math.abs(attackingPiece.getCurrentPos().x() - kingPosition.x()) <= BOARD_LOWER_BOUND
                    && Math.abs(attackingPiece.getCurrentPos().y() - kingPosition.y()) <= BOARD_LOWER_BOUND) {
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
                currentBoardState.getPieceCollection()) {
            if (p.getColor() == color && p.hasValidMove(this)) {
                return MoveResult.VALID;
            }
        }
        return MoveResult.STALEMATE;
    }

    @Override
    public MoveResult promote(PieceType promotingTo) {
        if (!currentBoardState.hasPromotionTarget()) {
            return MoveResult.NO_PROMOTION;
        }
        boolean successfulPromotion = currentBoardState.promote(promotingTo);
        if(!successfulPromotion){
            return MoveResult.INCORRECT_PROMOTION;
        }
        currentBoardState.changeTurn();
        currentPlayerIsInCheck = isInCheck(currentBoardState.getCurrentTurn());
        return findValidMoveResult(currentBoardState.getCurrentTurn());
    }

    @Override
    public boolean isEnPassant(Position pos, PieceColor color) {
        if (pos.x() != (color == PieceColor.WHITE ? 6 : 3)) {
            return false;
        }
        Piece targetPiece = currentBoardState.getPiece(new Position(color == PieceColor.WHITE ? 5 : 4, pos.y()));
        if (targetPiece == null) {
            return false;
        }
        if (targetPiece.getColor() == color || targetPiece.getType() != PieceType.PAWN) {
            return false;
        }
        return ((Pawn) targetPiece).canEnPassant();
    }

    @Override
    public boolean isInBounds(Position pos) {
        return (pos.x() >= BOARD_LOWER_BOUND) && (pos.x() <= BOARD_UPPER_BOUND)
                && (pos.y() >= BOARD_LOWER_BOUND) && (pos.y() <= BOARD_UPPER_BOUND);
    }


    public boolean isPositionUnderAttack(Position pos, PieceColor colorBeingAttacked, boolean ignoreKing) {
        for (Piece piece :
                currentBoardState.getPieceCollection()) {
            if (piece.getColor() == colorBeingAttacked) {
                continue;
            }
            if (ignoreKing && piece.getType() == PieceType.KING) {
                continue;
            }
            if (piece.isValidMove(this, pos, true)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isPositionUnderAttack(Position pos, PieceColor colorBeingAttacked) {
        return isPositionUnderAttack(pos, colorBeingAttacked, false);
    }

    @Override
    public boolean hasRookMoved(PieceColor color, Direction dir) {
        if (dir != Direction.R && dir != Direction.L) {
            return false;
        }

        Position pos = new Position(color == PieceColor.WHITE ? BOARD_LOWER_BOUND : BOARD_UPPER_BOUND,
                dir == Direction.L ? BOARD_LOWER_BOUND : BOARD_UPPER_BOUND);
        Piece cornerPiece = currentBoardState.getPiece(pos);
        if (cornerPiece == null) {
            return false;
        }
        if (cornerPiece.getType() != PieceType.ROOK) {
            return false;
        }
        return ((Rook) cornerPiece).hasMoved();
    }

    @Override
    public PieceTypeColor[][] getVisualBoard() {
        PieceTypeColor[][] visualBoard = new PieceTypeColor[BOARD_UPPER_BOUND][BOARD_UPPER_BOUND];
        for (int x = 0; x < BOARD_UPPER_BOUND; x++) {
            for (int y = 0; y < BOARD_UPPER_BOUND; y++) {
                Position pos = new Position(x + 1, y + 1);
                currentBoardState.getPieceTypeColor(pos);
                if (currentBoardState.getPiece(pos) != null) {
                    visualBoard[x][y] = getPieceTypeColor(pos);
                } else {
                    visualBoard[x][y] = new PieceTypeColor(PieceType.NONE, PieceColor.NONE);
                }
            }
        }
        return visualBoard;
    }

    private boolean isCastling(Position from, Position to) {
        if (from.equals(BOARD_LOWER_BOUND, CASTLING_KING_ORIGIN_COLUMN)) {
            return to.equals(BOARD_LOWER_BOUND, CASTLING_KING_LEFT_Y) || to.equals(BOARD_LOWER_BOUND, CASTLING_KING_RIGHT_Y);
        }
        if (from.equals(BOARD_UPPER_BOUND, CASTLING_KING_ORIGIN_COLUMN)) {
            return to.equals(BOARD_UPPER_BOUND, CASTLING_KING_LEFT_Y) || to.equals(BOARD_UPPER_BOUND, CASTLING_KING_RIGHT_Y);
        }
        return false;
    }

    @Override
    public PieceColor getCurrentTurn() {
        return currentBoardState.getCurrentTurn();
    }

    @Override
    public PieceColor getPieceColor(Position pos) {
        return currentBoardState.getPieceColor(pos);
    }

    @Override
    public PieceType getPieceType(Position pos) {
        return currentBoardState.getPieceType(pos);
    }

    @Override
    public PieceTypeColor getPieceTypeColor(Position pos) {
        return currentBoardState.getPieceTypeColor(pos);
    }
}
