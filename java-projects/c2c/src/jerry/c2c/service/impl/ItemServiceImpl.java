package jerry.c2c.service.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import jerry.c2c.domain.Category;
import jerry.c2c.domain.Item;
import jerry.c2c.domain.ItemComment;
import jerry.c2c.domain.Shop;
import jerry.c2c.exception.BusinessException;
import jerry.c2c.service.ItemService;

public class ItemServiceImpl extends HibernateDaoSupport implements ItemService
{
	private String FIND_BY_CATEGORY = "from Item where category=? and soldOut=false";

	private String FIND_BY_KEYWORD = "from Item where name like ? or description like ? and soldOut=false";

	private String FIND_BY_SHOP = "from Item where belongTo=? and soldOut=false";
	
	private String FIND_COMMENTS = "from ItemComment where destItem = ? order by time desc";

	public void delete(Item item)
	{
		this.getHibernateTemplate().delete(item);
	}

	public void deleteComment(ItemComment comment)
	{
		this.getHibernateTemplate().delete(comment);
	}

	public List findByCategory(Category category)
	{
		List temp = this.getHibernateTemplate()
				.find(FIND_BY_CATEGORY, category);
		return temp;
	}

	public List findByKeyword(String keyWord)
	{
		Object[] args = new Object[2];
		args[0] = args[1] = "%" + keyWord + "%";
		List temp = this.getHibernateTemplate().find(FIND_BY_KEYWORD, args);
		return temp;
	}

	public List findByShop(Shop shop)
	{
		List temp = this.getHibernateTemplate().find(FIND_BY_SHOP, shop);
		return temp;
	}

	public Item getById(long id) throws BusinessException
	{
		try
		{
			Item temp = null;
			temp = (Item) this.getHibernateTemplate().get(Item.class, id);
			this.getHibernateTemplate().initialize(temp.getComments());
			this.getHibernateTemplate().initialize(temp.getCategory());
			this.getHibernateTemplate().initialize(temp.getBelongTo());
			return temp;
		}
		catch (DataAccessException e)
		{
			throw new BusinessException("Item[id:" + id + "] does not exist");
		}
	}

	public ItemComment getCommentById(long id) throws BusinessException
	{
		try
		{
			ItemComment temp = null;
			temp = (ItemComment) this.getHibernateTemplate().get(
					ItemComment.class, id);
			this.getHibernateTemplate().initialize(temp.getMaker());
			return temp;
		}
		catch (DataAccessException e)
		{
			throw new BusinessException("ItemComment[id:" + id
					+ "] does not exist");
		}
	}

	public void save(Item item)
	{
		this.getHibernateTemplate().save(item);
	}

	public void saveComment(ItemComment comment)
	{
		this.getHibernateTemplate().save(comment);

	}

	public void update(Item item)
	{
		this.getHibernateTemplate().update(item);
	}

	public void updateComment(ItemComment comment)
	{
		this.getHibernateTemplate().update(comment);
	}

	public List findCommentsByItem(Item item)
	{
		List result = this.getHibernateTemplate().find(FIND_COMMENTS,item);
		return result;
	}

}
