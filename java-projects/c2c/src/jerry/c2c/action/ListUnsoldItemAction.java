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

import jerry.c2c.domain.Shop;
import jerry.c2c.service.ItemService;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ListUnsoldItemAction extends Action
{

	ItemService itemService = null;	
	String imagePath = "upload/itemImages";
	
	/**
	 * @return the itemService
	 */
	public ItemService getItemService()
	{
		return itemService;
	}

	/**
	 * @param itemService the itemService to set
	 */
	public void setItemService(ItemService itemService)
	{
		this.itemService = itemService;
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{

		String target = "success";
		HttpSession session = request.getSession();
		Shop shop = (Shop)session.getAttribute("user_shop");
		List itemList = itemService.findByShop(shop);
		request.setAttribute("item_list", itemList);
		request.setAttribute("base_dir", imagePath);
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
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath)
	{
		this.imagePath = imagePath;
	}

}