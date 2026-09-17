public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("   ONLINE EXAM EVALUATION PROCESS (TEMPLATE METHOD)       ");
        System.out.println("==========================================================\n");

        ExamEvaluator mcqEvaluator = new McqExamEvaluator();
        ExamEvaluator programmingEvaluator = new ProgrammingExamEvaluator();
        ExamEvaluator writtenEvaluator = new WrittenExamEvaluator();

        // Scenario 1: MCQ Exam
        System.out.println("--- Scenario 1: MCQ Exam ---");
        ExamSubmission mcqSubmission = new McqSubmission(
                "S101", "Alice", "CS101 MCQ Exam",
                70, 20, 10
        );
        mcqEvaluator.evaluate(mcqSubmission);

        // Scenario 2: Programming Exam (Success + Plagiarism)
        System.out.println("\n--- Scenario 2: Programming Exam ---");
        ExamSubmission progSubmission1 = new ProgrammingSubmission(
                "S102", "Bob", "CS201 Data Structures Exam",
                true, 20, 16, true
        );
        programmingEvaluator.evaluate(progSubmission1);

        // Scenario 3: Programming Exam with Compilation Error
        System.out.println("\n--- Scenario 3: Programming Exam with Compilation Error ---");
        ExamSubmission progSubmission2 = new ProgrammingSubmission(
                "S103", "Charlie", "CS201 Data Structures Exam",
                false, 20, 0, false
        );
        programmingEvaluator.evaluate(progSubmission2);

        // Scenario 4: Written Exam with Moderation Bonus
        System.out.println("\n--- Scenario 4: Written Exam ---");
        ExamSubmission writtenSubmission = new WrittenSubmission(
                "S104", "Diana", "CS301 Algorithms Written Exam",
                98.0, true
        );
        writtenEvaluator.evaluate(writtenSubmission);

        System.out.println("\n==========================================================");
        System.out.println("   All exam evaluation scenarios completed successfully.  ");
        System.out.println("==========================================================");
    }
}
