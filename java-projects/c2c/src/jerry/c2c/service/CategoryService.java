package jerry.c2c.service;

import java.util.List;

import jerry.c2c.domain.Category;
import jerry.c2c.exception.BusinessException;

public interface CategoryService
{
	public void save(Category category) throws BusinessException;
	public void update(Category category);
	public void delete(Category category);
	
	public Category getById(long id) throws BusinessException;
	public Category getByName(String name) throws BusinessException;
	
	public List findByKeyword(String keyWord);
	public List findByParent(Category category);
	public List findRoots();

}
