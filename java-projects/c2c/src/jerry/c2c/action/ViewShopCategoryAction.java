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

import jerry.c2c.domain.Category;
import jerry.c2c.exception.BusinessException;
import jerry.c2c.service.CategoryService;
import jerry.c2c.service.ShopService;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ViewShopCategoryAction extends Action
{
	ShopService shopService = null;
	CategoryService categoryService = null;

	/**
	 * @return the categoryService
	 */
	public CategoryService getCategoryService()
	{
		return categoryService;
	}

	/**
	 * @param categoryService the categoryService to set
	 */
	public void setCategoryService(CategoryService categoryService)
	{
		this.categoryService = categoryService;
	}

	/**
	 * @return the shopService
	 */
	public ShopService getShopService()
	{
		return shopService;
	}

	/**
	 * @param shopService the shopService to set
	 */
	public void setShopService(ShopService shopService)
	{
		this.shopService = shopService;
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{

		String target = "success";
		String categIdString = request.getParameter("categId");
		int categId = Integer.parseInt(categIdString);
		List result = null; 
		try
		{
			Category category = categoryService.getById(categId);
			result = shopService.findByCategory(category);
			request.setAttribute("shop_list", result);
		}
		catch (BusinessException e)
		{
			target = "fail";
			String pageTitle = "错误(Opps)";
			String msgTitle = "不能查找此类别店铺";
			String msgContent = "原因如下:" + e.getMessage();
			request.setAttribute("title", pageTitle);
			request.setAttribute("message_title", msgTitle);
			request.setAttribute("content", msgContent);
		}
		return mapping.findForward(target);
	}

}