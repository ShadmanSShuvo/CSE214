public abstract class ExamSubmission {
    private final String studentId;
    private final String studentName;
    private final String examTitle;

    public ExamSubmission(String studentId, String studentName, String examTitle) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.examTitle = examTitle;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getExamTitle() {
        return examTitle;
    }
}
