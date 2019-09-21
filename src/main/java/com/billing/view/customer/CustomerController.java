package com.billing.view.customer;

import java.net.URL;
import java.util.ResourceBundle;

import com.billing.model.Customer;
import com.billing.model.CustomerBean;
import com.billing.service.CustomerService;
import com.billing.service.StateCodeService;
import com.billing.util.DateUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.util.Callback;

public class CustomerController implements Initializable{

	@FXML TableView<CustomerBean> tableCustomerid;
	@FXML TableColumn<CustomerBean, Integer> iNo;
	@FXML TableColumn<CustomerBean, String> name;
	@FXML TableColumn<CustomerBean, String> email;
	@FXML TableColumn<CustomerBean,Integer> phone1;
	@FXML TableColumn<CustomerBean, Integer> phone2;
	@FXML TableColumn<CustomerBean, String> state;
	@FXML TableColumn<CustomerBean, String> city;
	@FXML TableColumn<CustomerBean, String> address;
	@FXML TableColumn<CustomerBean, Integer> pin;
	@FXML TableColumn<CustomerBean, String> created;
	@FXML TableColumn<CustomerBean, String> aliasName;
	@FXML TableColumn<CustomerBean, String> panNo;
	@FXML TableColumn<CustomerBean, String> gstNo;
	@FXML TableColumn<CustomerBean, Integer> active;

	@FXML TextField nameId;
	@FXML TextField emailId;
	@FXML TextField phone1Id;
	@FXML TextField phone2Id;
	@FXML ChoiceBox<String> stateId;
	@FXML TextField cityId;
	@FXML TextField addressId;
	@FXML TextField pinId;
	@FXML TextField aliasNameId;
	@FXML TextField panNoId;
	@FXML TextField gstNoId;
	@FXML CheckBox activeId;

	private ObservableList<CustomerBean> data = null;
	private ObservableList<String> stateData = null;

