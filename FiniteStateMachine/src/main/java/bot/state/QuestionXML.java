package bot.state;

public class QuestionXML extends Question {

    @Override
    public String getQuestion() {
        return "請問在計算機科學中，「XML」代表什麼？\n"
                + "A) Extensible Markup Language\n"
                + "B) Extensible Modeling Language\n"
                + "C) Extended Markup Language\n"
                + "D) Extended Modeling Language";
    }

    @Override
    public String getAnswer() {
        return "A";
    }

}