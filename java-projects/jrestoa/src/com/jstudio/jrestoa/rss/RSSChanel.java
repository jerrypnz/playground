package com.jstudio.jrestoa.rss;

import java.util.ArrayList;
import java.util.List;

public class RSSChanel
{
	private String title;

	private String description;

	private String link = "http://localhost:8080/jrestoa";

	private String language = "UTF-8";

	private String copyright = "copyright 2008,jstudio";

	private String docs = "http://blogs.law.harvard.edu/tech/rss";

	private String generator = "JRestOA";

	private List<RSSItem> items = new ArrayList<RSSItem>();

	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return the link
	 */
	public String getLink()
	{
		return link;
	}

	/**
	 * @param link
	 *            the link to set
	 */
	public void setLink(String link)
	{
		this.link = link;
	}

	/**
	 * @return the language
	 */
	public String getLanguage()
	{
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language)
	{
		this.language = language;
	}

	/**
	 * @return the copyright
	 */
	public String getCopyright()
	{
		return copyright;
	}

	/**
	 * @param copyright
	 *            the copyright to set
	 */
	public void setCopyright(String copyright)
	{
		this.copyright = copyright;
	}

	/**
	 * @return the docs
	 */
	public String getDocs()
	{
		return docs;
	}

	/**
	 * @param docs
	 *            the docs to set
	 */
	public void setDocs(String docs)
	{
		this.docs = docs;
	}

	/**
	 * @return the generator
	 */
	public String getGenerator()
	{
		return generator;
	}

	/**
	 * @param generator
	 *            the generator to set
	 */
	public void setGenerator(String generator)
	{
		this.generator = generator;
	}

	/**
	 * @return the items
	 */
	public List<RSSItem> getItems()
	{
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<RSSItem> items)
	{
		this.items = items;
	}

}
