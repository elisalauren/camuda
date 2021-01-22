package de.bamero.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CalculateCompensationTest {
	
	CompensationEntity entity;
	
	@Test
	public void calculateCompensation_onlyTrue() {
		
		//given
		entity = new CompensationEntity(true, true, true, true, true);
		
		///when
		double sum = entity.calculateCompensation();

		//then
		assertEquals(sum, 200, 0);
	}
	
	@Test
	public void calculateCompensation_onlyFalse() {
		
		//given
		entity = new CompensationEntity(false, false, false, false, false);
		
		///when
		double sum = entity.calculateCompensation();

		//then
		assertEquals(sum, 0, 0);
	}

}
