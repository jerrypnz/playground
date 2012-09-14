package jerry.c2c.service.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import jerry.c2c.domain.Category;
import jerry.c2c.domain.Shop;
import jerry.c2c.domain.ShopComment;
import jerry.c2c.exception.BusinessException;
import jerry.c2c.service.ShopService;

public class ShopServiceImpl extends HibernateDaoSupport implements ShopService
{
	private String FIND_BY_CATEGORY = "from Shop where category=?";
	private String FIND_BY_KEYWORD = "from Shop where name like ? or description like ?";
	private String FIND_BY_CREDIT = "from Shop where credit between ? and ?";
	private String GET_BY_NAME = "from Shop where name=?";

	public void delete(Shop shop)
	{
		this.getHibernateTemplate().delete(shop);
	}

	public void deleteComment(ShopComment comment)
	{
		this.getHibernateTemplate().delete(comment);
	}

	public List findByCategory(Category category)
	{
		List temp = this.getHibernateTemplate()
				.find(FIND_BY_CATEGORY, category);
		return temp;
	}

	public List findByCredit(int lowCredit, int highCredit)
	{
		int big;
		int small;
		if (lowCredit > highCredit)
		{
			big = lowCredit;
			small = highCredit;
		}
		else
		{
			small = lowCredit;
			big = highCredit;
		}
		Object[] args = new Object[2];
		args[0] = small;
		args[1] = big;
		List temp = this.getHibernateTemplate().find(FIND_BY_CREDIT, args);
		return temp;
	}

	public List findByKeyword(String keyWord)
	{
		Object[] args = new Object[2];
		args[0] = args[1] = "%" + keyWord + "%";
		List temp = this.getHibernateTemplate().find(FIND_BY_KEYWORD, args);
		return temp;
	}

	public Shop getById(long id) throws BusinessException
	{
		try
		{
			Shop temp = null;
			temp = (Shop) this.getHibernateTemplate()
				.get(Shop.class, id);
			this.getHibernateTemplate().initialize(temp.getComments());
			this.getHibernateTemplate().initialize(temp.getItems());
			this.getHibernateTemplate().initialize(temp.getCategory());
			this.getHibernateTemplate().initialize(temp.getOwner());
			return temp;
		}
		catch (DataAccessException e)
		{
			throw new BusinessException("Shop[id:" + id +"] does not exist");
		}
	}

	public Shop getByName(String name) throws BusinessException
	{
		List temp = this.getHibernateTemplate().find(GET_BY_NAME, name);
		if (temp.size() == 0)
			throw new BusinessException("Shop[name:" + name + "] does not exist");
		else
			return (Shop)temp.get(0);
	}

	public void save(Shop shop) throws BusinessException
	{
		List temp = this.getHibernateTemplate().find(GET_BY_NAME, shop.getName());
		if (temp.size() == 0)
			this.getHibernateTemplate().save(shop);
		else
			throw new BusinessException("Shop[name:" + shop.getName() + "] already exists");

	}

	public void saveComment(ShopComment comment)
	{
		this.getHibernateTemplate().save(comment);
	}

	public void update(Shop shop)
	{
		this.getHibernateTemplate().update(shop);
	}

	public void updateComment(ShopComment comment)
	{
		this.getHibernateTemplate().update(comment);
	}

	public ShopComment getCommentById(long id) throws BusinessException
	{
		try
		{
			ShopComment temp = null;
			temp = (ShopComment) this.getHibernateTemplate()
				.get(ShopComment.class, id);
			this.getHibernateTemplate().initialize(temp.getMaker());
			return temp;
		}
		catch (DataAccessException e)
		{
			throw new BusinessException("ShopComment[id:" + id + "] does not exist");
		}
		
	}

}
