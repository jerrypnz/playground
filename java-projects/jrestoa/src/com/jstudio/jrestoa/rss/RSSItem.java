package com.jstudio.jrestoa.rss;

public class RSSItem
{
	private String title;

	private String author = "JRestOA";

	private String description;

	private String pubDate;

	private String link = "http://localhost:8080/jrestoa";

	private String guid;

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
	 * @return the author
	 */
	public String getAuthor()
	{
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(String author)
	{
		this.author = author;
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
	 * @return the pubDate
	 */
	public String getPubDate()
	{
		return pubDate;
	}

	/**
	 * @param pubDate
	 *            the pubDate to set
	 */
	public void setPubDate(String pubDate)
	{
		this.pubDate = pubDate;
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
	 * @return the guid
	 */
	public String getGuid()
	{
		return guid;
	}

	/**
	 * @param guid
	 *            the guid to set
	 */
	public void setGuid(String guid)
	{
		this.guid = guid;
	}

}
