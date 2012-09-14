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
import javax.servlet.http.*;
import javax.servlet.ServletException;
import jerry.c2c.domain.*;
import jerry.c2c.exception.BusinessException;
import jerry.c2c.service.ItemService;
import jerry.c2c.util.DateTimeUtil;

import org.apache.struts.action.*;

public class ItemCommentAction extends Action
{
	ItemService itemService = null;

	/**
	 * @return the itemService
	 */
	public ItemService getItemService()
	{
		return itemService;
	}

	/**
	 * @param itemService
	 *            the itemService to set
	 */
	public void setItemService(ItemService itemService)
	{
		this.itemService = itemService;
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{
		HttpSession session = request.getSession();
		DynaActionForm itemForm = (DynaActionForm) form;
		String target = "success";
		String itemIdString = request.getParameter("productId");
		long itemId = Long.parseLong(itemIdString);
		ItemComment comment = new ItemComment();
		try
		{
			Item item = itemService.getById(itemId);
			User maker = (User) session.getAttribute("user");
			comment.setContent(itemForm.getString("content"));
			comment.setMaker(maker);
			comment.setDestItem(item);
			comment.setTime(DateTimeUtil.getCurrentTimestamp());
			itemService.saveComment(comment);
			request.setAttribute("productId", itemIdString);
			
		}
		catch (BusinessException e)
		{
			target = "fail";
			String pageTitle = "错误(Opps)";
			String msgTitle = "不能保存评论";
			String msgContent = "原因如下:" + e.getMessage();
			request.setAttribute("title", pageTitle);
			request.setAttribute("message_title", msgTitle);
			request.setAttribute("content", msgContent);
		}
		return mapping.findForward(target);
	}

}