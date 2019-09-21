package com.billing.service;

import java.util.ArrayList;
import java.util.List;

import com.billing.model.User;
import com.billing.model.UserBean;
import com.billing.repository.UserRepository;


public class UserService {
	
	private static final UserService INSTANCE = new UserService();
	
	public static UserService getInstance() {
		return INSTANCE;
	}

	private UserRepository userRepo;

	private UserService(){
		userRepo = new UserRepository();
	}

	public User getUserByUserId(String u){
		return userRepo.getUserByUserId(u);
	}

	public User getUserByUserIdPassword(String userId, String password){
		return userRepo.findByLogin(userId, password);
	}

	public List<User> findAllUser(){
		return userRepo.findAllUser();
	}

	public List<UserBean> findAllUserBean(){
		List<UserBean> userBeanList = new ArrayList<>();
		int count =1;
		for(User user : findAllUser()){
			userBeanList.add(new UserBean(count++, user.getUserId(), user.getPassword(), user.getActive(), user.getRole(), user.getCreated()));
		}
		return userBeanList;
	}
	
	public int getMaxUserId() {
		return userRepo.getMaxId();
	}

	public User save(User u){
		return userRepo.save(u);
	}

	public void delete(String u, String p){
		User user = userRepo.findByLogin(u, p);
		if(user != null)
			userRepo.delete(user);
	}

	public void update(UserBean userBean, String u, String p, int active){
		User user = userRepo.findByLogin(userBean.getRUserId(), userBean.getRPassword());
		user.setUserId(u);
		user.setPassword(p);
		user.setActive(active);
		userRepo.update(user);
	}
}
