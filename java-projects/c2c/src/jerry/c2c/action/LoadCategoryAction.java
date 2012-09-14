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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;

import jerry.c2c.domain.Category;
import jerry.c2c.service.CategoryService;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LoadCategoryAction extends Action
{
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

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{

		String target = "success";
		Map<Category,List> categMap = new HashMap<Category,List>();
		List rootCateg = categoryService.findRoots();
		for(int i=0;i<rootCateg.size();i++)
		{
			Category current = (Category)rootCateg.get(i);
			List children = categoryService.findByParent(current);
			categMap.put(current, children);
		}
		HttpSession session = request.getSession();
		session.setAttribute("categories", categMap);
		return mapping.findForward(target);
	}

}