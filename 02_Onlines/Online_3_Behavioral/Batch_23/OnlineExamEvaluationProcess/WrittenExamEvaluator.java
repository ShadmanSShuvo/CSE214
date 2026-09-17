public class WrittenExamEvaluator extends ExamEvaluator {

    @Override
    protected void evaluateAnswers(ExamSubmission submission) {
        // Written answer scripts are manually pre-evaluated
    }

    @Override
    protected double calculateScore(ExamSubmission submission) {
        WrittenSubmission written = (WrittenSubmission) submission;
        return written.getEvaluatedScore();
    }

    @Override
    protected void printCalculatedScore(double calculatedScore) {
        System.out.printf("Evaluated Score = %.0f\n", calculatedScore);
    }

    @Override
    protected double applyAdjustment(ExamSubmission submission, double calculatedScore) {
        WrittenSubmission written = (WrittenSubmission) submission;
        if (written.isModerationEnabled()) {
            System.out.println("Moderation\t= Enabled");
            System.out.println("Moderation Bonus = +5");
            return calculatedScore + 5.0;
        }
        return calculatedScore;
    }
}
