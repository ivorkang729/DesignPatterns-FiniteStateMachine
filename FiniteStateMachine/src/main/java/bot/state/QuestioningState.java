package bot.state;

import java.util.ArrayList;
import java.util.Arrays;

import bot.Bot;
import bot.BotState;
import bot.event.AllQuestionsFinishedEvent;
import bot.event.NewMessageEvent;
import fsm.FSMContext;
import fsm.EntryAction;
import fsm.ExitAction;

public class QuestioningState extends BotState {

	private KnowledgeKingState knowledgeKingState;
	private FSMContext context;

	public QuestioningState(FSMContext context, Bot bot, EntryAction entryStateAction, ExitAction exitStateAction) {
		super(bot, QuestioningState.class.getSimpleName(), entryStateAction, exitStateAction);
		this.context = context;
	}

	public void setKnowledgeKingState(KnowledgeKingState knowledgeKingState) {
		this.knowledgeKingState = knowledgeKingState;
	}

	public void resetQuestioningIndex() {
		knowledgeKingState.resetQuestioningIndex();
	}

	public void showNextQuestion() {
		Question question = knowledgeKingState.nextQuestion();
		bot.sendNewMessageToChatRoom(question.getQuestion(), new ArrayList<>());
	}

	@Override
	public void onNewMessage(NewMessageEvent event) {
		String respondent = event.getMessageAuthorId(); 
		String yourAnswer = event.getMessageContent();  

		boolean isCorrect = knowledgeKingState.chekAnswer(yourAnswer);
		if (isCorrect) {
			knowledgeKingState.recordAnswer(respondent);
			bot.sendNewMessageToChatRoom(
				"Congrats! you got the answer!",
				Arrays.asList(respondent)	//標註獲勝者
			);
			
			//若每一題都回答完了就進入感謝參與狀態
			if (knowledgeKingState.isAllQuestionsFinished()) {
				// 對FSM發出答題完畢事件，進入感謝參與狀態
				context.sendEvent(new AllQuestionsFinishedEvent());
			}
			else {
				// 顯示下一題
				showNextQuestion();
			}
		} 
	}

}
