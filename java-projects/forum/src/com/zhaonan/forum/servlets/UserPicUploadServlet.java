package com.zhaonan.forum.servlets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.zhaonan.forum.po.User;

/**
 * Servlet implementation class for Servlet: UserPicUploadServlet
 * 
 */
public class UserPicUploadServlet extends BaseServlet
{
	static final long serialVersionUID = 1L;

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public UserPicUploadServlet()
	{
		super();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		List<String> errors = new ArrayList<String>();
		User user = (User)request.getSession().getAttribute("user");
		if(user == null)
		{
			request.setAttribute("errors",new String[]{
					"请先登录再使用本功能"
				});
			request.getRequestDispatcher("login.jsp").forward(request,response);
			return;
		}
		String userPicDir = this.getServletContext().getRealPath("/UserFiles/UserFaces");
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if(!isMultipart)
		{
			errors.add("没有选择上传文件");
			request.setAttribute("errors", errors);
			this.toView("edit_profile", request, response);
			return;
		}
		
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try
		{
			List<FileItem> items = (List<FileItem>)upload.parseRequest(request);
			if(items == null || items.size()<=0)
			{
				errors.add("没有选择上传文件");
				request.setAttribute("errors", errors);
				this.toView("edit_profile", request, response);
				return;
			}
			FileItem item = items.get(0);
			if(item == null || item.isFormField())
			{
				errors.add("没有选择上传文件");
				request.setAttribute("errors", errors);
				this.toView("edit_profile", request, response);
				return;
			}
			File userPicFile = new File(userPicDir + "/" + user.getId());
			item.write(userPicFile);
			this.toView("edit_profile", request, response);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			errors.add("上传文件失败(服务器内部错误)");
			request.setAttribute("errors", errors);
			this.toView("edit_profile", request, response);
			return;
		}
	}
}