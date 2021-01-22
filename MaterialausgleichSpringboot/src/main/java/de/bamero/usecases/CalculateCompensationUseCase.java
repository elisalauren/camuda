package de.bamero.usecases;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.bamero.entities.CompensationEntity;

@Service
public class CalculateCompensationUseCase {

	@Autowired
	private RuntimeService runtimeService;
	
	public double calculateCompensation(String executionID) {
		
		boolean monday = (Boolean) runtimeService.getVariable(executionID, "monday");
		boolean tuesday = (Boolean) runtimeService.getVariable(executionID, "tuesday");
		boolean wednesday = (Boolean) runtimeService.getVariable(executionID, "wednesday");
		boolean thursday = (Boolean) runtimeService.getVariable(executionID, "thursday");
		boolean friday = (Boolean) runtimeService.getVariable(executionID, "friday");
		
		System.out.println("////Got monday: " + monday);
		
		CompensationEntity entity = new CompensationEntity(monday, tuesday, wednesday, thursday, friday);
		Double sum = entity.calculateCompensation();
		
		System.out.println("returning sum: "+sum);
		
		return sum;
	}
}

