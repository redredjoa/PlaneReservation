package team9.Data; 

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import team9.Data.PClass.SeatType;

public class Plane {	  
	
	//***************************
	//		   VARIABLESs
	//
	//***************************

	private String name;
	private String id;
	private String departure;
	private String arrival;
	private DateTime departureTime;
	private DateTime arrivalTime;

	private PClass[] pClass;
	
	//***************************
	//			GETTER
	//
	//***************************
	
	public String getName() {
		return name;
	}

	public String getID() {
		return id;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public String getDeparture() {
		return departure;
	}

	public String getArrival() {
		return arrival;
	}
	
	public PClass getClass(int index) {
		return pClass[index];
	}
	
	public int getClassCount() {
		return pClass.length;
	}

	//***************************
	//	      CONSTRUCTOR
	//
	//***************************
	
	private Plane() {
		super();
	}	
 
	//***************************
	//		    METHODs
	//
	//***************************
 
	public String getReservationID(int index, int row, int col) {
		if(pClass[index].getReservation(row, col) == SeatType.NONE) {
			return null;
		}
		return String.format("%s-%s-%c%d-%c", id, new SimpleDateFormat("yyMMdd").format(departureTime), 'a' + row, col, (pClass[index].getReservation(row, col) == SeatType.ADULT) ? 'a' : 'c');
	}
	
	/** 비행기 ID가 유효한지를 반환합니다. */
	private static boolean checkID(String id) {
		return true;
	}
	
	/** 데이터로부터 비행기 정보를 읽고 객체를 생성합니다. */ 
	public static Plane parse(Data data) {
		Plane result = new Plane(); 
		
		try {			
			result.name = data.get("name");
			
			String id = data.get("id");
			
			if(checkID(id)) { 
				result.id = id;
			}
			else {
				throw new ParseException("파싱 오류", -1);
			}

			result.departure = data.get("departure");
			result.arrival = data.get("arrival"); 
 
			result.departureTime = DateTime.parseDateTime(data.get("departure_time"));
			result.arrivalTime = DateTime.parseDateTime(data.get("arrival_time"));
			
			result.pClass = new PClass[Integer.parseInt(data.get("class_count"))]; 
			String[] className = data.get("class_name").split("\n");
			
			for(int i = 0; i < result.pClass.length; i++) {
				result.pClass[i] = PClass.parse(className[i], data.get(String.format("class_%d", i)));
			} 
		}
		catch(KeyNotFoundException e) { 
			e.printStackTrace();
		}
		catch(NumberFormatException e) { 
			e.printStackTrace();
		}
		catch(IndexOutOfBoundsException e) {  
			e.printStackTrace();
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
		
		return result; 
	}
 
}