	private CustomerService custServ;
	private StateCodeService scServ;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		scServ=StateCodeService.getInstance();
		custServ= CustomerService.getInstance();
		iNo.setCellFactory(new Callback<TableColumn<CustomerBean, Integer>, TableCell<CustomerBean, Integer>>() {
            @Override
            public TableCell<CustomerBean, Integer> call(TableColumn<CustomerBean, Integer> param) {
                return new TableCell<CustomerBean, Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (this.getTableRow() != null) {
                            int index = this.getTableRow().getIndex();
                            if( index < tableCustomerid.getItems().size()) {
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
		name.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>("rName"));
		email.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>("rEmail"));
		phone1.setCellValueFactory(new PropertyValueFactory<CustomerBean, Integer>("rPhone1"));
		phone2.setCellValueFactory(new PropertyValueFactory<CustomerBean, Integer>("rPhone2"));
		state.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>("rState"));
		city.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>("rCity"));
		address.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>("rAdress"));
		pin.setCellValueFactory(new PropertyValueFactory<CustomerBean, Integer>("rPin"));
		created.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>("rCreated"));
		aliasName.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>("rAliasName"));
		panNo.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>("rPanNo"));
		gstNo.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>("rGstNo"));
		active.setCellValueFactory(new PropertyValueFactory<CustomerBean, Integer>("rActive"));

		stateData = FXCollections.observableArrayList(scServ.getAllState());
		stateId.setItems(stateData);
		stateId.getSelectionModel().select("Bihar");
		data = FXCollections.observableArrayList(custServ.getAllCustomerBeans());

		tableCustomerid.setItems(data);

		populateCell();
	}

	public void onAdd(){
		
		Customer customer  = custServ.getByPanNo(panNoId.getText());

		if(customer == null){
			Customer o = new Customer();
			o.setName(nameId.getText());
			o.setEmail(emailId.getText());
			o.setPhone1(phone1Id.getText());
			o.setPhone2(phone2Id.getText());
			o.setState(stateId.getValue());
			o.setCity(cityId.getText());
			o.setAddress(addressId.getText());
			o.setPin(pinId.getText());
			o.setCreated(DateUtil.getDBCurrentDateS());
			o.setAliasName(aliasNameId.getText());
			o.setPanNo(panNoId.getText());
			o.setGstNo(gstNoId.getText());
			o.setActive(activeId.selectedProperty().get() ? 1 : 0);

			custServ.save(o);
			
			data.add(new CustomerBean(
					o.getName(),
					o.getEmail(),
					o.getPhone1(),
					o.getPhone2(),
					o.getState(),
					o.getCity(),
					o.getAddress(),
					o.getPin(),
					o.getCreated(),
					o.getAliasName(),
					o.getPanNo(),
					o.getGstNo(),
					o.getActive()));
		}else{
			Alert alert = new Alert(AlertType.NONE, "Customer already exist!!", ButtonType.OK);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.show();
			return;
		}
		clear();
	}

	public void onUpdate(){
		int selectedIndex = tableCustomerid.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			CustomerBean customerBean = tableCustomerid.getItems().get(selectedIndex);
			//customerService.update(customerBean, customerid.getText(), pass.getText(), active.selectedProperty().get() ? 1 : 0);

			Customer customer = custServ.getByNameAndPanNo(customerBean.getRName(), customerBean.getRPanNo());
			customer.setName(nameId.getText());
			customer.setEmail(emailId.getText());
			customer.setPhone1(phone1Id.getText());
			customer.setPhone2(phone2Id.getText());
			customer.setState(stateId.getValue());
			customer.setCity(cityId.getText());
			customer.setAddress(addressId.getText());
			customer.setPin(pinId.getText());
			customer.setCreated(DateUtil.getDBCurrentDateS());
			customer.setAliasName(aliasNameId.getText());
			customer.setPanNo(panNoId.getText());
			customer.setGstNo(gstNoId.getText());
			customer.setActive(activeId.selectedProperty().get() ? 1 : 0);

			custServ.update(customer);

			customerBean = new CustomerBean(
					customer.getName(),
					customer.getEmail(),
					customer.getPhone1(),
					customer.getPhone2(),
					customer.getState(),
					customer.getCity(),
					customer.getAddress(),
					customer.getPin(),
					customer.getCreated(),
					customer.getAliasName(),
					customer.getPanNo(),
					customer.getGstNo(),
					customer.getActive());

			tableCustomerid.getItems().set(selectedIndex, customerBean);

			clear();

		}
	}

	public void onDelete(){
		int selectedIndex = tableCustomerid.getSelectionModel().getSelectedIndex();
	    if (selectedIndex >= 0) {
	    	CustomerBean customerBean = tableCustomerid.getItems().remove(selectedIndex);
	    	if(customerBean != null){
	    		custServ.delete(customerBean.getRPanNo());
	    	}
	    }
	    clear();
	}

	public void populateCell(){
		tableCustomerid.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				int selectedIndex = tableCustomerid.getSelectionModel().getSelectedIndex();
				if (selectedIndex >= 0) {
					CustomerBean customerBean = tableCustomerid.getItems().get(selectedIndex);
					nameId.setText(customerBean.getRName());
					emailId.setText(customerBean.getREmail());
					phone1Id.setText(String.valueOf(customerBean.getRPhone1()));
					phone2Id.setText(String.valueOf(customerBean.getRPhone2()));
					stateId.setValue(customerBean.getRState());
					cityId.setText(customerBean.getRCity());
					addressId.setText(customerBean.getRAdress());
					pinId.setText(String.valueOf(customerBean.getRPin()));
					aliasNameId.setText(customerBean.getRAliasName());
					panNoId.setText(customerBean.getRPanNo());
					gstNoId.setText(customerBean.getRGstNo());
					activeId.setSelected(customerBean.getRActive() > 0 ? true : false);
				}
			}
		});
	}
	private void clear(){
		nameId.clear();
		emailId.clear();
		phone1Id.clear();
		phone2Id.clear();
		stateId.setValue("");
		cityId.clear();
		addressId.clear();
		pinId.clear();
		aliasNameId.clear();
		panNoId.clear();
		gstNoId.clear();
		activeId.setSelected(true);
	}
}
