package com.jstudio.jrestoa.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jstudio.jrestoa.domain.AddressbookItem;
import com.jstudio.jrestoa.domain.Employee;
import com.jstudio.jrestoa.domain.PersonalFile;
import com.jstudio.jrestoa.hbm.InTransaction;
import com.jstudio.jrestoa.rest.RestConstants;

public class FileUploadServlet extends HttpServlet
{

	private static final long serialVersionUID = 1L;

	public static final String DEFAULT_ROOT_DIR = "UserFiles";

	private static Log log = LogFactory.getLog(FileUploadServlet.class);

	private String saveDir;

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		log.debug("Upload servlet start");
		final HttpSession session = request.getSession();
		final PrintWriter out = response.getWriter();
		final Employee user = (Employee) session
				.getAttribute(RestConstants.SESSION_KEY_USER);
		if (user == null)
		{
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart)
		{
			log.debug("No file in this request");
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		// upload.setHeaderEncoding("UTF-8");
		final PersonalFile myFile = new PersonalFile();
		myFile.setOwner(user);
		try
		{
			List<FileItem> items = upload.parseRequest(request);
			Iterator<FileItem> it = items.iterator();
			while (it.hasNext())
			{
				FileItem item = it.next();

				if (!item.isFormField())
				{
					log.info("File found in this request:" + item.getName());
					String fileName = convertFileName(item.getName());
					String filePath = saveFileItem(item, user.getLoginName(),
							fileName);
					myFile.setFileName(fileName);
					myFile.setFilePath(filePath);
				}
				else
				{
					String fieldName = item.getFieldName();
					if ("description".equals(fieldName))
						myFile.setDescription(item.getString("UTF-8"));
				}
			}

		}
		catch (Exception e)
		{
			log.error("Error uploading file", e);
			out.print(ExtXmlResponseUtil.getErrorXML());
		}
		log.debug("file saved,about to save object to database");
		AddressbookItem.doInTransaction(new InTransaction()
		{

			@Override
			public void execute() throws Exception
			{
				try
				{
					user.merge();
					myFile.save();
				}
				catch (RuntimeException e)
				{
					throw new Exception(e);
				}
				out.print(ExtXmlResponseUtil.getSuccessXML());
			}

			@Override
			public void onException(Throwable e) throws Exception
			{
				File toDelete = new File(myFile.getFilePath());
				if (toDelete.exists())
				{
					toDelete.delete();
					log.debug("transaction rollback,delete file " + toDelete);
				}
				out.print(ExtXmlResponseUtil.getErrorXML());
			}

		});
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		response.getWriter().println("Only a test");
	}

	protected String saveFileItem(FileItem item, String userName,
			String shortFileName) throws Exception
	{
		String userHomeDir = saveDir + userName + File.separator;
		String temp = "";
		int pos = shortFileName.lastIndexOf(".");
		if (pos > 1)
			temp = shortFileName.substring(pos);
		String fileName = "" + System.currentTimeMillis() + temp;
		log.debug("final saved file name:" + fileName);
		File rootDir = new File(userHomeDir);
		if (!rootDir.exists())
			rootDir.mkdirs();
		File fileToSave = new File(rootDir, fileName);
		log.info("Saving file to:" + fileToSave);
		item.write(fileToSave);
		return DEFAULT_ROOT_DIR + "/" + userName + "/" + fileName;
	}

	protected String convertFileName(String original)
	{
		String temp = original;
		int pos = temp.lastIndexOf(File.separator);
		if (pos > 1)
		{
			temp = temp.substring(pos + 1);
		}
		return temp;
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	public void init() throws ServletException
	{
		saveDir = this.getServletContext().getRealPath(DEFAULT_ROOT_DIR)
				+ File.separator;
		log.info("File saved in:" + saveDir);
	}

}
