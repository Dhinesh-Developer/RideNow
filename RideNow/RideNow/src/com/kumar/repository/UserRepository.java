package com.kumar.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.kumar.model.User;

public class UserRepository {
	private final Map<String, User> users;
	private static UserRepository instance;

	private UserRepository() {
		users = new ConcurrentHashMap<>();
	}

	public static synchronized UserRepository getInstance() {
		if (instance == null) {
			instance = new UserRepository();
		}
		return instance;
	}

	public void addUser(User user) {
		users.put(user.getUserId(), user);
	}

	public User getUserById(String userId) {
		return users.get(userId);
	}

	public void updateUser(User user) {
		users.put(user.getUserId(), user);
	}

	public void removeUser(String userId) {
		users.remove(userId);
	}

	public boolean userExists(String userId) {
		return users.containsKey(userId);
	}

	public int getTotalUsers() {
		return users.size();
	}
}
