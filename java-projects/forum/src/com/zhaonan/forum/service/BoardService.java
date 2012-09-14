package com.zhaonan.forum.service;

import java.util.List;

import com.zhaonan.forum.po.Board;
import com.zhaonan.forum.vo.BoardBean;

public interface BoardService
{
	void save(final Board board);
	void update(final Board board);
	void delete(final int boardId);
	int getTopicNum(final int boardId);
	List<BoardBean> getAll();
	Board get(final int boardId);
}
