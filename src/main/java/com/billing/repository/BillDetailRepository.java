package com.billing.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.billing.db.DbUtil;
import com.billing.model.BillDetail;



public class BillDetailRepository {
	private Connection dbConnection;
	
	public BillDetailRepository() {
		this.dbConnection = DbUtil.getConnection();
	}
	
	public int getMaxId(){
		int max =0;
		try{
			PreparedStatement prepStatement = dbConnection.prepareStatement("select max (bill_detail_id) from bill_details");
			ResultSet result = prepStatement.executeQuery();
			if(result != null && result.next()){
				max = result.getInt(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return max;
	
	}

	public List<BillDetail> findBillDetailByReceiptId(String receiptId){
		List<BillDetail> list = null;
		try {
			PreparedStatement pst = dbConnection.prepareStatement("select * from bill_details where receipt_id = ? ");
			
	          pst.setString(1,receiptId);
	          
	          ResultSet result = pst.executeQuery();
	          
	          if (result != null) {
	        	  list = new ArrayList<>();
	        	  while(result.next()) {
	        		  BillDetail billDetail = new BillDetail();
	        		  billDetail.setReceiptId(result.getString("receipt_id"));
	        		  billDetail.setDescription(result.getString("description"));
	        		  billDetail.setHsnCode(result.getString("hsn_code"));
	        		  billDetail.setPer(result.getString("per"));
	        		  billDetail.setRate(result.getDouble("rate"));
	        		  billDetail.setQty(result.getInt("qty"));
	        		  billDetail.setDiscVal(result.getString("disc_val"));
	        		  billDetail.setDiscAmt(result.getDouble("disc_amt"));
	        		  billDetail.setTotalAmt(result.getDouble("total_amt"));
	        		  billDetail.setBillDate(result.getString("bill_date"));
	        		  
	        		  list.add(billDetail);
	        	  
	        	  }
	          }
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	//@Query(value="delete from bill_details where receipt_id= ?1", nativeQuery = true)
	public void deleteBillDetailsByReceiptId(String receiptId){
		try{
			PreparedStatement prepStatement = dbConnection.prepareStatement("delete from bill_details where receipt_id = ?");
			prepStatement.setString(1, receiptId);
	          
	         int del = prepStatement.executeUpdate();
	          System.out.println("Receipt deleted :"+del);
		
		}catch(Exception e){
			e.printStackTrace();
	     }
	}

	public BillDetail saveAndFlush(BillDetail billDetail) {
		try {
	    	  PreparedStatement pst = dbConnection.prepareStatement("insert into bill_details(bill_detail_id, receipt_id, description, "
	    	  		+ " hsn_code, per, rate, qty, disc_val, disc_amt, total_amt, bill_date) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	    	  
			  int id = getMaxId();
			  billDetail.setBillDetailId(++id);
	          pst.setInt(1, billDetail.getBillDetailId());
			  pst.setString(2, billDetail.getReceiptId());
			  pst.setString(3, billDetail.getDescription());
			  pst.setString(4, billDetail.getHsnCode());
			  pst.setString(5, billDetail.getPer());
			  pst.setDouble(6, billDetail.getRate());
			  pst.setDouble(7, billDetail.getQty());
			  pst.setString(8, billDetail.getDiscVal());
			  pst.setDouble(9, billDetail.getDiscAmt());
			  pst.setDouble(10, billDetail.getTotalAmt());
			  pst.setString(11, billDetail.getBillDate());
			  
			  pst.executeUpdate();
	    			  
	      }catch(Exception e){
	    	  e.printStackTrace();

	      }
		return billDetail;   
	}

	public void save(List<BillDetail> billDetails) {
		for(BillDetail bill: billDetails) {
			saveAndFlush(bill);
		}
	}
	
	public List<String> getBillDescription(){
		List<String> billDescs = new ArrayList<>();
		try {
			PreparedStatement pst = dbConnection.prepareStatement("select DISTINCT description from bill_details");
	        ResultSet result = pst.executeQuery();
	          if (result != null) {
	        	  while(result.next()) {
	        		  billDescs.add(result.getString("description"));
	        	  }
	          }
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return billDescs;
	}
}
