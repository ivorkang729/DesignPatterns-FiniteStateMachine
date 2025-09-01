package bot.state;

public class QuestionSQL extends Question {

    @Override
    public String getQuestion() {
        return  """
            請問在計算機科學中，「XML」代表什麼？
            A) Extensible Markup Language
            B) Extensible Modeling Language
            C) Extended Markup Language
            D) Extended Modeling Language
            """;
    }

    @Override
    public String getAnswer() {
        return "A";
    }

}
