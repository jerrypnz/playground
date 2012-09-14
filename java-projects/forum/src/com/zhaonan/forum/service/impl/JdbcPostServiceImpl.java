package com.zhaonan.forum.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zhaonan.forum.po.Post;
import com.zhaonan.forum.service.PostService;
import com.zhaonan.forum.service.UserService;
import com.zhaonan.forum.util.DatabaseUtil;
import com.zhaonan.forum.vo.PostBean;

public class JdbcPostServiceImpl implements PostService
{

	@Override
	public void delete(final int postId)
	{
		new JdbcTemplate().doTask(new JdbcTask()
		{
			@SuppressWarnings("finally")
			public Object executeTask(Connection conn)
			{
				try
				{
					String sql = "delete from t_post where id=?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setInt(1, postId);
					stmt.executeUpdate();
					stmt.close();
				}
				catch (SQLException ex)
				{
					Logger.getLogger(JdbcUserServiceImpl.class.getName()).log(
							Level.SEVERE, null, ex);
				}
				return null;
			}
		});

	}

	@SuppressWarnings("unchecked")
	public List<Post> query(final String sql)
	{
		final List<Post> temp = (List<Post>) new JdbcTemplate()
				.doTask(new JdbcTask()
				{
					public Object executeTask(Connection conn)
					{
						Post temp = null;
						List<Post> postList = new ArrayList<Post>();
						try
						{
							PreparedStatement stmt = conn.prepareStatement(sql);
							ResultSet result = stmt.executeQuery();
							while (result.next())
							{
								temp = new Post();
								temp.setId(result.getInt("id"));
								temp.setTopicId(result.getInt("topic_id"));
								temp.setUserId(result.getInt("user_id"));
								temp.setContent(result.getString("content"));
								temp.setPostTime(result
										.getTimestamp("post_time"));
								postList.add(temp);
							}
							stmt.close();
						}
						catch (SQLException ex)
						{
							Logger.getLogger(
									JdbcUserServiceImpl.class.getName()).log(
									Level.SEVERE, null, ex);
						}
						return postList;
					}
				});

		return temp;
	}

	@Override
	public void save(final Post post)
	{
		new JdbcTemplate().doTask(new JdbcTask()
		{
			@SuppressWarnings("finally")
			public Object executeTask(Connection conn)
			{
				try
				{
					String sql = "insert into t_post(content,user_id,topic_id,post_time) values(?,?,?,?)";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, post.getContent());
					stmt.setInt(2, post.getUserId());
					stmt.setInt(3, post.getTopicId());
					if (post.getPostTime() == null)
						post.setPostTime(new Timestamp(System
								.currentTimeMillis()));
					stmt.setTimestamp(4, post.getPostTime());
					stmt.executeUpdate();
					String getId = DatabaseUtil.getInstance().getLastIdFunc();
					Statement st2 = conn.createStatement();
					ResultSet set = st2.executeQuery(getId);
					if (set.next())
					{
						post.setId(set.getInt(1));
					}
					stmt.close();
					st2.close();
				}
				catch (SQLException ex)
				{
					Logger.getLogger(JdbcUserServiceImpl.class.getName()).log(
							Level.SEVERE, null, ex);
				}
				return null;
			}
		});

	}

	@Override
	public void update(final Post post)
	{
		new JdbcTemplate().doTask(new JdbcTask()
		{
			@SuppressWarnings("finally")
			public Object executeTask(Connection conn)
			{
				try
				{
					String sql = "update t_post set content=? where id=?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, post.getContent());
					stmt.setInt(2, post.getId());
					stmt.executeUpdate();
					stmt.close();
				}
				catch (SQLException ex)
				{
					Logger.getLogger(JdbcUserServiceImpl.class.getName()).log(
							Level.SEVERE, null, ex);
				}
				return null;
			}
		});

	}

	@Override
	public Post get(final int id)
	{
		Post temp = (Post) new JdbcTemplate().doTask(new JdbcTask()
		{

			public Object executeTask(Connection conn)
			{
				Post temp = null;
				try
				{
					String sql = "select * from t_post where id = ?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setInt(1, id);
					ResultSet result = stmt.executeQuery();
					if (result.next())
					{
						temp = new Post();
						temp.setId(result.getInt("id"));
						temp.setTopicId(result.getInt("topic_id"));
						temp.setUserId(result.getInt("user_id"));
						temp.setContent(result.getString("content"));
						temp.setPostTime(result.getTimestamp("post_time"));
					}
					stmt.close();
				}
				catch (SQLException ex)
				{
					Logger.getLogger(JdbcUserServiceImpl.class.getName()).log(
							Level.SEVERE, null, ex);
				}
				return temp;
			}
		});
		return temp;
	}

	@Override
	public List<PostBean> getPostsByTopic(final int topicId, int pageIndex,
			int pageNum)
	{
		UserService userService = new JdbcUserServiceImpl();
		List<Post> posts = query("select * from t_post where topic_id="
				+ topicId + " order by post_time limit " + pageIndex * pageNum
				+ "," + pageNum);
		List<PostBean> result = new ArrayList<PostBean>();
		for(Post p:posts)
		{
			PostBean bean = new PostBean();
			bean.setPost(p);
			bean.setUser(userService.get(p.getUserId()));
			result.add(bean);
		}
		return result;
	}

}
