package com.jstudio.jrestoa.domain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jstudio.jrestoa.hbm.HbmManager;
import com.jstudio.jrestoa.hbm.InTransaction;

public class DomainBase
{
	private static Log log = LogFactory.getLog(DomainBase.class);
	
	public void save()
	{
		HbmManager.getSession().save(this);
	}

	public void delete()
	{
		HbmManager.getSession().delete(this);
	}

	public void update()
	{
		HbmManager.getSession().update(this);
	}
	
	public void merge()
	{
		HbmManager.getSession().merge(this);
	}

	public static void doInTransaction(InTransaction callback)
	{
		Session session = HbmManager.getSession();
		Transaction tx = session.beginTransaction();
		try
		{
			callback.execute();
			tx.commit();
		}
		catch (Exception e)
		{
			try
			{
				callback.onException(e);
			}
			catch (Exception e1)
			{
				log.error("fatal error",e1);
			}
			tx.rollback();
			HbmManager.closeSession();
			log.error("exception in transaction", e);
		}
	}
}
