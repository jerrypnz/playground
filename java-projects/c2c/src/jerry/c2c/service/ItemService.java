package jerry.c2c.service;

import java.util.List;


import jerry.c2c.domain.Category;
import jerry.c2c.domain.Item;
import jerry.c2c.domain.ItemComment;
import jerry.c2c.domain.Shop;
import jerry.c2c.exception.BusinessException;

public interface ItemService
{
	public void save(Item item);
	public void update(Item item);
	public void delete(Item item);
	
	public Item getById(long id) throws BusinessException;
	
	public List findByCategory(Category category);
	public List findByKeyword(String keyWord);
	public List findByShop(Shop shop);
	
	public void saveComment(ItemComment comment);
	public void updateComment(ItemComment comment);
	public void deleteComment(ItemComment comment);
	public ItemComment getCommentById(long id) throws BusinessException;
	public List findCommentsByItem(Item item);
}
