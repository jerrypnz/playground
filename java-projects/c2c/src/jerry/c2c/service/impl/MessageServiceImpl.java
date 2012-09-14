package jerry.c2c.service.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import jerry.c2c.domain.Message;
import jerry.c2c.domain.User;
import jerry.c2c.exception.BusinessException;
import jerry.c2c.service.MessageService;

public class MessageServiceImpl extends HibernateDaoSupport implements MessageService
{
	
	private String FIND_BY_USER = "from Message where to=?";

	public void delete(Message msg)
	{
		this.getHibernateTemplate().delete(msg);

	}

	public Message getById(long id) throws BusinessException 
	{
		try
		{
			Message temp = (Message)this.getHibernateTemplate()
						.get(Message.class, id);
			this.getHibernateTemplate().initialize(temp.getFrom());
			this.getHibernateTemplate().initialize(temp.getTo());
			return temp;
		}
		catch (DataAccessException e)
		{
			throw new BusinessException("Message does not exist");
		}
	}

	public List receive(User user)
	{
		List temp = this.getHibernateTemplate()
				.find(FIND_BY_USER, user);
		return temp;
	}

	public void save(Message msg)
	{
		this.getHibernateTemplate().save(msg);

	}

	public void send(User from, User to, Message msg)
	{
		msg.setFrom(from);
		msg.setTo(to);
		this.save(msg);
	}

	public void update(Message msg)
	{
		this.getHibernateTemplate().update(msg);
	}

}
