package com.excilys.formation.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.mapper.CompanyRowMapper;
import com.excilys.formation.model.Company;

@Component
public class CompanyDAO {
	private DataSource dataSource;
	
	public CompanyDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	private static final String COUNT = "SELECT COUNT(id) FROM company;";
	private static final String GET_BY_ID = "SELECT id, name FROM company WHERE id=?;";
	private static final String GET_RANGE = "SELECT id, name FROM company LIMIT ?, ?;";
	private static final String GET_ALL = "SELECT id, name FROM company;";
	private static final String EXISTS = "SELECT COUNT(id) FROM company WHERE id=?;";
	private static final String DELETE_COMPUTER_WITH_COMPANY_ID = "DELETE FROM computer WHERE company_id=?;";
	private static final String DELETE_COMPANY_WITH_ID = "DELETE FROM company WHERE id=?;";

	public int count() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate.queryForObject(COUNT, Integer.class);
	}

	public Optional<Company> getByID(int id) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate.query(GET_BY_ID, new CompanyRowMapper(), id).get(0);
	}
	
	public List<Optional<Company>> getRange(int offset, int numberOfRows) {
		List<Optional<Company>> companies = new ArrayList<>();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		companies = jdbcTemplate.query(GET_RANGE, new CompanyRowMapper(), offset, numberOfRows);
		return companies;
	}

	public List<Optional<Company>> getAll() {	
		List<Optional<Company>> companies = new ArrayList<>();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		companies = jdbcTemplate.query(GET_ALL, new CompanyRowMapper());
		return companies;
	}

	public boolean exists(int id) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate.queryForObject(EXISTS, Integer.class, id) > 0;
	}
	
	@Transactional
	public void delete(int id) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(DELETE_COMPUTER_WITH_COMPANY_ID, id);
		jdbcTemplate.update(DELETE_COMPANY_WITH_ID, id);
	}
}
