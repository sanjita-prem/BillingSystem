package com.billing.service;

import java.util.ArrayList;
import java.util.List;

import com.billing.model.StateCode;
import com.billing.repository.StateCodeRepository;


public class StateCodeService {
	private static final StateCodeService INSTANCE = new StateCodeService();
	
	public static StateCodeService getInstance() {
		return INSTANCE;
	}
	private StateCodeRepository scRepo;

	private StateCodeService(){
		scRepo = new StateCodeRepository() ;
	}

	public List<StateCode> getAll(){
		return scRepo.findAll();
	}

	public List<String> getAllState(){
		List<String> stateList = new ArrayList<>();
		for(StateCode state : scRepo.findAll()){
			stateList.add(state.getName());
		}
		return stateList;
	}
}
