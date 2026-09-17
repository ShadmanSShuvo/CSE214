public class McqExamEvaluator extends ExamEvaluator {

    @Override
    protected void evaluateAnswers(ExamSubmission submission) {
        McqSubmission mcq = (McqSubmission) submission;
        System.out.println("Correct Answers : " + mcq.getCorrectAnswers());
        System.out.println("Wrong Answers   : " + mcq.getWrongAnswers());
    }

    @Override
    protected double calculateScore(ExamSubmission submission) {
        McqSubmission mcq = (McqSubmission) submission;
        // Correct: +1, Wrong: -0.25, Unanswered: 0
        return (mcq.getCorrectAnswers() * 1.0) - (mcq.getWrongAnswers() * 0.25);
    }

    @Override
    protected double applyAdjustment(ExamSubmission submission, double calculatedScore) {
        // No bonus or adjustment for MCQ; floor at 0
        return Math.max(0.0, calculatedScore);
    }
}
