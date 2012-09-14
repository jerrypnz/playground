package com.zhaonan.forum.service;

import java.util.List;

import com.zhaonan.forum.po.User;

public interface UserService
{
	void save(final User user);
	void update(final User user);
	void delete(final int userId);
	User get(final int userId);
	User getByName(final String loginName);
	User login(final String username,final String password);
	List<User> listAll();
	List<User> query(final String sql);
}
