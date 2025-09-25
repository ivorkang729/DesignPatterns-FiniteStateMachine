package bot.state.knowledgeKing;

public class QuestionSQL extends Question {

    @Override
    public String getQuestion() {
        return "請問哪個 SQL 語句用於選擇所有的行？\n"
				+ "A) SELECT *\n"
				+ "B) SELECT ALL\n"
				+ "C) SELECT ROWS\n"
				+ "D) SELECT DATA";
    }

    @Override
    public String getAnswer() {
        return "A";
    }

}