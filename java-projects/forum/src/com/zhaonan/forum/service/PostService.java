package com.zhaonan.forum.service;

import java.util.List;

import com.zhaonan.forum.po.Post;
import com.zhaonan.forum.vo.PostBean;

public interface PostService
{
	void save(final Post post);
	Post get(final int id);
	void update(final Post post);
	void delete(final int postId);
	List<PostBean> getPostsByTopic(final int id, int pageIndex, int pageNum);
}
