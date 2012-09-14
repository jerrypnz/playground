package com.zhaonan.forum.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zhaonan.forum.po.Board;
import com.zhaonan.forum.po.Topic;
import com.zhaonan.forum.service.BoardService;
import com.zhaonan.forum.service.TopicService;
import com.zhaonan.forum.util.DatabaseUtil;
import com.zhaonan.forum.vo.BoardBean;

public class JdbcBoardServiceImpl implements BoardService
{

	@Override
	public void delete(final int boardId)
	{
		new JdbcTemplate().doTask(new JdbcTask()
		{

			@SuppressWarnings("finally")
			public Object executeTask(Connection conn)
			{
				try
				{
					String sql = "delete from t_board where id=?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setInt(1, boardId);
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
	public List<Board> query(final String sql)
	{
		final List<Board> temp = (List<Board>) new JdbcTemplate()
				.doTask(new JdbcTask()
				{
					public Object executeTask(Connection conn)
					{
						Board temp = null;
						List<Board> boardList = new ArrayList<Board>();
						try
						{
							PreparedStatement stmt = conn.prepareStatement(sql);
							ResultSet result = stmt.executeQuery();
							while (result.next())
							{
								temp = new Board();
								temp.setId(result.getInt("id"));
								temp.setName(result.getString("name"));
								temp.setDescription(result
										.getString("description"));
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
	public void save(final Board board)
	{
		new JdbcTemplate().doTask(new JdbcTask()
		{
			@SuppressWarnings("finally")
			public Object executeTask(Connection conn)
			{
				try
				{
					String sql = "insert into t_board(name,description) values(?,?)";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, board.getName());
					stmt.setString(2, board.getDescription());
					stmt.executeUpdate();
					String getId = DatabaseUtil.getInstance().getLastIdFunc();
					Statement st2 = conn.createStatement();
					ResultSet set = st2.executeQuery(getId);
					if (set.next())
					{
						board.setId(set.getInt(1));
					}
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
	public void update(final Board board)
	{
		new JdbcTemplate().doTask(new JdbcTask()
		{
			@SuppressWarnings("finally")
			public Object executeTask(Connection conn)
			{
				try
				{
					String sql = "update t_board set name=?,description=? where id=?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, board.getName());
					stmt.setString(2, board.getDescription());
					stmt.setInt(3, board.getId());
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
	public int getTopicNum(final int boardId)
	{
		Integer temp = (Integer) new JdbcTemplate().doTask(new JdbcTask()
		{

			public Object executeTask(Connection conn)
			{
				int temp = 0;
				try
				{
					String sql = "select count(*) as topic_num from t_topic where board_id=?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setInt(1, boardId);
					ResultSet result = stmt.executeQuery();
					if (result.next())
					{
						temp = result.getInt("topic_num");
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
	public List<BoardBean> getAll()
	{
		List<BoardBean> result = new ArrayList<BoardBean>();
		List<Board> boards = this.query("select * from t_board");
		TopicService topicService = new JdbcTopicServiceImpl();
		for (Board b : boards)
		{
			BoardBean boardBean = new BoardBean();
			boardBean.setId(b.getId());
			boardBean.setName(b.getName());
			boardBean.setDescription(b.getDescription());
			boardBean.setTopicNum(this.getTopicNum(b.getId()));
			boardBean.setPostNum(topicService.getPostNumInBoard(b.getId()));
			Topic latest = topicService.getLatestByBoard(b.getId());
			if (latest != null)
			{
				boardBean.setLatestTopicId(latest.getId());
				boardBean.setLatestTopicTitle(latest.getTitle());
			}
			result.add(boardBean);
		}
		return result;
	}

	@Override
	public Board get(int boardId)
	{
		Board board = null;
		List<Board> temp = this.query("select * from t_board where id=" + boardId);
		if(temp.size()>0)
			board = temp.get(0);
		return board;
	}
}
