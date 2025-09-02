package bot;

import java.util.ArrayList;
import java.util.Arrays;

import bot.state.DefaultConversationState;
import bot.state.InteractingState;
import bot.state.KnowledgeKingState;
import bot.state.NormalState;
import bot.state.QuestioningState;
import bot.state.RecordState;
import bot.state.RecordingState;
import bot.state.ThanksForJoiningState;
import bot.state.WaitingState;
import community.Member;
import community.Role;
import community.WaterballCommunity;
import fsm.FSMContext;
import fsm.Event;
import fsm.State;
import fsm.Transition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BotFactory {
	private static final Logger logger = LogManager.getLogger(BotFactory.class);

    public static Bot createBot(WaterballCommunity waterballCommunity, String startedTime, int initialCommandQuota) {
		
		// Bot ---------------------------------------
		FSMContext context = new FSMContext();
		Bot bot = new bot.Bot(context, waterballCommunity, startedTime, initialCommandQuota);
		
		// States --------------------------------------
		State defaultConversationState = new DefaultConversationState(
				bot, 
				(FSMContext c, State s) -> {},
				(FSMContext c, State s) -> {
					((DefaultConversationState) s).resetReplyIndex();	// Reset reply index when exiting the state
				});
		context.registerState(defaultConversationState);
		
		State interactingState = new InteractingState(
				bot, 
				(FSMContext c, State s) -> {},
				(FSMContext c, State s) -> {
					((InteractingState) s).resetReplyIndex();	// Reset reply index when exiting the state
				});
		context.registerState(interactingState);
		
		State normalState = new NormalState(
				bot, 
				// Parent --> SubState
				// 如果線上人數 < 10，則初始狀態為預設對話狀態，否則初始狀態為互動狀態
				(FSMContext c, State s) -> {
					if (waterballCommunity.getLoggedInMemberCount() < 10) {
						logger.debug("線上人數 < 10，切換到預設對話狀態");
						State newState = c.getState(defaultConversationState.getName());
						c.transCurrentStateTo(newState);
					} else {
						logger.debug("線上人數 >= 10，切換到互動狀態");
						State newState = c.getState(interactingState.getName());
						c.transCurrentStateTo(newState);
					}
				},
				(FSMContext c, State s) -> {});
		context.registerState(normalState);

		State waitingState = new WaitingState(
				bot, 
				(FSMContext c, State s) -> {},
				(FSMContext c, State s) -> {});
		context.registerState(waitingState);

		State recordingState = new RecordingState(
				bot, 
				(FSMContext c, State s) -> {
					// 進入時先重置錄音
					((RecordingState) s).resetRecordingContent();
				},
				(FSMContext c, State s) -> {
					// 離開時清空錄音
					((RecordingState) s).resetRecordingContent();
				});
		context.registerState(recordingState);

		State recordState = new RecordState(
				bot, 
				// Parent --> SubState
				(FSMContext c, State s) -> {
					//如果已經有講者正在廣播，初始狀態為錄音中狀態，否則會直接進入等待狀態。
					if (waterballCommunity.isSomeoneBroadcasting()) {
						logger.debug("有人正在廣播，切換到錄音中狀態");
						State newState = c.getState(recordingState.getName());
						c.transCurrentStateTo(newState);
					} else {
						logger.debug("沒有人正在廣播，切換到等待狀態");
						State newState = c.getState(waitingState.getName());
						c.transCurrentStateTo(newState);
					}
				},
				(FSMContext c, State s) -> {});
		context.registerState(recordState);

		QuestioningState questioningState = new QuestioningState(
				context,
				bot, 
				(FSMContext c, State s) -> {
					bot.sendNewMessageToChatRoom("KnowledgeKing is started!", new ArrayList<>());
					// 開始出題
					((QuestioningState) s).showNextQuestion();
				},
				(FSMContext c, State s) -> {});
		context.registerState(questioningState);

		ThanksForJoiningState thanksForJoiningState = new ThanksForJoiningState(
				bot, 
				(FSMContext c, State s) -> {
					((ThanksForJoiningState) s).winnerAnnouncement();
				},
				(FSMContext c, State s) -> {});
		context.registerState(thanksForJoiningState);
				
		KnowledgeKingState knowledgeKingState = new KnowledgeKingState(
				bot, 
				// Parent --> SubState
				(FSMContext c, State s) -> {
					((KnowledgeKingState) s).resetQuestioningIndex();
					// 進入 QuestioningState
					State newState = c.getState(questioningState.getName());
					c.transCurrentStateTo(newState);
				},
				(FSMContext c, State s) -> {});
		context.registerState(knowledgeKingState);
		
		// QuestioningState 需要知道 KnowledgeKingState
		questioningState.setKnowledgeKingState(knowledgeKingState);
		thanksForJoiningState.setKnowledgeKingState(knowledgeKingState);
		
		// State Composition -------------------
		defaultConversationState.setParentState(normalState);
		interactingState.setParentState(normalState);
		waitingState.setParentState(recordState);
		recordingState.setParentState(recordState);
		
		// Transition --------------------------------------

		// DefaultConversationState 事件 Login --> InteractingState
		Transition loginTransition = new Transition(
				"LoginTransition",
				bot.event.LoginEvent.class,
				(FSMContext c, State s, Event e) -> waterballCommunity.getLoggedInMemberCount() >= 10 ,  // Guard always returns true for simplicity
				(FSMContext c, State s, Event e) -> {}, 	// Action to execute on transition
				InteractingState.class);
		defaultConversationState.addTransition(loginTransition);
		
		// InteractingState 事件 Logout --> DefaultConversationState
		Transition logoutTransition = new Transition(
				"LogoutTransition",
				bot.event.LogoutEvent.class,
				(FSMContext c, State s, Event e) -> waterballCommunity.getLoggedInMemberCount() < 10 ,  // Guard always returns true for simplicity
				(FSMContext c, State s, Event e) -> {}, 	// Action to execute on transition
				DefaultConversationState.class);
		interactingState.addTransition(logoutTransition);
		
		// NormalState 指令 "king" --> KnowledgeKing
		Transition kingTransition = new Transition(
				"KingTransition",
				bot.event.NewMessageEvent.class,
				// Guard
				(FSMContext c, State s, Event e) -> {
					bot.event.NewMessageEvent event = (bot.event.NewMessageEvent)e;
					return "king".equals(event.getMessageContent()) 
							&& event.getMessageTags().stream().anyMatch(tag -> tag.equals(Bot.BOT_TAG)
							&& Arrays.asList(Role.ADMIN)
								.contains(waterballCommunity.getMemberById(event.getMessageAuthorId()).getRole())
							&& bot.isCommandQuotaEnough(5)) ; 
				},
				// Action
				(FSMContext c, State s, Event e) -> {
					bot.deductCommandQuota(5);
				}, 
				KnowledgeKingState.class);	
		normalState.addTransition(kingTransition);

		// NormalState 指令 "record" --> RecordState
		Transition recordTransition = new Transition(
				"RecordTransition",
				bot.event.NewMessageEvent.class,
				//Guard
				(FSMContext c, State s, Event e) -> {
					bot.event.NewMessageEvent event = (bot.event.NewMessageEvent)e;
					return "record".equals(event.getMessageContent()) //使用此指令的人叫做"錄音者"
							&& event.getMessageTags().stream().anyMatch(tag -> tag.equals(Bot.BOT_TAG)
							&& Arrays.asList(Role.ADMIN, Role.MEMBER)
								.contains(waterballCommunity.getMemberById(event.getMessageAuthorId()).getRole())
							&& bot.isCommandQuotaEnough(3)) ; 
				} , 
				// Action
				(FSMContext c, State s, Event e) -> {
					bot.deductCommandQuota(3);
					// 找出錄音者，並設定錄音者
					bot.event.NewMessageEvent event = (bot.event.NewMessageEvent)e;
					Member recorder = waterballCommunity.getMemberById(event.getMessageAuthorId());	//錄音者
					RecordState recStat = (RecordState)c.getState(RecordState.class.getSimpleName());
					recStat.setRecorder(recorder);
				},
				RecordState.class);	
		normalState.addTransition(recordTransition);
		
		// WaitingState 事件 GoBroadcasting --> RecordingState
		Transition goBroadcastingTransition = new Transition(
				"GoBroadcastingTransition",
				bot.event.GoBroadcastingEvent.class,
				(FSMContext c, State s, Event e) -> {return true;} , // Guard
				(FSMContext c, State s, Event e) -> {}, // Action
				RecordingState.class);	
		waitingState.addTransition(goBroadcastingTransition);
		
		// RecordingState 事件 StopBroadcasting --> WaitingState
		Transition stopBroadcastingTransition = new Transition(
				"StopBroadcastingTransition",
				bot.event.StopBroadcastingEvent.class,
				// Guard
				(FSMContext c, State s, Event e) -> {return true;} ,
				// Action 
				(FSMContext c, State s, Event e) -> {
					bot.deductCommandQuota(0);

					// @TODO 輸出錄下的所有語音訊息、標記「講者」。
					Member recorder = ((RecordState)c.getState(RecordState.class.getSimpleName())).getRecorder();
					((RecordingState)s).replayRecordingContent(recorder.getId());

				}, 
				WaitingState.class);	
		recordingState.addTransition(stopBroadcastingTransition);

		// RecordState 指令 "stop-recording" --> NormalState
		Transition stopRecordingTransitionForRecordState = new Transition(
				"StopRecordingTransitionForRecordState",
				bot.event.NewMessageEvent.class,
				// Guard
				(FSMContext c, State s, Event e) -> {
					//只有錄音者方可使用此指令
					bot.event.NewMessageEvent event = (bot.event.NewMessageEvent)e;
					return "stop-recording".equals(event.getMessageContent()) 
							&& event.getMessageTags().stream().anyMatch(tag -> tag.equals(Bot.BOT_TAG)
							&& event.getMessageAuthorId().equals(((RecordState)c.getState(RecordState.class.getSimpleName())).getRecorder().getId())	//只有錄音者方可使用此指令
							&& bot.isCommandQuotaEnough(0)) ; 
				} , 
				// Action
				(FSMContext c, State s, Event e) -> {
					// 清除錄音者
					((RecordState)s).clearRecorder();
				},
				NormalState.class);	
		recordState.addTransition(stopRecordingTransitionForRecordState);

		// RecordingState 指令 "stop-recording" --> NormalState
		Transition stopRecordingTransitionForRecordingState = new Transition(
				"StopRecordingTransitionForRecordingState",
				bot.event.NewMessageEvent.class,
				// Guard
				(FSMContext c, State s, Event e) -> {
					//只有錄音者方可使用此指令
					bot.event.NewMessageEvent event = (bot.event.NewMessageEvent)e;
					return "stop-recording".equals(event.getMessageContent()) 
							&& event.getMessageTags().stream().anyMatch(tag -> tag.equals(Bot.BOT_TAG)
							&& event.getMessageAuthorId().equals(((RecordState)c.getState(RecordState.class.getSimpleName())).getRecorder().getId())	//只有錄音者方可使用此指令
							&& bot.isCommandQuotaEnough(0)) ; 
				} , 
				// Action
				(FSMContext c, State s, Event e) -> {
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
		Transition kingStopTransition = new Transition(
				"KingStopTransition",
				bot.event.NewMessageEvent.class,
				// Guard
				(FSMContext c, State s, Event e) -> {
					bot.event.NewMessageEvent event = (bot.event.NewMessageEvent)e;
					return "king-stop".equals(event.getMessageContent()) 
							&& event.getMessageTags().stream().anyMatch(tag -> tag.equals(Bot.BOT_TAG)
							&& Arrays.asList(Role.ADMIN)
								.contains(waterballCommunity.getMemberById(event.getMessageAuthorId()).getRole())
							&& bot.isCommandQuotaEnough(0)) ; 
				} , 
				// Action
				(FSMContext c, State s, Event e) -> {
					bot.deductCommandQuota(0);
				},
				KnowledgeKingState.class);	
		knowledgeKingState.addTransition(kingStopTransition);
		
		// 指令 play-again


		// QuestioningState 事件 AllQuestionsFinishedEvent --> ThanksForJoiningState
		Transition allQuestionsFinishedTransition = new Transition(
				"AllQuestionsFinishedTransition",
				bot.event.AllQuestionsFinishedEvent.class,
				(FSMContext c, State s, Event e) -> {return true;} , // Guard
				(FSMContext c, State s, Event e) -> {}, // Action
				ThanksForJoiningState.class);	
		questioningState.addTransition(allQuestionsFinishedTransition);
		
		
		// 設置初始狀態為 NormalState
		context.transCurrentStateTo(normalState);
		
		// 機器人登入 WaterballCommunity
		bot.loginWaterballCommunity();
		
		/* 機器人配置完畢---------------------------------------------------- */
		return bot;
	}

}
