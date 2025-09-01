package bot.state;

public class QuestionSQL extends Question {

    @Override
    public String getQuestion() {
        return  """
            請問哪個 CSS 屬性可用於設置文字的顏色？
            A) text-align
            B) font-size
            C) color
            D) padding
            """;
    }

    @Override
    public String getAnswer() {
        return "C";
    }

}
