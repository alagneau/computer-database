package com.excilys.formation.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.logger.CDBLogger;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;

public class ComputerRowMapper implements RowMapper<Computer> {
	CDBLogger logger = new CDBLogger(ComputerDAO.class);
			
	@Override
	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
    	Computer computer = null;
		try {
			computer = new Computer.ComputerBuilder(rs.getString("name")).id(rs.getInt("id"))
					.company(new Company.CompanyBuilder().id(rs.getInt("companyID"))
							.name(rs.getString("companyName"))
							.build())
					.introduced(dateToLocalDate(rs.getDate("introduced")))
					.discontinued(dateToLocalDate(rs.getDate("discontinued")))
					.build();
		} catch (ArgumentException exception) {
			logger.error(exception.getMessage());
		}
        return computer;
	}

	private LocalDate dateToLocalDate(Date date) {
		return (date != null) ? date.toLocalDate() : null;
	}
}
