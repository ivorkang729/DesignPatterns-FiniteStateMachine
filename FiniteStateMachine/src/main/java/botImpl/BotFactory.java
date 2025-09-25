package botImpl;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import botImpl.state.DefaultConversationState;
import botImpl.state.InteractingState;
import botImpl.state.Question;
import botImpl.state.QuestionCSS;
import botImpl.state.QuestionSQL;
import botImpl.state.QuestionXML;
import botImpl.state.QuestioningState;
import botImpl.state.WaitingState;
import botImpl.state.knowledgeKing.KnowledgeKingState;
import botImpl.state.knowledgeKing.KnowledgeKingStateCommandKingStopTransition;
import botImpl.state.normal.NormalState;
import botImpl.state.normal.NormalStateCommandKingTransition;
import botImpl.state.normal.NormalStateCommandRecordTransition;
import botImpl.state.record.RecordState;
import botImpl.state.record.RecordStateCommandStopRecordingTransition;
import botImpl.state.recording.RecordingState;
import botImpl.state.recording.RecordingStateCommandStopRecordingTransition;
import botImpl.state.recording.RecordingStateEventStopBroadcastingAction;
import botImpl.state.thanksForJoining.ThanksForJoiningState;
import botImpl.state.thanksForJoining.ThanksForJoiningStateCommandPlayAgainTransition;
import fsm.IEvent;
import fsm.FSMContext;
import fsm.FSMTransition;
import fsm.NoOpAction;
import fsm.NoOpGuard;
import fsm.IState;
import fsm.ITransition;
import waterballCommunity.WaterballCommunity;

public class BotFactory {
	private static final Logger logger = LogManager.getLogger(BotFactory.class);

    public static Bot createBot(WaterballCommunity waterballCommunity, String startedTime, int initialCommandQuota) {
		
		// Bot ---------------------------------------
		FSMContext context = new FSMContext();
		Bot bot = new botImpl.Bot(context, waterballCommunity, startedTime, initialCommandQuota);
		
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
			(FSMContext c, IState s) -> {
				((DefaultConversationState) s).initState();
			},
			(FSMContext c, IState s) -> {});
		context.registerState(defaultConversationState);
		
		IState interactingState = new InteractingState(
			NAME_INTERACTING_STATE,
			bot, 
			(FSMContext c, IState s) -> {
				((InteractingState) s).initState();
			},
			(FSMContext c, IState s) -> {});
		context.registerState(interactingState);
		
		IState normalState = new NormalState(
			NAME_NORMAL_STATE,
			bot, 
			// Parent --> SubState
			// 如果線上人數 < 10，則初始狀態為預設對話狀態，否則初始狀態為互動狀態
			(FSMContext c, IState s) -> {
				if (waterballCommunity.getLoggedInMemberCount() < 10) {
					logger.debug("線上人數 < 10，切換到預設對話狀態");
					IState newState = c.getState(NAME_DEFAULT_CONVERSATION_STATE);
					c.transCurrentStateTo(newState);
				} else {
					logger.debug("線上人數 >= 10，切換到互動狀態");
					IState newState = c.getState(NAME_INTERACTING_STATE);
					c.transCurrentStateTo(newState);
				}
			},
			(FSMContext c, IState s) -> {});
		context.registerState(normalState);

		IState waitingState = new WaitingState(
			NAME_WAITING_STATE,
			bot, 
			(FSMContext c, IState s) -> {},
			(FSMContext c, IState s) -> {});
		context.registerState(waitingState);

		IState recordingState = new RecordingState(
			NAME_RECORDING_STATE,
			bot, 
			(FSMContext c, IState s) -> {
				((RecordingState) s).initState();
			},
			(FSMContext c, IState s) -> {});
		context.registerState(recordingState);

