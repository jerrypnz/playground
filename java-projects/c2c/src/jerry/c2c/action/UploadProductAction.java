/*******************************************************************************
 * Copyright (c) 2005 Eteration A.S. and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Eteration A.S. - initial API and implementation
 ******************************************************************************/
package jerry.c2c.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import jerry.c2c.domain.*;
import jerry.c2c.exception.BusinessException;
import jerry.c2c.formbean.NewProductForm;
import jerry.c2c.service.*;
import jerry.c2c.util.DateTimeUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;

public class UploadProductAction extends Action
{
	private ItemService itemService = null;

	private ShopService shopService = null;

	private CategoryService categoryService = null;

	private String imagePath = "/upload/shop";

	private String baseName = "item_image";

	private int maxFileSize = 512000;

	/**
	 * @return the maxFileSize
	 */
	public int getMaxFileSize()
	{
		return maxFileSize;
	}

	/**
	 * @param maxFileSize
	 *            the maxFileSize to set
	 */
	public void setMaxFileSize(int maxFileSize)
	{
		this.maxFileSize = maxFileSize;
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
	{

		String target = "result";
		String resultPageTitle = "error";
		String msgTitle = "error";
		String msgContent = "error";
		NewProductForm productForm = (NewProductForm) form;
		HttpSession session = request.getSession();
		Item item = new Item();

		try
		{
			BeanUtils.copyProperties(item, productForm);
			item.setCreateTime(DateTimeUtil.getCurrentTimestamp());
			Timestamp createTime = DateTimeUtil.getCurrentTimestamp();
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, productForm.getDays());
			Timestamp endTime = new Timestamp(calendar.getTimeInMillis());
			item.setBelongTo((Shop) session.getAttribute("user_shop"));
			item.setCreateTime(createTime);
			item.setEndTime(endTime);
			Category category = categoryService.getById(productForm
					.getCategoryId());
			item.setCategory(category);
			itemService.save(item);
			this.handleUploadImage(productForm.getImageFile(), item
					.getBelongTo().getName(), item.getId());
			resultPageTitle = "上传商品成功";
			msgTitle = "上传商品成功";
			msgContent = "您的商品(" + item.getName() + ")上传成功";
		}
		catch (IllegalAccessException e)
		{
			itemService.delete(item);
			resultPageTitle = "上传商品失败";
			msgTitle = "上传商品失败";
			msgContent = "您的商品上传失败,错误信息是:" + e.getMessage();
		}
		catch (InvocationTargetException e)
		{
			itemService.delete(item);
			resultPageTitle = "上传商品失败";
			msgTitle = "上传商品失败";
			msgContent = "您的商品上传失败,错误信息是:" + e.getMessage();
		}
		catch (IOException e)
		{
			itemService.delete(item);
			e.printStackTrace();
			resultPageTitle = "上传商品失败";
			msgTitle = "上传商品失败";
			msgContent = "您的商品上传失败,错误信息是:" + e.getMessage();
		}
		catch (BusinessException e)
		{
			itemService.delete(item);
			e.printStackTrace();
			resultPageTitle = "上传商品失败";
			msgTitle = "上传商品失败";
			msgContent = "您的商品上传失败,错误信息是:" + e.getMessage();
		}
		finally
		{
			request.setAttribute("title", resultPageTitle);
			request.setAttribute("message_title", msgTitle);
			request.setAttribute("message_content", msgContent);
		}
		return mapping.findForward(target);
	}

	public void handleUploadImage(FormFile imageFile, String username,
			long itemId) throws IOException
	{
		String originalName = imageFile.getFileName();
		int dotPos = originalName.lastIndexOf(".");
		String suffix = originalName.substring(dotPos);
		if (!this.imagePath.startsWith("/"))
		{
			imagePath = "/" + imagePath;
		}
		String realPath = this.servlet.getServletContext().getRealPath(
				imagePath);
		System.out.println("File name:" + realPath);
		File dir = new File(realPath);
		if (!dir.exists())
			dir.mkdirs();
		String fileName = realPath + "/" + baseName + itemId + suffix;
		InputStream is = imageFile.getInputStream();
		OutputStream os = new FileOutputStream(fileName);
		byte[] bytes = new byte[8192];
		int bytesRead = 0;
		while ((bytesRead = is.read(bytes, 0, 8192)) != -1)
		{
			os.write(bytes, 0, bytesRead);
		}
		os.close();
		is.close();
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
	 * @return the shopService
	 */
	public ShopService getShopService()
	{
		return shopService;
	}

	/**
	 * @param imagePath
	 *            the imagePath to set
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

	/**
	 * @param shopService
	 *            the shopService to set
	 */
	public void setShopService(ShopService shopService)
	{
		this.shopService = shopService;
	}

	/**
	 * @return the baseName
	 */
	public String getBaseName()
	{
		return baseName;
	}

	/**
	 * @param baseName
	 *            the baseName to set
	 */
	public void setBaseName(String baseName)
	{
		this.baseName = baseName;
	}

	/**
	 * @return the categoryService
	 */
	public CategoryService getCategoryService()
	{
		return categoryService;
	}

	/**
	 * @param categoryService
	 *            the categoryService to set
	 */
	public void setCategoryService(CategoryService categoryService)
	{
		this.categoryService = categoryService;
	}

}