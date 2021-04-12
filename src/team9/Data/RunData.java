package team9.Data;

import java.io.*;
import java.util.*;

public class RunData { 
	public HashMap<String, User> user = new HashMap<String, User>();
	
	private PlaneManager planeManager = new PlaneManager();
	 
	public User currentUser = null;
	
	public PlaneManager getPlaneManager() {
		return planeManager;
	}
	
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
			User u = User.parse(userData[i]);
			user.put(u.getID(), u);
		}
		
		for(int i = 0; i < planeData.length; i++) {
			Plane p = Plane.parse(planeData[i]);
			planeManager.plane.put(p.getID(), p);
		}
		
		for(String key : user.keySet()) {
			for(int index = 0; index < user.get(key).getReservationIDCount(); index++) {
				User u = user.get(key);
				String id = u.getReservationID(index);
				 
				planeManager.reserve(u, id);
			}
		}
		
	}

	public void save() { 
		/*
		for(String key : user.keySet()) {
			User u = user.get(key);
			
			Data data = User.parseToData(u);
			
			Data.write(data, new File(String.format("D:/test/%s.txt", user.get(key).getID())));
		}
		*/
	}
}
