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

@SuppressWarnings("serial")
public class RegisterForm extends ActionForm
{

	private String name;

	private String password;

	private String passEnsure;

	private String address;

	private String nickName;

	private String sex;

	private String email;

	public java.lang.String getAddress()
	{
		return address;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName()
	{
		return nickName;
	}

	public String getPassEnsure()
	{
		return passEnsure;
	}

	public String getPassword()
	{
		return password;
	}

	/**
	 * @return the sex
	 */
	public String getSex()
	{
		return sex;
	}

	public String getName()
	{
		return name;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{

		super.reset(mapping, request);
		this.name = "";
		this.password = "";
		this.passEnsure = "";
	}

	public void setAddress(java.lang.String address)
	{
		this.address = address;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @param nickName
	 *            the nickName to set
	 */
	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}

	public void setPassEnsure(java.lang.String passEnsure)
	{
		this.passEnsure = passEnsure;
	}

	public void setPassword(java.lang.String password)
	{
		this.password = password;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(String sex)
	{
		this.sex = sex;
	}

	public void setName(java.lang.String username)
	{
		this.name = username;
	}

	public static boolean validateEmail(String email)
	{
		int atPos = email.indexOf("@");
		int dotPos = email.lastIndexOf(".");
		if (atPos < 0 || dotPos < 0)
			return false;
		else if (atPos > dotPos)
			return false;
		else
			return true;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();
		// Validate an attribute named "xxx"
		// if( getXXX() == null || getXXX().length() == 0 )
		// errors.add("xxx",new ActionMessage("errors.required","xxx"));
		if (name.isEmpty())
			errors.add("Username", new ActionMessage("errors.required", "用户名"));
		if (!validateEmail(email))
			errors.add("Email", new ActionMessage("errors.email", email));
		if (password.isEmpty() || passEnsure.isEmpty())
			errors.add("Username", new ActionMessage("errors.required",
					"密码和密码确认"));
		else if (!password.equals(passEnsure))
			errors.add("Password", new ActionMessage("errors.not_the_same"));
		return errors;
	}

}