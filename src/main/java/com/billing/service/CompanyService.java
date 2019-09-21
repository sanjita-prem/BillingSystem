package com.billing.service;

import java.util.List;

import com.billing.model.Company;
import com.billing.repository.CompanyRepository;


public class CompanyService {
	private static final CompanyService INSTANCE = new CompanyService();
	
	public static CompanyService getInstance() {
		return INSTANCE;
	}
	private CompanyRepository comRepo;

	private CompanyService(){
		comRepo = new CompanyRepository();
	}

	public Company save(Company c){
		return comRepo.save(c);
	}
	public void update(Company c) {
		comRepo.update(c);
	}

	public Company getByName(String name){
		return comRepo.findCompanyByName(name);
	}
	
	public Company getByPanNo(String panNo){
		return comRepo.findCompanyByPanNo(panNo);
	}
	
	public Company getByNameAndPanNo(String name , String panNo) {
		return comRepo.findCompanyByNameAndPanNo(name, panNo);
	}

	public List<Company> getAll(){
		return comRepo.findAll();
	}

	public Company getByCompanyId(int id){
		return comRepo.findOne(id);
	}
}
