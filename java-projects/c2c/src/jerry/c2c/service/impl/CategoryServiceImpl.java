package jerry.c2c.service.impl;

import java.util.List;

import jerry.c2c.domain.Category;
import jerry.c2c.exception.BusinessException;
import jerry.c2c.service.CategoryService;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class CategoryServiceImpl extends HibernateDaoSupport implements CategoryService
{
	private String FIND_BY_KEYWORD = "from Category where name like ? or description like ? order by id";
	private String FIND_BY_PARENT = "from Category where parent = ? order by id";
	private String FIND_ROOT = "from Category where parent is null order by id";
	private String GET_BY_NAME = "from Category where name = ?";
	
	public void delete(Category category)
	{
		this.getHibernateTemplate().delete(category);
	}

	public List findByKeyword(String keyWord)
	{
		Object[] args = new Object[2];
		args[0] = "%" + keyWord + "%";
		args[1] = "%" + keyWord + "%";
		List temp = this.getHibernateTemplate()
				.find(FIND_BY_KEYWORD, args);
		return temp;
	}

	public List findByParent(Category category)
	{
		List temp = this.getHibernateTemplate()
				.find(FIND_BY_PARENT,category);
		return temp;
	}

	public List findRoots()
	{
		List temp = this.getHibernateTemplate()
				.find(FIND_ROOT);
		return temp;
	}

	public Category getById(long id) throws BusinessException
	{
		try
		{
			Category temp = (Category)this.getHibernateTemplate()
					.get(Category.class, id);
			this.getHibernateTemplate().initialize(temp.getParent());
			return temp;
		}
		catch (DataAccessException e)
		{
			throw new BusinessException("Category[id:" + id +"] does not exist");
		}
	}

	public Category getByName(String name) throws BusinessException
	{
		List temp = this.getHibernateTemplate()
			.find(GET_BY_NAME, name);
		if(temp.size()==0)
			throw new BusinessException("Category[name:" + name + "] does not exist");
		else
			return (Category)temp.get(0);
	}

	public void save(Category category) throws BusinessException
	{
		List temp = this.getHibernateTemplate()
			.find(GET_BY_NAME, category.getName());
		if(temp.size()==0)
			this.getHibernateTemplate().save(category);
		else
			throw new BusinessException("Category[" + category.getName() + "] already exists");
	}

	public void update(Category category)
	{
		this.getHibernateTemplate().update(category);
	}

}
