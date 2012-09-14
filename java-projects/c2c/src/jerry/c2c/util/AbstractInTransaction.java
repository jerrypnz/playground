package jerry.c2c.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public abstract class AbstractInTransaction implements InsideTransaction
{

	public void execute(Session session)
	{

	}

	public void handleException(HibernateException e)
	{
		e.printStackTrace();
	}

}
