package com.zhaonan.forum.vo;

import com.zhaonan.forum.po.Post;
import com.zhaonan.forum.po.User;

public class PostBean
{
	Post post;
	User user;
	/**
	 * @return the post
	 */
	public Post getPost()
	{
		return post;
	}
	/**
	 * @param post the post to set
	 */
	public void setPost(Post post)
	{
		this.post = post;
	}
	/**
	 * @return the user
	 */
	public User getUser()
	{
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user)
	{
		this.user = user;
	}
	
}
