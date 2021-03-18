package com.excilys.formation.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.formation.model.Computer;

@Repository
public class ComputerDAO {
	private DataSource dataSource;
	
	public ComputerDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	private static final String NUMBER_OF_COMPUTER = "SELECT COUNT(id) FROM computer;";
	private static final String NUMBER_OF_COMPUTER_FILTERED = "SELECT COUNT(id) FROM computer WHERE computer.name LIKE ? ;";
	private static final String GET_RANGE = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id as \"companyID\", company.name as \"companyName\" "
								+ "FROM computer LEFT JOIN company ON computer.company_id=company.id "
								+ "ORDER BY computer.id "
								+ "LIMIT ?, ?;";
	private static final String GET_RANGE_SERVLET = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id as \"companyID\", company.name as \"companyName\" "
								+ "FROM computer LEFT JOIN company ON computer.company_id=company.id "
								+ "WHERE computer.name LIKE ? "
								+ "ORDER BY :! "
								+ "LIMIT ?, ?;";
	private static final String GET_BY_ID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id as \"companyID\", company.name as \"companyName\" "
								+ "FROM computer LEFT JOIN company ON computer.company_id=company.id "
								+ "WHERE computer.id=?"
								+ ";";
	private static final String EXISTS = "SELECT COUNT(id) FROM computer WHERE id=?;";
	private static final String ADD_COMPUTER = "INSERT INTO computer(name, introduced, discontinued, company_id) " + "VALUES (:name, :introduced, :discontinued, :companyId);";
	private static final String UPDATE_NAME = "UPDATE computer SET name = :name WHERE id = :id;";
	private static final String UPDATE_COMPANY = "UPDATE computer SET company_id = :companyId WHERE id = :id;";
	private static final String UPDATE_INTRODUCED = "UPDATE computer SET introduced = :introduced WHERE id = :id;";
	private static final String UPDATE_DISCONTINUED = "UPDATE computer SET discontinued = :discontinued WHERE id = :id;";
	private static final String UPDATE_ALL_PARAMETERS = "UPDATE computer SET name = :name, introduced = :introduced, discontinued = :discontinued, company_id = :companyId WHERE id = :id;";
	private static final String DELETE = "DELETE FROM computer WHERE id = :id;";

	public int count() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate.queryForObject(NUMBER_OF_COMPUTER, Integer.class);
	}

	public int filterAndCount(String filter) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate.queryForObject(NUMBER_OF_COMPUTER_FILTERED, Integer.class, "%" + filter + "%");
	}

	public List<Computer> getRange(int offset, int numberOfRows) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
        return jdbcTemplate.query(GET_RANGE, new ComputerRowMapper(), offset, numberOfRows);
	}

	public List<Computer> getRangeServlet(int offset, int numberOfRows, String search, String orderByValue, String orderByDirection) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		String newRequest = new String(GET_RANGE_SERVLET).replace(":!", orderByValue + " " + orderByDirection);
		
        return jdbcTemplate.query(newRequest, new ComputerRowMapper(), "%" + search + "%", offset, numberOfRows);
	}

	public Optional<Computer> getByID(int id) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return Optional.ofNullable(jdbcTemplate.query(GET_BY_ID, new ComputerRowMapper(), id).get(0));
	}

	public boolean exists(int id) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate.queryForObject(EXISTS, Integer.class, id) > 0;
	}

	public int add(Computer computer) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(computer);
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(ADD_COMPUTER, params, keyHolder);
		
		return keyHolder.getKey().intValue();
	}
	private void update1Parameter(Computer computer, String sqlQuery, String paramName, Object value) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", computer.getId());
		params.addValue(paramName, value);

		jdbcTemplate.update(sqlQuery, params);
	}

	public void updateName(Computer computer, String name) {
		update1Parameter(computer, UPDATE_NAME, "name", name);
	}

	public void updateCompany(Computer computer, int companyId) {
		update1Parameter(computer, UPDATE_COMPANY, "companyId", companyId);
	}

	public void updateIntroduced(Computer computer, LocalDate introduced) {
		update1Parameter(computer, UPDATE_INTRODUCED, "introduced", introduced);
	}

	public void updateDiscontinued(Computer computer, LocalDate discontinued) {
		update1Parameter(computer, UPDATE_DISCONTINUED, "discontinued", discontinued);
	}

	public void updateAllParameters(Computer computer) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(computer);
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

		jdbcTemplate.update(UPDATE_ALL_PARAMETERS, params);
	}

	public void delete(int computerID) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", computerID);

		jdbcTemplate.update(DELETE, params);
	}
}
