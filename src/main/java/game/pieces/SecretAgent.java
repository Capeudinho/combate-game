package game.pieces;

import game.Board;
import game.feedbacks.AttackFeedback;
import game.feedbacks.Feedback;

/**
 * Regra especial: único que pode eliminar o General se atacar primeiro.
 */
public class SecretAgent extends Piece {

    public SecretAgent(String player, Board board) {
        super(1, player, board);
    }

    public SecretAgent(SecretAgent original, Board newBoard) {
        super(original, newBoard);
    }

    @Override
    public Feedback fight(Piece piece) {
        if (piece.getClass().getSimpleName().equals("Colonel")) {

            int initialPosX = this.posX;
            int initialPosY = this.posY;

            board.setPiece(initialPosX, initialPosY, null);
            board.setPiece(piece.posX, piece.posY, this);
            this.setPosition(piece.posX, piece.posY);
            return new AttackFeedback(this, piece, initialPosX, initialPosY, piece.posX, piece.posY);
        }

        return super.fight(piece);
    };

    @Override
    public String getRepresentation() {
        return "AS";
    }

    @Override
    public SecretAgent copy(Board board) {
        return new SecretAgent(this, board);
    }
}
