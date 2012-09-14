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
import jerry.c2c.service.*;
import jerry.c2c.util.DateTimeUtil;

import org.apache.struts.action.*;
import org.apache.struts.util.MessageResources;

public class ShopRegAction extends Action
{

	private UserService userService = null;
	private ShopService shopService = null;
	private CategoryService categoryService = null;
	
	
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

		String target = "message";
		DynaActionForm shopRegForm = (DynaActionForm)form;
		MessageResources res = this.getResources(request);
		ActionMessages errors = this.getErrors(request);
		HttpSession session = request.getSession();	
		String msgTitle = "";
		String msgContent = "";
		String pageTitle = "";
		try
		{
			Shop shop = new Shop();
			shop.setName(shopRegForm.getString("name"));
			shop.setDescription(shopRegForm.getString("description"));
			User user = (User)session.getAttribute("user");
			shop.setOwner(user);
			Category category = categoryService
				.getById((Long)shopRegForm.get("category"));
			shop.setCategory(category);
			shop.setCreateTime(DateTimeUtil.getCurrentTimestamp());
			shopService.save(shop);
			//userService.update(user);
			session.setAttribute("user_shop", shop);
			msgTitle = res.getMessage("messages.shop_reg_success_title");
			msgContent = res.getMessage("messages.shop_reg_success_content",shop.getName());
			pageTitle = res.getMessage("pagetitles.shop_reg.success");
		}
		catch (BusinessException e)
		{
			errors.add("error",new ActionMessage("errors.shop_exists",e.getMessage()));
			pageTitle = res.getMessage("pagetitles.shop_reg.fail");
			msgTitle = res.getMessage("errors.shop_reg_fail_title");
			msgContent = res.getMessage("errors.shop_reg_fail_content");
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