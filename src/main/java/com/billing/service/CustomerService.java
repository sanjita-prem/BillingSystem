package com.billing.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billing.model.Customer;
import com.billing.model.CustomerBean;
import com.billing.repository.CustomerRepository;

public class CustomerService {
	private static final CustomerService INSTANCE = new CustomerService();
	
	public static CustomerService getInstance() {
		return INSTANCE;
	}

	private CustomerRepository custRepo;

	private CustomerService(){
		custRepo = new CustomerRepository();
	}

	public List<Customer> getAllCustomers(){
		return custRepo.findAll();
	}

	public List<CustomerBean> getAllCustomerBeans(){
		List<Customer> customerList =  getAllCustomers();
		List<CustomerBean> Obean = new ArrayList<>();
		for(Customer o : customerList){
			Obean.add(new CustomerBean(
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
		}
		return Obean;
	}

	public Customer save(Customer o){
		return custRepo.save(o);
	}

	public Customer getByName(String name){
		return custRepo.findByName(name);
	}
	public void update(Customer c) {
		
		custRepo.update(c);
	}
	

	public Customer getByPanNo(String panNo){
		
		return custRepo.findByPanNo(panNo);
	}

	public void delete(String panNo){
		Customer o = getByPanNo(panNo);
		custRepo.delete(o);
	}

	public Customer getByNameAndPanNo(String name, String panNo){
		Customer c = custRepo.findByNameAndPanNo(name, panNo);
		return c;
	}

	public List<Customer> getAllCustomer(){
		return custRepo.findAll();
	}

	public Map<String, Customer> getAllCustomerMap(){
		Map<String, Customer> map = new HashMap<>();
		for(Customer c : getAllCustomer()){
			map.put(c.getAliasName(), c);
		}
		return map;
	}
	public Customer getCustomerById(int id){
		return custRepo.findOne(id);
	}
}
