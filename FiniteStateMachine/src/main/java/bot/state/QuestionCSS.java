package bot.state;

public class QuestionCSS extends Question {

    @Override
    public String getQuestion() {
        return "請問哪個 CSS 屬性可用於設置文字的顏色？\n"
                + "A) text-align\n"
                + "B) font-size\n"
                + "C) color\n"
                + "D) padding";
    }

    @Override
    public String getAnswer() {
        return "C";
    }

}