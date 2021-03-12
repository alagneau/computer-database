package com.excilys.formation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
import org.springframework.stereotype.Component;

import com.excilys.formation.exception.AddDataException;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.exception.DeletingDataException;
import com.excilys.formation.exception.ReadDataException;
import com.excilys.formation.exception.UpdatingDataException;
import com.excilys.formation.mapper.ComputerRowMapper;
import com.excilys.formation.model.Computer;

@Component
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
	private static final String DELETE = "DELETE FROM computer WHERE id = ?;";

	public int count() throws ReadDataException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate.queryForObject(NUMBER_OF_COMPUTER, Integer.class);
		/*
		int value = 0;
		try (Connection connection = dataSource.getConnection()) {
			ResultSet result = connection.createStatement().executeQuery(NUMBER_OF_COMPUTER);
			result.next();

			value = result.getInt(1);
		} catch (SQLException sqlException) {
			throw new ReadDataException(sqlException.getMessage());
		}
		return value;
		*/
	}
	public void testArgs(String... test) {
		
	}

	public int filterAndCount(String filter) throws ReadDataException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate.queryForObject(NUMBER_OF_COMPUTER_FILTERED, Integer.class, "%" + filter + "%");
		/*
		int value = 0;
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(NUMBER_OF_COMPUTER_FILTERED);
			String searchValue = "";
			if (filter != null) {
				searchValue = filter;
			}
			statement.setString(1, "%" + searchValue + "%");
			ResultSet result = statement.executeQuery();
			result.next();

			value = result.getInt(1);
		} catch (SQLException sqlException) {
			throw new ReadDataException(sqlException.getMessage());
		}
		return value;
		*/
	}

	public List<Optional<Computer>> getRange(int offset, int numberOfRows) throws ReadDataException, ArgumentException {
		List<Optional<Computer>> computers = new ArrayList<>();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
        computers = jdbcTemplate.query(GET_RANGE, new ComputerRowMapper(), offset, numberOfRows);
		return computers;
		/*
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(GET_RANGE);
			statement.setInt(1, offset);
			statement.setInt(2, numberOfRows);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Optional<Computer> computer = Optional.ofNullable(new Computer.ComputerBuilder(result.getString("name")).id(result.getInt("id"))
						.company(new Company.CompanyBuilder().id(result.getInt("companyID"))
								.name(result.getString("companyName"))
								.build())
						.introduced(dateToLocalDate(result.getDate("introduced")))
						.discontinued(dateToLocalDate(result.getDate("discontinued")))
						.build());
				computers.add(computer);
			}

		} catch (SQLException sqlException) {
			throw new ReadDataException(sqlException.getMessage());
		}
		return computers;
		*/
	}

	public List<Optional<Computer>> getRangeServlet(int offset, int numberOfRows, String search, String orderByValue, String orderByDirection) throws ReadDataException, ArgumentException {
		List<Optional<Computer>> computers = new ArrayList<>();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		String newRequest = new String(GET_RANGE_SERVLET).replace(":!", orderByValue + " " + orderByDirection);
		
        computers = jdbcTemplate.query(newRequest, new ComputerRowMapper(), "%" + search + "%", offset, numberOfRows);
		return computers;
		
		
		/*
		List<Optional<Computer>> computers = new ArrayList<>();
		try (Connection connection = dataSource.getConnection()) {
			
			String newRequest = new String(GET_RANGE_SERVLET);
			newRequest = newRequest.replace(":!", orderByValue + " " + orderByDirection);
			
			PreparedStatement statement = connection.prepareStatement(newRequest);
			statement.setString(1, "%" + search + "%");
			statement.setInt(2, offset);
			statement.setInt(3, numberOfRows);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Optional<Computer> computer = Optional.ofNullable(new Computer.ComputerBuilder(result.getString("name")).id(result.getInt("id"))
						.company(new Company.CompanyBuilder().id(result.getInt("companyID"))
								.name(result.getString("companyName"))
								.build())
						.introduced(dateToLocalDate(result.getDate("introduced")))
						.discontinued(dateToLocalDate(result.getDate("discontinued")))
						.build());
				computers.add(computer);
			}

		} catch (SQLException sqlException) {
			throw new ReadDataException(sqlException.getMessage());
		}
		return computers;
		*/
	}

	public Optional<Computer> getByID(int id) throws ReadDataException, ArgumentException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate.query(GET_BY_ID, new ComputerRowMapper(), id).get(0);
		
		
		/*
		Optional<Computer> computer = Optional.empty();
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(GET_BY_ID);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				computer = Optional.ofNullable(new Computer.ComputerBuilder(result.getString("name")).id(result.getInt("id"))
						.company(new Company.CompanyBuilder().id(result.getInt("companyID"))
								.name(result.getString("companyName"))
								.build())
						.introduced(dateToLocalDate(result.getDate("introduced")))
						.discontinued(dateToLocalDate(result.getDate("discontinued")))
						.build());
			}
		} catch (SQLException sqlException) {
			throw new ReadDataException(sqlException.getMessage());
		}
		return computer;
		*/
	}

	public boolean exists(int id) throws ReadDataException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate.queryForObject(EXISTS, Integer.class, id) > 0;
		
		/*
		boolean returnValue = false;
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(EXISTS);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			
			result.next();

			returnValue = (result.getInt(1) > 0) ? true : false;
		} catch (SQLException sqlException) {
			throw new ReadDataException(sqlException.getMessage());
		}
		return returnValue;
		*/
	}

	public int add(Computer computer) throws AddDataException {
		SqlParameterSource params = new BeanPropertySqlParameterSource(computer);
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(ADD_COMPUTER, params, keyHolder);
		
		return keyHolder.getKey().intValue();
		
		/*
		int newID = 0;
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(ADD_COMPUTER, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, computer.getName());
			statement.setDate(2, localDateToDate(computer.getIntroduced()));
			statement.setDate(3, localDateToDate(computer.getDiscontinued()));
			statement.setInt(4, computer.getCompany().getID());

			statement.executeUpdate();
			ResultSet result = statement.getGeneratedKeys();
			result.next();

			newID = result.getInt(1);
		} catch (SQLException sqlException) {
			throw new AddDataException(sqlException.getMessage());
		}

		return newID;
		*/
	}
	private void update1Parameter(Computer computer, String sqlQuery, String paramName, Object value) throws UpdatingDataException {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", computer.getId());
		params.addValue(paramName, value);

		jdbcTemplate.update(sqlQuery, params);
	}

	public void updateName(Computer computer, String name) throws UpdatingDataException {
		update1Parameter(computer, UPDATE_NAME, "name", name);
		
		/*
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(UPDATE_NAME);
			statement.setString(1, name);
			statement.setInt(2, computer.getID());

			statement.executeUpdate();
		} catch (SQLException sqlException) {
			throw new UpdatingDataException(sqlException.getMessage());
		}
		*/
	}

	public void updateCompany(Computer computer, int companyId) throws ArgumentException, UpdatingDataException {
		update1Parameter(computer, UPDATE_COMPANY, "companyId", companyId);
		/*
		if (companyID <= 0) {
			throw new ArgumentException("Invalid company ID : " + companyID);
		}
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(UPDATE_COMPANY);
			statement.setInt(1, companyID);
			statement.setInt(2, computer.getID());

			statement.executeUpdate();
		} catch (SQLException sqlException) {
			throw new UpdatingDataException(sqlException.getMessage());
		}
		*/
	}

	public void updateIntroduced(Computer computer, LocalDate introduced) throws UpdatingDataException {
		update1Parameter(computer, UPDATE_INTRODUCED, "introduced", introduced);
		/*
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(UPDATE_INTRODUCED);
			statement.setDate(1, localDateToDate(introduced));
			statement.setInt(2, computer.getID());

			statement.executeUpdate();
		} catch (SQLException sqlException) {
			throw new UpdatingDataException(sqlException.getMessage());
		}
		*/
	}

	public void updateDiscontinued(Computer computer, LocalDate discontinued) throws UpdatingDataException {
		update1Parameter(computer, UPDATE_DISCONTINUED, "discontinued", discontinued);
		/*
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(UPDATE_DISCONTINUED);
			statement.setDate(1, localDateToDate(discontinued));
			statement.setInt(2, computer.getID());

			statement.executeUpdate();
		} catch (SQLException sqlException) {
			throw new UpdatingDataException(sqlException.getMessage());
		}
		*/
	}

	public void updateAllParameters(Computer computer) throws UpdatingDataException {
		SqlParameterSource params = new BeanPropertySqlParameterSource(computer);
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

		jdbcTemplate.update(UPDATE_ALL_PARAMETERS, params);
		/*
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(UPDATE_ALL_PARAMETERS);
			statement.setString(1, computer.getName());
			statement.setDate(2, localDateToDate(computer.getIntroduced()));
			statement.setDate(3, localDateToDate(computer.getDiscontinued()));
			statement.setInt(4, computer.getCompany().getID());
			statement.setInt(5, computer.getID());

			statement.executeUpdate();
		} catch (SQLException sqlException) {
			throw new UpdatingDataException(sqlException.getMessage());
		}
		*/
	}

	public void delete(int computerID) throws DeletingDataException {
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(DELETE);
			statement.setInt(1, computerID);

			statement.executeUpdate();
		} catch (SQLException sqlException) {
			throw new DeletingDataException(sqlException.getMessage());
		}
	}
}
