package com.zhaonan.forum.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhaonan.forum.po.Board;
import com.zhaonan.forum.po.Post;
import com.zhaonan.forum.po.Topic;
import com.zhaonan.forum.po.User;
import com.zhaonan.forum.service.PostService;
import com.zhaonan.forum.service.TopicService;

public class SaveTopicServlet extends BaseServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		try
		{
			User user = (User) request.getSession().getAttribute("user");
			Board board = (Board) request.getSession().getAttribute(
					"current_board");
			TopicService topicService = this.getServiceFactory()
					.getTopicService();
			PostService postService = this.getServiceFactory().getPostService();
			Topic topic = new Topic();
			Post mainPost = new Post();
			topic.setTitle(request.getParameter("title"));
			topic.setUserId(user.getId());
			topic.setReadTimes(0);
			topic.setBoardId(board.getId());
			topicService.save(topic);
			System.out.println("Topic[id:" + topic.getId() + ",title:"
					+ topic.getTitle() + "] saved");
			mainPost.setContent(request.getParameter("content"));
			mainPost.setUserId(user.getId());
			mainPost.setTopicId(topic.getId());
			postService.save(mainPost);
			System.out.println("Post[id:" + mainPost.getId() + "] saved");
			request.getRequestDispatcher("topic?topicid=" + topic.getId())
					.forward(request, response);
		}
		catch (RuntimeException e)
		{
			e.printStackTrace();
			request.setAttribute("msg_title", "糟糕，发生了异常");
			request.setAttribute("msgs", new String[]
			{ "服务器端异常：" + e.getMessage() });
			toView("msg", request, response);
		}

	}

}
