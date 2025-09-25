package waterballCommunity.broadcast;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import waterballCommunity.Member;
import waterballCommunity.WaterballCommunity;

public class Broadcast {
	private static final Logger logger = LogManager.getLogger(Broadcast.class);
	
	private WaterballCommunity community;
	private Member broadcastingMember;
	
	public Broadcast(WaterballCommunity community) {
		this.community = community;
	}
	
	/** 開始廣播 */
	public void goBroadcasting(Member member) {
		// 同時間只能有0~1位成員在廣播
		if (this.broadcastingMember != null) {
			throw new RuntimeException("廣播中，無法進行廣播");
		}
		this.broadcastingMember = member;
		if (member.getId().equalsIgnoreCase("bot")) {
			logger.info(String.format("🤖 go broadcasting..."));
		}
		else{
			logger.info(String.format("📢 %s is broadcasting...", member.getId()));
			
			// 發送事件
			WaterballCommunityGoBroadcastingEvent event = new WaterballCommunityGoBroadcastingEvent(member);
			community.eventPublish(event);
		}
		
	}

	/** 傳遞語音訊息 */
	public void speak(Member speaker, String content) {
		if (this.broadcastingMember != null && this.broadcastingMember.getId().equals(speaker.getId())) {
			if (speaker.getId().equalsIgnoreCase("bot")) {
				logger.info(String.format("🤖 speaking: %s", content));
			}
			else{
				logger.info(String.format("📢 %s: %s", speaker.getId(), content));

				//發送事件
				WaterballCommunitySpeakEvent event = new WaterballCommunitySpeakEvent(speaker, content);
				community.eventPublish(event);
			}
		}
		else {
			throw new RuntimeException("只有「廣播」之後，才有權限傳遞語音訊息");
		}
	}

	/** 停止廣播 */
	public void stopBroadcasting(Member speaker) {
		if (this.broadcastingMember != null && this.broadcastingMember.getId().equals(speaker.getId())) {
			if (speaker.getId().equalsIgnoreCase("bot")) {
				logger.info(String.format("🤖 stop broadcasting..."));
			}
			else {
				logger.info(String.format("📢 %s stop broadcasting", speaker.getId()));
				// 發送事件
				// 結束者是speaker
				WaterballCommunityStopBroadcastingEvent event = new WaterballCommunityStopBroadcastingEvent(speaker);
				community.eventPublish(event);
			}
			this.broadcastingMember = null;
		}
		else {
			throw new RuntimeException("只有廣播者才能停止廣播");
		}
	}

	/** 檢查是否有人正在廣播 */
	public boolean isSomeoneBroadcasting() {
		return this.broadcastingMember != null;
	}
	
}
