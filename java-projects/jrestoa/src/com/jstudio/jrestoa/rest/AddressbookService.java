package com.jstudio.jrestoa.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.jstudio.jrestoa.domain.AddressbookItem;
import com.jstudio.jrestoa.domain.Employee;
import com.jstudio.jrestoa.hbm.InTransaction;
import com.jstudio.jrestoa.util.ExtXmlResponseUtil;
import com.jstudio.jrestoa.util.XMLUtil;
import com.jstudio.rest.AbstractRESTResource;
import com.jstudio.rest.annotation.URLMapping;

@URLMapping("/addressbook/{item_id}")
public class AddressbookService extends AbstractRESTResource
{

	private Log log = LogFactory.getLog(AddressbookService.class);

	@Override
	public void delete(Map<String, String> params, HttpServletRequest request,
			final HttpServletResponse response) throws IOException, ServletException
	{
		final PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		log.debug("Addressbook delete");
		Employee user = (Employee) session
				.getAttribute(RestConstants.SESSION_KEY_USER);
		if (user == null)
		{
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		final Long id = Long.valueOf(params.get("item_id"));
		AddressbookItem.doInTransaction(new InTransaction()
		{

			@Override
			public void execute() throws Exception
			{
				AddressbookItem item = AddressbookItem.findById(id);
				if (item != null)
				{
					item.delete();
				}
				out.print(ExtXmlResponseUtil.getSuccessXML());
			}

			@Override
			public void onException(Throwable e) throws Exception
			{
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}

		});
	}

	@Override
	public void get(Map<String, String> params, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException
	{
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		log.debug("Addressbook list");
		String temp = params.get("item_id");
		if ("list".equalsIgnoreCase(temp))
		{
			Employee user = (Employee) session
					.getAttribute(RestConstants.SESSION_KEY_USER);
			if (user == null)
			{
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			List<AddressbookItem> userAddressbook = user.listAddressbook();
			out.println(XMLUtil.xmlHeader);
			String result = XMLUtil.listToXML(userAddressbook, "addressbook");
			log.debug(result);
			out.println(result);

		}
		else
		{
			Long id = Long.valueOf(temp);
			AddressbookItem item = AddressbookItem.findById(id);
			if (item == null)
			{
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			out.println(XMLUtil.xmlHeader);
			out.println(XMLUtil.toXML(item));
		}
	}

	@Override
	public void create(Map<String, String> params, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException
	{
		if(!"new".equals(params.get("item_id")))
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		HttpSession session = request.getSession();
		final PrintWriter out = response.getWriter();
		final Employee emp = (Employee)session.getAttribute(RestConstants.SESSION_KEY_USER);
		if(emp==null)
		{
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		
		Map<String,String> errors = new HashMap<String, String>();
		String name = request.getParameter("name").trim();
		String mobile = request.getParameter("mobile").trim();
		String homePhone = request.getParameter("homePhone").trim();
		String email = request.getParameter("email").trim();
		boolean succ = true;
		
		if(name==null || "".equals(name))
		{
			errors.put("name", "姓名不能为空");
			succ = false;
		}
		if(mobile==null || "".equals(mobile))
		{
			errors.put("mobile", "手机号码不能为空");
			succ = false;
		}
		if(email==null || "".equals(email))
		{
			errors.put("email", "邮箱不能为空");
			succ = false;
		}
		if(!succ)
		{
			out.println(ExtXmlResponseUtil.getErrorXML(errors));
			return;
		}
		final AddressbookItem item = new AddressbookItem();
		item.setName(name);
		item.setEmail(email);
		item.setHomePhone(homePhone);
		item.setMobilePhone(mobile);
		item.setOwner(emp);
		AddressbookItem.doInTransaction(new InTransaction()
		{

			@Override
			public void execute() throws Exception
			{
				emp.update();
				item.save();
				out.print(ExtXmlResponseUtil.getSuccessXML());
			}

			@Override
			public void onException(Throwable e) throws Exception
			{
				out.print(ExtXmlResponseUtil.getErrorXML());
			}

		});
	}

}
