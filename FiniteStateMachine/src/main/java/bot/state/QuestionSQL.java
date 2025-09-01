package bot.state;

public class QuestionSQL extends Question {

    @Override
    public String getQuestion() {
        return  """
            請問哪個 SQL 語句用於選擇所有的行？
            A) SELECT *
            B) SELECT ALL
            C) SELECT ROWS
            D) SELECT DATA
            """;
    }

    @Override
    public String getAnswer() {
        return "A";
    }

}
