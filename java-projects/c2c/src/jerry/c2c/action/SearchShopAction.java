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

import jerry.c2c.service.ShopService;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class SearchShopAction extends Action
{
	ShopService shopService = null;
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
		DynaActionForm searchForm = (DynaActionForm)form;
		String keyword = searchForm.getString("keyword");
		System.out.println("Keyword is " + keyword);
		List result = shopService.findByKeyword(keyword);
		request.setAttribute("shop_list", result);
		return mapping.findForward(target);
	}

}