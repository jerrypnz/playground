/*******************************************************************************
 * Copyright (c) 2005 Eteration A.S. and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Eteration A.S. - initial API and implementation
 ******************************************************************************/

package jerry.c2c.formbean;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

@SuppressWarnings("serial")
public class NewProductForm extends ActionForm
{

	private java.lang.String name;

	private java.lang.String description;

	private long categoryId;

	private java.lang.Integer basePrice;

	private java.lang.Integer tradePrice;

	private java.lang.Integer days;

	private FormFile imageFile;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();
		// Validate an attribute named "xxx"
		// if( getXXX() == null || getXXX().length() == 0 )
		// errors.add("xxx",new ActionMessage("errors.required","xxx"));
		if ("".equals(name))
			errors.add("name", new ActionMessage("errors.required", "宝贝名"));
		if ("".equals(description))
			errors.add("descrip", new ActionMessage("errors.required", "宝贝描述"));
		if (imageFile == null)
			errors.add("imageFile", new ActionMessage("errors.required", "宝贝图片"));
		if (basePrice >= tradePrice)
			errors.add("trade_price", new ActionMessage(
					"errors.trade_price_too_low"));
		String originalName = imageFile.getFileName();
		int dotPos = originalName.lastIndexOf(".");
		if (dotPos < 0)
			errors.add("filetype", new ActionMessage(
					"errors.filetype_not_correct"));
		String suffix = originalName.substring(dotPos);
		if (!".jpg".equalsIgnoreCase(suffix))
		{
			errors.add("filetype", new ActionMessage(
					"errors.filetype_not_correct"));
		}
		return errors;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		super.reset(mapping, request);
	}

	public java.lang.String getName()
	{
		return name;
	}

	public void setName(java.lang.String name)
	{
		this.name = name;
	}

	public java.lang.String getDescription()
	{
		return description;
	}

	public void setDescription(java.lang.String description)
	{
		this.description = description;
	}

	public long getCategoryId()
	{
		return categoryId;
	}

	public void setCategoryId(long categoryId)
	{
		this.categoryId = categoryId;
	}

	public java.lang.Integer getBasePrice()
	{
		return basePrice;
	}

	public void setBasePrice(java.lang.Integer basePrice)
	{
		this.basePrice = basePrice;
	}

	public java.lang.Integer getTradePrice()
	{
		return tradePrice;
	}

	public void setTradePrice(java.lang.Integer tradePrice)
	{
		this.tradePrice = tradePrice;
	}

	public java.lang.Integer getDays()
	{
		return days;
	}

	public void setDays(java.lang.Integer days)
	{
		this.days = days;
	}

	/**
	 * @return the imageFile
	 */
	public FormFile getImageFile()
	{
		return imageFile;
	}

	/**
	 * @param imageFile
	 *            the imageFile to set
	 */
	public void setImageFile(FormFile image)
	{
		System.out.println("setting image property");
		this.imageFile = image;
	}


}