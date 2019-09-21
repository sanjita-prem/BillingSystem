package com.billing.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.billing.db.DbUtil;
import com.billing.model.Customer;

public class CustomerRepository{
	private Connection dbConnection;

	public CustomerRepository(){
		dbConnection = DbUtil.getConnection();
	}

	public Customer findByName(String name){
		Customer customer=null;
		
		try {
			 PreparedStatement prepStatement = dbConnection.prepareStatement("select * from customer where name = ? ");
	          prepStatement.setString(1, name);
	         
	          ResultSet result = prepStatement.executeQuery();
	          
	          if(result != null) {
	        	  
	        	  while(result.next()) {
	        		  customer = new Customer();
	            	  customer.setCustomerId(result.getInt("customer_id"));
	            	  customer.setName(result.getString("name"));
	            	  customer.setEmail(result.getString("email"));
	            	  customer.setPhone1(result.getInt("phone1"));
	            	  customer.setPhone2(result.getInt("phone2"));
	            	  customer.setState(result.getString("state"));
	            	  customer.setCity(result.getString("city"));
	            	  customer.setAddress(result.getString("address"));
	            	  customer.setPin(result.getInt("pin"));
	            	  customer.setCreated(result.getString("created"));
	            	  customer.setAliasName(result.getString("alias_name"));
	            	  customer.setPanNo(result.getString("pan_no"));
	            	  customer.setGstNo(result.getString("gst_no"));
	            	  customer.setActive(result.getInt("active"));
	        		  
	        	  }
	          }
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return customer;
	}

	public Customer findByPanNo(String panNo){
		//LOGGER.info("panNo ");
		Customer customer=null;
		
		try {
			 PreparedStatement prepStatement = dbConnection.prepareStatement("select * from customer where pan_no = ?  and active = 1");
	          prepStatement.setString(1, panNo);
	         
	          ResultSet result = prepStatement.executeQuery();
	          
	          if(result != null) {
	        	  
	        	  while(result.next()) {
	        		  customer=new Customer();
	        		  customer.setCustomerId(result.getInt("customer_id"));
	            	  customer.setName(result.getString("name"));
	            	  customer.setEmail(result.getString("email"));
	            	  customer.setPhone1(result.getInt("phone1"));
	            	  customer.setPhone2(result.getInt("phone2"));
	            	  customer.setState(result.getString("state"));
	            	  customer.setCity(result.getString("city"));
	            	  customer.setAddress(result.getString("address"));
	            	  customer.setPin(result.getInt("pin"));
	            	  customer.setCreated(result.getString("created"));
	            	  customer.setAliasName(result.getString("alias_name"));
	            	  customer.setPanNo(result.getString("pan_no"));
	            	  customer.setGstNo(result.getString("gst_no"));
	            	  customer.setActive(result.getInt("active"));
	        		  
	        	  }
	          }
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return customer;
	}

	public Customer findByNameAndPanNo(String name, String panNo){
		
			Customer customer=null;
		
		try {
			 PreparedStatement prepStatement = dbConnection.prepareStatement("select * from customer where pan_no = ? and name = ?  and active = 1");
	          prepStatement.setString(1, panNo);
	          prepStatement.setString(2,name);
	         
	          ResultSet result = prepStatement.executeQuery();
	          
	          if(result != null) {
	        	  
	        	  while(result.next()) {
	        		  customer=new Customer();
	        		  customer.setCustomerId(result.getInt("customer_id"));
	            	  customer.setName(result.getString("name"));
	            	  customer.setEmail(result.getString("email"));
	            	  customer.setPhone1(result.getInt("phone1"));
	            	  customer.setPhone2(result.getInt("phone2"));
	            	  customer.setState(result.getString("state"));
	            	  customer.setCity(result.getString("city"));
	            	  customer.setAddress(result.getString("address"));
	            	  customer.setPin(result.getInt("pin"));
	            	  customer.setCreated(result.getString("created"));
	            	  customer.setAliasName(result.getString("alias_name"));
	            	  customer.setPanNo(result.getString("pan_no"));
	            	  customer.setGstNo(result.getString("gst_no"));
	            	  customer.setActive(result.getInt("active"));
	        		  
	        	  }
	          }
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return customer;
	}
	

	public List<Customer> findAll() {
		List<Customer> customers = null;
		try {
			PreparedStatement prepStatement = dbConnection.prepareStatement("select * from customer ");
			ResultSet result = prepStatement.executeQuery();
			if (result != null) {
				customers = new ArrayList<>();
	              while (result.next()) {
	            	  Customer customer = new Customer();
	            	  customer.setCustomerId(result.getInt("customer_id"));
	            	  customer.setName(result.getString("name"));
	            	  customer.setEmail(result.getString("email"));
	            	  customer.setPhone1(result.getInt("phone1"));
	            	  customer.setPhone2(result.getInt("phone2"));
	            	  customer.setState(result.getString("state"));
	            	  customer.setCity(result.getString("city"));
	            	  customer.setAddress(result.getString("address"));
	            	  customer.setPin(result.getInt("pin"));
	            	  customer.setCreated(result.getString("created"));
	            	  customer.setAliasName(result.getString("alias_name"));
	            	  customer.setPanNo(result.getString("pan_no"));
	            	  customer.setGstNo(result.getString("gst_no"));
	            	  customer.setActive(result.getInt("active"));
	            	  customers.add(customer);
	              }
	          }

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}
	
	
	public int getMaxId(){
		int max =0;
		try{
			PreparedStatement prepStatement = dbConnection.prepareStatement("select max(customer_id) from customer");
			ResultSet result = prepStatement.executeQuery();
			if(result != null && result.next()){
				max = result.getInt(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return max;
	}
	
	

	public Customer save(Customer o) {
				
		try {
			PreparedStatement prepStatement = dbConnection.prepareStatement("insert into customer ( customer_id, name,email, phone1,phone2,state,city,address,pin,created,alias_name,pan_no,gst_no, active) values (?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?)");
	          int id = getMaxId();
	          prepStatement.setInt(1, ++id);
	          prepStatement.setString(2, o.getName());
	          prepStatement.setString(3, o.getEmail());
	          prepStatement.setInt(4, o.getPhone1());
	          prepStatement.setInt(5, o.getPhone2());
	          prepStatement.setString(6, o.getState());
	          prepStatement.setString(7, o.getCity());
	          prepStatement.setString(8, o.getAddress());
	          prepStatement.setInt(9, o.getPin());
	          prepStatement.setString(10, o.getCreated());
	          prepStatement.setString(11, o.getAliasName());
	          prepStatement.setString(12, o.getPanNo());
	          prepStatement.setString(13, o.getGstNo());
	          prepStatement.setInt(14,o.getActive());
	          
	          prepStatement.executeUpdate();
	          o = findByNameAndPanNo(o.getName(), o.getPanNo());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return o;
	}

	public void delete(Customer o) {
		try{
			PreparedStatement prepStatement = dbConnection.prepareStatement("delete from customer where pan_no = ?");
			prepStatement.setString(1, o.getPanNo());
	          
	          prepStatement.executeUpdate();
	          System.out.println("Record deleted");
		
			
		}catch(Exception e){
			e.printStackTrace();
	     }
	}
	
	
	
	public void update(Customer customer) {
		try {
			PreparedStatement prepStatement = dbConnection.prepareStatement("update customer set  name = ? , email = ? , phone1 = ? , "
					+ "phone2 = ? , state = ? , city = ? , "
					+ "address = ? , pin = ? , created = ? , "
					+ "alias_name = ? , pan_no = ? , gst_no = ? , "
					+ "active = ? where customer_id = ?");

			prepStatement.setString(1, customer.getName());
	          prepStatement.setString(2, customer.getEmail());
	          prepStatement.setInt(3, customer.getPhone1());
	          prepStatement.setInt(4, customer.getPhone2());
	          prepStatement.setString(5, customer.getState());
	          prepStatement.setString(6, customer.getCity());
	          prepStatement.setString(7, customer.getAddress());
	          prepStatement.setInt(8, customer.getPin());
	          prepStatement.setString(9, customer.getCreated());
	          prepStatement.setString(10, customer.getAliasName());
	          prepStatement.setString(11, customer.getPanNo());
	          prepStatement.setString(12, customer.getGstNo());
	          prepStatement.setInt(13, customer.getActive());
	          prepStatement.setInt(14, customer.getCustomerId());
	   
	          prepStatement.executeUpdate();	
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
	}

	public Customer findOne(int id) {
		Customer customer=null;
		
		try {
			 PreparedStatement prepStatement = dbConnection.prepareStatement("select * from customer where customer_id = ? ");
	          prepStatement.setInt(1, id);
	         
	          ResultSet result = prepStatement.executeQuery();
	          
	          if(result != null) {
	        	  
	        	  while(result.next()) {
	        		  customer = new Customer();
	            	  customer.setCustomerId(result.getInt("customer_id"));
	            	  customer.setName(result.getString("name"));
	            	  customer.setEmail(result.getString("email"));
	            	  customer.setPhone1(result.getInt("phone1"));
	            	  customer.setPhone2(result.getInt("phone2"));
	            	  customer.setState(result.getString("state"));
	            	  customer.setCity(result.getString("city"));
	            	  customer.setAddress(result.getString("address"));
	            	  customer.setPin(result.getInt("pin"));
	            	  customer.setCreated(result.getString("created"));
	            	  customer.setAliasName(result.getString("alias_name"));
	            	  customer.setPanNo(result.getString("pan_no"));
	            	  customer.setGstNo(result.getString("gst_no"));
	            	  customer.setActive(result.getInt("active"));
	        		  
	        	  }
	          }
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return customer;
	}
}
