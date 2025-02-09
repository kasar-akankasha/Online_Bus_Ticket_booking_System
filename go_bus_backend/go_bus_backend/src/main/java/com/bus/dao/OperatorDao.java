package com.bus.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bus.pojos.Customer;
import com.bus.pojos.Operator;

public interface OperatorDao extends JpaRepository<Operator, Long>{
	
	
	public Operator findByOperatorEmail(String OperatorEmail);
	
	
}
