package jerry.c2c.service;

import java.util.List;

import jerry.c2c.domain.Category;
import jerry.c2c.domain.Shop;
import jerry.c2c.domain.ShopComment;
import jerry.c2c.exception.BusinessException;

public interface ShopService
{
	public void save(Shop shop) throws BusinessException;
	public void update(Shop shop);
	public void delete(Shop shop);
	
	public Shop getById(long id) throws BusinessException;
	public Shop getByName(String name) throws BusinessException;
	
	public List findByKeyword(String keyWord);
	public List findByCredit(int lowCredit,int highCredit);
	public List findByCategory(Category category);
	
	public void saveComment(ShopComment comment);
	public void updateComment(ShopComment comment);
	public void deleteComment(ShopComment comment);
	public ShopComment getCommentById(long id) throws BusinessException;
}
