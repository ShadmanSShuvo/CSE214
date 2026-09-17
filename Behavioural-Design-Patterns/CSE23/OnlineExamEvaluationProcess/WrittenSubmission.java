public class WrittenSubmission extends ExamSubmission {
    private final double evaluatedScore;
    private final boolean moderationEnabled;

    public WrittenSubmission(String studentId, String studentName, String examTitle,
                             double evaluatedScore, boolean moderationEnabled) {
        super(studentId, studentName, examTitle);
        this.evaluatedScore = evaluatedScore;
        this.moderationEnabled = moderationEnabled;
    }

    public double getEvaluatedScore() {
        return evaluatedScore;
    }

    public boolean isModerationEnabled() {
        return moderationEnabled;
    }
}
