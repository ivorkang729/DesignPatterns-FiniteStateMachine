package bot.state;

import java.util.HashMap;
import java.util.Map;

import bot.Bot;
import bot.BotState;
import fsm.EntryAction;
import fsm.ExitAction;

public class KnowledgeKingState extends BotState {
	
	private final Question[] questions = {	
		new QuestionCSS(),
		new QuestionSQL(),
		new QuestionXML()
	};
	private int replyIndex = 0;

	//做一個table, 紀錄每位答題者答對的題數
	private final Map<String, Integer> answerCount = new HashMap<>();
	
	public KnowledgeKingState(Bot bot, EntryAction entryStateAction, ExitAction exitStateAction) {
		super(bot, KnowledgeKingState.class.getSimpleName(), entryStateAction, exitStateAction);
	}

	public void resetQuestioningIndex() {
		replyIndex = 0;
	}

	public Question nextQuestion() {
		replyIndex++;
		return questions[replyIndex % questions.length];
	}

	public boolean chekAnswer(String yourAnswer) {
		Question question = questions[replyIndex % questions.length];
		return question.isCorrectAnswer(yourAnswer);
	}

	public void recordAnswer(String respondent) {
		answerCount.put(respondent, answerCount.getOrDefault(respondent, 0) + 1);
	}

	public boolean isAllQuestionsFinished() {
		return replyIndex == questions.length;
	}

	public String getWinnerId() {	
		// 如果有多人答對同樣的分數，則返回null代表沒有贏家
		int maxScore = answerCount.entrySet().stream()
			.mapToInt(Map.Entry::getValue)
			.max()
			.orElse(0);
		if (answerCount.entrySet().stream()
			.filter(entry -> entry.getValue() == maxScore)
			.count() > 1) {
			return null;
		}
		return answerCount.entrySet().stream()
			.filter(entry -> entry.getValue() == maxScore)
			.findFirst()
			.get()
			.getKey();
	}
}