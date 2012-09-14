/*******************************************************************************
 * Copyright (c) 2005 Eteration A.S. and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Eteration A.S. - initial API and implementation
 ******************************************************************************/
package jerry.c2c.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.*;
import javax.servlet.ServletException;

import jerry.c2c.domain.User;
import jerry.c2c.exception.BusinessException;
import jerry.c2c.formbean.RegisterForm;
import jerry.c2c.service.UserService;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.*;
import org.apache.struts.util.MessageResources;

public class RegisterAction extends Action
{
	private UserService userService = null;
	

	/**
	 * @return the userService
	 */
	public UserService getUserService()
	{
		return userService;
	}


	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService)
	{
		this.userService = userService;
	}


	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{	
		RegisterForm userForm = (RegisterForm)form;
		String target = "message";
		ActionMessages errors = this.getErrors(request);
		MessageResources msgs = this.getResources(request);
		String messageTitle = "error";
		String messageContent = "error";
		String title = "error";
		if(userService==null)
		{
			target="message";
			errors.add("fatal", new ActionMessage("errors.fatal"));
		}
		User user = new User();
		try
		{
			BeanUtils.copyProperties(user, userForm);
			userService.save(user);
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			messageTitle = msgs.getMessage("messages.reg_success_title");
			Object[] args = new Object[2];
			args[0] = user.getName();
			args[1] = user.getId();
			messageContent = msgs.getMessage("messages.reg_success_content", args);
			title = msgs.getMessage("pagetitles.register.success");
		}
		catch (IllegalAccessException e)
		{
			errors.add("fatal", new ActionMessage("errors.fatal"));
			messageTitle = msgs.getMessage("errors.reg_fail_title");
			messageContent = msgs.getMessage("errors.reg_fail_content");
			title = msgs.getMessage("pagetitles.register.fail");
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			title = msgs.getMessage("pagetitles.register.fail");
			messageTitle = msgs.getMessage("errors.reg_fail_title");
			messageContent = msgs.getMessage("errors.reg_fail_content");
			errors.add("fatal", new ActionMessage("errors.fatal"));
			e.printStackTrace();
		}
		catch (BusinessException e)
		{
			title = msgs.getMessage("pagetitles.register.fail");
			messageTitle = msgs.getMessage("errors.reg_fail_title");
			messageContent = msgs.getMessage("errors.reg_fail_content");
			errors.add("user_exists", new ActionMessage("errors.user_exists",user.getName()));
		}
		finally
		{
			request.setAttribute("title", title);
			request.setAttribute("message_title", messageTitle);
			request.setAttribute("message_content", messageContent);
		}
		return mapping.findForward(target);
	}

}