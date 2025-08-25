package community;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import community.broadcast.Broadcast;
import community.chatroom.ChatRoom;
import community.chatroom.Message;
import community.forum.Comment;
import community.forum.Forum;
import community.forum.Post;

// TODO 權限控管: 大部分的操作都要檢查是否有登入
public class WaterballCommunity {
	private static final Logger logger = LogManager.getLogger(WaterballCommunity.class);
	
	private List<Member> loggedInMembers = new LinkedList<>();
	private ChatRoom chatRoom;
	private Forum forum;
	private Broadcast broadcast;
	private Set<WaterballCommunityEventListener> eventListeners = new HashSet<>();
	
	public WaterballCommunity() {
		this.chatRoom = new ChatRoom(this);
		this.forum = new Forum(this);
		this.broadcast = new Broadcast(this);
	}
	
	/** 登入 */
	public Member login(String userId, boolean isAdmin) {
		Member member = new Member(userId, isAdmin ? Role.ADMIN : Role.MEMBER);
		if (loggedInMembers.contains(member)) {
			throw new IllegalArgumentException("Member with ID " + userId + " is already logged in.");
		}
		loggedInMembers.add(member);
		logger.debug("Member " + userId + " logged in as " + member.getRole().getDisplayName() + ".");
		
		// Trigger login event
		WaterballCommunityEvent event = new WaterballCommunityLoginEvent(member);
		eventPublish(event);
		
		return member;
	}
	
	/** 登出 */
	public void logout(String userId) {
		
		Member member = loggedInMembers.stream()
			.filter(m -> m.getId().equals(userId))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Member with ID " + userId + " is not logged in."));

		loggedInMembers.remove(member);
		logger.debug("Member " + userId + " logged out.");
		
		// Trigger logout event
		WaterballCommunityEvent event = new WaterballCommunityLogoutEvent(member);
		eventPublish(event);
	}
	
	/** 取得已登入成員數 */
	public int getLoggedInMemberCount() {
		return loggedInMembers.size();
	}
	
	/** 取得已登入成員物件 */
	public Member getMemberById(String memberId) {
		Member member = loggedInMembers.stream()
			.filter(m -> m.getId().equals(memberId))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Member with ID " + memberId + " is not logged in."));
		return member;
	}

	/** 取得已登入成員列表 */
	public List<Member> getLoggedInMembers() {
		return new ArrayList<>(loggedInMembers);
	}

	/** 註冊事件監聽器 */
	public void registerEventListener(WaterballCommunityEventListener listener) {
		eventListeners.add(listener);
	}
	
	/** 發佈事件給所有的 EventListeners */
	public void eventPublish(WaterballCommunityEvent event) {
		for (WaterballCommunityEventListener eventListener : eventListeners) {
			eventListener.onEvent(event);
		}
	}

	/** 有人在聊天室發送新訊息 */
	public void newMessage(Message message) {
		chatRoom.newMessage(message);
	}

	public void newMessage(String authorId, String content, String... tagIds) {
		Message message = new Message(authorId, content);
		message.addTagsByIds(List.of(tagIds));
		chatRoom.newMessage(message);
	}

	/** 有人在論壇發新貼文 */
	public void newPost(Post post) {
		forum.newPost(post);
	}

	public void newPost(String id, String authorId, String title, String content, String... tagIds) {
		Post post = new Post(id, authorId, title, content);
		post.addTagsByIds(List.of(tagIds));
		forum.newPost(post);
	}

	/** 有人在貼文下貼新留言 */
	public void newComment(Comment comment) {
		forum.newComment(comment);
	}	

	/** 開始廣播 */
	public void goBroadcasting(Member member) {
		broadcast.goBroadcasting(member);
	}

	/** 傳遞語音訊息 */
	public void speak(Member speaker, String content){
		broadcast.speak(speaker, content);
	}

	/** 停止廣播 */
	public void stopBroadcasting(Member speaker){
		broadcast.stopBroadcasting(speaker);
	}
	
	/** 檢查是否有人正在廣播 */
	public boolean isSomeoneBroadcasting() {
		return broadcast.isSomeoneBroadcasting();
	}
}
