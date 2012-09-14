package jerry.c2c.service.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import jerry.c2c.domain.User;
import jerry.c2c.exception.BusinessException;
import jerry.c2c.service.UserService;

public class UserServiceImpl extends HibernateDaoSupport implements UserService
{

	private String GET_BY_NAME = "from User where name=?";

	private String FIND_BY_KEYWORD = "from User where name like ?";

	public void delete(User user)
	{
		this.getHibernateTemplate().delete(user);
	}

	public List findByKeyword(String keyword)
	{
		List temp = this.getHibernateTemplate().find(FIND_BY_KEYWORD,
				"%" + keyword + "%");
		return temp;
	}

	public User getById(long id) throws BusinessException
	{
		try
		{
			User temp = (User) this.getHibernateTemplate()
						.get(User.class, id);
			return temp;
		}
		catch (Exception e)
		{
			throw new BusinessException("User does not exist");
		}
	}

	public User getByName(String username) throws BusinessException
	{
		List temp = this.getHibernateTemplate().find(GET_BY_NAME, username);
		if (temp.size() == 0)
			throw new BusinessException("User does not exist");
		User user = (User)temp.get(0);
		this.getHibernateTemplate().initialize(user.getOwnedShops());
		return user;
	}

	public User login(String username, String password)
			throws BusinessException
	{
		User temp = (User)this.getByName(username);
		if (!password.equals(temp.getPassword()))
			throw new BusinessException("Password wrong");
		else
			return temp;
	}

	public void save(User user) throws BusinessException
	{
		String name = user.getName();
		List temp = this.getHibernateTemplate().find(GET_BY_NAME, name);
		if (temp.size() != 0)
			throw new BusinessException("This user already exists");
		else
			this.getHibernateTemplate().save(user);

	}

	public void update(User user)
	{
		this.getHibernateTemplate().update(user);

	}

}
