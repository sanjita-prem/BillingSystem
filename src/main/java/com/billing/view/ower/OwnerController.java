package com.billing.view.ower;

import java.net.URL;
import java.util.ResourceBundle;

import com.billing.AppContext;
import com.billing.model.Company;
import com.billing.model.Owner;
import com.billing.model.OwnerBean;
import com.billing.service.CompanyService;
import com.billing.service.OwnerService;
import com.billing.service.StateCodeService;
import com.billing.util.DateUtil;
import com.billing.view.company.CompanyController;
import com.billing.view.company.CompanyView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class OwnerController implements Initializable{

	@FXML TableView<OwnerBean> tableOwnerid;
	@FXML TableColumn<OwnerBean, Integer> iNo;
	@FXML TableColumn<OwnerBean, String> name;
	@FXML TableColumn<OwnerBean, String> email;
	@FXML TableColumn<OwnerBean, String> phone1;
	@FXML TableColumn<OwnerBean, String> phone2;
	@FXML TableColumn<OwnerBean, String> state;
	@FXML TableColumn<OwnerBean, String> city;
	@FXML TableColumn<OwnerBean, String> address;
	@FXML TableColumn<OwnerBean, String> pin;
	@FXML TableColumn<OwnerBean, String> created;
	@FXML TableColumn<OwnerBean, String> aliasName;
	@FXML TableColumn<OwnerBean, String> panNo;
	@FXML TableColumn<OwnerBean, String> companyName;
	@FXML TableColumn<OwnerBean, String> gstNo;
	@FXML TableColumn<OwnerBean, Integer> active;

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
	@FXML TextField companyId;
	
	@FXML Label validationMsg;
	

	private ObservableList<OwnerBean> data = null;
	private ObservableList<String> stateData = null;

	private OwnerService oserv;

	private StateCodeService scServ;
	private CompanyService comServ;
	private CompanyView companyView;
	private OwnerView ownerView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		scServ=StateCodeService.getInstance();
		oserv = OwnerService.getInstance();
		comServ=CompanyService.getInstance();
		companyView =CompanyView.getInstance();
		ownerView = OwnerView.getInstance();
		iNo.setCellFactory(new Callback<TableColumn<OwnerBean, Integer>, TableCell<OwnerBean, Integer>>() {
            @Override
            public TableCell<OwnerBean, Integer> call(TableColumn<OwnerBean, Integer> param) {
                return new TableCell<OwnerBean, Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (this.getTableRow() != null) {
                            int index = this.getTableRow().getIndex();
                            if( index < tableOwnerid.getItems().size()) {
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
		//iNo.setCellValueFactory(new PropertyValueFactory<OwnerBean, Integer>("rNo"));
		name.setCellValueFactory(new PropertyValueFactory<OwnerBean, String>("rName"));
		email.setCellValueFactory(new PropertyValueFactory<OwnerBean, String>("rEmail"));
		phone1.setCellValueFactory(new PropertyValueFactory<OwnerBean, String>("rPhone1"));
		phone2.setCellValueFactory(new PropertyValueFactory<OwnerBean, String>("rPhone2"));
		state.setCellValueFactory(new PropertyValueFactory<OwnerBean, String>("rState"));
		city.setCellValueFactory(new PropertyValueFactory<OwnerBean, String>("rCity"));
		address.setCellValueFactory(new PropertyValueFactory<OwnerBean, String>("rAdress"));
		pin.setCellValueFactory(new PropertyValueFactory<OwnerBean, String>("rPin"));
		created.setCellValueFactory(new PropertyValueFactory<OwnerBean, String>("rCreated"));
		aliasName.setCellValueFactory(new PropertyValueFactory<OwnerBean, String>("rAliasName"));
		panNo.setCellValueFactory(new PropertyValueFactory<OwnerBean, String>("rPanNo"));
		companyName.setCellValueFactory(new PropertyValueFactory<OwnerBean, String>("rCompany"));
		gstNo.setCellValueFactory(new PropertyValueFactory<OwnerBean, String>("rGstNo"));
		active.setCellValueFactory(new PropertyValueFactory<OwnerBean, Integer>("rActive"));

		stateData = FXCollections.observableArrayList(scServ.getAllState());
		stateId.setItems(stateData);
		stateId.getSelectionModel().select("Bihar");
		data = FXCollections.observableArrayList(oserv.getAllOwnerBeans());

		tableOwnerid.setItems(data);

		populateCell();
	}

	public void onAdd(){
		if(!isValidatePage()) {
			return;
		}
		if(isEmpty(companyId.getText()) || isEmpty(nameId.getText()) || isEmpty(panNoId.getText())) {
			AppContext.displayAlert("Owner Name, Company Name and Pan Number is required.");
			return;
		}
		Company c = comServ.getByNameAndPanNo(companyId.getText(), panNoId.getText());
		if(c == null){
			c = new Company();
			c.setName(companyId.getText());
			c.setPanNo(panNoId.getText());
			c = comServ.save(c);
		}
		Owner owner  = oserv.getByNameAndPanNo(nameId.getText(), panNoId.getText());

		if(owner == null){
			Owner o = new Owner();
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
			o.setCompanyId(c.getCompanyId());

			oserv.save(o);
			data.add(new OwnerBean(
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
					o.getActive(),
					c.getName()));
		}else{
			AppContext.displayAlert("Owner already exist!!");
			return;
		}
		clear();
	}
	
	public boolean isValidatePage() {
		validationMsg.setText("");
		if(nameId.getText().isEmpty() || panNoId.getText().isEmpty() || companyId.getText().isEmpty()){
			validationMsg.setText("Name, Company Name and PanNo are required.");
			return false;
		}
		return true;
	}

	public void onUpdate(){
		if(!isValidatePage()) {
			return;
		}
		int selectedIndex = tableOwnerid.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			
			OwnerBean ownerBean = tableOwnerid.getItems().get(selectedIndex);

			Company c = comServ.getByPanNo(panNoId.getText());
			if(c == null){
				c = new Company();
				c.setName(companyId.getText());
				c.setPanNo(panNoId.getText());
				c = comServ.save(c);
			}else if(!c.getName().equals(companyId.getText())) {
				c.setName(companyId.getText());
				comServ.update(c);
			}

			Owner o = oserv.getByNameAndPanNo(ownerBean.getRName(), ownerBean.getRPanNo());
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
			o.setCompanyId(c.getCompanyId());
			
			oserv.update(o);

			ownerBean = new OwnerBean(
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
					o.getActive(),
					c.getName());

			companyId.setText(c.getName());
			tableOwnerid.getItems().set(selectedIndex, ownerBean);

			clear();

		}
	}


	public void onDelete(){
		int selectedIndex = tableOwnerid.getSelectionModel().getSelectedIndex();
	    if (selectedIndex >= 0) {
	    	OwnerBean ownerBean = tableOwnerid.getItems().remove(selectedIndex);
	    	if(ownerBean != null){
	    		oserv.delete(ownerBean.getRName(), ownerBean.getRPanNo());
	    	}
	    }
	    clear();
	}

	public void onReset() {
		clear();
	}
	
	
	public void populateCell(){
		tableOwnerid.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				int selectedIndex = tableOwnerid.getSelectionModel().getSelectedIndex();
				if (selectedIndex >= 0) {
					OwnerBean ownerBean = tableOwnerid.getItems().get(selectedIndex);
					nameId.setText(ownerBean.getRName());
					emailId.setText(ownerBean.getREmail());
					phone1Id.setText(ownerBean.getRPhone1());
					phone2Id.setText(ownerBean.getRPhone2());
					stateId.setValue(ownerBean.getRState());
					cityId.setText(ownerBean.getRCity());
					addressId.setText(ownerBean.getRAdress());
					pinId.setText(ownerBean.getRPin());
					aliasNameId.setText(ownerBean.getRAliasName());
					panNoId.setText(ownerBean.getRPanNo());
					companyId.setText(ownerBean.getRCompany());
					gstNoId.setText(ownerBean.getRGstNo());
					activeId.setSelected(ownerBean.getRActive() > 0 ? true : false);
					
					companyId.setText(ownerBean.getRCompany());
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
		companyId.clear();
		validationMsg.setText("");
	}

	private boolean isEmpty(String s){
		return (s == null || s.trim().isEmpty());
	}

	public void getCompanyDetail(){
		if(isEmpty(companyId.getText())) { 
			AppContext.displayAlert("Please select a row.");
			return;
			}
		if(!AppContext.isCurrentUserAdmin()) {
			AppContext.displayPermissionDialog();
			return;
		}
		CompanyController companyController = (CompanyController)companyView.getPresenter();
		companyController.setCompanyName(companyId.getText());
		Stage stage = new Stage();
		stage.setTitle("Update Company Detail");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(ownerView.getView().getScene().getWindow());
		stage.setScene(new Scene(companyView.getView()));
		stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/green-icon.png")));
		stage.setResizable(false);
		stage.centerOnScreen();
		stage.show();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	        	  stage.getScene().setRoot(new Region());
	          }
	      });
	}
}
