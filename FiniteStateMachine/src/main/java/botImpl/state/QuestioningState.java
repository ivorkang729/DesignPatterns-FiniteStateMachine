package botImpl.state;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import botBase.BotBaseState;
import botBase.event.AllQuestionsFinishedEvent;
import botBase.event.NewMessageEvent;
import botBase.event.TimeoutEvent;
import botImpl.Bot;
import fsm.FSMContext;
import fsm.EntryAction;
import fsm.ExitAction;

public class QuestioningState extends BotBaseState {

	// 計算自進入本狀態，已經過了幾秒
	protected int elapsedSeconds = 0;

	private KnowledgeKingState knowledgeKingState;
	private FSMContext context;

	private final Question[] questions;
	private int questionIndex = -1;
	
	public QuestioningState(String stateName, Question[] questions, KnowledgeKingState knowledgeKingState, FSMContext context, Bot bot, EntryAction entryStateAction, ExitAction exitStateAction) {
		super(bot, stateName, entryStateAction, exitStateAction);
		this.context = context;
		this.knowledgeKingState = knowledgeKingState;
		this.questions = questions;
	}

	@Override
	public void initState() {
		questionIndex = -1;
		elapsedSeconds = 0;
	}

	public void showNextQuestion() {
		questionIndex++;
		String nextQuestion = questionIndex + ". " + questions[questionIndex % questions.length].getQuestion();
		bot.sendNewMessageToChatRoom(nextQuestion, new ArrayList<>());
	}
	
	@Override
	public void onNewMessage(NewMessageEvent event) {
		
		if (!event.getMessageTags().contains(Bot.BOT_TAG)) {
			return;
		}

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
		Question question = questions[questionIndex % questions.length];
		return question.isCorrectAnswer(yourAnswer);
	}

	private boolean isAllQuestionsFinished() {
		return questionIndex >= questions.length - 1;
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
		return elapsedSeconds >= 3600;
	}
	
}
