package com.excilys.formation.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.formation.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
