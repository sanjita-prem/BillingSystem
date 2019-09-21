package com.billing.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.billing.db.DbUtil;
import com.billing.model.Owner;

public class OwnerRepository {
	private Connection dbConnection;

	public OwnerRepository(){
		dbConnection = DbUtil.getConnection();
	}

	public Owner findByName(String name){
		Owner owner = null;
		try {
			
			PreparedStatement pst = dbConnection.prepareStatement("select * from owner where name = ? ");
	
	          pst.setString(1,name);
	          
	          ResultSet result = pst.executeQuery();
	          
	          if (result != null) {
	        	  while(result.next()) {
	        	  owner = new Owner();
	        	  
	        	  owner.setName(result.getString("name"));
	        	  owner.setEmail(result.getString("email"));
	        	  owner.setPhone1(result.getString("phone1"));
	        	  owner.setPhone2(result.getString("phone2"));
	        	  owner.setState(result.getString("state"));
	        	  owner.setCity(result.getString("city"));
	        	  owner.setAddress(result.getString("address"));
	        	  owner.setPin(result.getString("pin"));
	        	  owner.setAliasName(result.getString("alias_name"));
	        	  owner.setPanNo(result.getString("pan_no"));
	        	  owner.setGstNo(result.getString("gst_no"));
	        	  owner.setActive(result.getInt("active"));
	        	  owner.setCompanyId(result.getInt("company_id"));
	        	  owner.setOwnerId(result.getInt("owner_id"));
	          }
	          }
		}catch(Exception e) {
			e.printStackTrace();
		}
		return owner;
	}

	public Owner findByPanNo(String panNo){
		Owner owner = null;
		try {
			
			PreparedStatement pst = dbConnection.prepareStatement("select * from owner where pan_no = ?");
			 pst.setString(1, panNo);
	          
	          
	          ResultSet result = pst.executeQuery();
	          
	          if (result != null) {
	        	  while(result.next()) {
	        	  owner = new Owner();
	        	  owner.setName(result.getString("name"));
	        	  owner.setEmail(result.getString("email"));
	        	  owner.setPhone1(result.getString("phone1"));
	        	  owner.setPhone2(result.getString("phone2"));
	        	  owner.setState(result.getString("state"));
	        	  owner.setCity(result.getString("city"));
	        	  owner.setAddress(result.getString("address"));
	        	  owner.setPin(result.getString("pin"));
	        	  owner.setAliasName(result.getString("alias_name"));
	        	  owner.setPanNo(result.getString("pan_no"));
	        	  owner.setGstNo(result.getString("gst_no"));
	        	  owner.setActive(result.getInt("active"));
	        	  owner.setCompanyId(result.getInt("company_id"));
	        	  owner.setOwnerId(result.getInt("owner_id"));
	        	  
	          }
	          }
		}catch(Exception e) {
			e.printStackTrace();
		}
		return owner;
	}

	public Owner findByNameAndPanNo(String name, String panNo){
		Owner owner = null;
		try {
			
			PreparedStatement pst = dbConnection.prepareStatement("select * from owner where name = ? and pan_no = ?");
			 pst.setString(1, name);
	          pst.setString(2,panNo);
	          
	          ResultSet result = pst.executeQuery();
	          
	          if (result != null) {
	        	  while(result.next()) {
	        	  owner = new Owner();
	        	  
	        	  owner.setName(result.getString("name"));
	        	  owner.setEmail(result.getString("email"));
	        	  owner.setPhone1(result.getString("phone1"));
	        	  owner.setPhone2(result.getString("phone2"));
	        	  owner.setState(result.getString("state"));
	        	  owner.setCity(result.getString("city"));
	        	  owner.setAddress(result.getString("address"));
	        	  owner.setPin(result.getString("pin"));
	        	  owner.setAliasName(result.getString("alias_name"));
	        	  owner.setPanNo(result.getString("pan_no"));
	        	  owner.setGstNo(result.getString("gst_no"));
	        	  owner.setActive(result.getInt("active"));
	        	  owner.setCompanyId(result.getInt("company_id"));
	        	  owner.setOwnerId(result.getInt("owner_id"));
	        	  
	        	  
	          }
	          }
		}catch(Exception e) {
			e.printStackTrace();
		}
		return owner;
	}

