package com.jstudio.jrestoa.util;

import java.util.Map;

public class ExtXmlResponseUtil
{
	public static String getSuccessXML()
	{
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<response success=\"true\">\r\n" + "</response>\r\n";
		return xml;
	}

	public static String getErrorXML(Map<String, String> errorMsgs)
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		buffer.append("<response success=\"false\">\r\n");
		if (errorMsgs != null)
		{
			for (String id : errorMsgs.keySet())
			{
				String msg = (String) errorMsgs.get(id);
				buffer.append("<field>\r\n");
				buffer.append("<id>\r\n");
				buffer.append(id);
				buffer.append("</id>\r\n");
				buffer.append("<msg><![CDATA[\r\n");
				buffer.append(msg);
				buffer.append("]]></msg>\r\n");
				buffer.append("</field>\r\n");
			}
		}
		buffer.append("</response>\r\n");
		return buffer.toString();
	}

	public static String getErrorXML(Object[] errorMsgs)
	{

		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		buffer.append("<response success=\"false\">\r\n");
		if (errorMsgs != null)
		{
			for (int i = 0; i < errorMsgs.length; i += 2)
			{
				String key = (String) errorMsgs[i];
				String msg = (String) errorMsgs[i + 1];
				buffer.append("<field>\r\n");
				buffer.append("<id>\r\n");
				buffer.append(key);
				buffer.append("</id>\r\n");
				buffer.append("<msg><![CDATA[\r\n");
				buffer.append(msg);
				buffer.append("]]></msg>\r\n");
				buffer.append("</field>\r\n");
			}
		}
		buffer.append("</response>\r\n");
		return buffer.toString();

	}

	public static String getErrorXML()
	{
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<response success=\"false\">\r\n" + "</response>\r\n";
		return xml;
	}
}
