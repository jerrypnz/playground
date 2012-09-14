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

import com.zhaonan.forum.po.Topic;
import com.zhaonan.forum.service.TopicService;
import com.zhaonan.forum.service.UserService;
import com.zhaonan.forum.util.DatabaseUtil;
import com.zhaonan.forum.vo.TopicBean;

public class JdbcTopicServiceImpl implements TopicService
{

	@Override
	public void delete(final int topicId)
	{
		new JdbcTemplate().doTask(new JdbcTask()
		{
			@SuppressWarnings("finally")
			public Object executeTask(Connection conn)
			{
				try
				{
					String sql = "delete from t_topic where id=?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setInt(1, topicId);
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
	public List<Topic> query(final String sql)
	{
		final List<Topic> temp = (List<Topic>) new JdbcTemplate()
				.doTask(new JdbcTask()
				{
					public Object executeTask(Connection conn)
					{
						Topic temp = null;
						List<Topic> boardList = new ArrayList<Topic>();
						try
						{
							PreparedStatement stmt = conn.prepareStatement(sql);
							ResultSet result = stmt.executeQuery();
							while (result.next())
							{
								temp = new Topic();
								temp.setId(result.getInt("id"));
								temp.setBoardId(result.getInt("board_id"));
								temp.setUserId(result.getInt("user_id"));
								temp.setTitle(result.getString("title"));
								temp.setPostTime(result
										.getTimestamp("post_time"));
								temp.setReadTimes(result.getInt("read_times"));
								boardList.add(temp);
							}
							stmt.close();

						}
						catch (SQLException ex)
						{
							Logger.getLogger(
									JdbcUserServiceImpl.class.getName()).log(
									Level.SEVERE, null, ex);
						}
						return boardList;
					}
				});

		return temp;
	}

	@Override
	public void save(final Topic topic)
	{
		new JdbcTemplate().doTask(new JdbcTask()
		{
			@SuppressWarnings("finally")
			public Object executeTask(Connection conn)
			{
				try
				{
					String sql = "insert into t_topic(title,user_id,board_id,post_time) values(?,?,?,?)";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, topic.getTitle());
					stmt.setInt(2, topic.getUserId());
					stmt.setInt(3, topic.getBoardId());
					if (topic.getPostTime() == null)
						topic.setPostTime(new Timestamp(System
								.currentTimeMillis()));
					stmt.setTimestamp(4, topic.getPostTime());
					stmt.executeUpdate();
					String getId = DatabaseUtil.getInstance().getLastIdFunc();
					Statement st2 = conn.createStatement();
					ResultSet set = st2.executeQuery(getId);
					if (set.next())
					{
						topic.setId(set.getInt(1));
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
	public void update(final Topic topic)
	{
		new JdbcTemplate().doTask(new JdbcTask()
		{
			@SuppressWarnings("finally")
			public Object executeTask(Connection conn)
			{
				try
				{
					String sql = "update t_topic set title=?,user_id=?,board_id=?, read_times=? where id=?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, topic.getTitle());
					stmt.setInt(2, topic.getUserId());
					stmt.setInt(3, topic.getBoardId());
					stmt.setInt(4, topic.getReadTimes());
					stmt.setInt(5, topic.getId());
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
	public Topic get(final int topicId)
	{
		Topic temp = (Topic) new JdbcTemplate().doTask(new JdbcTask()
		{

			public Object executeTask(Connection conn)
			{
				Topic temp = null;
				try
				{
					String sql = "select * from t_topic where id = ?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setInt(1, topicId);
					ResultSet result = stmt.executeQuery();
					if (result.next())
					{
						temp = new Topic();
						temp.setId(result.getInt("id"));
						temp.setBoardId(result.getInt("board_id"));
						temp.setUserId(result.getInt("user_id"));
						temp.setTitle(result.getString("title"));
						temp.setPostTime(result.getTimestamp("post_time"));
						temp.setReadTimes(result.getInt("read_times"));
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
	public int getPostNum(final int topicId)
	{
		Integer temp = (Integer) new JdbcTemplate().doTask(new JdbcTask()
		{

			public Object executeTask(Connection conn)
			{
				int temp = 0;
				try
				{
					String sql = "select count(*) as post_num from t_post where topic_id=?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setInt(1, topicId);
					ResultSet result = stmt.executeQuery();
					if (result.next())
					{
						temp = result.getInt("post_num");
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
	public int getPostNumInBoard(final int boardId)
	{
		Integer temp = (Integer) new JdbcTemplate().doTask(new JdbcTask()
		{

			public Object executeTask(Connection conn)
			{
				int temp = 0;
				try
				{
					String sql = "select count(*) as post_num from t_post,t_topic where board_id=? and t_topic.id = t_post.topic_id";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setInt(1, boardId);
					ResultSet result = stmt.executeQuery();
					if (result.next())
					{
						temp = result.getInt("post_num");
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
	public List<TopicBean> getAllByBoard(int boardId, int pageIndex, int pageNum)
	{
		List<TopicBean> result = new ArrayList<TopicBean>();
		List<Topic> temp = query("select * from t_topic where board_id="
				+ boardId
				+ " order by priority desc,post_time desc limit " + pageIndex
				* pageNum + "," + pageNum);
		UserService userService = new JdbcUserServiceImpl();
		for (Topic t : temp)
		{
			TopicBean bean = new TopicBean();
			bean.setId(t.getId());
			bean.setBoardId(t.getBoardId());
			bean.setTitle(t.getTitle());
			bean.setAuthorId(t.getUserId());
			bean.setAuthorName(userService.get(t.getUserId()).getNickName());
			bean.setPostTime(t.getPostTime());
			bean.setReadTimes(t.getReadTimes());
			bean.setReplyTimes(this.getPostNum(t.getId()));
			bean.setPriority(t.getPriority());
			result.add(bean);
		}
		return result;
	}

	@Override
	public Topic getLatestByBoard(int boardId)
	{
		Topic result = null;
		List<Topic> temp = query("select * from t_topic where board_id="
				+ boardId + " order by post_time desc limit 1");
		if (temp.size() > 0)
		{
			result = temp.get(0);
		}
		return result;
	}

}
