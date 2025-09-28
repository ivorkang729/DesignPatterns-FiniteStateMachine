package bot.facade;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bot.Bot;
import bot.state.defaultConversation.DefaultConversationState;
import bot.state.defaultConversation.DefaultConversationStateEntryAction;
import bot.state.defaultConversation.DefaultConversationStateLoginEventGuard;
import bot.state.interacting.InteractingState;
import bot.state.interacting.InteractingStateEntryAction;
import bot.state.interacting.InteractingStateLogoutEventGuard;
import bot.state.knowledgeKing.KnowledgeKingState;
import bot.state.knowledgeKing.KnowledgeKingStateCommandKingStopTransition;
import bot.state.knowledgeKing.KnowledgeKingStateEntryAction;
import bot.state.normal.NormalState;
import bot.state.normal.NormalStateCommandKingTransition;
import bot.state.normal.NormalStateCommandRecordTransition;
import bot.state.normal.NormalStateEntryAction;
import bot.state.questioning.Question;
import bot.state.questioning.QuestionCSS;
import bot.state.questioning.QuestionSQL;
import bot.state.questioning.QuestionXML;
import bot.state.questioning.QuestioningState;
import bot.state.questioning.QuestioningStateEntryAction;
import bot.state.record.RecordState;
import bot.state.record.RecordStateCommandStopRecordingTransition;
import bot.state.record.RecordStateEntryAction;
import bot.state.recording.RecordingState;
import bot.state.recording.RecordingStateCommandStopRecordingTransition;
import bot.state.recording.RecordingStateEntryAction;
import bot.state.recording.RecordingStateStopBroadcastingEventAction;
import bot.state.thanksForJoining.ThanksForJoiningState;
import bot.state.thanksForJoining.ThanksForJoiningStateCommandPlayAgainTransition;
import bot.state.thanksForJoining.ThanksForJoiningStateEntryAction;
import bot.state.waiting.WaitingState;
import fsm.FSMContext;
import fsm.FSMTransition;
import fsm.IState;
import fsm.ITransition;
import fsm.NoOpAction;
import fsm.NoOpEntryAction;
import fsm.NoOpExitAction;
import fsm.NoOpGuard;
import waterballCommunity.WaterballCommunity;

public class BotFactory {
	private static final Logger logger = LogManager.getLogger(BotFactory.class);

