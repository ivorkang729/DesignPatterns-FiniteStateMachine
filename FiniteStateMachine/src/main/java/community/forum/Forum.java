package community.forum;

import community.WaterballCommunity;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Forum {
	private static final Logger logger = LogManager.getLogger(Forum.class);
	
	private WaterballCommunity community;
	private int currentPostId = 1;
	
	public Forum(WaterballCommunity community) {
		this.community = community;
	}
	
	/** 有人在論壇發新貼文 */
	public void newPost(Post post) {
		if (StringUtils.isBlank(post.getId())){
			post.setId(String.valueOf(currentPostId++));
		}
		logger.info(post);
		if (!"bot".equals(post.getAuthorId())) {
			WaterballCommunityNewPostEvent event = new WaterballCommunityNewPostEvent(post);
			community.eventPublish(event);
		}
	}

	/** 有人在貼文下貼新留言 */
	public void newComment(Comment comment) {		
		logger.info(comment);
	}

}
