package game.feedbacks;

import game.pieces.OpponentPiece;

public class ConvertFeedbackToEnemy {
    public static Feedback convert(Feedback feedback) {
        if (feedback instanceof InvalidMoveFeedback) {
            return new InvalidMoveFeedback();
        }

        if (feedback instanceof MoveFeedback) {
            return new MoveFeedback(new OpponentPiece(feedback.getPiece()));
        }

        return feedback; // LandmineFeedback, LandMineDeactivationFeedback, PrisonerFeedback, AttackFeedback, EqualStrengthFeedback, DefeatFeedback
    }
}
