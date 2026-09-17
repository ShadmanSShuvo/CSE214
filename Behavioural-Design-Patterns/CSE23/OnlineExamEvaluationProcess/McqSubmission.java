public class McqSubmission extends ExamSubmission {
    private final int correctAnswers;
    private final int wrongAnswers;
    private final int unanswered;

    public McqSubmission(String studentId, String studentName, String examTitle,
                         int correctAnswers, int wrongAnswers, int unanswered) {
        super(studentId, studentName, examTitle);
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
        this.unanswered = unanswered;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public int getUnanswered() {
        return unanswered;
    }
}
