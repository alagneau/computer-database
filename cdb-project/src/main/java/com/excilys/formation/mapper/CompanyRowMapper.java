package com.excilys.formation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.formation.dao.CompanyDAO;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.logger.CDBLogger;
import com.excilys.formation.model.Company;

public class CompanyRowMapper implements RowMapper<Optional<Company>> {
	CDBLogger logger = new CDBLogger(CompanyDAO.class);
			
	@Override
	public Optional<Company> mapRow(ResultSet rs, int rowNum) throws SQLException {
    	Optional<Company> company = Optional.empty();
		try {
			company = Optional.ofNullable(new Company.CompanyBuilder()
											.name(rs.getString("name"))
											.id(rs.getInt("id"))
											.build());
		} catch (ArgumentException e) {
			logger.info(e.getMessage());
		}
        return company;
	}
}
