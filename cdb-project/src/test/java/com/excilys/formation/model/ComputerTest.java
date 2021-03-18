package com.excilys.formation.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.formation.exception.ArgumentException;

public class ComputerTest {
	
	private static Company company;
	
	@BeforeClass
	public static void initTests() {
		try {
			company = new Company.CompanyBuilder().id(1).build();
		} catch (ArgumentException exception) {
			exception.printStackTrace();
		}
	}

	@Test(expected = ArgumentException.class)
	public void testComputerName() throws ArgumentException {
		new Computer.ComputerBuilder("").build();
	}
	
	public void testComputerBuilder() {
		try {
			Computer computer = new Computer.ComputerBuilder("test")
					.id(1)
					.introduced(LocalDate.of(2020, 1, 1))
					.discontinued(LocalDate.of(2021, 1, 1))
					.company(company)
					.build();
			assertEquals(computer.getName(), "test");
			assertEquals(computer.getId(), 1);
			assertEquals(computer.getIntroduced(), LocalDate.of(2020, 1, 1));
			assertEquals(computer.getDiscontinued(), LocalDate.of(2021, 1, 1));
			assertEquals(computer.getCompany(), company);
		} catch (ArgumentException exception) {
			exception.printStackTrace();
		}
	}

}
