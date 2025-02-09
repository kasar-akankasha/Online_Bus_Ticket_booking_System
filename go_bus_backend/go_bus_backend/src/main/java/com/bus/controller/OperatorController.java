package com.bus.controller;

import java.util.List;

import jakarta.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bus.BusBookingApplication;
import com.bus.dto.BusDto;
import com.bus.dto.BusScheduleDto;
import com.bus.pojos.Booking;
import com.bus.pojos.Bus;
import com.bus.pojos.BusSchedule;
import com.bus.pojos.Operator;
import com.bus.service.OperatorService;
@CrossOrigin("*")
@RequestMapping("/bus/operator")
@RestController
@Validated
public class OperatorController {

	@Autowired
	private OperatorService operatorService;
	
	
	@PostMapping("/addbus/{operatorId}")
	public BusDto addBus(@RequestBody BusDto bus,@PathVariable long operatorId) {
			System.out.println("\n New bus :-"+ bus+"\n");
		return operatorService.addBus(bus, operatorId);
	}
	
	@PostMapping("/addSchedule/{busid}")
	public BusScheduleDto addScheduleForBus(@RequestBody BusSchedule busSchedule,@PathVariable String busid) {
		
		return operatorService.addScheduleForBus(busSchedule, busid);
	}
	
	@GetMapping("/{operatorid}")
	public List<BusDto> getBuses(@PathVariable long operatorid){
		System.out.println("in getBuses");
		try {
		      return operatorService.getBuses(operatorid);
		}catch(Exception e) {
			System.out.println("exception during returing from controller");
		}
		return null;
	}
	
	@GetMapping("/schedule/{rtoRegNo}")
	public List<BusScheduleDto> getScheduleByBusId(@PathVariable String rtoRegNo){
		
		//operatorService.getSchedule(rtoRegNo);
		
		return operatorService.getSchedule(rtoRegNo);
	}
	
	@GetMapping("/bookings/{scheduleId}")
	public List<Booking> getBookingByScheduleId(@PathVariable Long scheduleId){
		
		
		return operatorService.getBookings(scheduleId);
	}
	
	@PostMapping("/addOperator")
	public Operator registerOperator(@RequestBody @Valid Operator operator) {
		
operator.setOperatorPassword(BCrypt.hashpw(operator.getOperatorPassword(), BCrypt.gensalt()));
System.out.println("\noperator password: " + operator.getOperatorPassword()+"\n");
		operator.setApproved(false);
		return operatorService.registerOperator(operator);
	}
		
	@PostMapping("/login")
	public ResponseEntity<Object> authenticateOperator(@RequestBody Operator operator) {
		
		
		Operator o =  operatorService.authenticateOperator(operator.getOperatorEmail(), operator.getOperatorPassword());
	
		if(o == null) {
			return new ResponseEntity<Object>("invalid credential", HttpStatus.UNAUTHORIZED);
		}
		else
			return new ResponseEntity<Object>(o, HttpStatus.OK);
			
	}
	
	
	@GetMapping("/deleteSchedule/{scheduleId}")
	public	 boolean deleteSchedule(@PathVariable Long scheduleId) {
		
			operatorService.deleteSchedule(scheduleId);
			return true;
			}
	
	@DeleteMapping("/deleteBus/{busId}")
	public boolean deleteBus(@PathVariable String busId) {
		
		operatorService.deleteBus(busId);
		return true;
	}
	
	
}
