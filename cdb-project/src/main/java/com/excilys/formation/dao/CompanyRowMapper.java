package com.excilys.formation.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.logger.CDBLogger;
import com.excilys.formation.model.Company;

public class CompanyRowMapper implements RowMapper<Company> {
	CDBLogger logger = new CDBLogger(CompanyDAO.class);
			
	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
    	Company company = null;
		try {
			company = new Company.CompanyBuilder()
							.name(rs.getString("name"))
							.id(rs.getInt("id"))
							.build();
		} catch (ArgumentException e) {
			logger.info(e.getMessage());
		}
        return company;
	}
}
