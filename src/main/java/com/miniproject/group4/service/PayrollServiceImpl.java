package com.miniproject.group4.service;

import com.miniproject.group4.exception.RecordNotFoundException;
import com.miniproject.group4.model.Payroll;
import com.miniproject.group4.repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PayrollServiceImpl implements PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    @Override
    public Payroll getPayrollById(Long id) throws RecordNotFoundException {
        return payrollRepository.findById(id)
                .orElseThrow(()-> new RecordNotFoundException("Payroll not found."));
    }

    @Override
    public Payroll savePayroll(Payroll payroll) {
        return payrollRepository.save(payroll);
    }

    @Override
    public Page<Payroll> getAllPayroll(Pageable pageable) {
        return payrollRepository.findAll(pageable);
    }

    @Override
    public Payroll updatePayroll(Payroll payroll, Long id) throws RecordNotFoundException {
        Payroll payrollObj = payrollRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Payroll not found."));
        payrollObj.setName(payroll.getName());
        payrollObj.setBasicPay(payroll.getBasicPay());
        payrollObj.setAllowances(payroll.getAllowances());
        payrollObj.setType(payroll.getType());
        return payrollRepository.save(payrollObj);
    }

    @Override
    public void deletePayroll(Long id) throws RecordNotFoundException {
        Payroll payroll = payrollRepository.findById(id)
                .orElseThrow(()-> new RecordNotFoundException("Payroll not found."));
        payrollRepository.delete(payroll);
    }
}
