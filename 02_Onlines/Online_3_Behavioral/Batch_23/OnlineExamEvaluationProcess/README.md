# Problem Statement: Online Exam Evaluation Process

**Batch**: CSE23
**Source**: `Online Exam Evaluation Process.pdf`
**Duration**: 25 Minutes
**Design Pattern**: **Template Method Pattern**

---

## 1. Problem Description

An online examination platform supports multiple types of exams. Although the details of evaluating each exam type differ, the overall evaluation process follows the exact same sequence:

$$\text{Validate Submission} \longrightarrow \text{Evaluate Answers} \longrightarrow \text{Calculate Score} \longrightarrow \text{Apply Adjustment} \longrightarrow \text{Publish Result}$$

The overall sequence must remain identical for all exam types. However, individual steps may behave differently depending on the exam type.

### The Three Exam Types:
1. **MCQ Exam**:
   - **Validation**: Verifies submission format.
   - **Evaluation**: Automatic answer checking. Correct answer: `+1` mark; Wrong answer: `-0.25` mark; Unanswered: `0` marks.
   - **Adjustment**: No additional bonus or penalty applied. Final score cannot be negative.
2. **Programming Exam**:
   - **Validation**: Checks whether the submitted program compiles successfully. If compilation fails, evaluation stops immediately and the result is not published.
   - **Evaluation**: Program executed against test cases. Raw score = $\frac{\text{Passed Test Cases}}{\text{Total Test Cases}} \times 100$.
   - **Adjustment**: Plagiarism check. If plagiarism is detected, a **50% penalty** is applied.
3. **Written Exam**:
   - **Validation**: Validates submission script.
   - **Evaluation**: Manually supplied evaluated score.
   - **Adjustment**: Optional moderation bonus. If enabled, adds `+5` marks (clamped at a maximum of `100`).

---

## 2. Design Pattern & Strategy for Solving

### Design Pattern: **Template Method Pattern**

### Why Template Method Pattern?
- **Invariable Skeleton**: All exam types follow the exact 5-step evaluation workflow in a strict, unchangeable sequence (`validate` &rarr; `evaluate` &rarr; `calculate` &rarr; `adjust` &rarr; `publish`).
- **Open/Closed Principle**: The abstract base class `ExamEvaluator` defines the `final` template method `evaluate(ExamSubmission submission)`. This prevents subclasses from altering the evaluation lifecycle.
- **Hook & Primitive Operations**:
  - Subclasses override primitive steps (`evaluateAnswers`, `calculateScore`, `applyAdjustment`) or hook methods (`validateSubmission`).
  - Validation failure immediately halts processing and aborts publication without duplicating control flow across subclasses.
- **Extensibility**: Introducing a new exam type (e.g., `VivaExamEvaluator`, `PracticalExamEvaluator`) requires only implementing a new subclass without modifying existing evaluators.
- **Score Integrity**: Score clamping ($0 \le \text{Final Score} \le 100$) is enforced centrally in the base template method.

---

## 3. Class Diagram

```
+------------------------------------------------------------------+
|                          ExamEvaluator                           |
+------------------------------------------------------------------+
| + evaluate(submission: ExamSubmission) : void <<final>>          |
| # validateSubmission(submission: ExamSubmission) : boolean       |
| # onValidationFailed(submission: ExamSubmission) : void          |
| # evaluateAnswers(submission: ExamSubmission) : void             |
| # calculateScore(submission: ExamSubmission) : double            |
| # applyAdjustment(submission: ExamSubmission, score) : double    |
| # clampScore(score: double) : double                             |
| # publishResult(submission: ExamSubmission, finalScore) : void   |
+------------------------------------------------------------------+
          ^                         ^                         ^
          |                         |                         |
+----------------------+ +-------------------------+ +------------------------+
|   McqExamEvaluator   | | ProgrammingExamEvaluat. | |  WrittenExamEvaluator  |
+----------------------+ +-------------------------+ +------------------------+
| - negativeMark: 0.25 | | + validateSubmission()  | | - moderationBonus: 5   |
| + evaluateAnswers()  | | + evaluateAnswers()     | | + evaluateAnswers()    |
| + calculateScore()   | | + calculateScore()      | | + calculateScore()     |
| + applyAdjustment()  | | + applyAdjustment()     | | + applyAdjustment()    |
+----------------------+ +-------------------------+ +------------------------+
```

---

## 4. Expected Scenarios from Specification

- **Scenario 1: MCQ Exam**
  - Input: Correct = 70, Wrong = 20, Unanswered = 10
  - Output: Calculated = 65, Final = 65, Result Published.
- **Scenario 2: Programming Exam**
  - Input: Compilation = OK, 16/20 passed, Plagiarism = Yes
  - Output: Calculated = 80, 50% penalty, Final = 40, Result Published.
- **Scenario 3: Programming Exam with Compilation Error**
  - Input: Compilation = Failed
  - Output: Compilation failed. Evaluation stopped. Result not published.
- **Scenario 4: Written Exam**
  - Input: Evaluated = 98, Moderation = Enabled (+5)
  - Output: Evaluated = 98, Moderation = Enabled (+5), Final = 100 (clamped), Result Published.

---

## 5. How to Compile & Run

```bash
cd /CSE23/OnlineExamEvaluationProcess
javac *.java
java Main
```
