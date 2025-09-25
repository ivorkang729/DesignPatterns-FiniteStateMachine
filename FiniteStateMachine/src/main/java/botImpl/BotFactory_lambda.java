package botImpl;

import java.util.ArrayList;
import java.util.Arrays;

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
import botImpl.state.normal.NormalState;
import botImpl.state.record.RecordState;
import botImpl.state.record.RecordStateCommandStopRecordingTransition;
import botImpl.state.recording.RecordingState;
import botImpl.state.thanksForJoining.ThanksForJoiningState;
import fsm.IEvent;
import fsm.FSMContext;
import fsm.FSMTransition;
import fsm.IState;
import fsm.ITransition;
import waterballCommunity.Member;
import waterballCommunity.Role;
import waterballCommunity.WaterballCommunity;

public class BotFactory_lambda {
	private static final Logger logger = LogManager.getLogger(BotFactory_lambda.class);

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
		ITransition kingTransition = new FSMTransition(
				botBase.event.NewMessageEvent.class,
				// Guard
				(FSMContext c, IState s, IEvent e) -> {
					botBase.event.NewMessageEvent event = (botBase.event.NewMessageEvent)e;
					return "king".equals(event.getMessageContent()) 
							&& event.getMessageTags().stream().anyMatch(tag -> tag.equals(Bot.BOT_TAG)
							&& Arrays.asList(Role.ADMIN)
								.contains(waterballCommunity.getMemberById(event.getMessageAuthorId()).getRole())
							&& bot.isCommandQuotaEnough(5)) ; 
				},
				// Action
				(FSMContext c, IState s, IEvent e) -> {
					bot.deductCommandQuota(5);
				}, 
				KnowledgeKingState.class);	
		normalState.addTransition(kingTransition);

		// NormalState 指令 "record" --> RecordState
		ITransition recordTransition = new FSMTransition(
				botBase.event.NewMessageEvent.class,
				//Guard
				(FSMContext c, IState s, IEvent e) -> {
					botBase.event.NewMessageEvent event = (botBase.event.NewMessageEvent)e;
					return "record".equals(event.getMessageContent()) //使用此指令的人叫做"錄音者"
							&& event.getMessageTags().stream().anyMatch(tag -> tag.equals(Bot.BOT_TAG)
							&& Arrays.asList(Role.ADMIN, Role.MEMBER)
								.contains(waterballCommunity.getMemberById(event.getMessageAuthorId()).getRole())
							&& bot.isCommandQuotaEnough(3)) ; 
				} , 
				// Action
				(FSMContext c, IState s, IEvent e) -> {
					bot.deductCommandQuota(3);
					// 找出錄音者，並設定錄音者
					botBase.event.NewMessageEvent event = (botBase.event.NewMessageEvent)e;
					Member recorder = waterballCommunity.getMemberById(event.getMessageAuthorId());	//錄音者
					RecordState recStat = (RecordState)c.getState(RecordState.class.getSimpleName());
					recStat.setRecorder(recorder);
				},
				RecordState.class);	
		normalState.addTransition(recordTransition);
		
		// WaitingState 事件 GoBroadcasting --> RecordingState
		ITransition goBroadcastingTransition = new FSMTransition(
				botBase.event.GoBroadcastingEvent.class,
				(FSMContext c, IState s, IEvent e) -> {return true;} , // Guard
				(FSMContext c, IState s, IEvent e) -> {}, // Action
				RecordingState.class);	
		waitingState.addTransition(goBroadcastingTransition);
		
		// RecordingState 事件 StopBroadcasting --> WaitingState
		ITransition stopBroadcastingTransition = new FSMTransition(
				botBase.event.StopBroadcastingEvent.class,
				// Guard
				(FSMContext c, IState s, IEvent e) -> {return true;} ,
				// Action 
				(FSMContext c, IState s, IEvent e) -> {
					bot.deductCommandQuota(0);

					// 輸出錄下的所有語音訊息、標記「講者」。
					Member recorder = ((RecordState)c.getState(RecordState.class.getSimpleName())).getRecorder();
					((RecordingState)s).replayRecordingContent(recorder.getId());

				}, 
				WaitingState.class);	
		recordingState.addTransition(stopBroadcastingTransition);

		// RecordState 指令 "stop-recording" --> NormalState
		ITransition stopRecordingTransitionForRecordState = new FSMTransition(
				botBase.event.NewMessageEvent.class,
				// Guard
				(FSMContext c, IState s, IEvent e) -> {
					//只有錄音者方可使用此指令
					botBase.event.NewMessageEvent event = (botBase.event.NewMessageEvent)e;
					return "stop-recording".equals(event.getMessageContent()) 
			              && event.getMessageTags().stream().anyMatch(tag -> tag.equals(Bot.BOT_TAG)
			              && event.getMessageAuthorId().equals(((RecordState)c.getState(RecordState.class.getSimpleName())).getRecorder().getId())    //只有錄音者方可使用此指令
			              && bot.isCommandQuotaEnough(0)) ; 
				} , 
				// Action
				(FSMContext c, IState s, IEvent e) -> {
					// 清除錄音者
					((RecordState)s).clearRecorder();
				},
				NormalState.class);    

