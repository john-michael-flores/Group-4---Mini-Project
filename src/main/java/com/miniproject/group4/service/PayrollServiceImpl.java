package com.miniproject.group4.service;

import com.miniproject.group4.exception.RecordNotFoundException;
import com.miniproject.group4.model.Payroll;
import com.miniproject.group4.repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
    public List<Payroll> getAllPayroll() {
        return payrollRepository.findAll();
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
