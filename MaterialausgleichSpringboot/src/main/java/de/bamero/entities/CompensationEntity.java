package de.bamero.entities;

import java.util.HashMap;
import java.util.Map;

public class CompensationEntity {

	private Map<String, Boolean> hoValues;
	private static final int COMPENSATION = 40;
	
	
	public CompensationEntity(boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday) {
		
		hoValues = new HashMap<String, Boolean>();
		
		hoValues.put("mo", monday);
		hoValues.put("tue", tuesday);
		hoValues.put("wed", wednesday);
		hoValues.put("thu", thursday);
		hoValues.put("fri", friday);
	}

	public double calculateCompensation() {
		System.out.println("Entity called");
		
		int homeofficeDays = (int) this.hoValues.entrySet()
												.stream()
												.filter(x -> x.getValue() == true)
												.count();
		
		return homeofficeDays*COMPENSATION;
	}
}

