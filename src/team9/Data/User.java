package team9.Data;

import java.text.ParseException;
import java.util.*;

public class User {
	
	//***************************
	//		   VARIABLESs
	//
	//***************************

	private String id;
	private String password;
	private String name;
	private DateTime birthday;
	private int mileage;
	
	private boolean isAdministrator; 
	
	private List<String> reservationID = new ArrayList<String>();
	
	//***************************
	//			GETTER
	//
	//***************************
	
	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}
	
	public String getName() {
		return name;
	}
	
	public int getMileage() {
		return mileage;
	}
 
	public DateTime getBirthday() {
		return birthday;
	}
	
	public boolean isAdministrator() {
		return isAdministrator;
	}

	//***************************
	//	      CONSTRUCTOR
	//
	//***************************
	
	private User() {  
		super();
	} 
	
	public User(String id, String password) { 
		this(id, password, false);
	}  
	
	public User(String id, String password, boolean isAdministrator ) {
		this.id = id;
		this.password = password;
		
		this.isAdministrator = isAdministrator;
	} 

	//***************************
	//		    METHODs
	//
	//***************************
 
	/** 아이디가 형식에 부합하는지의 여부를 반환합니다. */
	public static boolean checkID(String id) {
		return true;
	}
	
	/** 비밀번호가 형식에 부합하는지의 여부를 반환합니다. */
	public static boolean checkPassword(String password) {
		return true;
	}

	/** 데이터로부터 유저 정보를 읽고 객체를 생성합니다. */
	public static User parse(Data data) {
		User result = new User(); 
		
		try {
			result.id = data.get("id");  
			result.password = data.get("password");
			result.name = data.get("name");
			result.birthday = DateTime.parseDate(data.get("birthday")); 
			result.mileage = Integer.parseInt(data.get("mileage"));
			result.isAdministrator = data.get("admin") == "true"; 
	
			String[] rID = data.get("reservationID").split("\n");
			
			for(int i = 0; i < rID.length; i++) { 
				if(rID[i].isBlank()) {
					break;	
				}
				
				result.reservationID.add(rID[i]); 
			}
		}
		catch(KeyNotFoundException e) { 
			e.printStackTrace();
		}
		catch(NumberFormatException e) { 
			e.printStackTrace();
		} 
		catch (ParseException e) { 
			e.printStackTrace();
		}
		
		return result; 
	}
}
