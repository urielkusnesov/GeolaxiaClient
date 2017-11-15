package geolaxia.geolaxia.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Question {
    private int Id;
    private String Question;
    private String Answers;
    private String CorrectAnswer;

    public Question(int id, String question, String answers, String correctAnswer) {
        this.Id = id;
        this.Question = question;
        this.Answers = answers;
        this.CorrectAnswer = correctAnswer;
    }

    public int GetId() {
        return (this.Id);
    }

    public String GetQuestion() {
        return (this.Question);
    }

    public ArrayList<String> GetAnswers() {
        ArrayList<String> answersList = new ArrayList<String>(Arrays.asList(this.Answers.replace("|",",").split(",")));
        return (answersList);
    }

    public String GetCorrectAnswer() {
        return (this.CorrectAnswer);
    }

    public boolean IsCorrectAnswer(String answer) {
        return (answer.equals(this.CorrectAnswer));
    }
}
