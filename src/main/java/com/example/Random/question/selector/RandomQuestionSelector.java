import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Question {
    int id;
    String rating;

    Question(int id, String rating) {
        this.id = id;
        this.rating = rating;
    }
}

public class RandomQuestionSelector {
    List<Question> questions;
    String currentLevel;
    int correctAnswers;

    public RandomQuestionSelector() {
        questions = new ArrayList<>();
        currentLevel = "beginner";
        correctAnswers = 0;
    }

    public void initializeQuestions() {
        // Initialize the list of questions
        questions.add(new Question(1, "beginner"));
        questions.add(new Question(2, "beginner"));
        questions.add(new Question(3, "intermediate"));
        questions.add(new Question(4, "intermediate"));
        questions.add(new Question(5, "expert"));
        questions.add(new Question(6, "expert"));
        // Add more questions as needed
    }

    public Question randomlySelectQuestion(String level) {
        // Filter questions based on the level
        List<Question> filteredQuestions = new ArrayList<>();
        for (Question question : questions) {
            if (question.rating.equals(level)) {
                filteredQuestions.add(question);
            }
        }

        // Randomly select a question from the filtered list
        Random random = new Random();
        int index = random.nextInt(filteredQuestions.size());
        return filteredQuestions.get(index);
    }

    public void displayQuestion(Question question) {
        System.out.println("Question ID: " + question.id);
        // Display the question details
    }

    public boolean userAnswersCorrectly() {
        // Logic to check if the user's answer is correct
        // Return true if the answer is correct, false otherwise
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your answer (1 for correct, 0 for incorrect): ");
        int answer = scanner.nextInt();
        return answer == 1;
    }

    public boolean userContinues() {
        // Logic to check if the user wants to continue answering questions
        // Return true if the user wants to continue, false otherwise
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to continue? (1 for yes, 0 for no): ");
        int choice = scanner.nextInt();
        return choice == 1;
    }

    public void displayPassMessage() {
        System.out.println("Congratulations! You passed the test.");
    }

    public void displayStopMessage() {
        System.out.println("You have chosen to stop. Goodbye!");
    }

    public void runQuestionSelector() {
        initializeQuestions();

        while (!currentLevel.equals("pass") && userContinues()) {
            Question question;
            if (currentLevel.equals("beginner")) {
                question = randomlySelectQuestion("beginner");
            } else if (currentLevel.equals("intermediate")) {
                question = randomlySelectQuestion("intermediate");
            } else {
                question = randomlySelectQuestion("expert");
            }

            displayQuestion(question);

            if (userAnswersCorrectly()) {
                correctAnswers++;

                if (currentLevel.equals("beginner") && correctAnswers == 1) {
                    currentLevel = "intermediate";
                } else if (currentLevel.equals("intermediate") && correctAnswers == 1) {
                    currentLevel = "expert";
                } else if (currentLevel.equals("expert") && correctAnswers == 1) {
                    currentLevel = "pass";
                }
            } else {
                correctAnswers = 0;
                if (currentLevel.equals("intermediate") || currentLevel.equals("expert")) {
                    currentLevel = "beginner";
                }
            }
        }

        if (currentLevel.equals("pass")) {
            displayPassMessage();
        } else {
            displayStopMessage();
        }
    }

    public static void main(String[] args) {
        RandomQuestionSelector questionSelector = new RandomQuestionSelector();
        questionSelector.runQuestionSelector();
    }
}
