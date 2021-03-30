package com.excilys.formation.dao;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.excilys.formation.dto.model.CompanyDTODatabase;
import com.excilys.formation.dto.model.ComputerDTODatabase;

@Transactional
public interface ComputerRepository extends JpaRepository<ComputerDTODatabase, Long> {
	long countByNameContaining(String name);
	ComputerDTODatabase findById(long id);
	
	List<ComputerDTODatabase> findAllByNameContaining(String name, Pageable pageable);
	void deleteByCompany(CompanyDTODatabase id);
	
	@Modifying
	@Query("UPDATE computer c set c.name = :name WHERE c.id = :id")
	void updateName(@Param("id") long computerId, @Param("name") String name);
	
	@Modifying
	@Query("UPDATE computer c set c.company = :company WHERE c.id = :id")
	void updateCompany(@Param("id") long computerId, @Param("company") CompanyDTODatabase company);
	
	@Modifying
	@Query("UPDATE computer c set c.introduced = :introduced WHERE c.id = :id")
	void updateIntroduced(@Param("id") long computerId, @Param("introduced") LocalDate introduced);
	
	@Modifying
	@Query("UPDATE computer c set c.discontinued = :discontinued WHERE c.id = :id")
	void updateDiscontinued(@Param("id") long computerId, @Param("discontinued") LocalDate discontinued);
	
	long deleteByIdIn(List<Long> id_list);
	
}
