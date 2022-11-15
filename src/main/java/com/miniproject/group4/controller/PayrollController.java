package com.miniproject.group4.controller;

import com.miniproject.group4.exception.RecordNotFoundException;
import com.miniproject.group4.model.Payroll;
import com.miniproject.group4.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payrolls")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    @GetMapping
    public ResponseEntity<List<Payroll>> getAllPayroll(){
        return new ResponseEntity<>(payrollService.getAllPayroll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payroll> getPayrollById(@PathVariable Long id) throws RecordNotFoundException {
        return new ResponseEntity<>(payrollService.getPayrollById(id), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Payroll> savePayroll(@RequestBody Payroll payroll){
        return new ResponseEntity<>(payrollService.savePayroll(payroll), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payroll> updatePayroll(@RequestBody Payroll payroll, @PathVariable Long id) throws RecordNotFoundException {
        return new ResponseEntity<>(payrollService.updatePayroll(payroll, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayroll(@PathVariable Long id) throws RecordNotFoundException {
        payrollService.deletePayroll(id);
        return ResponseEntity.noContent().build();
    }
}
