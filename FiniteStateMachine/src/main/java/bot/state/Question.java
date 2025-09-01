package bot.state;



public abstract class Question {

    public abstract String getQuestion();
    public abstract String getAnswer();

    public boolean isCorrectAnswer(String yourAnswer) {
        return getAnswer().equalsIgnoreCase(yourAnswer);
    }

}
