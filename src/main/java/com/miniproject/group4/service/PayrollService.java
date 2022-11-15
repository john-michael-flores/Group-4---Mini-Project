package com.miniproject.group4.service;

import com.miniproject.group4.exception.RecordNotFoundException;
import com.miniproject.group4.model.Payroll;

import java.util.List;

public interface PayrollService {
    Payroll getPayrollById(Long id) throws RecordNotFoundException;
    Payroll savePayroll(Payroll payroll);
    List<Payroll> getAllPayroll();
    Payroll updatePayroll(Payroll updatePayroll, Long id) throws RecordNotFoundException;
    void deletePayroll(Long id) throws RecordNotFoundException;
}
