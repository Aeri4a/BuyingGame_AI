package put.ai;

import java.util.ArrayList;

public class Question {
    private String name;
    private String question;
    private boolean many;
    private ArrayList<Answer> answers;
    public Question(String name, String question, boolean many, ArrayList<Answer> answers) {
        this.name = name;
        this.question = question;
        this.many = many;
        this.answers = answers;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public void setMany(boolean many) {
        this.many = many;
    }
    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }
    public String getName() {
        return this.name;
    }
    public String getQuestion() {
        return this.question;
    }
    public boolean getMany() {
        return this.many;
    }
    public ArrayList<Answer> getAnswers() {
        return this.answers;
    }
}