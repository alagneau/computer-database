package com.excilys.formation.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.excilys.formation.exception.ArgumentException;

public class ComputerTest {

	@Test(expected = ArgumentException.class)
	public void testComputer() throws ArgumentException {
		Computer test = new Computer.ComputerBuilder("").build();
		assertEquals(test.getName(), "");
	}

	@Test
	public void testComputerInt() {
		//fail("Not yet implemented");
	}

	@Test
	public void testComputerString() {
		//fail("Not yet implemented");
	}

	@Test
	public void testComputerIntStringCompany() {
		//fail("Not yet implemented");
	}

	@Test
	public void testComputerIntStringCompanyLocalDateLocalDate() {
		//fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		//fail("Not yet implemented");
	}

	@Test
	public void testEqualsObject() {
		//fail("Not yet implemented");
	}

}
