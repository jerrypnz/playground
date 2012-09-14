package jerry.c2c.action;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jerry.c2c.domain.Category;
import jerry.c2c.service.CategoryService;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SysInitAction extends Action
{
	private CategoryService categoryService;

	private String initDir;

	private String categoryFileName;

	/**
	 * @return the initDir
	 */
	public String getInitDir()
	{
		return initDir;
	}

	/**
	 * @param initDir
	 *            the initDir to set
	 */
	public void setInitDir(String initDir)
	{
		this.initDir = initDir;
	}

	/**
	 * @param categoryService
	 *            the categoryService to set
	 */
	public void setCategoryService(CategoryService categoryService)
	{
		this.categoryService = categoryService;
	}

	/**
	 * @return the categoryService
	 */
	public CategoryService getCategoryService()
	{
		return categoryService;
	}

	private boolean addCategories()
	{
		Category parent = null;
		String filePath = this.getServlet().getServletContext().getRealPath(
				initDir + "/" + categoryFileName);
		try
		{
			InputStreamReader temp = new InputStreamReader(
					new FileInputStream(filePath), "UTF-8");
			BufferedReader reader = new BufferedReader(temp);
			String line;
			while( (line=reader.readLine())!= null)
			{
				boolean isParent;
				if(line.startsWith("@"))
					isParent = true;
				else
					isParent = false;
				Category current = new Category();
				StringTokenizer tokens = new StringTokenizer(line,"#");
				tokens.nextToken();
				current.setName(tokens.nextToken().trim());
				current.setDescription(tokens.nextToken().trim());
				if(isParent)
					parent = current;
				else
					current.setParent(parent);
				categoryService.save(current);
			}
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		String target = "result";
		String msgTitle = "系统初始化完毕";
		String msgContent = "系统初始化完毕，一切正常";
		boolean result = addCategories();
		if(!result)
		{
			msgTitle = "系统初始化失败";
			msgContent = "系统初始化失败，可能是类别描述文件格式有误";
		}
		request.setAttribute("message_title", msgTitle);
		request.setAttribute("message_content", msgContent);
		return mapping.findForward(target);
	}

	/**
	 * @return the categoryFileName
	 */
	public String getCategoryFileName()
	{
		return categoryFileName;
	}

	/**
	 * @param categoryFileName
	 *            the categoryFileName to set
	 */
	public void setCategoryFileName(String categoryFileName)
	{
		this.categoryFileName = categoryFileName;
	}

}
