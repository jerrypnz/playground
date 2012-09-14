package com.zhaonan.forum.service;

import java.util.List;

import com.zhaonan.forum.po.Topic;
import com.zhaonan.forum.vo.TopicBean;

public interface TopicService
{
	void save(final Topic topic);
	void update(final Topic topic);
	void delete(final int topicId);
	Topic get(final int topicId);
	Topic getLatestByBoard(final int boardId);
	List<TopicBean> getAllByBoard(final int boardId,int pageIndex,int pageNum);
	int getPostNum(final int topicId);
	int getPostNumInBoard(final int boardId);
}
