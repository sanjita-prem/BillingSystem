package com.billing.view.bill;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.billing.model.BillDetail;
import com.billing.model.BillDetailBean;
import com.billing.model.BillReceipt;
import com.billing.model.Company;
import com.billing.model.Customer;
import com.billing.model.Owner;
import com.billing.pdf.PDFGenerator;
import com.billing.service.BillDetailService;
import com.billing.service.BillReceiptService;
import com.billing.service.CompanyService;
import com.billing.service.CustomerService;
import com.billing.service.OwnerService;
import com.billing.util.DateUtil;
import com.billing.util.Formatter;
import com.billing.util.TextFieldAutoComplete;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
/*import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;*/
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public abstract class AbstractBillController implements Initializable{
	Logger log = Logger.getLogger(AbstractBillController.class.getName());
	@FXML ChoiceBox<String> seller;
	@FXML Label sellerName;
	@FXML Label sellerAddress;
	@FXML Label sellerGst;
	@FXML Label sellerPhones;
	@FXML Label sellerEmail;
	@FXML Label sellerBank;


	@FXML TextField description;
	@FXML TextField hsnCode;
	@FXML TextField qty;
	@FXML TextField rate;
	@FXML ChoiceBox<String> per;
	@FXML TextField discVal;

	@FXML TableView<BillDetailBean> tableBillDetailid;
	@FXML TableColumn<BillDetailBean, Integer> iNo;
	@FXML TableColumn<BillDetailBean, String> idescription;
	@FXML TableColumn<BillDetailBean, String> ihsnCode;
	@FXML TableColumn<BillDetailBean, Integer> iqty;
	@FXML TableColumn<BillDetailBean, Double> irate;
	@FXML TableColumn<BillDetailBean, String> iper;
	@FXML TableColumn<BillDetailBean, String> idiscVal;
	@FXML TableColumn<BillDetailBean, Double> itotalDisc;
	@FXML TableColumn<BillDetailBean, Double> iamount;

	@FXML Label taxableAmtId;
	protected double taxableAmt = 0;

	@FXML Label sGSTId;
	@FXML Label cGSTId;
	@FXML Label totalTaxId;
	protected double totalTax =0;

	@FXML TextField tranportCharge;
	protected double tranportChargeVal =0;
	@FXML TextField tranportType;
	@FXML Label TOTAL;
	protected double total;

	protected CustomerService custServ;
	protected OwnerService oserv;
	protected CompanyService comServ;
	protected TextFieldAutoComplete buyerAuto = TextFieldAutoComplete.getInstance1();
	protected TextFieldAutoComplete descAuto = TextFieldAutoComplete.getInstance2();
	protected BillDetailService billDetailService;
	protected BillReceiptService billReceiptService;

	protected Map<String, Owner> ownerMap;

	protected Map<String, Customer> custMap;

	protected ObservableList<BillDetailBean> billData;
	protected boolean isSameState = false;

	private int customerId;
	private String customerGST;
	
	List<String> descs = new ArrayList<String>();

	public void initialize(){
		custServ = CustomerService.getInstance();
		oserv = OwnerService.getInstance();
		comServ = CompanyService.getInstance();
		billDetailService = BillDetailService.getInstance();
		billReceiptService = BillReceiptService.getInstance();
	}
	public AbstractBillController(){
		custServ = CustomerService.getInstance();
		oserv = OwnerService.getInstance();
		comServ = CompanyService.getInstance();
		billDetailService = BillDetailService.getInstance();
		billReceiptService = BillReceiptService.getInstance();
	}

	protected void setSellerInfo(String aliasName){
		log.info("setSellerInfo : "+aliasName);
		if(aliasName == null) return;
		Owner o = ownerMap.get(aliasName);
		Company c = comServ.getByCompanyId(o.getCompanyId());
		sellerName.setText(o.getName());
		sellerAddress.setText(o.getAddress()+" "+o.getCity()+" "+o.getPin());
		sellerGst.setText("GSTIN/UIN: "+o.getGstNo());
		sellerPhones.setText("Contact: "+o.getPhone1() +", "+o.getPhone2());
		sellerEmail.setText("EMail: "+o.getEmail());
		sellerBank.setText("BANK: "+(c!= null ? c.getBankName() : ""));
		log.info("Owner Info : "+ o);
		log.info("Customer Info : "+ c);
	}



	protected void totalTaxCalcutation(){
		//TotalTax cal
		String buyerGSTVal = getCustomerGST();//buyerGst.getText();
		String sellerGSTVal = null;
		if(!sellerGst.getText().isEmpty())
			sellerGSTVal = sellerGst.getText().substring(sellerGst.getText().indexOf(" ")+1, sellerGst.getText().indexOf(" ")+3);
		isSameState  = buyerGSTVal != null && !buyerGSTVal.isEmpty()
				&& (sellerGSTVal == null || buyerGSTVal.substring(0, 2).equals(sellerGSTVal));
		if(isSameState){
			sGSTId.setVisible(true);
			String iGST = " IGST 5%  ";
			double iGSTVal = toDouble(getTaxableAmt()*5/100D);
			sGSTId.setText(iGST);
			setTotalTax(iGSTVal);
			totalTaxId.setText(String.valueOf(getTotalTax()));
			log.info("In Same State : "+iGST+" : "+totalTaxId.getText());
		}else{
			sGSTId.setVisible(true);
			cGSTId.setVisible(true);
			String cGST = " CGST 2.5% : ";
			String sGST = " SGST 2.5% : ";
			double cGSTVal = toDouble(getTaxableAmt()*2.50/100D);
			double sGSTVal = toDouble(getTaxableAmt()*2.50/100D);
			cGSTId.setText(cGST+cGSTVal+" ");
			sGSTId.setText(sGST+sGSTVal+" ");
			setTotalTax(cGSTVal+sGSTVal);
			totalTaxId.setText(String.valueOf(getTotalTax()));
			log.info("In Different Same State : "+cGST+" : "+sGST+" : "+totalTaxId.getText());
		}
	}
	protected void calculateTranspotationCharge(){
		double transportCharge = Double.parseDouble(tranportCharge.getText().isEmpty() ? "0" : tranportCharge.getText());
		setTranportChargeVal(transportCharge);
		//setTotal(getTotal() + getTranportChargeVal());
		TOTAL.setText(String.valueOf(getTotal()+ getTranportChargeVal()));
	}
	protected void setTotalAmt(){
		double transportCharge = Double.parseDouble(tranportCharge.getText().isEmpty() ? "0" : tranportCharge.getText());
		setTranportChargeVal(transportCharge);
		setTotal(getTaxableAmt()+getTotalTax());
		TOTAL.setText(String.valueOf(getTotal() + transportCharge));
		log.info("TOTAL : "+TOTAL.getText());
	}

	public void onDelete(){
		int selectedIndex = tableBillDetailid.getSelectionModel().getSelectedIndex();
	    if (selectedIndex >= 0) {
	    	BillDetailBean billBean = tableBillDetailid.getItems().remove(selectedIndex);
	    	billData.remove(billBean);
	    	log.info("Deleted item : "+ billBean);
	    	setTaxableAmt(getTaxableAmt() - billBean.getRAmount());
	    	totalTaxCalcutation();
	    	setTotalAmt();
	    	description.clear();
			description.requestFocus();
	    }
	}

	public void populateCell(){
		tableBillDetailid.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				int selectedIndex = tableBillDetailid.getSelectionModel().getSelectedIndex();
				if (selectedIndex >= 0) {
					BillDetailBean billBean = tableBillDetailid.getItems().get(selectedIndex);
					description.setText(billBean.getRDescription());
					hsnCode.setText(billBean.getRHSNCode());
					qty.setText(String.valueOf(billBean.getRQTY()));
					rate.setText(String.valueOf(billBean.getRRate()));
					//per.setValue(billBean.getRPer());
					discVal.setText(billBean.getRDiscVal().contains("%")
							? billBean.getRDiscVal().substring(0, billBean.getRDiscVal().length()-1)
									: billBean.getRDiscVal());
				}
			}
		});
	}
	protected double toDouble(double val){
		return Math.round(val * 100.0) / 100D;
	}

	public double getTaxableAmt() {
		return taxableAmt;
	}

	public void setTaxableAmt(double taxableAmt) {
		if(taxableAmt >= 0)
			this.taxableAmt = toDouble(taxableAmt);
		taxableAmtId.setText(String.valueOf(getTaxableAmt()));
		log.info("Added Item Taxable amount : "+getTaxableAmt());
	}

	public double getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(double totalTax) {
		this.totalTax = totalTax;
	}

	public double getTranportChargeVal() {
		return tranportChargeVal;
	}

	public void setTranportChargeVal(double tranportChargeVal) {
		this.tranportChargeVal = tranportChargeVal;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public void refresPage(){
		description.clear();
		qty.clear();
		rate.clear();
		discVal.clear();
		taxableAmtId.setText("");
		taxableAmt = 0;

		sGSTId.setVisible(false);
		cGSTId.setVisible(false);
		totalTaxId.setText("");
		totalTax =0;

		tranportCharge.clear();
		tranportChargeVal =0;
		tranportType.clear();
		TOTAL.setText("");
		total =0;
		billData.clear();
		tableBillDetailid.refresh();
		isSameState = false;
	}

	public void generateBill(ActionEvent e) throws JRException{
		String receiptId = saveBills();
		descs = billDetailService.getBillDescription();
		if(receiptId == null || receiptId.isEmpty()) return;
		BillReceipt billReceipt = billReceiptService.getBillReceiptByReceiptId(receiptId);
		List<BillDetail> listBillDetail = billDetailService.getAllBillByReceipt(receiptId);
		Customer c = null;
		Company company = null;
		Owner o = null;
		if(billReceipt != null && billReceipt.getOwnerId() > 0 && billReceipt.getCustomerId() >0){
			
			c = custServ.getCustomerById(billReceipt.getCustomerId());
			o = oserv.getOwnerById(billReceipt.getOwnerId());
			if(o != null)
				company = comServ.getByCompanyId(o.getCompanyId());
		}
		Map<String, Object> map = new HashMap<>();
		map.put("billReceiptId", receiptId);
		map.put("customer", c);
		map.put("owner", o);
		map.put("company", company);
		map.put("billReceipt", billReceipt);
		JRBeanCollectionDataSource dataSource1 = new JRBeanCollectionDataSource(listBillDetail);
		map.put("billDetailList", dataSource1);
		JRBeanCollectionDataSource dataSource2 = new JRBeanCollectionDataSource(Arrays.asList(billReceipt));
		map.put("billReceiptDataSource", dataSource2);

		String file = PDFGenerator.generateBillPDF(e, map);
		PDFGenerator.OpenPdfFile(file);
	}

	protected String saveBills(){
		if(getTaxableAmt() == 0d) return null;
		String dateStr = DateUtil.getDBCurrentDateS();
		Owner o = ownerMap.get(seller.getValue());
		//Customer c = custMap.get(buyer.getText());
		log.info("Save Bills :: owner detail : "+o);
		BillReceipt br = new BillReceipt();
		br.setReceiptId(billReceiptService.getReceiptId(sellerName.getText()));
		br.setOwnerId(o.getOwnerId());
		br.setCustomerId(getCustomerId());
		br.setTaxableAmt(getTaxableAmt());
		if(isSameState){
			br.setIgstAmt(getTotalTax());
		}else{
			br.setCgstAmt(toDouble(getTaxableAmt()*2.50/100D));
			br.setSgstAmt(toDouble(getTaxableAmt()*2.50/100D));
		}
		br.setBillType(sellerGst.getText().isEmpty() ? "UNREG" : "REG");
		br.setTransportType(tranportType.getText());
		br.setReceiptDate(dateStr);
		br.setTotal(getTotal()+getTranportChargeVal());
		br.setTransportAmt(getTranportChargeVal());
		br = billReceiptService.saveBillReceipt(br);
		log.info("Save Bills :: billReceipt detail : "+br);
		for(BillDetailBean billBean : billData){
			BillDetail billDetail = new BillDetail();
			billDetail.setReceiptId(br.getReceiptId());
			billDetail.setDescription(billBean.getRDescription());
			billDetail.setHsnCode(billBean.getRHSNCode());
			billDetail.setQty(billBean.getRQTY());
			billDetail.setRate(billBean.getRRate());
			billDetail.setPer(billBean.getRPer());
			billDetail.setDiscVal(billBean.getRDiscVal());
			billDetail.setDiscAmt(billBean.getRDiscAmt());
			billDetail.setTotalAmt(billBean.getRAmount());
			billDetail.setBillDate(dateStr);

			billDetailService.saveBillDetail(billDetail);
			//log.info("Save Bills :: BILL detail : "+billDetail);
		}
		return br.getReceiptId();
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCustomerGST() {
		return customerGST;
	}
	public void setCustomerGST(String customerGST) {
		this.customerGST = customerGST;
	}
	protected void configureBillDetailTable(){
		iNo.setCellFactory(new Callback<TableColumn<BillDetailBean, Integer>, TableCell<BillDetailBean, Integer>>() {
            @Override
            public TableCell<BillDetailBean, Integer> call(TableColumn<BillDetailBean, Integer> param) {
                return new TableCell<BillDetailBean, Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (this.getTableRow() != null) {
                            int index = this.getTableRow().getIndex();
                            if( index < tableBillDetailid.getItems().size()) {
                                int rowNum = index + 1;
                                setText(String.valueOf(rowNum));
                            } else {
                                setText("");
                            }
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
		idescription.setCellValueFactory(new PropertyValueFactory<BillDetailBean, String>("rDescription"));
		ihsnCode.setCellValueFactory(new PropertyValueFactory<BillDetailBean, String>("rHSNCode"));
		iqty.setCellValueFactory(new PropertyValueFactory<BillDetailBean, Integer>("rQTY"));
		irate.setCellValueFactory(new PropertyValueFactory<BillDetailBean, Double>("rRate"));
		iper.setCellValueFactory(new PropertyValueFactory<BillDetailBean, String>("rPer"));
		idiscVal.setCellValueFactory(new PropertyValueFactory<BillDetailBean, String>("rDiscVal"));
		itotalDisc.setCellValueFactory(new PropertyValueFactory<BillDetailBean, Double>("rDiscAmt"));
		iamount.setCellValueFactory(new PropertyValueFactory<BillDetailBean, Double>("rAmount"));

		billData = FXCollections.observableArrayList();
		tableBillDetailid.setItems(billData);
	}
	protected void configureOwner(){
		ownerMap = oserv.getAllSellerMap();
		seller.setItems(FXCollections.observableArrayList(ownerMap.keySet()));
		seller.getSelectionModel().selectFirst();
		if(seller.getItems().size() > 0)
			setSellerInfo(seller.getItems().get(0));

		seller.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		      @Override
		      public void changed(ObservableValue<? extends String> observableValue, String str1, String str2) {
		    	  setSellerInfo(str2);
		      }
		    });
	}
	protected void configurePer(){
		String[] perOption = {"PCS", "CHK %"};
		per.setItems(FXCollections.observableArrayList(Arrays.asList(perOption)));
		per.getSelectionModel().selectFirst();
	}
	protected void onAddButtonClick(){
		if(!description.getText().isEmpty()
				&& !qty.getText().isEmpty()
				&& !rate.getText().isEmpty())
		{
			qty.setStyle("");
			rate.setStyle("");
			int _qty = Integer.parseInt(qty.getText());
			double _rate = Double.parseDouble(rate.getText());
			double _discVal = Double.parseDouble(discVal.getText().isEmpty() ? "0" : discVal.getText());
			double totalDisc = 0;
			double _amt = 0;
			if(per.getValue().equals("PCS")){
				totalDisc = toDouble(_qty * _discVal);
			}else{
				totalDisc = toDouble(_qty * _rate *_discVal /100D);
			}
			_amt = toDouble(_qty * _rate - totalDisc);

			BillDetailBean billBean = new BillDetailBean(description.getText(),
					hsnCode.getText(),
					_qty,
					_rate,
					per.getValue().substring(0, 3),
					"PCS".equals(per.getValue()) ? String.valueOf(_discVal) : String.valueOf(_discVal)+"%",
					totalDisc, _amt);

			billData.add(billBean);
			log.info("Added Item :: "+billBean);
			description.clear();
			description.requestFocus();
			setTaxableAmt(getTaxableAmt() + _amt);
			totalTaxCalcutation();
			setTotalAmt();
		}else{
			if(qty.getText().isEmpty())
				qty.setStyle("-fx-background-color: #ffc9d3;");
			if(rate.getText().isEmpty())
				rate.setStyle("-fx-background-color: #ffc9d3;");
		}
	}
	protected void configureInputField(){
		sGSTId.setVisible(false);
		cGSTId.setVisible(false);

		qty.setTextFormatter(Formatter.getIntegerFormatter());
		rate.setTextFormatter(Formatter.getDoubleFormatter());
		discVal.setTextFormatter(Formatter.getDoubleFormatter());
		tranportCharge.setTextFormatter(Formatter.getDoubleFormatter());
		descs = billDetailService.getBillDescription();
		descAuto.addAutoCompleteOptions(description, descs);
	}

	protected void configureTransport(){
		tranportCharge.setOnKeyReleased(event -> {
			  if (event.getCode() == KeyCode.ENTER){
				  calculateTranspotationCharge();
				  }
				});
		tranportCharge.focusedProperty().addListener(new ChangeListener<Boolean>(){
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
		        if (!newPropertyValue){
		        	calculateTranspotationCharge();
		        }
		    }
		});
	}
}
