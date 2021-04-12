package team9.Data;

import java.io.*;
import java.util.*;

class KeyNotFoundException extends RuntimeException{
 
	private static final long serialVersionUID = -7956643306604310282L;

	public KeyNotFoundException(String msg){
		super(msg);
	}

}

public class Data {
	private HashMap<String, String> data = new HashMap<String, String>(); 
	
	public boolean contains(String key) {
		return data.containsKey(key);
	}
	
	public String get(String key) throws KeyNotFoundException { 
		if(contains(key)) {
			return data.get(key);
		}
		else {
			throw new KeyNotFoundException(String.format("오류 : 데이터 파일에서 키 %s를 찾을 수 없습니다.", key));
		}  
	}
	
	public void set(String key, String value) { 
		data.put(key, value);
	}
	
	public Set<String> keyset (){
		return data.keySet();
	}
	
	protected static Data read(File file) {
		var result = new Data();
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"))) {    
			boolean readOneMore = false; // true인 경우 현재 행을 다시 읽습니다.
			
			String line = null, s;
			StringBuilder sb = new StringBuilder();
			
			for(int i = 0; readOneMore || ((line = reader.readLine()) != null); i++) { 
				readOneMore = false;
		
				 s = line.trim();
				 
				 if(s.isBlank() || s.startsWith("#")) {
					 continue;
				 } 

				 // '=>'에 대한 처리 코드입니다.
				 if(s.endsWith("=>")) {
					 String key = s.replace("=>", "");
					
					 while ((line = reader.readLine()) != null) {
						 // 현재 행에 '\t'문자가 존재하는 경우
						 if(line.contains("\t")) {
							 i++; 
							 
							 int index = line.indexOf("\t");
							 
							 // '\t'문자가 행의 첫 번째 문자인 경우
							 if(line.substring(0, index).trim().isBlank()) {  
								 sb.append(line.substring(index + 1, line.length()));
								 sb.append('\n'); 
							 } 
						 }  
						 else { 
							 readOneMore = true;
							 
							 break;
						 }
					 }
					// 해당 키를 가진 데이터가 존재하지 않으면 데이터를 추가합니다.
					 if(!result.data.containsKey(key)) { 
						 result.data.put(key, sb.toString());
						 
						 sb.setLength(0);
					 }  
					 continue;
				 }
				 
				 // '='에 대한 처리 코드입니다.
				 if(s.contains("=")) {
					 // '='문자를 기준으로 문자열을 둘로 나눕니다.
					 String[] data = s.split("=", 2);
					 // 해당 키를 가진 데이터가 존재하지 않으면 데이터를 추가합니다.
					 if(!result.data.containsKey(data[0])) { 
						 result.data.put(data[0], data[1]);
					 } 
					 continue;
				 }
			 }
			 		
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	protected static void write(Data data, File file) {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) { 
			for(String key : data.keyset()) {
				String value = data.get(key); 

				if(value.contains("\n")) {
					writer.write(String.format("%s=>\n", key));

					String[] values = value.split("\n");
					for(int i = 0; i < values.length; i++) { 
						writer.write(String.format("\t%s\n", values[i]));
					}
				}
				else { 
				    writer.write(String.format("%s=%s\n", key, value)); 
				} 
			} 
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
}
