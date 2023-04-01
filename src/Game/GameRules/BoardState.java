package Game.GameRules;

import Game.Direction;
import Game.Pieces.*;
import Game.Pieces.PieceInfo.PieceColor;
import Game.Pieces.PieceInfo.PieceType;
import Game.Pieces.PieceInfo.PieceTypeColor;
import Game.Position;

import java.util.*;

public class BoardState {

    private static final int BOARD_LOWER_BOUND = 1;
    private static final int BOARD_UPPER_BOUND = 8;
    private final Map<Position, Piece> piecesInGame;
    private PieceColor currentTurn;
    private MovementInformation lastMove;
    private Piece promotionTarget;
    @SuppressWarnings("all") //not used, but leaving here for compatibility with a possible UI feature
    private final List<PieceTypeColor> capturedPieces;

    public BoardState(List<Piece> startingPieceLayout){
        piecesInGame = new HashMap<>();
        startingPieceLayout.forEach(piece -> piecesInGame.put(piece.getCurrentPos(),piece));
        currentTurn = PieceColor.WHITE;
        capturedPieces = new LinkedList<>();
    }

    public void changeTurn(){
        currentTurn = currentTurn.getOppositeColor();
    }

    public void movePiece(Position from, Position to){
        Piece capturedPiece = piecesInGame.get(to);
        Piece movingPiece = piecesInGame.remove(from);
        piecesInGame.put(to, movingPiece);
        if(capturedPiece != null){
            capturedPieces.add(capturedPiece.getTypeColor());
            lastMove = new MovementInformation(MovementInformation.MoveType.CAPTURE, movingPiece,
                    from, to, capturedPiece);
        } else {
            lastMove = new MovementInformation(MovementInformation.MoveType.MOVE, movingPiece,
                    from, to, null);
        }
    }

    public void finishMove(Position piecePosition){
        piecesInGame.get(piecePosition).afterMove(piecePosition);
    }

    public void enPassantCapture(Position pawnPosition){
        capturedPieces.add(piecesInGame.remove(pawnPosition).getTypeColor());
    }

    public void setPromotionTarget(Piece target){
        promotionTarget = target;
    }

    public boolean hasPromotionTarget(){
        return promotionTarget != null;
    }

    public boolean promote(PieceType promotionTo){
        switch (promotionTo) {
            case  ROOK ->
                    piecesInGame.replace(promotionTarget.getCurrentPos(),
                            new Rook(promotionTarget.getColor(), promotionTarget.getCurrentPos()));
            case BISHOP ->
                    piecesInGame.replace(promotionTarget.getCurrentPos(),
                            new Bishop(promotionTarget.getColor(), promotionTarget.getCurrentPos()));
            case KNIGHT ->
                    piecesInGame.replace(promotionTarget.getCurrentPos(),
                            new Knight(promotionTarget.getColor(), promotionTarget.getCurrentPos()));
            case QUEEN ->
                    piecesInGame.replace(promotionTarget.getCurrentPos(),
                            new Queen(promotionTarget.getColor(), promotionTarget.getCurrentPos()));
            default -> {
                return false;
            }
        }

        promotionTarget = null;
        return true;
    }

    public void revertLastMove(){
        switch (lastMove.moveType){
            case MOVE -> revertMove();
            case CAPTURE -> revertCapture();
            case EN_PASSANT -> revertEnPassant();
            case CASTLING -> revertCastling();
            case PROMOTION -> revertPromotion();
        }
    }

    private void revertMove(){
        piecesInGame.remove(lastMove.to());
        piecesInGame.put(lastMove.from(), lastMove.movingPiece());
    }

    private void revertCapture(){
        piecesInGame.put(lastMove.to(), lastMove.secondPiece());
        piecesInGame.put(lastMove.from(), lastMove.movingPiece());
    }

    private void revertEnPassant(){
        piecesInGame.remove(lastMove.to());
        piecesInGame.put(lastMove.from(), lastMove.movingPiece());
        piecesInGame.put(new Position(lastMove.from().x(),lastMove.to().y()), lastMove.secondPiece());
    }

    private void revertCastling(){
        piecesInGame.remove(lastMove.to());
        piecesInGame.put(lastMove.from(), lastMove.movingPiece());
        Direction castlingDirection = Direction.findDirection(lastMove.from, lastMove.to);
        Position rookFrom = new Position(lastMove.from.x(),
                castlingDirection == Direction.L ? BOARD_LOWER_BOUND : BOARD_UPPER_BOUND);
        Position rookTo = new Position(lastMove.to().x(), lastMove.to().y() - castlingDirection.y);
        piecesInGame.remove(rookTo);
        piecesInGame.put(rookFrom, lastMove.secondPiece());
    }

    private void revertPromotion(){
        piecesInGame.remove(lastMove.to());
        piecesInGame.put(lastMove.from(), lastMove.secondPiece());
    }

    public Piece getPiece(Position position){
        return piecesInGame.get(position);
    }

    public PieceColor getPieceColor(Position position){
        if(getPiece(position) == null){
            return PieceColor.NONE;
        }
        return getPiece(position).getColor();
    }

    public PieceType getPieceType(Position position){
        if(getPiece(position) == null){
            return PieceType.NONE;
        }
        return getPiece(position).getType();
    }

    public PieceTypeColor getPieceTypeColor(Position position){
        return new PieceTypeColor(getPieceType(position), getPieceColor(position));
    }

    public PieceColor getCurrentTurn() {
        return currentTurn;
    }

    public King getKing(PieceColor color){
        for (Piece piece:
             piecesInGame.values()) {
            if (piece.getType() == PieceType.KING && piece.getColor() == color){
                return (King) piece;
            }
        }
        return null; //will never happen
    }

    public Collection<Piece> getPieceCollection(){
        return piecesInGame.values();
    }

    private record MovementInformation(MoveType moveType, Piece movingPiece, Position from, Position to,
                                       Piece secondPiece){
        enum MoveType {MOVE, CAPTURE, EN_PASSANT, CASTLING, PROMOTION}
    }

}
