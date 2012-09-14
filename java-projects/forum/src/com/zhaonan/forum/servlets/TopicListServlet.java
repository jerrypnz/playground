package com.zhaonan.forum.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhaonan.forum.po.Board;
import com.zhaonan.forum.service.BoardService;
import com.zhaonan.forum.service.TopicService;
import com.zhaonan.forum.vo.TopicBean;

/**
 * Servlet implementation class for Servlet: TopicListServlet
 * 
 */
public class TopicListServlet extends com.zhaonan.forum.servlets.BaseServlet
		implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;
	private int topicNumPerPage = 10;

	public TopicListServlet()
	{
		super();
	}


	@Override
	public void init() throws ServletException
	{
		super.init();
		String temp = this.getInitParameter("topicsPerPage");
		if(temp!=null && !"".equals(temp))
		{
			int t = Integer.parseInt(temp);
			topicNumPerPage = t;
		}
	}


	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		TopicService topicService = this.getServiceFactory().getTopicService();
		BoardService boardService = this.getServiceFactory().getBoardService();
		int page = 0;
		int boardId = Integer.parseInt(request.getParameter("boardid"));
		int totalNum = boardService.getTopicNum(boardId);
		Board board = boardService.get(boardId);
		request.getSession().setAttribute("current_board", board);
		int totalPages = totalNum / topicNumPerPage;
		String pageStr = request.getParameter("page");
		if(pageStr!=null && !"".equals(pageStr))
			page = Integer.parseInt(pageStr);
		if(page > 0)
			request.setAttribute("prev_page", page-1);
		if(page < totalPages)
			request.setAttribute("next_page", page+1);
		
		request.setAttribute("total_pages", totalPages+1);
		request.setAttribute("total_num", totalNum);
		request.setAttribute("current_page", page+1);
		request.setAttribute("board", board);
		List<TopicBean> topicList = topicService.getAllByBoard(boardId, page, topicNumPerPage);
		request.setAttribute("topics", topicList);
		
		this.toView("topic_list", request, response);
		
	}


	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request,response);
	}
}