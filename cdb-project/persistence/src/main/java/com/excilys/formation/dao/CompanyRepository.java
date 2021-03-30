package com.excilys.formation.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.formation.dto.model.CompanyDTODatabase;

public interface CompanyRepository extends JpaRepository<CompanyDTODatabase, Long> {
	List<CompanyDTODatabase> findAllByNameContaining(String searchValue, Pageable pageable);
	CompanyDTODatabase findById(long id);
}
