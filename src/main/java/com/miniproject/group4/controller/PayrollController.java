package com.miniproject.group4.controller;

import com.miniproject.group4.enums.Message;
import com.miniproject.group4.enums.PayrollTypes;
import com.miniproject.group4.exception.RecordNotFoundException;
import com.miniproject.group4.model.Payroll;
import com.miniproject.group4.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payrolls")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    @GetMapping
    public ResponseEntity<Page<Payroll>> getAllPayroll(Pageable pageable){
        return new ResponseEntity<>(payrollService.getAllPayroll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payroll> getPayrollById(@PathVariable Long id) throws RecordNotFoundException {
        return new ResponseEntity<>(payrollService.getPayrollById(id), HttpStatus.OK);

    }

    @GetMapping("/type/{type}")
    public ResponseEntity<Page<Payroll>> getPayrollByType(@PathVariable PayrollTypes type, Pageable pageable){
        return new ResponseEntity<>(payrollService.findByPayrollType(type, pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Payroll> savePayroll(@RequestBody Payroll payroll){
        return new ResponseEntity<>(payrollService.savePayroll(payroll), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payroll> updatePayroll(@RequestBody Payroll payroll, @PathVariable Long id) throws RecordNotFoundException {
        return new ResponseEntity<>(payrollService.updatePayroll(payroll, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayroll(@PathVariable Long id) throws RecordNotFoundException {
        payrollService.deletePayroll(id);
        return new ResponseEntity<>(Message.PAYROLL_DELETE.getMessage().formatted(id), HttpStatus.OK);
    }
}
