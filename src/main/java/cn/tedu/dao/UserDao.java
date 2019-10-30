package cn.tedu.dao;

import cn.tedu.entity.User;

public interface UserDao {

	public int addUser(User user);
	
	public int deleteUser(User user);
	
	public int updateUser(User user);
	
	public User findUserById(String id);
	
}
