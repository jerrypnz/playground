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
import javax.servlet.ServletException;

import jerry.c2c.domain.Item;
import jerry.c2c.exception.BusinessException;
import jerry.c2c.service.ItemService;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowItemAction extends Action
{
	ItemService itemService = null;
	String imagePath;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{

		String target = "success";
		String itemIdString = request.getParameter("productId");
		if("".equals(itemIdString))
			itemIdString = (String)request.getAttribute("productId");
		long itemId = Long.parseLong(itemIdString);
		try
		{
			Item theProduct = itemService.getById(itemId);
			List commentList = itemService.findCommentsByItem(theProduct);
			request.setAttribute("product", theProduct);
			request.setAttribute("product_comments", commentList);
			request.setAttribute("base_dir", imagePath);
		}
		catch (BusinessException e)
		{
			target = "fail";
			String pageTitle = "错误(Opps)";
			String msgTitle = "不能查看此宝贝的详情";
			String msgContent = "原因如下:" + e.getMessage();
			request.setAttribute("title", pageTitle);
			request.setAttribute("message_title", msgTitle);
			request.setAttribute("content", msgContent);
		}
		return mapping.findForward(target);
	}

	/**
	 * @return the imagePath
	 */
	public String getImagePath()
	{
		return imagePath;
	}

	/**
	 * @return the itemService
	 */
	public ItemService getItemService()
	{
		return itemService;
	}

	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath)
	{
		this.imagePath = imagePath;
	}

	/**
	 * @param itemService
	 *            the itemService to set
	 */
	public void setItemService(ItemService itemService)
	{
		this.itemService = itemService;
	}

}