package bot.state;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import bot.Bot;
import bot.BotState;
import bot.event.AllQuestionsFinishedEvent;
import bot.event.NewMessageEvent;
import bot.event.TimeoutEvent;
import fsm.FSMContext;
import fsm.EntryAction;
import fsm.ExitAction;

public class QuestioningState extends BotState {

	// 計算自進入本狀態，已經過了幾秒
	protected int elapsedSeconds = 0;

	private KnowledgeKingState knowledgeKingState;
	private FSMContext context;

	// TODO 改由外部注入
	private final Question[] questions = {	
		new QuestionCSS(),
		new QuestionSQL(),
		new QuestionXML()
	};
	private int replyIndex = 0;
	
	public QuestioningState(String stateName, KnowledgeKingState knowledgeKingState, FSMContext context, Bot bot, EntryAction entryStateAction, ExitAction exitStateAction) {
		super(bot, stateName, entryStateAction, exitStateAction);
		this.context = context;
		this.knowledgeKingState = knowledgeKingState;
	}

	public void reset() {
		replyIndex = 0;
		elapsedSeconds = 0;
		knowledgeKingState.reset();
	}

	public void showNextQuestion() {
		Question nextQuestion = questions[replyIndex % questions.length];
		replyIndex++;
		bot.sendNewMessageToChatRoom(nextQuestion.getQuestion(), new ArrayList<>());
	}
	
	@Override
	public void onNewMessage(NewMessageEvent event) {
		String respondent = event.getMessageAuthorId(); 
		String yourAnswer = event.getMessageContent();  
		
		boolean isCorrect = chekAnswer(yourAnswer);
		if (isCorrect) {
			knowledgeKingState.recordAnswer(respondent);
			bot.sendNewMessageToChatRoom(
				"Congrats! you got the answer!",
				Arrays.asList(respondent)	//標註獲勝者
			);
			
			//若每一題都回答完了就進入感謝參與狀態
			if (isAllQuestionsFinished()) {
				// 對FSM發出答題完畢事件，進入感謝參與狀態
				context.sendEventToCurrentState(new AllQuestionsFinishedEvent());
			}
			else {
				// 顯示下一題
				showNextQuestion();
			}
		} 
	}

	private boolean chekAnswer(String yourAnswer) {
		Question question = questions[replyIndex % questions.length];
		return question.isCorrectAnswer(yourAnswer);
	}

	private boolean isAllQuestionsFinished() {
		return replyIndex >= questions.length - 1;
	}

	@Override
	public void increaseElapsedTime(int time, TimeUnit unit) {
		if (unit == TimeUnit.SECONDS) {
			elapsedSeconds += time;
		} 
		else if (unit == TimeUnit.MINUTES) {
			elapsedSeconds += time * 60;
		} 
		else if (unit == TimeUnit.HOURS) {
			elapsedSeconds += time * 60 * 60;
		} 
		else {
			throw new IllegalArgumentException("Invalid time unit.");
		}

		if (isTimeOut()) {
			context.sendEventToCurrentState(new TimeoutEvent());
		}
	}

	private boolean isTimeOut() {
		return isAllQuestionsFinished() && elapsedSeconds >= 3600;
	}
	
}
