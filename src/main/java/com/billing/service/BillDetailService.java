package com.billing.service;

import java.util.List;

import com.billing.model.BillDetail;
import com.billing.repository.BillDetailRepository;


public class BillDetailService {
	private static final BillDetailService INSTANCE = new BillDetailService();
	
	public static BillDetailService getInstance() {
		return INSTANCE;
	}
	private BillDetailRepository billDetailRepo;

	private BillDetailService(){
		billDetailRepo = new BillDetailRepository();
	}

	public BillDetail saveBillDetail(BillDetail billDetail){
		return billDetailRepo.saveAndFlush(billDetail);
	}

	public void saveBillDetail(List<BillDetail> billDetails){
		 billDetailRepo.save(billDetails);
	}

	public List<BillDetail> getAllBillByReceipt(String receiptId){
		return billDetailRepo.findBillDetailByReceiptId(receiptId);
	}

	public void deleteBillDetailByreceipt(String receiptId){
		 billDetailRepo.deleteBillDetailsByReceiptId(receiptId);
	}
	
	public List<String> getBillDescription(){
		return billDetailRepo.getBillDescription();
	}
}
