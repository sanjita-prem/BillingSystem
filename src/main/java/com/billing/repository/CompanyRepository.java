package com.billing.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.billing.db.DbUtil;
import com.billing.model.Company;

public class CompanyRepository{
	
	private Connection dbConnection;
	
	public CompanyRepository() {
		this.dbConnection= DbUtil.getConnection();
	}
	

	public Company findCompanyByName(String name){
		Company company=null;
		try {
			PreparedStatement pst=dbConnection.prepareStatement("select * from company_detail where name = ? ");
			pst.setString(1, name);
	
			
			ResultSet result = pst.executeQuery();
			
			if(result != null) {
				while(result.next()) {
					company= new Company();
					company.setName(result.getString("name"));
					company.setCompanyId(result.getInt("company_id"));
					company.setBankAcNo(result.getString("bank_ac_no"));
					company.setBankIfsc(result.getString("bank_ifsc"));
					company.setPanNo(result.getString("pan_no"));
					company.setBankName(result.getString("bank_name"));
				
					
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return company;
	}
	
	
	
	public Company findCompanyByNameAndPanNo(String name, String panNo){
		Company company=null;
		try {
			PreparedStatement pst = dbConnection.prepareStatement("select * from company_detail where name = ? and pan_no = ? ");
			pst.setString(1, name);
			pst.setString(2, panNo);
	
			
			ResultSet result = pst.executeQuery();
			
			if(result != null) {
				while(result.next()) {
					company= new Company();
					company.setName(result.getString("name"));
					company.setCompanyId(result.getInt("company_id"));
					company.setBankAcNo(result.getString("bank_ac_no"));
					company.setBankIfsc(result.getString("bank_ifsc"));
					company.setPanNo(result.getString("pan_no"));
					company.setBankIfsc(result.getString("bank_name"));
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return company;
	}
	
	
	public void update(Company company) {
		
		try {
			
			PreparedStatement pst = dbConnection.prepareStatement("update company_detail set  name = ?, bank_ac_no = ?, bank_ifsc = ?,"
					+ " pan_no = ?, bank_name = ? where company_id = ?  ");
			
			pst.setString(1, company.getName());
			pst.setString(2, company.getBankAcNo());
			pst.setString(3, company.getBankIfsc());
			pst.setString(4, company.getPanNo());
			pst.setString(5, company.getBankName());
			pst.setInt(6, company.getCompanyId());
			
			pst.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	public Company save(Company c) {
		
		try {
		PreparedStatement pst=dbConnection.prepareStatement("insert into company_detail (company_id , name , pan_no , bank_name , bank_ac_no , bank_ifsc) values (?, ?, ?, ?, ?, ?)");
		
		int id = getMaxId();
		 pst.setInt(1, ++id);
		 pst.setString(2, c.getName());
         pst.setString(3, c.getPanNo());
         pst.setString(4, c.getBankName());
         pst.setString(5, c.getBankAcNo());
         pst.setString(6, c.getBankIfsc());
		
         pst.executeUpdate();
	
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		c = findCompanyByNameAndPanNo(c.getName(), c.getPanNo());
		return c;
	}
	public int getMaxId(){
		int max =0;
		try{
			PreparedStatement prepStatement = dbConnection.prepareStatement("select max(company_id) from company_detail");
			ResultSet result = prepStatement.executeQuery();
			if(result != null && result.next()){
				max = result.getInt(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return max;
	}

	public List<Company> findAll() {
		List<Company> companys = null;
		try {
			PreparedStatement prepStatement = dbConnection.prepareStatement("select * from company_detail ");
			ResultSet result = prepStatement.executeQuery();
			if (result != null) {
				companys = new ArrayList<>();
	              while (result.next()) {
	            	  Company company = new Company();
	            	  
	            	  company.setCompanyId(result.getInt("company_id"));
	            	  company.setName(result.getString("name"));
	            	  company.setPanNo(result.getString("pan_no"));
	            	  company.setBankName(result.getString("bank_name"));
	            	  company.setBankAcNo(result.getString("bank_ac_no"));
	            	  company.setBankIfsc(result.getString("banl_ifsc"));
	            	 
	            	  companys.add(company);
	              }
	          }

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return companys;

	}

	public Company findOne(int id) {
		Company cmp=null;
		try {
			
			PreparedStatement prepStatement=dbConnection.prepareStatement("select * from company_detail where company_id = ?");
			prepStatement.setInt(1, id);
			
			ResultSet result = prepStatement.executeQuery();
			
			if(result != null) {
				while(result.next()) {
					cmp = new Company();
	            	  cmp.setCompanyId(result.getInt("company_id"));
	            	  cmp.setName(result.getString("name"));
	            	  cmp.setPanNo(result.getString("pan_no"));
	            	  cmp.setBankName(result.getString("bank_name"));
	            	  cmp.setBankIfsc(result.getString("bank_ifsc"));
	            	  cmp.setBankAcNo(result.getString("bank_ac_no"));
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	return cmp;
   }


	public Company findCompanyByPanNo(String panNo) {
		Company company=null;
		try {
			PreparedStatement pst = dbConnection.prepareStatement("select * from company_detail where pan_no = ? ");
			pst.setString(1, panNo);
			ResultSet result = pst.executeQuery();
			if(result != null) {
				while(result.next()) {
					company= new Company();
					company.setName(result.getString("name"));
					company.setCompanyId(result.getInt("company_id"));
					company.setBankAcNo(result.getString("bank_ac_no"));
					company.setBankIfsc(result.getString("bank_ifsc"));
					company.setPanNo(result.getString("pan_no"));
					company.setBankIfsc(result.getString("bank_name"));
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return company;
	}	
}		
