package team9;

import team9.Data.*;

public class Menu {
	
	RunData rData;
	
	public Menu(RunData rData) {
		this.rData = rData;
	}
	
	public void show() {
		mainMenu();
	}
	
	private void mainMenu() {
		
		while(true) {
			
			for(String key : rData.getPlaneManager().plane.keySet()) {
				Plane plane = rData.getPlaneManager().plane.get(key);
				
				for(int j = 0; j < plane.getClassCount(); j++) {
					PClass pClass = plane.getClass(j); 

					System.out.println("=========================");
					System.out.println(pClass.getName());
					System.out.println("=========================");
					plane.showClass(j, false); 
					System.out.println("=========================");
				}
			}
			break;
		}
	}
}
