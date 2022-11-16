package com.miniproject.group4.repository;

import com.miniproject.group4.model.Payroll;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PayrollRepository extends PagingAndSortingRepository<Payroll, Long> {
}
