package TextUI;

import Game.*;
import Game.GameRules.ChessRules;
import Game.GameRules.BoardFactory;
import Game.GameRules.ModernChessRules;
import Game.Pieces.PieceInfo.PieceType;

import java.util.Scanner;

public class TextChessEngine {

    private ChessRules board;

    public void startGame() {
        board = new ModernChessRules(BoardFactory.createDefaultBoard());
        Scanner scanner = new Scanner(System.in);
        String stringInput;
        MoveResult m = MoveResult.GAME_START;
        while (m != MoveResult.STALEMATE && m != MoveResult.CHECKMATE) {
            drawGame(m);
            System.out.print("Next move: ");
            stringInput = scanner.nextLine();
            m = processInput(stringInput);
        }
        drawGame(m);
    }

    private MoveResult processInput(String input) {
        input = input.toLowerCase().trim();
        String[] splits = input.split(" ");
        if (splits.length > 2) {
            return MoveResult.INVALID_STRING;
        } else if (splits.length == 2) {
            Position[] pos = new Position[2];
            for (int i = 0; i < 2; i++) {
                if (splits[i].length() != 2) {
                    return MoveResult.INVALID_STRING;
                }
                int x, y;
                if (Character.isDigit(splits[i].charAt(0)) && !Character.isDigit(splits[i].charAt(1))) {
                    x = Character.getNumericValue(splits[i].charAt(0));
                    y = splits[i].charAt(1) - 'a' + 1;
                } else if (Character.isDigit(splits[i].charAt(1)) && !Character.isDigit(splits[i].charAt(0))) {
                    x = Character.getNumericValue(splits[i].charAt(1));
                    y = splits[i].charAt(0) - 'a' + 1;
                } else {
                    return MoveResult.INVALID_STRING;
                }
                pos[i] = new Position(x, y);
            }
            return board.move(pos[0], pos[1]);
        } else {
            PieceType p;
            switch (splits[0]) {
                case "queen", "q" -> p = PieceType.QUEEN;
                case "knight", "n" -> p = PieceType.KNIGHT;
                case "rook", "r" -> p = PieceType.ROOK;
                case "bishop", "b" -> p = PieceType.BISHOP;
                default -> {
                    return MoveResult.INVALID_STRING;
                }
            }
            return board.promote(p);
        }
    }

    private void drawGame(MoveResult lastMoveResult) {
        System.out.println("------------------------------");
        CreateBoard.printHeader(board.getCurrentTurn());
        CreateBoard.printBoard(board.getVisualBoard());
        CreateBoard.printStatus(lastMoveResult, board.getCurrentTurn());


    }
}
