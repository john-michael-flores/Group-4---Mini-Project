package com.miniproject.group4.service;

import com.miniproject.group4.enums.Message;
import com.miniproject.group4.enums.PayrollTypes;
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
                .orElseThrow(()-> new RecordNotFoundException(Message.PAYROLL_NOT_FOUND.getMessage()));
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
                .orElseThrow(() -> new RecordNotFoundException(Message.PAYROLL_NOT_FOUND.getMessage()));
        payrollObj.setName(payroll.getName());
        payrollObj.setBasicPay(payroll.getBasicPay());
        payrollObj.setAllowances(payroll.getAllowances());
        payrollObj.setType(payroll.getType());
        return payrollRepository.save(payrollObj);
    }

    @Override
    public void deletePayroll(Long id) throws RecordNotFoundException {
        Payroll payroll = payrollRepository.findById(id)
                .orElseThrow(()-> new RecordNotFoundException(Message.PAYROLL_NOT_FOUND.getMessage()));
        payrollRepository.delete(payroll);
    }

    @Override
    public Page<Payroll> findByPayrollType(PayrollTypes type, Pageable pageable) {
        return new PageImpl<>(payrollRepository.findAll(pageable)
                .stream()
                .filter(payroll -> payroll.getType().equals(type))
                .toList());
    }
}
