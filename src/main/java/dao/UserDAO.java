package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.User;

public class UserDAO extends JpaDAO<User> implements GenericDAO<User>{

	public UserDAO() {
	}

	public User create(User user) {
		return super.create(user);
	}
	
	@Override
	public User update(User user) {
		return super.update(user);
	}

	@Override
	public User get(Object userId) {
		return super.find(User.class, userId);
	}
	
	public User findByEmail(String email) {
		List<User> listUsers = super.findWithNamedQuery("Users.findByEmail", "email", email);
		
		if (listUsers != null && listUsers.size() > 0) {
			return listUsers.get(0);
		}
		
		return null;
	}
	
	public boolean checkLogin(String email, String password) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("email", email);
		parameters.put("password", password);
		
		List<User> listUsers = super.findWithNamedQuery("Users.checkLogin", parameters);
		
		if (listUsers.size() == 1) {
			return true;
		}
		
		return false;
	}

	@Override
	public void delete(Object userId) {
		super.delete(User.class, userId);
	}

	@Override
	public List<User> listAll() {
		return super.findWithNamedQuery("Users.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("Users.countAll");
	}
}

