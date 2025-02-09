package com.bus.service;

import java.util.ArrayList;
import java.util.List;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.bus.dao.BookingDao;
import com.bus.dao.BusDao;
import com.bus.dao.BusScheduleDao;
import com.bus.dao.OperatorDao;
import com.bus.dto.BusDto;
import com.bus.dto.BusScheduleDto;
import com.bus.pojos.Booking;
import com.bus.pojos.Bus;
import com.bus.pojos.BusSchedule;
import com.bus.pojos.Customer;
import com.bus.pojos.Operator;

@Transactional
@Service
public class OperatorService {
	
	@Autowired
	private BusDao busDao;
	
	@Autowired
	private BusScheduleDao busScheduleDao;
	
	@Autowired
	private BookingDao bookingDao;
	
	@Autowired
	private OperatorDao operatorDao;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public BusDto addBus(BusDto busDto,long operatorId) {
		
		Operator o = operatorDao.findById(operatorId).orElseThrow(()->new RuntimeException("invalid id"));
		
		
		
		Bus bus = modelMapper.map(busDto, Bus.class);
		bus.setId(o);
		//String rtoRegNo, int seatCapacity, boolean isAc, boolean isSleeper, List<BusSchedule> schedules,
		//List<Booking> bookings
		Bus newBus = busDao.save(bus);
		
//		BusDto busDto = new BusDto(newBus.getRtoRegNo(),newBus.getSeatCapacity(),newBus.isAc(),
//				newBus.isSleeper(),newBus.getSchedules());
		
		return busDto;
	}

	public BusScheduleDto addScheduleForBus( BusSchedule busSchedule, String busid) {
		
		System.out.println(busid.trim());
		Bus bus = busDao.findByRtoRegNo(busid.trim());
		
		System.out.println(bus);
		
		bus.addSchedule(busSchedule); //maintaining bidirectional relation
		
		busSchedule.initializeSeat(); //initializing seat to default capacity and value
		
		BusSchedule newBusSche =  busScheduleDao.save(busSchedule);
		
		/*
		 * Long scheduleId, String boardingPoint, String destinationPoint, LocalDate
		 * departureDate, LocalDate arrivalDate, LocalTime departureTime, LocalTime
		 * arrivalTime, String sourceCity, String destinationCity, double busFare,
		 * List<SeatAllocation> seatAllocation
		 */
		return new BusScheduleDto(newBusSche.getScheduleId(),newBusSche.getBoardingPoint(),
				newBusSche.getDestinationPoint(),newBusSche.getDepartureDate(),newBusSche.getArrivalDate(),
				newBusSche.getDepartureTime(),newBusSche.getArrivalTime(),newBusSche.getSourceCity(),
				newBusSche.getDestinationCity(),newBusSche.getBusFare(),newBusSche.getSeatAllocation()) ;
	}	
		
	
	
	public List<BusDto> getBuses(long operatorId){
		System.out.println(operatorId);
		
		List<Bus> list = busDao.findByOperatorId(operatorId);
		
		//System.out.println(list.get(0).getSchedules().get(0).getSeatAllocation());
		  List<BusDto> dtoList = new ArrayList<>();
		 
		  
		  list.forEach(e->{
			  dtoList.add(new BusDto(e.getRtoRegNo(), e.getSeatCapacity(), e.isAc(),
							     e.isSleeper()));
					  });
		  return dtoList;
		 		
		//return null;
	}
	
	public List<BusScheduleDto> getSchedule(String rtoRegNo){
		
		List<BusScheduleDto> busScheDto = new ArrayList<>(); 
		
		List<BusSchedule> list = busScheduleDao.findByRtoRegNo(rtoRegNo);
		
		list.forEach(e->{
			busScheDto.add(new BusScheduleDto(e.getScheduleId(),e.getBoardingPoint(),
					e.getDestinationPoint(),e.getDepartureDate(),e.getArrivalDate(),
					e.getDepartureTime(),e.getArrivalTime(),e.getSourceCity(),
					e.getDestinationCity(),e.getBusFare(),e.getSeatAllocation())
					);
		});
		
 		
		
		return busScheDto;
	}

	public List<Booking> getBookings(Long scheduleId) {
		// TODO Auto-generated method stub
		List<Booking> bookings = bookingDao.findByScheduleId(scheduleId);
		return bookings;
	}
	
	public Operator registerOperator(Operator operator) {
		
			return operatorDao.save(operator);
	}

	public Operator authenticateOperator(String operatorEmail, String operatorPassword) {
		
		Operator tempOperator = operatorDao.findByOperatorEmail(operatorEmail);
		System.out.println("\n "+ operatorEmail+","+operatorPassword+"\n");
		System.out.println("\n"+tempOperator);
			if(tempOperator == null)
				return null;
			String hashedPassword = tempOperator.getOperatorPassword();
			 Boolean correctLogin =  BCrypt.checkpw(operatorPassword, hashedPassword);
			 System.out.println("\n"+correctLogin+"\n");
			if(!correctLogin)
			return null;
			else
			return tempOperator ;
		
		
	}
	

	public List<Operator> getAllOperator(){
		
		return operatorDao.findAll();
	}
	
	public Operator approveOperator(long operatorId) {
		
		Operator o = operatorDao.findById(operatorId).orElseThrow(()->new RuntimeException("in operator service approve operator"));
		
		o.setApproved(true);
	    
		return o;
	}
	
	public void deleteOperator(long operatorId) {
		
		operatorDao.deleteById(operatorId);
		
	}



	public boolean deleteSchedule(Long scheduleId) {

		
		 BusSchedule busSchedule = busScheduleDao.findById(scheduleId).orElseThrow(()-> new RuntimeException("Inside operatorService/delete Schedule"));
		 
		 List<Booking> b = bookingDao.findByScheduleId(scheduleId);
		 
		 bookingDao.deleteAll(b);

		 busSchedule.getRtoRegNo().getSchedules().remove(busSchedule);
		 busScheduleDao.delete(busSchedule);
		 
		 
		 return true;				
	}
	
	public boolean deleteBus(String busId) {
		
		Bus bus = busDao.findByRtoRegNo(busId);
		 
        List<BusSchedule> list = bus.getSchedules();
        
        //for each shedule we remove all bookings 
        list.forEach(e->{
        	List<Booking> b = bookingDao.findByScheduleId(e.getScheduleId());
        	 bookingDao.deleteAll(b);
        	
        });
        
        //then delete bus
        
		 busDao.delete(bus);
		 return true;				
	}
	

}
