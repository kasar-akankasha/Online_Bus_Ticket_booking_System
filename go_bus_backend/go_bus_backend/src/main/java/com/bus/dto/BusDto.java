	package com.bus.dto;

import java.util.List;

import com.bus.pojos.BusSchedule;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="rtoRegNo")

public class BusDto {
	
	private String rtoRegNo;
	
	private int seatCapacity;
	
	private boolean isAc;
	
	private boolean isSleeper;
	
	private AminityDto busAmenitiesId;
//	private List<BusSchedule> schedules;
	
	//private List<Booking> bookings;

//	public BusDto(String rtoRegNo, int seatCapacity, boolean isAc, boolean isSleeper, List<BusSchedule> schedules
//			) {
//		//List<Booking> bookings
//		super();
//		this.rtoRegNo = rtoRegNo;
//		this.seatCapacity = seatCapacity;
//		this.isAc = isAc;
//		this.isSleeper = isSleeper;
//		this.schedules = schedules;
//		//this.bookings = bookings;
//	}
	public BusDto(String rtoRegNo, int seatCapacity, boolean isAc, boolean isSleeper 
			) {
		//List<Booking> bookings
		super();
		this.rtoRegNo = rtoRegNo;
		this.seatCapacity = seatCapacity;
		this.isAc = isAc;
		this.isSleeper = isSleeper;
		//this.schedules = schedules;
		//this.bookings = bookings;
	}
	
	
}
