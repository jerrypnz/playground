package com.jstudio.jrestoa.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jstudio.jrestoa.domain.AddressbookItem;
import com.jstudio.jrestoa.domain.Appointment;
import com.jstudio.jrestoa.domain.AskOffRecord;
import com.jstudio.jrestoa.domain.Department;
import com.jstudio.jrestoa.domain.Employee;
import com.jstudio.jrestoa.domain.News;
import com.jstudio.jrestoa.domain.Note;
import com.jstudio.jrestoa.domain.PersonalFile;
import com.jstudio.jrestoa.domain.ReceivedMail;
import com.jstudio.jrestoa.domain.SentMail;
import com.jstudio.jrestoa.domain.SharedFile;
import com.jstudio.jrestoa.domain.ToDo;
import com.thoughtworks.xstream.XStream;

public class XMLUtil
{
	private static XStream xstream = new XStream();
	private static Log log = LogFactory.getLog(XMLUtil.class); 
	
	public static String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	
	static {
		log.debug("initializing XML utility..");
		xstream.autodetectAnnotations(true);
		xstream.alias("addressItem", AddressbookItem.class);
		xstream.alias("appointment", Appointment.class);
		xstream.alias("askoffRecord", AskOffRecord.class);
		xstream.alias("department", Department.class);
		xstream.alias("employee", Employee.class);
		xstream.alias("receivedMail", ReceivedMail.class);
		xstream.alias("sentMail", SentMail.class);
		xstream.alias("personalFile", PersonalFile.class);
		xstream.alias("sharedFile", SharedFile.class);
		xstream.alias("todoItem", ToDo.class);
		xstream.alias("news", News.class);
		xstream.alias("note", Note.class);
	}
	
	public static String toXML(Object myObj) {
		log.debug("serializing " + myObj + " to XML");
		return xstream.toXML(myObj);
	}
	
	@SuppressWarnings("unchecked")
	public static String listToXML(List list,String name) {
		log.debug("serializing list " + list + " to XML");
		xstream.alias(name, List.class);
		return xstream.toXML(list);
	}
}
