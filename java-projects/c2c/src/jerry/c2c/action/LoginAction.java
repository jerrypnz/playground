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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;

import jerry.c2c.domain.Shop;
import jerry.c2c.domain.User;
import jerry.c2c.exception.BusinessException;
import jerry.c2c.service.UserService;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.util.MessageResources;

public class LoginAction extends Action
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
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(UserService userService)
	{
		this.userService = userService;
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{

		String target = "success";
		DynaActionForm loginForm = (DynaActionForm) form;
		User user = null;
		MessageResources resources = this.getResources(request);
		try
		{
			user = this.userService.login(loginForm.getString("username"), loginForm
					.getString("password"));
			HttpSession session = request.getSession();
			if(!user.getOwnedShops().isEmpty())
			{
				Shop shop = user.getOwnedShops().iterator().next();
				session.setAttribute("user_shop", shop);
			}
			session.setAttribute("user", user);
		}
		catch (BusinessException e)
		{
			request.setAttribute("message_title", resources.getMessage("errors.login_fail_title"));
			request.setAttribute("message_content", e.getMessage());
			request.setAttribute("title", resources.getMessage("pagetitles.login.fail"));
			target = "fail";
		}
		return mapping.findForward(target);
	}

}