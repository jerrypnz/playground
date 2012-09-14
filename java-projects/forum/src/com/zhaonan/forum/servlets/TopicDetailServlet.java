package com.zhaonan.forum.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhaonan.forum.po.Board;
import com.zhaonan.forum.po.Topic;
import com.zhaonan.forum.service.BoardService;
import com.zhaonan.forum.service.PostService;
import com.zhaonan.forum.service.TopicService;
import com.zhaonan.forum.vo.PostBean;

/**
 * Servlet implementation class for Servlet: TopicDetailServlet
 * 
 */
public class TopicDetailServlet extends com.zhaonan.forum.servlets.BaseServlet
		implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;
	private int postNumPerPage = 10;

	public TopicDetailServlet()
	{
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		TopicService topicService = this.getServiceFactory().getTopicService();
		PostService postService = this.getServiceFactory().getPostService();
		BoardService boardService = this.getServiceFactory().getBoardService();
		int page = 0;
		int topicId = Integer.parseInt(request.getParameter("topicid"));
		int totalNum = topicService.getPostNum(topicId);
		int totalPages = totalNum / postNumPerPage;
		String pageStr = request.getParameter("page");
		if(pageStr!=null && !"".equals(pageStr))
			page = Integer.parseInt(pageStr);
	
		Topic topic = topicService.get(topicId);
		if(request.getSession().getAttribute("current_board") == null)
		{
			Board board = boardService.get(topic.getBoardId());
			request.getSession().setAttribute("current_board", board);
		}
		List<PostBean> postList = postService.getPostsByTopic(topicId, page, postNumPerPage);
		request.setAttribute("topic", topic);
		request.setAttribute("posts", postList);
		if(page > 0)
			request.setAttribute("prev_page", page-1);
		if(page < totalPages)
			request.setAttribute("next_page", page+1);		
		request.setAttribute("total_pages", totalPages+1);
		request.setAttribute("total_num", totalNum);
		request.setAttribute("current_page", page+1);	
		
		this.toView("topic_detail", request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}