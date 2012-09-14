package com.zhaonan.forum.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhaonan.forum.service.BoardService;
import com.zhaonan.forum.vo.BoardBean;

/**
 * Servlet implementation class for Servlet: BoardListServlet
 * 
 */
public class BoardListServlet extends com.zhaonan.forum.servlets.BaseServlet
		implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;

	public BoardListServlet()
	{
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		BoardService service = this.getServiceFactory().getBoardService();
		List<BoardBean> boards = service.getAll();
		request.setAttribute("boards", boards);
		this.toView("board_list", request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

}