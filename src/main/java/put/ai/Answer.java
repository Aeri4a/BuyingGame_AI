package put.ai;

public class Answer {
    private String name;
    private String answer;
    public Answer(String name, String answer) {
        this.name = name;
        this.answer = answer;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public String getName() {
        return this.name;
    }
    public String getAnswer() {
        return this.answer;
    }
}