    public static Bot createBot(WaterballCommunity waterballCommunity, String startedTime, int initialCommandQuota) {
		
		// Bot ---------------------------------------
		FSMContext context = new FSMContext();
		Bot bot = new bot.Bot(context, waterballCommunity, startedTime, initialCommandQuota);
		
		// States --------------------------------------
		final String NAME_NORMAL_STATE = NormalState.class.getSimpleName();
		final String NAME_INTERACTING_STATE = InteractingState.class.getSimpleName();
		final String NAME_DEFAULT_CONVERSATION_STATE = DefaultConversationState.class.getSimpleName();
		final String NAME_WAITING_STATE = WaitingState.class.getSimpleName();
		final String NAME_RECORDING_STATE = RecordingState.class.getSimpleName();
		final String NAME_RECORD_STATE = RecordState.class.getSimpleName();
		final String NAME_QUESTIONING_STATE = QuestioningState.class.getSimpleName();
		final String NAME_THANKS_FOR_JOINING_STATE = ThanksForJoiningState.class.getSimpleName();
		final String NAME_KNOWLEDGE_KING_STATE = KnowledgeKingState.class.getSimpleName();
		
		IState defaultConversationState = new DefaultConversationState(
			NAME_DEFAULT_CONVERSATION_STATE,
			bot, 
			new DefaultConversationStateEntryAction(),
			new NoOpExitAction());
		context.registerState(defaultConversationState);
		
		IState interactingState = new InteractingState(
			NAME_INTERACTING_STATE,
			bot, 
			new InteractingStateEntryAction(),
			new NoOpExitAction());
		context.registerState(interactingState);
		
		IState normalState = new NormalState(
			NAME_NORMAL_STATE,
			bot, 
			new NormalStateEntryAction(waterballCommunity),
			new NoOpExitAction());
		context.registerState(normalState);

		IState waitingState = new WaitingState(
			NAME_WAITING_STATE,
			bot, 
			new NoOpEntryAction(),
			new NoOpExitAction());
		context.registerState(waitingState);

		IState recordingState = new RecordingState(
			NAME_RECORDING_STATE,
			bot, 
			new RecordingStateEntryAction(),
			new NoOpExitAction());
		context.registerState(recordingState);

		IState recordState = new RecordState(
			NAME_RECORD_STATE,
			bot, 
			new RecordStateEntryAction(waterballCommunity),
			new NoOpExitAction());
		context.registerState(recordState);
		
		KnowledgeKingState knowledgeKingState = new KnowledgeKingState(
			NAME_KNOWLEDGE_KING_STATE,
			bot, 
			new KnowledgeKingStateEntryAction(bot),
			new NoOpExitAction());
		context.registerState(knowledgeKingState);

		QuestioningState questioningState = new QuestioningState(
			NAME_QUESTIONING_STATE,
			new Question[]{	
				new QuestionSQL(),
				new QuestionCSS(),
				new QuestionXML()
			},
			knowledgeKingState,
			context,
			bot, 
			new QuestioningStateEntryAction(),
			new NoOpExitAction());
		context.registerState(questioningState);

		ThanksForJoiningState thanksForJoiningState = new ThanksForJoiningState(
			NAME_THANKS_FOR_JOINING_STATE,
			knowledgeKingState,
			context,
			bot, 
			new ThanksForJoiningStateEntryAction(),
			new NoOpExitAction());
		context.registerState(thanksForJoiningState);
		
		
		// State Composition ------------------------------
		defaultConversationState.setParentState(normalState);
		interactingState.setParentState(normalState);
		waitingState.setParentState(recordState);
		recordingState.setParentState(recordState);
		questioningState.setParentState(knowledgeKingState);
		thanksForJoiningState.setParentState(knowledgeKingState);
		
		// Transition --------------------------------------

		// DefaultConversationState 事件 Login --> InteractingState
		ITransition loginTransition = new FSMTransition(
				bot.event.LoginEvent.class,
				new DefaultConversationStateLoginEventGuard(bot, waterballCommunity), // Guard
				new NoOpAction(), // Action
				InteractingState.class);
		defaultConversationState.addTransition(loginTransition);
		
		// InteractingState 事件 Logout --> DefaultConversationState
		ITransition logoutTransition = new FSMTransition(
				bot.event.LogoutEvent.class,
				new InteractingStateLogoutEventGuard(bot, waterballCommunity), // Guard
				new NoOpAction(), // Action
				DefaultConversationState.class);
		interactingState.addTransition(logoutTransition);
		
		// NormalState 指令 "king" --> KnowledgeKing
		ITransition kingTransition = new NormalStateCommandKingTransition(bot, waterballCommunity);	
		normalState.addTransition(kingTransition);

		// NormalState 指令 "record" --> RecordState
		ITransition recordTransition = new NormalStateCommandRecordTransition(bot, waterballCommunity);	
		normalState.addTransition(recordTransition);
		
		// WaitingState 事件 GoBroadcasting --> RecordingState
		ITransition goBroadcastingTransition = new FSMTransition(
				bot.event.GoBroadcastingEvent.class,
				new NoOpGuard(), // Guard
				new NoOpAction(), // Action
				RecordingState.class);	
		waitingState.addTransition(goBroadcastingTransition);
		
		// RecordingState 事件 StopBroadcasting --> WaitingState
		ITransition stopBroadcastingTransition = new FSMTransition(
				bot.event.StopBroadcastingEvent.class,
				new NoOpGuard(), // Guard
				new RecordingStateStopBroadcastingEventAction(bot, waterballCommunity),	// Action 
				WaitingState.class);	
		recordingState.addTransition(stopBroadcastingTransition);

		// RecordState 指令 "stop-recording" --> NormalState
		ITransition stopRecordingTransitionForRecordState = new RecordStateCommandStopRecordingTransition(bot, waterballCommunity);
		recordState.addTransition(stopRecordingTransitionForRecordState);

		// RecordingState 指令 "stop-recording" --> NormalState
		ITransition stopRecordingTransitionForRecordingState = new RecordingStateCommandStopRecordingTransition(bot, waterballCommunity);
		recordingState.addTransition(stopRecordingTransitionForRecordingState);

		// KnowledgeKingState 指令 "king-stop" --> Normal
		ITransition kingStopTransition = new KnowledgeKingStateCommandKingStopTransition(bot, waterballCommunity);	
		knowledgeKingState.addTransition(kingStopTransition);
		
		// 指令 play again
		ITransition playAgainTransition = new ThanksForJoiningStateCommandPlayAgainTransition(bot, waterballCommunity);	
		thanksForJoiningState.addTransition(playAgainTransition);


		// QuestioningState 事件 AllQuestionsFinishedEvent --> ThanksForJoiningState
		ITransition allQuestionsFinishedTransition = new FSMTransition(
				bot.event.AllQuestionsFinishedEvent.class,
				new NoOpGuard(), // Guard
				new NoOpAction(), // Action
				ThanksForJoiningState.class);	
		questioningState.addTransition(allQuestionsFinishedTransition);

		// QuestioningState 事件 1小時 TimeoutEvent --> ThanksForJoiningState
		ITransition questioningTimeoutTransition = new FSMTransition(
				bot.event.TimeoutEvent.class,
				new NoOpGuard(), // Guard
				new NoOpAction(), // Action
				ThanksForJoiningState.class);	
		questioningState.addTransition(questioningTimeoutTransition);

		// ThanksForJoiningState 事件 20秒  TimeoutEvent --> NormalState
		ITransition thanksForJoiningTimeoutTransition = new FSMTransition(
				bot.event.TimeoutEvent.class,
				new NoOpGuard(), // Guard
				new NoOpAction(), // Action
				NormalState.class);	
		thanksForJoiningState.addTransition(thanksForJoiningTimeoutTransition);

		// 設置初始狀態為 NormalState
		context.transCurrentStateTo(normalState);
		
		// 機器人登入 WaterballCommunity
		bot.loginWaterballCommunity();
		
		/* 機器人配置完畢---------------------------------------------------- */
		return bot;
	}

}
