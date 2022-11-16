package com.miniproject.group4.service;

import com.miniproject.group4.enums.PayrollTypes;
import com.miniproject.group4.exception.RecordNotFoundException;
import com.miniproject.group4.model.Payroll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PayrollService {
    Payroll getPayrollById(Long id) throws RecordNotFoundException;
    Payroll savePayroll(Payroll payroll);
    Page<Payroll> getAllPayroll(Pageable pageable);
    Payroll updatePayroll(Payroll updatePayroll, Long id) throws RecordNotFoundException;
    void deletePayroll(Long id) throws RecordNotFoundException;

    Page<Payroll> findByPayrollType(PayrollTypes type, Pageable pageable);
}
