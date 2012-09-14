/**
 * 
 */
package jerry.c2c.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * @author Jerry
 *
 */
public interface InsideTransaction
{
	public void execute(Session session);
	public void handleException(HibernateException e);
}
