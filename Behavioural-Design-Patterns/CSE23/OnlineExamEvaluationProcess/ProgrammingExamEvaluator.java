public class ProgrammingExamEvaluator extends ExamEvaluator {

    @Override
    protected boolean validateSubmission(ExamSubmission submission) {
        ProgrammingSubmission prog = (ProgrammingSubmission) submission;
        if (prog.isCompiledSuccessfully()) {
            System.out.println("Compilation successful.");
            return true;
        } else {
            System.out.println("Compilation failed.");
            return false;
        }
    }

    @Override
    protected void evaluateAnswers(ExamSubmission submission) {
        ProgrammingSubmission prog = (ProgrammingSubmission) submission;
        System.out.println("Passed Test Cases : " + prog.getPassedTestCases() + " / " + prog.getTotalTestCases());
    }

    @Override
    protected double calculateScore(ExamSubmission submission) {
        ProgrammingSubmission prog = (ProgrammingSubmission) submission;
        if (prog.getTotalTestCases() == 0) return 0.0;
        return ((double) prog.getPassedTestCases() / prog.getTotalTestCases()) * 100.0;
    }

    @Override
    protected double applyAdjustment(ExamSubmission submission, double calculatedScore) {
        ProgrammingSubmission prog = (ProgrammingSubmission) submission;
        if (prog.isPlagiarismDetected()) {
            System.out.println("Plagiarism detected.");
            System.out.println("Penalty applied.");
            // 50% penalty
            return calculatedScore * 0.5;
        }
        return calculatedScore;
    }
}
