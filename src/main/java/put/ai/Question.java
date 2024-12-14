package put.ai;

import java.util.ArrayList;

public class Question {
    private String name;
    private String content;
    private ArrayList<Answer> answers;
    public Question(String name, String content, ArrayList<Answer> answers) {
        this.name = name;
        this.content = content;
        this.answers = answers;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }
    public String getName() {
        return this.name;
    }
    public String getContent() {
        return this.content;
    }
    public ArrayList<Answer> getAnswers() {
        return this.answers;
    }
}
