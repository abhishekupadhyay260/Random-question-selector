package com.example.Random.question.selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Question {
    int id;
    int rating;

    Question(int id, int rating) {
        this.id = id;
        this.rating = rating;
    }
}

public class RandomQuestionSelector2 {
    List<Question> questions;
    String currentLevel;
    int correctAnswers;

    public RandomQuestionSelector2() {
        questions = new ArrayList<>();
        currentLevel = "beginner";
        correctAnswers = 0;
    }

    public void initializeQuestions() {
        // Initialize the list of questions with their IDs and ratings
        questions.add(new Question(1, 1));
        questions.add(new Question(2, 1));
        questions.add(new Question(3, 2));
        questions.add(new Question(4, 2));
        questions.add(new Question(5, 3));
        questions.add(new Question(6, 3));
        // Add more questions as needed
    }

    public Question getNextQuestion() {
        // Filter questions based on the current level
        List<Question> filteredQuestions = new ArrayList<>();
        for (Question question : questions) {
            if (question.rating == getRatingForLevel(currentLevel)) {
                filteredQuestions.add(question);
            }
        }

        // Sort filtered questions by ID in ascending order
        filteredQuestions.sort((q1, q2) -> Integer.compare(q1.id, q2.id));

        // Return the next question with the lowest ID
        return filteredQuestions.get(0);
    }

    public int getRatingForLevel(String level) {
        if (level.equals("beginner")) {
            return 1;
        } else if (level.equals("intermediate")) {
            return 2;
        } else if (level.equals("expert")) {
            return 3;
        }
        return 0; // Default rating if level is invalid
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
            Question question = getNextQuestion();
            displayQuestion(question);

            if (userAnswersCorrectly()) {
                correctAnswers++;

                if (currentLevel.equals("beginner") && correctAnswers == 1) {
                    currentLevel = "intermediate";
                } else if (currentLevel.equals("beginner") && correctAnswers == 2) {
                    currentLevel = "expert";
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
        RandomQuestionSelector2 questionSelector = new RandomQuestionSelector2();
        questionSelector.runQuestionSelector();
    }
}
