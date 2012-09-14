package com.jstudio.jrestoa.domain.test;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;
import com.jstudio.jrestoa.domain.Employee;
import com.jstudio.jrestoa.domain.Mail;
import com.jstudio.jrestoa.domain.ReceivedMail;
import com.jstudio.jrestoa.domain.SentMail;
import com.jstudio.jrestoa.util.XMLUtil;

public class TestMail extends DomainTestBase {
	@Test
	public void testSentReceiveMail() throws Exception {
		Employee sender = new Employee();
		Employee receiver = new Employee();
		sender.setLoginName("bar");
		receiver.setLoginName("foo");
		final String mailTitle = "###___###Hello###___###";
		sender.save();
		receiver.save();

		Mail mail = new Mail();
		mail.setTitle(mailTitle);
		mail.setBody("Hello,you foo");

		sender.sendMail("foo", mail);

		boolean flag1 = false;
		boolean flag2 = false;

		List<SentMail> sentMails = sender.checkSentMail();
		List<ReceivedMail> receivedMails = receiver.receiveMail();

		for (SentMail sm : sentMails) {
			if (sm.getTitle().equals(mailTitle)) {
				assertEquals("Sender not correct", sender, sm.getSender());
				assertEquals("Receiver not correct", "foo", sm.getReceiver());
				flag1 = true;
			}
		}

		for (ReceivedMail rm : receivedMails) {
			if (rm.getTitle().equals(mailTitle)) {
				assertEquals("Sender not correct", receiver, rm.getReceiver());
				assertEquals("Receiver not correct", "bar", rm.getSender());
				flag2 = true;
			}
		}
		String xml1 = XMLUtil.listToXML(sentMails, "sentMails");
		String xml2 = XMLUtil.listToXML(receivedMails, "receivedMails");
		System.out.println("--------------------------------");
		System.out.println(xml1);
		System.out.println("--------------------------------");
		System.out.println(xml2);
		assertTrue("Mail are not correctly sent", flag1);
		assertTrue("Mail are not correctly received", flag2);

	}
}
