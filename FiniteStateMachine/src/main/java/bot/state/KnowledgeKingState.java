package bot.state;

import java.util.HashMap;
import java.util.Map;

import bot.Bot;
import bot.BotState;
import fsm.EntryAction;
import fsm.ExitAction;

public class KnowledgeKingState extends BotState {
	
	//做一個table, 紀錄每位答題者答對的題數
	private Map<String, Integer> answerCount = new HashMap<>();

	public KnowledgeKingState(String stateName, Bot bot, EntryAction entryStateAction, ExitAction exitStateAction) {
		super(bot, stateName, entryStateAction, exitStateAction);
	}

	@Override
	public void initState() {
		answerCount.clear();
	}

	public void recordAnswer(String respondent) {
		answerCount.put(respondent, answerCount.getOrDefault(respondent, 0) + 1);
	}

	public String getWinnerId() {	
		// 如果有多人答對同樣的分數，則返回null代表沒有贏家
		int maxScore = answerCount.entrySet().stream()
			.mapToInt(Map.Entry::getValue)
			.max()
			.orElse(0);
		if (maxScore == 0) {
			return null; // 沒有人答對
		}
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