package de.bamero.tasks;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Named("calculateCompensationTask")
public class CalculateCompensationTask implements JavaDelegate{

	public void execute(DelegateExecution execution) throws Exception {
		
		Boolean mondayHo = (Boolean) execution.getVariable("mondayHo");
		Boolean tuesdayHo = (Boolean) execution.getVariable("tuesdayHo");
		Boolean wednesdayHo = (Boolean) execution.getVariable("wednesdayHo");
		Boolean thursdayHo = (Boolean) execution.getVariable("thursdayHo");
		Boolean fridayHo = (Boolean) execution.getVariable("fridayHo");
		
		int sum = 0;
		if(mondayHo) {
			sum+=40;
		}
		if(tuesdayHo) {
			sum+=40;
		}
		if(wednesdayHo) {
			sum+=40;
		}
		
		execution.setVariable("sum", sum);
	}
}
