package Game.Pieces.PieceInfo;

public enum PieceColor {
    NONE,
    WHITE,
    BLACK;

    public PieceColor getOppositeColor() {
        switch (this) {
            case WHITE:
                return BLACK;
            case BLACK:
                return WHITE;
        }
        return NONE;
    }
}
