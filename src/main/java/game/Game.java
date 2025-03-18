package game;

import game.feedbacks.Feedback;
import game.feedbacks.PrisonerFeedback;
import game.pieces.Piece;
import game.pieces.PieceAction;
import game.players.Player;
import game.players.SimplePlayer;

import java.util.Random;

public class Game {
    private final Board board;
    private final Player player1;
    private final Player player2;
    private int round = 0;

    public Game(Player player1, Player player2) {
        board = new Board();
        board.player1 = player1;
        board.player2 = player2;
        this.player1 = player1;
        this.player2 = player2;
    }

    private void increaseRound() {
        this.round++;
    }

    private int getRound() { return round; }

    /**
     * Inicia o jogo.
     */
    public void start() {
        Piece[][] player1Setup = player1.setup(this.board);
        var player1SetupIsValid = this.board.addPlayerSetup(player1Setup, 1);
        Piece[][] player2Setup = player2.setup(this.board);
        var player2SetupIsValid = this.board.addPlayerSetup(player2Setup, 2);

        if (!player1SetupIsValid && !player2SetupIsValid) {
            System.out.println("Jogo concluído por setup inválido de ambos jogadores!");
            System.out.println("Jogo empatado!");
            return;
        } else if (!player1SetupIsValid) {
            System.out.println("Jogo concluído por setup inválido!");
            System.out.println("Jogador " + player2.getPlayerName() + " venceu o jogo!");
            return;
        } else if (!player2SetupIsValid) {
            System.out.println("Jogo concluído por setup inválido!");
            System.out.println("Jogador " + player1.getPlayerName() + " venceu o jogo!");
            return;
        }

        System.out.println("Estado inicial do tabuleiro:");
        System.out.println(board.getFeedback());

        Random rand = new Random();
        boolean actualPlayer = rand.nextBoolean();

        game:
        while (true) {
            System.out.println("Rodada " + this.getRound() + ":");

            for (int i = 0; i < 2; i++) {
                Feedback roundFeedback = null;
                if (actualPlayer) {
                    // Jogada do Player1
                    PieceAction action = player1.play(board.getHiddenView(player1.getPlayerName()));
                    roundFeedback = board.executeAction(action);
                    System.out.println("Player1: " + roundFeedback.getMessage());
                    System.out.println(board.getFeedback());
                } else {
                    // Jogada do Player2
                    PieceAction action = player2.play(board.getHiddenView(player2.getPlayerName()));
                    roundFeedback = board.executeAction(action);
                    System.out.println("Player2: " + roundFeedback.getMessage());
                    System.out.println(board.getFeedback());
                }

                if (roundFeedback instanceof PrisonerFeedback) {
                    String playerName = actualPlayer ? player1.getPlayerName() : player2.getPlayerName();

                    System.out.println("Jogo concluído com sucesso!!!");
                    System.out.println("Parabéns ao jogador " + playerName + "!!!");
                    break game;
                }

                actualPlayer = !actualPlayer;
                Feedback actualState = board.isGameFinished();
                if (actualState != null){
                    System.out.println(actualState.getMessage());
                    break game;
                }
            }
            this.increaseRound();
        }
    }

    public static void main(String[] args) {

        SimplePlayer player1 = new SimplePlayer("Player1");
        SimplePlayer player2 = new SimplePlayer("Player2");

        Game game = new Game(player1, player2);
        game.start();
    }
}
