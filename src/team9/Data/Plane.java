package team9.Data; 

import java.text.ParseException; 
import java.util.*;
 

class SeatPosition {
	public int index, row, col;
	
	public SeatPosition(int index, int row, int col) {
		this.index = index;
		this.row = row;
		this.col = col;
	}
}

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
	//		 public METHODs
	//
	//***************************
  
	public void showClass(int index, boolean alsoReservation) {
		int total = 0;
		
		for(int i = 0; i <= index; i++) {
			if(i == index) { 
				pClass[index].show(total, alsoReservation);
				
				return;
			}
			total += pClass[i].getRowCount();
		}
	} 
	
	/** 좌석 번호로부터 좌석의 위치를 얻어 반환합니다. */
	public SeatPosition getSeatPosition(String seatID) {
		int index = 0, row = seatID.charAt(0) - 'A', col = seatID.charAt(1) - '0';
	 
		while(row >= pClass[index].getRowCount()) { 
			row -= pClass[index++].getRowCount();
		}
		 
		return new SeatPosition(index, row, col);
	}
	
	public String getSeatID(int index, int row, int col) {
		return getSeatID(new SeatPosition(index, row, col));
	} 
	
	/** 지정된 좌석의 좌석 번호를 반환합니다.*/
	public String getSeatID(SeatPosition pos) {
		int total = 0;
		
		for(int i = 0; i <= pos.index; i++) {
			if(i == pos.index) {
				total += pos.row;
				
				break;
			}
			total += pClass[i].getRowCount();
		}
		
		return String.format("%c%d", 'a' + total, pos.col);
	}

	@Override
	public boolean equals(Object o) {
		return id.equals(((Plane)o).id); 
	}

	//***************************
	//		    METHODs
	//
	//***************************

	//***************************
	//		    METHODs
	//
	//***************************
	
	/** 비행기 ID가 유효한지를 반환합니다. */
	private static boolean checkID(String id) {
		return true;
	}
	
	/** 데이터로부터 비행기 정보를 읽고 객체를 생성합니다. */ 
	protected static Plane parse(Data data) {
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
