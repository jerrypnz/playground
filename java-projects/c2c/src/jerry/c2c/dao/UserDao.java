package jerry.c2c.dao;

import java.util.List;

import jerry.c2c.domain.User;

public interface UserDao
{
	public void saveUser(User user);
	public void deleteUser(User user);
	public User getUserById(final long id);
	public User getUserByName(final String name);
	public List listAllUsers();
}