		recordState.addTransition(stopRecordingTransitionForRecordState);

		// RecordingState 指令 "stop-recording" --> NormalState
		ITransition stopRecordingTransitionForRecordingState = new FSMTransition(
				botBase.event.NewMessageEvent.class,
				// Guard
				(FSMContext c, IState s, IEvent e) -> {
					//只有錄音者方可使用此指令
					botBase.event.NewMessageEvent event = (botBase.event.NewMessageEvent)e;
					return "stop-recording".equals(event.getMessageContent()) 
							&& event.getMessageTags().stream().anyMatch(tag -> tag.equals(Bot.BOT_TAG)
							&& event.getMessageAuthorId().equals(((RecordState)c.getState(RecordState.class.getSimpleName())).getRecorder().getId())	//只有錄音者方可使用此指令
							&& bot.isCommandQuotaEnough(0)) ; 
				} , 
				// Action
				(FSMContext c, IState s, IEvent e) -> {
					bot.deductCommandQuota(0);
					// 輸出錄下的所有語音訊息、標記「錄音者」。
					Member recorder = ((RecordState)c.getState(RecordState.class.getSimpleName())).getRecorder();
					((RecordingState)s).replayRecordingContent(recorder.getId());
					
					// 清除錄音者
					((RecordState)c.getState(RecordState.class.getSimpleName())).clearRecorder();
				},
				NormalState.class);	
		recordingState.addTransition(stopRecordingTransitionForRecordingState);


		// KnowledgeKingState 指令 "king-stop" --> Normal
		ITransition kingStopTransition = new FSMTransition(
				botBase.event.NewMessageEvent.class,
				// Guard
				(FSMContext c, IState s, IEvent e) -> {
					botBase.event.NewMessageEvent event = (botBase.event.NewMessageEvent)e;
					return "king-stop".equals(event.getMessageContent()) 
							&& event.getMessageTags().stream().anyMatch(tag -> tag.equals(Bot.BOT_TAG)
							&& Arrays.asList(Role.ADMIN)
								.contains(waterballCommunity.getMemberById(event.getMessageAuthorId()).getRole())
							&& bot.isCommandQuotaEnough(0)) ; 
				} , 
				// Action
				(FSMContext c, IState s, IEvent e) -> {
					bot.deductCommandQuota(0);
				},
				NormalState.class);	
		knowledgeKingState.addTransition(kingStopTransition);
		

		// 指令 play again
		ITransition playAgainTransition = new FSMTransition(
				botBase.event.NewMessageEvent.class,
				// Guard
				(FSMContext c, IState s, IEvent e) -> {
					botBase.event.NewMessageEvent event = (botBase.event.NewMessageEvent)e;
					return "play again".equals(event.getMessageContent()) 
							&& event.getMessageTags().stream().anyMatch(tag -> tag.equals(Bot.BOT_TAG)
							&& Arrays.asList(Role.ADMIN, Role.MEMBER)
								.contains(waterballCommunity.getMemberById(event.getMessageAuthorId()).getRole())
							&& bot.isCommandQuotaEnough(5)) ; 
				} , 
				// Action
				(FSMContext c, IState s, IEvent e) -> {
					bot.deductCommandQuota(5);
					bot.sendNewMessageToChatRoom("KnowledgeKing is gonna start again!", new ArrayList<>());
				},
				QuestioningState.class);	
		thanksForJoiningState.addTransition(playAgainTransition);


		// QuestioningState 事件 AllQuestionsFinishedEvent --> ThanksForJoiningState
		ITransition allQuestionsFinishedTransition = new FSMTransition(
				botBase.event.AllQuestionsFinishedEvent.class,
				(FSMContext c, IState s, IEvent e) -> {return true;} , // Guard
				(FSMContext c, IState s, IEvent e) -> {}, // Action
				ThanksForJoiningState.class);	
		questioningState.addTransition(allQuestionsFinishedTransition);

		// QuestioningState 事件 1小時 TimeoutEvent --> ThanksForJoiningState
		ITransition questioningTimeoutTransition = new FSMTransition(
				botBase.event.TimeoutEvent.class,
				(FSMContext c, IState s, IEvent e) -> {return true;} , // Guard
				(FSMContext c, IState s, IEvent e) -> {}, // Action
				ThanksForJoiningState.class);	
		questioningState.addTransition(questioningTimeoutTransition);

		// ThanksForJoiningState 事件 20秒  TimeoutEvent --> NormalState
		ITransition thanksForJoiningTimeoutTransition = new FSMTransition(
				botBase.event.TimeoutEvent.class,
				(FSMContext c, IState s, IEvent e) -> {return true;} , // Guard
				(FSMContext c, IState s, IEvent e) -> {}, // Action
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
