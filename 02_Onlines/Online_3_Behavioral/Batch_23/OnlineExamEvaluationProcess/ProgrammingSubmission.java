public class ProgrammingSubmission extends ExamSubmission {
    private final boolean compiledSuccessfully;
    private final int totalTestCases;
    private final int passedTestCases;
    private final boolean plagiarismDetected;

    public ProgrammingSubmission(String studentId, String studentName, String examTitle,
                                 boolean compiledSuccessfully, int totalTestCases, int passedTestCases, boolean plagiarismDetected) {
        super(studentId, studentName, examTitle);
        this.compiledSuccessfully = compiledSuccessfully;
        this.totalTestCases = totalTestCases;
        this.passedTestCases = passedTestCases;
        this.plagiarismDetected = plagiarismDetected;
    }

    public boolean isCompiledSuccessfully() {
        return compiledSuccessfully;
    }

    public int getTotalTestCases() {
        return totalTestCases;
    }

    public int getPassedTestCases() {
        return passedTestCases;
    }

    public boolean isPlagiarismDetected() {
        return plagiarismDetected;
    }
}
