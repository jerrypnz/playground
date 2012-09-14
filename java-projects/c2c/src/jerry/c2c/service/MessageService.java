package jerry.c2c.service;

import java.util.List;

import jerry.c2c.domain.Message;
import jerry.c2c.domain.User;
import jerry.c2c.exception.BusinessException;

public interface MessageService
{
	public void save(Message msg);
	public void update(Message msg);
	public void delete(Message msg);
	
	public Message getById(long id) throws BusinessException;
	
	public void send(User from,User to,Message msg);
	public List receive(User user);

}
