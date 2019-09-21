package com.billing.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.billing.AppContext;
import com.billing.db.DbUtil;
import com.billing.model.User;
import com.billing.model.UserBean;

public class UserRepository {
	private static final Logger LOGGER = Logger.getLogger(UserRepository.class.getName());
	private Connection dbConnection;

	public UserRepository(){
		this.dbConnection = DbUtil.getConnection();
	}

	public User findByLogin(String userName, String password) {
		LOGGER.info("User try to login : "+userName+"/"+password);
		User user = null;
	      try {
	          PreparedStatement prepStatement = dbConnection.prepareStatement("select * from user where user_id = ? and password = ? and active = 1");
	          prepStatement.setString(1, userName);
	          prepStatement.setString(2, password);

	          ResultSet result = prepStatement.executeQuery();

	          if (result != null && result.next()) {
	        	  user = new User();
            	  user.setId(result.getInt("id"));
            	  user.setUserId(result.getString("user_id"));
            	  user.setCreated(result.getString("created"));
            	  user.setRole(result.getString("role"));
	          }
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
	      return user;
	  }

	public User getUserByUserId(String userName) {
		User user = null;
	      try {
	          PreparedStatement prepStatement = dbConnection.prepareStatement("select * from user where user_id = ?");
	          prepStatement.setString(1, userName);

	          ResultSet result = prepStatement.executeQuery();

	          if (result != null) {
	        	  //user = new User();
	              while (result.next()) {
	            	  user = new User();
	            	  user.setId(result.getInt("id"));
	            	  user.setUserId(result.getString("user_id"));
	            	  user.setPassword(result.getString("password"));
	            	  user.setCreated(result.getString("created"));
	            	  user.setActive(result.getInt("active"));
	            	  user.setRole(result.getString("role"));
	              }
	          }
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
	      return user;
	  }
	public int getMaxId(){
		int max =0;
		try{
			PreparedStatement prepStatement = dbConnection.prepareStatement("select max(id) from user");
			ResultSet result = prepStatement.executeQuery();
			if(result != null && result.next()){
				max = result.getInt(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return max;
	}
	public List<User> findAllUser(){
		List<User> userList = null;
		try {
			PreparedStatement prepStatement = dbConnection.prepareStatement("select * from user ");
			ResultSet result = prepStatement.executeQuery();
			if (result != null) {
				userList = new ArrayList<>();
				//iterate result set
	              while (result.next()) {
	            	  
	            	  int id = result.getInt("id");
	            	  String userId = result.getString("user_id");
	            	  String password = result.getString("password");
	            	  String created = result.getString("created");
	            	  int active = result.getInt("active");
	            	  String role =  result.getString("role");
	            	  //initialize user with above collected data
	            	  User user=new User();
	            	  user.setId(id);
	            	  user.setUserId(userId);
	            	  user.setPassword(password);
	            	  user.setCreated(created);
	            	  user.setActive(active);
	            	  user.setRole(role);
	            	  
	            	  //add user object in list
	            	  userList.add(user);
	              }
	          }

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}

	public List<UserBean> findAllUserBean(){
		List<UserBean> userBeanList = new ArrayList<>();
		int count =1;
		for(User user : findAllUser()){
			userBeanList.add(new UserBean(count++, user.getUserId(), user.getPassword(), user.getActive(), user.getRole(), user.getCreated()));
		}
		return userBeanList;
	}

	public User save(User user) {
		try {
			User existingUser = getUserByUserId(user.getUserId());
			if(existingUser == null){
	          PreparedStatement prepStatement = dbConnection.prepareStatement("insert into user(id, user_id, password, created, active, role) values (?, ?, ?, ?,?,?)");
	          int id = getMaxId();
	          prepStatement.setInt(1, ++id);
	          prepStatement.setString(2, user.getUserId());
	          prepStatement.setString(3, user.getPassword());
	          prepStatement.setString(4, user.getCreated());
	          prepStatement.setInt(5, user.getActive());
	          prepStatement.setString(6, user.getRole() == null ? AppContext.NORMAL : user.getRole());

	          prepStatement.executeUpdate();
	          user = getUserByUserId(user.getUserId());
			}else{
				System.out.println("User Allready Exist");
			}
	      } catch (SQLException e) {
	          e.printStackTrace();
	      }
		return user;
	}

	public void delete(User user) {

		//delete from user where user_id = ?
		try{
			PreparedStatement prepStatement = dbConnection.prepareStatement("delete from user where user_id = ?");
			prepStatement.setString(1, user.getUserId());
	          
	          prepStatement.executeUpdate();
	          System.out.println("Record deleted");
		
			
		}catch(Exception e){
			e.printStackTrace();
	     }
	}

	public void update(User user){
		try{
			PreparedStatement prepStatement = dbConnection.prepareStatement("update user set user_id = ? , password = ?, created = ?, active = ? where id = ?");
			prepStatement.setString(1, user.getUserId());
	          prepStatement.setString(2, user.getPassword());
	          prepStatement.setString(3, user.getCreated());
	          prepStatement.setInt(4, user.getActive());
	          prepStatement.setInt(1, user.getId());
	          prepStatement.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}

	}
}
