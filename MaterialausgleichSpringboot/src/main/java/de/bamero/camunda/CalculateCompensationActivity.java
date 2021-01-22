package de.bamero.camunda;

import javax.inject.Named;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import de.bamero.usecases.CalculateCompensationUseCase;


@Named("calculateCompensation")
public class CalculateCompensationActivity implements JavaDelegate {
	
	@Autowired
	private CalculateCompensationUseCase calculateCompensationUseCase;
	
	public void execute(DelegateExecution execution) throws Exception {
		double sum = calculateCompensationUseCase.calculateCompensation(execution.getId());
		execution.setVariable("sum", sum);
	}

}
