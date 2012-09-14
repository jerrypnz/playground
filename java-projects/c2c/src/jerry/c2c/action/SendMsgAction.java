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

import jerry.c2c.domain.Message;
import jerry.c2c.domain.User;
import jerry.c2c.exception.BusinessException;
import jerry.c2c.service.MessageService;
import jerry.c2c.service.UserService;
import jerry.c2c.util.DateTimeUtil;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.util.MessageResources;

public class SendMsgAction extends Action
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
		MessageResources res = this.getResources(request);
		String target = "message";
		String pageTitle = "error";
		String msgTitle = "error";
		String msgContent = "error";
		DynaActionForm msgForm = (DynaActionForm) form;
		try
		{
			Message msg = new Message();
			msg.setTitle(msgForm.getString("title"));
			msg.setContent(msgForm.getString("content"));
			msg.setSendTime(DateTimeUtil.getCurrentTimestamp());
			User receiver = userService
					.getByName(msgForm.getString("receiver"));
			User sender = (User) session.getAttribute("user");
			messageService.send(sender, receiver, msg);
			pageTitle = res.getMessage("pagetitles.send_msg.success");
			msgTitle = res.getMessage("messages.send_msg_success_title");
			msgContent = res.getMessage("messages.send_msg_success_content",
					receiver.getName());
		}
		catch (BusinessException e)
		{
			pageTitle = res.getMessage("pagetitles.send_msg.fail");
			msgTitle = res.getMessage("messages.send_msg_fail_title");
			msgContent = res.getMessage("messages.send_msg_fail_content");
		}
		finally
		{
			request.setAttribute("title", pageTitle);
			request.setAttribute("message_title", msgTitle);
			request.setAttribute("message_content", msgContent);
		}
		return mapping.findForward(target);
	}

}