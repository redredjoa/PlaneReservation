package team9.Data;

import java.io.*;
import java.util.*;

public class RunData { 
	public List<User> user = new ArrayList<User>();
	public List<Plane> plane = new ArrayList<Plane>();
	public HashMap<String, Reservation> reservation = new HashMap<String, Reservation>();
	
	public RunData() { 
		try {
			Data[] userData = read("user/", ".txt");
			Data[] planeData = read("data/", ".txt");  

			parse(userData, planeData); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	}
	
	/** 지정된 경로에서 특정 파일 확장자를 가진 파일들을 모두 반환합니다. */
	public static File[] getFiles(String dir, String extension) {
		File file = new File(dir);
		
		return file.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(extension);
			}
		}); 
	}
	
	/** 지정된 경로에 있는 모든 데이터 파일을 읽습니다. */
	public Data[] read(String dir, String extension) throws IOException {  
		File[] files = getFiles(dir, extension);
		
		if(files.length == 0) {
			throw new IOException();
		}
		
		Data[] result = new Data[files.length];

		for(int i = 0; i < result.length; i++) {
			result[i] = Data.read(files[i]);
		}  
		
		return result;
	} 
	
	/** 입력받은 데이터를 이용해서 사용자와 비행기 정보 객체를 생성합니다. */
	public void parse(Data[] userData, Data[] planeData) {
		for(int i = 0; i < userData.length; i++) {
			user.add(User.parse(userData[i]));
		}
		
		for(int i = 0; i < planeData.length; i++) {
			plane.add(Plane.parse(planeData[i]));
		}
	}
}