		IState recordState = new RecordState(
			NAME_RECORD_STATE,
			bot, 
			// Parent --> SubState
			(FSMContext c, IState s) -> {
				//如果已經有講者正在廣播，初始狀態為錄音中狀態，否則會直接進入等待狀態。
				if (waterballCommunity.isSomeoneBroadcasting()) {
					logger.debug("有人正在廣播，切換到錄音中狀態");
					IState newState = c.getState(NAME_RECORDING_STATE);
					c.transCurrentStateTo(newState);
				} else {
					logger.debug("沒有人正在廣播，切換到等待狀態");
					IState newState = c.getState(NAME_WAITING_STATE);
					c.transCurrentStateTo(newState);
				}
			},
			(FSMContext c, IState s) -> {});
		context.registerState(recordState);
		
				

		KnowledgeKingState knowledgeKingState = new KnowledgeKingState(
			NAME_KNOWLEDGE_KING_STATE,
			bot, 
			// Parent --> SubState
			(FSMContext c, IState s) -> {
				bot.sendNewMessageToChatRoom("KnowledgeKing is started!", new ArrayList<>());
				((KnowledgeKingState) s).initState();
				// 進入 QuestioningState
				IState newState = c.getState(NAME_QUESTIONING_STATE);
				c.transCurrentStateTo(newState);
			},
			(FSMContext c, IState s) -> {});
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
			(FSMContext c, IState s) -> {
				((QuestioningState) s).initState();
				((QuestioningState) s).showNextQuestion();
			},
			(FSMContext c, IState s) -> {});
		context.registerState(questioningState);

		ThanksForJoiningState thanksForJoiningState = new ThanksForJoiningState(
			NAME_THANKS_FOR_JOINING_STATE,
			knowledgeKingState,
			context,
			bot, 
			(FSMContext c, IState s) -> {
				((ThanksForJoiningState) s).initState();
				((ThanksForJoiningState) s).winnerAnnouncement();
			},
			(FSMContext c, IState s) -> {});
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
				botBase.event.LoginEvent.class,
				(FSMContext c, IState s, IEvent e) -> waterballCommunity.getLoggedInMemberCount() >= 10 , 
				(FSMContext c, IState s, IEvent e) -> {}, 	
				InteractingState.class);
		defaultConversationState.addTransition(loginTransition);
		
		// InteractingState 事件 Logout --> DefaultConversationState
		ITransition logoutTransition = new FSMTransition(
				botBase.event.LogoutEvent.class,
				(FSMContext c, IState s, IEvent e) -> waterballCommunity.getLoggedInMemberCount() < 10 , 
				(FSMContext c, IState s, IEvent e) -> {}, 	
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
				botBase.event.GoBroadcastingEvent.class,
				new NoOpGuard(), // Guard
				new NoOpAction(), // Action
				RecordingState.class);	
		waitingState.addTransition(goBroadcastingTransition);
		
		// RecordingState 事件 StopBroadcasting --> WaitingState
		ITransition stopBroadcastingTransition = new FSMTransition(
				botBase.event.StopBroadcastingEvent.class,
				new NoOpGuard(), // Guard
				new RecordingStateEventStopBroadcastingAction(bot, waterballCommunity),	// Action 
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
				botBase.event.AllQuestionsFinishedEvent.class,
				new NoOpGuard(), // Guard
				new NoOpAction(), // Action
				ThanksForJoiningState.class);	
		questioningState.addTransition(allQuestionsFinishedTransition);

		// QuestioningState 事件 1小時 TimeoutEvent --> ThanksForJoiningState
		ITransition questioningTimeoutTransition = new FSMTransition(
				botBase.event.TimeoutEvent.class,
				new NoOpGuard(), // Guard
				new NoOpAction(), // Action
				ThanksForJoiningState.class);	
		questioningState.addTransition(questioningTimeoutTransition);

		// ThanksForJoiningState 事件 20秒  TimeoutEvent --> NormalState
		ITransition thanksForJoiningTimeoutTransition = new FSMTransition(
				botBase.event.TimeoutEvent.class,
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
