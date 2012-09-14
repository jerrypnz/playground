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
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;

import jerry.c2c.domain.User;
import jerry.c2c.service.MessageService;
import jerry.c2c.service.UserService;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ReceiveMsgAction extends Action
{

	UserService userService = null;

	MessageService messageService = null;

	/**
	 * @return the messageService
	 */
	public MessageService getMessageService()
	{
		return messageService;
	}

	/**
	 * @param messageService
	 *            the messageService to set
	 */
	public void setMessageService(MessageService messageService)
	{
		this.messageService = messageService;
	}

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
		HttpSession session = request.getSession();
		String target = "success";
		User user = (User)session.getAttribute("user");
		List allMsgs = messageService.receive(user);
		request.setAttribute("msg_list", allMsgs);
		return mapping.findForward(target);
	}

}