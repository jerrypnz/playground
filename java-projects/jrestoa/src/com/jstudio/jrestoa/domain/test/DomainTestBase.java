package com.jstudio.jrestoa.domain.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;

import com.jstudio.jrestoa.hbm.HbmManager;

public class DomainTestBase {
	private Log log = LogFactory.getLog(DomainTestBase.class);

	private Transaction tx = null;
	

	@Before
	public void beginTransaction() {
		log.debug("beginning transaction for test "
						+ this.getClass().getName());
		if(tx!=null)
		{
			log.warn("test transaction state error,rolling it back");
			tx.rollback();
		}
		tx = HbmManager.getSession().beginTransaction();
	}

	@After
	public void rollback() {
		if (tx != null) {
			log.debug("rolling back transaction for test "
					+ this.getClass().getName());
			tx.rollback();
			tx = null;
		}
	}
}
