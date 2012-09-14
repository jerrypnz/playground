package jerry.c2c.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HbmTransactionUtil
{
	public static void executeInTransaction(Session session,
			InsideTransaction inTransaction)
	{
		Transaction tx = session.beginTransaction();
		try
		{
			tx.begin();
			inTransaction.execute(session);
			tx.commit();
		}
		catch(HibernateException e)
		{
			inTransaction.handleException(e);
			tx.rollback();
		}
	}

}
