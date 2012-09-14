package jerry.c2c.service;

import java.util.List;

import jerry.c2c.domain.User;
import jerry.c2c.exception.BusinessException;

public interface UserService
{
	public void save(User user) throws BusinessException;
	public void delete(User user);
	public void update(User user);
	
	public User getByName(String username) throws BusinessException;
	public User getById(long id) throws BusinessException;
	
	public List findByKeyword(String keyword);
	
	public User login(String username,String password) throws BusinessException;
}