	public List<Owner> findAll() {
		List<Owner> owners = null;
		try {
			PreparedStatement prepStatement = dbConnection.prepareStatement("select * from owner ");
			ResultSet result = prepStatement.executeQuery();
			if (result != null) {
				owners = new ArrayList<>();
	              while (result.next()) {
	            	  Owner owner = new Owner();
	            	  owner.setOwnerId(result.getInt("owner_id"));
	            	  owner.setName(result.getString("name"));
	            	  owner.setEmail(result.getString("email"));
	            	  owner.setPhone1(result.getString("phone1"));
	            	  owner.setPhone2(result.getString("phone2"));
	            	  owner.setState(result.getString("state"));
	            	  owner.setCity(result.getString("city"));
	            	  owner.setAddress(result.getString("address"));
	            	  owner.setPin(result.getString("pin"));
	            	  owner.setCreated(result.getString("created"));
	            	  owner.setAliasName(result.getString("alias_name"));
	            	  owner.setPanNo(result.getString("pan_no"));
	            	  owner.setGstNo(result.getString("gst_no"));
	            	  owner.setActive(result.getInt("active"));
	            	  owner.setCompanyId(result.getInt("company_id"));
	            	  owners.add(owner);
	              }
	          }

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return owners;
	}
	
	
	public int getMaxId(){
		int max =0;
		try{
			PreparedStatement prepStatement = dbConnection.prepareStatement("select max (owner_id) from owner");
			ResultSet result = prepStatement.executeQuery();
			if(result != null && result.next()){
				max = result.getInt(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return max;
	}

	public Owner save(Owner o) {
		
      try {
    	  PreparedStatement pst = dbConnection.prepareStatement("insert into owner(owner_id, name, email, phone1, phone2, state, city, address, pin, created, alias_name, pan_no, gst_no, active, company_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    	  
    			  int id = getMaxId();
    			  o.setOwnerId(++id);
 		          pst.setInt(1, o.getOwnerId());
    			  pst.setString(2, o.getName());
    			  pst.setString(3, o.getEmail());
    			  pst.setString(4, o.getPhone1());
    			  pst.setString(5, o.getPhone2());
    			  pst.setString(6, o.getState());
    			  pst.setString(7, o.getCity());
    			  pst.setString(8, o.getAddress());
    			  pst.setString(9, o.getPin());
    			  pst.setString(10, o.getCreated());
    			  pst.setString(11, o.getAliasName());
    			  pst.setString(12, o.getPanNo());
    			  pst.setString(13, o.getGstNo());
    			  pst.setInt(14, o.getActive());
    			  pst.setInt(15, o.getCompanyId());
    			  
    			  pst.executeUpdate();
    			  
    			  
      }catch(Exception e){
    	  e.printStackTrace();

      }
		return o;
	}

	public void delete(Owner o) {
		try{
			PreparedStatement prepStatement = dbConnection.prepareStatement("delete from owner where pan_no = ?");
			prepStatement.setString(1, o.getPanNo());
	          
	          prepStatement.executeUpdate();
	          System.out.println("Record deleted");
		
			
		}catch(Exception e){
			e.printStackTrace();
	     }

	}

	public Owner findOne(int id) {
		Owner own = null;
		try{
			
			PreparedStatement pst = dbConnection.prepareStatement("select * from owner where owner_id = ? ");
			 pst.setInt(1,id);
			 
			 ResultSet result = pst.executeQuery();
			 
			 if(result != null) {
				own = new Owner();
				own.setOwnerId(result.getInt("Owner_id"));
				own.setName(result.getString("name"));
				own.setEmail(result.getString("email"));
				own.setPhone1(result.getString("phone1"));
				own.setPhone2(result.getString("phone2"));
				own.setState(result.getString("state"));
				own.setCity(result.getString("city"));
				own.setAddress(result.getString("address"));
				own.setPin(result.getString("pin"));
				own.setCreated(result.getString("created"));
				own.setAliasName(result.getString("alias_name"));
				own.setPanNo(result.getString("pan_no"));
				own.setGstNo(result.getString("gst_no"));
				own.setActive(result.getInt("active"));
				own.setCompanyId(result.getInt("company_id"));
			
			 }
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		return own;
	}
	
	public void update(Owner owner) {
		
		try {
			
			PreparedStatement pst = dbConnection.prepareStatement("update owner set name = ? , email = ? , phone1 = ? , phone2 = ? , "
					+ "state = ? , city = ? , address = ? , pin = ? ,  alias_name = ? , pan_no = ? "
					+ ", gst_no = ? , active = ? , company_id =? where owner_id = ?");
			
			
			pst.setString(1, owner.getName());
			pst.setString(2, owner.getEmail());
			pst.setString(3, owner.getPhone1());
			pst.setString(4, owner.getPhone2());
			pst.setString(5, owner.getState());
			pst.setString(6, owner.getCity());
			pst.setString(7, owner.getAddress());
			pst.setString(8, owner.getPin());
			pst.setString(9, owner.getAliasName());
			pst.setString(10, owner.getPanNo());
			pst.setString(11, owner.getGstNo());
			pst.setInt(12, owner.getActive());
			pst.setInt(13, owner.getCompanyId());
			pst.setInt(14, owner.getOwnerId());
			
			pst.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
