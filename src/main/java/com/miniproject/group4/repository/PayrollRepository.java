package com.miniproject.group4.repository;

import com.miniproject.group4.model.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface PayrollRepository extends PagingAndSortingRepository<Payroll, Long> {
}
