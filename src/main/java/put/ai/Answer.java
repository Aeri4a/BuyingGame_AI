package put.ai;

public class Answer {
    private String name;
    private String content;
    public Answer(String name, String content) {
        this.name = name;
        this.content = content;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getName() {
        return this.name;
    }
    public String getContent() {
        return this.content;
    }
}