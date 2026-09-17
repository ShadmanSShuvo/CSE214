public abstract class ExamEvaluator {

    // Template Method - defines the invariable 5-step evaluation lifecycle
    public final void evaluate(ExamSubmission submission) {
        // Step 1: Validate Submission
        if (!validateSubmission(submission)) {
            onValidationFailed(submission);
            return;
        }

        // Step 2: Evaluate Answers
        evaluateAnswers(submission);

        // Step 3: Calculate Score
        double calculatedScore = calculateScore(submission);
        printCalculatedScore(calculatedScore);

        // Step 4: Apply Adjustment
        double adjustedScore = applyAdjustment(submission, calculatedScore);

        // Step 5: Score Clamping (0 <= Final Score <= 100) & Publish Result
        double finalScore = clampScore(adjustedScore);
        System.out.printf("Final Score      = %.0f\n", finalScore);
        publishResult(submission, finalScore);
    }

    // Hook: Step 1 Validation (defaults to true if submission is non-null)
    protected boolean validateSubmission(ExamSubmission submission) {
        if (submission != null) {
            System.out.println("Submission validated.");
            return true;
        }
        System.out.println("Validation failed: submission is null.");
        return false;
    }

    // Hook executed when validation fails
    protected void onValidationFailed(ExamSubmission submission) {
        System.out.println("Evaluation stopped.");
        System.out.println("Result not published.");
    }

    // Step 2: Evaluate Answers (abstract)
    protected abstract void evaluateAnswers(ExamSubmission submission);

    // Step 3: Calculate Score (abstract)
    protected abstract double calculateScore(ExamSubmission submission);

    // Hook: display calculated score
    protected void printCalculatedScore(double calculatedScore) {
        System.out.printf("Calculated Score = %.0f\n", calculatedScore);
    }

    // Step 4: Apply Adjustment (hook with default no-adjustment)
    protected double applyAdjustment(ExamSubmission submission, double calculatedScore) {
        return calculatedScore;
    }

    // Enforce 0 <= Final Score <= 100
    protected final double clampScore(double score) {
        return Math.max(0.0, Math.min(100.0, score));
    }

    // Step 5: Publish Result
    protected void publishResult(ExamSubmission submission, double finalScore) {
        System.out.println("Result Published.");
    }
}
