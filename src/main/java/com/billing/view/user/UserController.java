package com.billing.view.user;

import java.net.URL;
import java.util.ResourceBundle;

import com.billing.AppContext;
import com.billing.model.User;
import com.billing.model.UserBean;
import com.billing.service.UserService;
import com.billing.util.DateUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class UserController implements Initializable{

	@FXML
	TableView<UserBean> tableUserid;

	@FXML TableColumn<UserBean, Integer> iNo;

	@FXML
	TableColumn<UserBean, String> iUserId;

	@FXML
	TableColumn<UserBean, String> iPassowrd;
	
	@FXML
	TableColumn<UserBean, Integer> iActive;
	
	@FXML
	TableColumn<UserBean, String> iRole;

	@FXML
	TableColumn<UserBean, String> iCreated;

	private UserService userService;

	private ObservableList<UserBean> data = null;

	@FXML TextField userid;
	@FXML TextField pass;
	@FXML CheckBox active;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userService = UserService.getInstance();
		iNo.setCellValueFactory(new PropertyValueFactory<UserBean, Integer>("rNo"));
		iUserId.setCellValueFactory(new PropertyValueFactory<UserBean, String>("rUserId"));
		iPassowrd.setCellValueFactory(new PropertyValueFactory<UserBean, String>("rPassword"));
		iRole.setCellValueFactory(new PropertyValueFactory<UserBean, String>("rRole"));
		iActive.setCellValueFactory(new PropertyValueFactory<UserBean, Integer>("rActive"));
		iCreated.setCellValueFactory(new PropertyValueFactory<UserBean, String>("rCreated"));

		data = FXCollections.observableArrayList(userService.findAllUserBean());
		tableUserid.setEditable(true);
		tableUserid.setItems(data);

		populateCell();
	}

	private void clear(){
		userid.clear();
		pass.clear();
		active.setSelected(true);
	}
	public void onAddClick(){
		if(userid.getText().trim().isEmpty() || pass.getText().trim().isEmpty()){
			AppContext.displayAlert("User Id and Password required.");
			return;
		}
		User user1 = userService.getUserByUserId(userid.getText());
		if(user1 == null){
			User user = new User();
			user.setUserId(userid.getText());
			user.setPassword(pass.getText());
			user.setActive(active.selectedProperty().get() ? 1 : 0);
			user.setCreated(DateUtil.getCurrentDateS());
			user = userService.save(user);
			data.add(new UserBean(user.getUserId(), user.getPassword(), user.getActive(), user.getRole(), user.getCreated()));
		}else{
			AppContext.displayAlert("User Id already exist!!");
			return;
		}
		clear();
	}
	public void onDeleteClick(){
		int selectedIndex = tableUserid.getSelectionModel().getSelectedIndex();
	    if (selectedIndex >= 0) {
	    	UserBean userBean = tableUserid.getItems().get(selectedIndex);
	    	if(userBean != null && !AppContext.ADMIN.equals(userBean.getRRole())){
	    		userService.delete(userBean.getRUserId(), userBean.getRPassword());
	    		tableUserid.getItems().remove(selectedIndex);
	    	}else {
	    		AppContext.displayAlert("Can't delete Admin User!!");
	    		return;
	    	}
	    }
	    clear();
	}

	public void onUpdateClick(){
		int selectedIndex = tableUserid.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			UserBean userBean = tableUserid.getItems().get(selectedIndex);
			if(userBean != null && AppContext.ADMIN.equals(userBean.getRRole())){
				AppContext.displayAlert("Can't update Admin User!!");
				return;
			}
			userService.update(userBean, userid.getText(), pass.getText(), active.selectedProperty().get() ? 1 : 0);

			userBean.setRUserId(userid.getText());
			userBean.setRPassword(pass.getText());
			userBean.setRActive(active.selectedProperty().get() ? 1 : 0);
			tableUserid.getItems().set(selectedIndex, userBean);

			clear();

		}
	}

	public void populateCell(){
		tableUserid.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				int selectedIndex = tableUserid.getSelectionModel().getSelectedIndex();
				if (selectedIndex >= 0) {
					UserBean userBean = tableUserid.getItems().get(selectedIndex);
					userid.setText(userBean.getRUserId());
					pass.setText(userBean.getRPassword());
					active.setSelected(userBean.getRActive() > 0 ? true : false);
				}
			}
		});
	}
}
