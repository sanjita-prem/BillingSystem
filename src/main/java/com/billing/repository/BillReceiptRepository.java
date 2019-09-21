package com.billing.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.billing.db.DbUtil;
import com.billing.model.BillReceipt;


public class BillReceiptRepository{
	private Connection dbConnection;
	
	public BillReceiptRepository() {
		this.dbConnection = DbUtil.getConnection();
	}
	//@Query(value="select coalesce(max(b.bill_id), 0) from bill_receipts b", nativeQuery = true)
	public int getMaxId(){
		int max =0;
		try{
			PreparedStatement prepStatement = dbConnection.prepareStatement("select max (bill_id) from bill_receipts");
			ResultSet result = prepStatement.executeQuery();
			if(result != null && result.next()){
				max = result.getInt(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return max;
	
	}

	public BillReceipt findBillReceiptByReceiptId(String receiptId){
		BillReceipt billReceipt = null;
		try {
			PreparedStatement pst = dbConnection.prepareStatement("select * from bill_receipts where receipt_id = ? ");
			
	          pst.setString(1,receiptId);
	          
	          ResultSet result = pst.executeQuery();
	          
	          if (result != null) {
	        	  while(result.next()) {
	        		  billReceipt = new BillReceipt();
	        		  billReceipt.setBillId(result.getInt("bill_id"));
	        		  billReceipt.setReceiptId(result.getString("receipt_id"));
	        		  billReceipt.setOwnerId(result.getInt("owner_id"));
	        		  billReceipt.setCustomerId(result.getInt("customer_id"));
	        		  billReceipt.setTaxableAmt(result.getInt("taxable_amt"));
	        		  billReceipt.setSgstAmt(result.getInt("sgst_amt"));
	        		  billReceipt.setCgstAmt(result.getInt("cgst_amt"));
	        		  billReceipt.setIgstAmt(result.getInt("igst_amt"));
	        		  billReceipt.setTransportAmt(result.getInt("transport_amt"));
	        		  billReceipt.setTotal(result.getInt("total"));
	        		  billReceipt.setReceiptDate(result.getString("receipt_date"));
	        		  billReceipt.setBillType(result.getString("bill_type"));
	        		  billReceipt.setTransportType(result.getString("transport_type"));
	        	  
	        	  }
	          }
		}catch(Exception e){
			e.printStackTrace();
		}
		return billReceipt;
	}

	//@Query(value="SELECT receipt_id FROM bill_receipts where  receipt_date between date(?1) and date(?2)", nativeQuery = true)
	public List<String> findReceiptsByDate(String fromDate, String toDate){
		List<String> receiptIds = null;
		try {
			PreparedStatement pst = dbConnection.prepareStatement("SELECT receipt_id FROM bill_receipts where  receipt_date between date(?) and date(?)");
			
	          pst.setString(1,fromDate);
	          pst.setString(2,toDate);
	          
	          ResultSet result = pst.executeQuery();
	          
	          if (result != null) {
	        	  receiptIds = new ArrayList<>();
	        	  while(result.next()) {
	        		  receiptIds.add(result.getString("receipt_id"));
	        	  }
	          }
		}catch(Exception e) {
			e.printStackTrace();
		}
		return receiptIds;
	}

	public BillReceipt save(BillReceipt billReceipt) {
		 try {
	    	  PreparedStatement pst = dbConnection.prepareStatement("insert into bill_receipts(bill_id, receipt_id, owner_id, "
	    	  		+ " customer_id, taxable_amt, sgst_amt, cgst_amt, igst_amt, transport_amt, total, receipt_date, bill_type, "
	    	  		+ "transport_type) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	    	  
			  int id = getMaxId();
			  billReceipt.setBillId(++id);
	          pst.setInt(1, billReceipt.getBillId());
			  pst.setString(2, billReceipt.getReceiptId());
			  pst.setInt(3, billReceipt.getOwnerId());
			  pst.setInt(4, billReceipt.getCustomerId());
			  pst.setDouble(5, billReceipt.getTaxableAmt());
			  pst.setDouble(6, billReceipt.getSgstAmt());
			  pst.setDouble(7, billReceipt.getCgstAmt());
			  pst.setDouble(8, billReceipt.getIgstAmt());
			  pst.setDouble(9, billReceipt.getTaxableAmt());
			  pst.setDouble(10, billReceipt.getTotal());
			  pst.setString(11, billReceipt.getReceiptDate());
			  pst.setString(12, billReceipt.getBillType());
			  pst.setString(13, billReceipt.getTransportType());
			  
			  pst.executeUpdate();
	    			  
	    			  
	      }catch(Exception e){
	    	  e.printStackTrace();

	      }
		return billReceipt;   
	}
}
