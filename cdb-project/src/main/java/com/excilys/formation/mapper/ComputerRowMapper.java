package com.excilys.formation.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.formation.dao.ComputerDAO;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.logger.CDBLogger;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;

public class ComputerRowMapper implements RowMapper<Optional<Computer>> {
	CDBLogger logger = new CDBLogger(ComputerDAO.class);
			
	@Override
	public Optional<Computer> mapRow(ResultSet rs, int rowNum) throws SQLException {
    	Optional<Computer> computer = Optional.empty();
		try {
			computer = Optional.ofNullable(new Computer.ComputerBuilder(rs.getString("name")).id(rs.getInt("id"))
					.company(new Company.CompanyBuilder().id(rs.getInt("companyID"))
							.name(rs.getString("companyName"))
							.build())
					.introduced(dateToLocalDate(rs.getDate("introduced")))
					.discontinued(dateToLocalDate(rs.getDate("discontinued")))
					.build());
		} catch (ArgumentException e) {
			logger.info(e.getMessage());
		}
        return computer;
	}

	private LocalDate dateToLocalDate(Date date) {
		return (date != null) ? date.toLocalDate() : null;
	}

	private Date localDateToDate(LocalDate localDate) {
		return (localDate != null) ? Date.valueOf(localDate) : null;
	}
}
