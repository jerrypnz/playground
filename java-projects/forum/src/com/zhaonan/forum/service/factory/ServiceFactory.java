package com.zhaonan.forum.service.factory;

import com.zhaonan.forum.service.BoardService;
import com.zhaonan.forum.service.PostService;
import com.zhaonan.forum.service.TopicService;
import com.zhaonan.forum.service.UserService;

public interface ServiceFactory
{
	UserService getUserService();
	BoardService getBoardService();
	TopicService getTopicService();
	PostService getPostService();
}
