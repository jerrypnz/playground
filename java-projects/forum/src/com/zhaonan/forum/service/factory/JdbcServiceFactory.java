package com.zhaonan.forum.service.factory;

import com.zhaonan.forum.service.BoardService;
import com.zhaonan.forum.service.PostService;
import com.zhaonan.forum.service.TopicService;
import com.zhaonan.forum.service.UserService;
import com.zhaonan.forum.service.impl.JdbcBoardServiceImpl;
import com.zhaonan.forum.service.impl.JdbcPostServiceImpl;
import com.zhaonan.forum.service.impl.JdbcTopicServiceImpl;
import com.zhaonan.forum.service.impl.JdbcUserServiceImpl;

public class JdbcServiceFactory implements ServiceFactory
{

	@Override
	public BoardService getBoardService()
	{
		return new JdbcBoardServiceImpl();
	}

	@Override
	public PostService getPostService()
	{
		return new JdbcPostServiceImpl();
	}

	@Override
	public TopicService getTopicService()
	{
		return new JdbcTopicServiceImpl();
	}

	@Override
	public UserService getUserService()
	{
		return new JdbcUserServiceImpl();
	}

}
