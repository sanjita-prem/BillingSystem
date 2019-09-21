package com.billing.service;

import java.util.Calendar;
import java.util.List;

import com.billing.model.BillReceipt;
import com.billing.repository.BillReceiptRepository;


public class BillReceiptService {
	private static final BillReceiptService INSTANCE = new BillReceiptService();
	
	public static BillReceiptService getInstance() {
		return INSTANCE;
	}
	private BillReceiptRepository billReRepo;

	private BillReceiptService(){
		billReRepo = new BillReceiptRepository();
	}
	public BillReceipt saveBillReceipt(BillReceipt billReceipt){
		return billReRepo.save(billReceipt);
	}

	public String getReceiptId(String ownerName){
		ownerName = ownerName.trim().toUpperCase();
		StringBuilder sb = new StringBuilder();
		Calendar now = Calendar.getInstance();   // Gets the current date and time
		int year = now.get(Calendar.YEAR);
		if(ownerName.contains(" ")){
			sb.append(ownerName.substring(0,1)).append(ownerName.substring(ownerName.indexOf(" ")+1, ownerName.indexOf(" ")+2));
		}else{
			sb.append(ownerName.substring(0,2));
		}
		sb.append("/").append(String.valueOf(year).substring(2, 4)).append("-").append(String.valueOf(year+1).substring(2, 4));
		String formatted = String.format("%06d", getMaxId()+1);
		sb.append("/").append(formatted);
		return sb.toString();
	}

	public int getMaxId(){
		return billReRepo.getMaxId();
	}

	public BillReceipt getBillReceiptByReceiptId(String receiptId){
		return billReRepo.findBillReceiptByReceiptId(receiptId);
	}

	public List<String> getReceiptsByDates(String fromDate, String toDate){
		return billReRepo.findReceiptsByDate(fromDate, toDate);
	}
}
