package com.excilys.formation.dao;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.excilys.formation.model.Computer;

@Transactional
public interface ComputerRepository extends JpaRepository<Computer, Long> {
	long countByNameContaining(String name);
	List<Computer> findAllByNameContaining(String name, Pageable pageable);
	
	@Modifying
	@Query("UPDATE computer c set c.name = :name WHERE c.id = :id")
	void updateName(@Param("id") long computerId, @Param("name") String name);
	
	@Modifying
	@Query("UPDATE computer c set c.company = :companyId WHERE c.id = :id")
	void updateCompany(@Param("id") long computerId, @Param("companyId") long companyId);
	
	@Modifying
	@Query("UPDATE computer c set c.introduced = :introduced WHERE c.id = :id")
	void updateIntroduced(@Param("id") long computerId, @Param("introduced") LocalDate introduced);
	
	@Modifying
	@Query("UPDATE computer c set c.discontinued = :discontinued WHERE c.id = :id")
	void updateDiscontinued(@Param("id") long computerId, @Param("discontinued") LocalDate discontinued);
}
