package TextUI;

import Game.MoveResult;
import Game.Pieces.PieceInfo.PieceColor;
import Game.Pieces.PieceInfo.PieceTypeColor;

public class CreateBoard {

    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_BLACK = "\u001B[30m";
    static final String ANSI_WHITE = "\u001B[39m";


    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";

    public static void printHeader(PieceColor currentTurn) {
        System.out.println("Current turn: " + (currentTurn == PieceColor.WHITE ? "White" : "Black"));
    }

    public static void printBoard(PieceTypeColor[][] table) {
        System.out.println("   a   b   c   d   e   f   g   h  ");
        for (int x = table.length - 1; x >= 0; x--) {
            System.out.print(ANSI_WHITE + (x + 1) + " ");
            for (int y = 0; y < table[x].length; y++) {
                System.out.print((x + y) % 2 == 1 ? ANSI_BLUE_BACKGROUND : ANSI_CYAN_BACKGROUND);
                System.out.print(" " + ANSI_BLACK + getChessPieceRepresentation(table[x][y]) + " ");
                System.out.print(ANSI_RESET);
            }
            System.out.println();
        }
        System.out.println(ANSI_RESET);
    }

    public static void printStatus(MoveResult lastMove, PieceColor currentTurn) {
        String message = "";
        switch (lastMove) {
            case VALID -> {
            }
            case INVALID -> {
                message += "Invalid move";
            }
            case INVALID_STRING -> {
                message += "Move input invalid";
            }
            case INVALID_NO_PIECE -> {
                message += "No piece on the specified square";
            }
            case INVALID_WRONG_COLOR -> {
                message += "Wrong piece color";
            }
            case INVALID_OUT_OF_BOUNDS -> {
                message += "Move out of board bounds";
            }
            case INVALID_CHECKED -> {
                message += "Move doesn't clear check";
            }
            case CHECK -> {
                message += (currentTurn == PieceColor.WHITE ? "White" : "Black") + " is currently checked";
            }
            case CHECKMATE -> {
                message += "Checkmate! " + (currentTurn == PieceColor.WHITE ? "White" : "Black") + " wins";
            }
            case STALEMATE -> {
                message += "Stalemate!" + (currentTurn == PieceColor.WHITE ? "White" : "Black") + " has no valid moves";
            }
            case PROMOTION -> {
                message += "Select which piece the pawn will promote to";
            }
            case NO_PROMOTION -> {
                message += "No piece to be promoted currently"; //this message should never happen
            }
            case INCORRECT_PROMOTION -> {
                message += "Cannot be promoted to this piece"; //this message should never happen
            }
            case GAME_START -> {
                message += "Input format: <moving piece position> <destination position>" +
                        "\nExample: 2e 4e (moves pawn on 2e to 4e)";
            }
        }
        System.out.println(message);
    }

    private static String getChessPieceRepresentation(PieceTypeColor piece) {
        if (piece.color() == PieceColor.NONE) {
            return "  ";
        }

        String txtPiece = "";

        switch (piece.color()) {
            case WHITE -> txtPiece = "w";
            case BLACK -> txtPiece = "b";

        }
        switch (piece.type()) {
            case PAWN -> txtPiece += "P";
            case ROOK -> txtPiece += "R";
            case KNIGHT -> txtPiece += "N";
            case BISHOP -> txtPiece += "B";
            case QUEEN -> txtPiece += "Q";
            case KING -> txtPiece += "K";
        }
        return txtPiece;
    }
}
