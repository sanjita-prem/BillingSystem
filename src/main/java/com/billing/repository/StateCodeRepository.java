package com.billing.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.billing.db.DbUtil;
import com.billing.model.StateCode;

public class StateCodeRepository{
	private Connection dbConnection;

	public StateCodeRepository(){
		this.dbConnection = DbUtil.getConnection();
	}
	
	public List<StateCode> findAll(){
		List<StateCode> StateCodeList = null;
		try {
			PreparedStatement prepStatement = dbConnection.prepareStatement("select * from state_gst_code ");
			ResultSet result = prepStatement.executeQuery();
			if(result != null) {
				StateCodeList = new ArrayList<>();
				while(result.next()) {
					 String code = result.getString("code");
	            	  String name = result.getString("name");
	            	  
	            	  
	            	  StateCode stc= new StateCode();
	            	  stc.setCode(code);
	            	  stc.setName(name);
	            	  
	            	  StateCodeList.add(stc);
				}
				
			}
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	          		
		return StateCodeList ;
	}
}


