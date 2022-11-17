package com.miniproject.group4;

import com.miniproject.group4.enums.PayrollTypes;
import com.miniproject.group4.exception.RecordNotFoundException;
import com.miniproject.group4.model.Payroll;
import com.miniproject.group4.repository.PayrollRepository;
import com.miniproject.group4.service.PayrollServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PayrollServiceImplTest {

    @Mock
    private PayrollRepository payrollRepository;

    @InjectMocks
    private PayrollServiceImpl payrollService;


    private Payroll payroll1 = new Payroll(1L, "Dondon", new BigDecimal(30000), new BigDecimal(1500), PayrollTypes.MANAGERIAL);
    private Payroll payroll2 = new Payroll(2L, "Michael", new BigDecimal(50000), new BigDecimal(2500), PayrollTypes.RANK_AND_FILE);
    private Payroll payroll3 = new Payroll(3L, "Vonn", new BigDecimal(40000), new BigDecimal(2500), PayrollTypes.RANK_AND_FILE);
    private Payroll payroll4 = new Payroll(4L, "Marvin", new BigDecimal(60000), new BigDecimal(3500), PayrollTypes.MANAGERIAL);
    private Payroll payroll5 = new Payroll(5L, "Ali", new BigDecimal(45000), new BigDecimal(2500), PayrollTypes.RANK_AND_FILE);
    private List<Payroll> allPayroll;
    @BeforeEach
    public void setup(){
        allPayroll = List.of(payroll1, payroll2, payroll3, payroll4, payroll5);
    }


    //payroll1(1L, "Dondon", new BigDecimal(30000), new BigDecimal(1500), PayrollTypes.MANAGERIAL);
    //payroll2(2L, "Michael", new BigDecimal(50000), new BigDecimal(2500), PayrollTypes.RANK_AND_FILE);
    //payroll3(3L, "Vonn", new BigDecimal(40000), new BigDecimal(2500), PayrollTypes.RANK_AND_FILE);
    //payroll4(4L, "Marvin", new BigDecimal(60000), new BigDecimal(3500), PayrollTypes.MANAGERIAL);
    //payroll5(5L, "Ali", new BigDecimal(45000), new BigDecimal(2500), PayrollTypes.RANK_AND_FILE);
    @Test
    @DisplayName("" +
            "Given Payroll with the setup above " +
            "When getPayrollById(Long) is executed " +
            "Then result should return payroll2")
    public void getPayrollById() throws RecordNotFoundException {
        //ARRANGE
        when(payrollRepository.findById(anyLong())).thenReturn(Optional.ofNullable(payroll2));
        //ACT
        Payroll result = payrollService.getPayrollById(2L);
        //ASSERT
        //check if findById is use;
        verify(payrollRepository).findById(anyLong());
        assertEquals(payroll2, result);

    }

    //payroll1(1L, "Dondon", new BigDecimal(30000), new BigDecimal(1500), PayrollTypes.MANAGERIAL);
    //payroll2(2L, "Michael", new BigDecimal(50000), new BigDecimal(2500), PayrollTypes.RANK_AND_FILE);
    //payroll3(3L, "Vonn", new BigDecimal(40000), new BigDecimal(2500), PayrollTypes.RANK_AND_FILE);
    //payroll4(4L, "Marvin", new BigDecimal(60000), new BigDecimal(3500), PayrollTypes.MANAGERIAL);
    //payroll5(5L, "Ali", new BigDecimal(45000), new BigDecimal(2500), PayrollTypes.RANK_AND_FILE);
    @Test
    @DisplayName("" +
            "Given Payroll with the setup above " +
            "When savePayroll(Payroll.class) is executed " +
            "Then result should return payroll5")
    public void savePayroll() {
        //ARRANGE
        when(payrollRepository.save(any(Payroll.class))).thenReturn(payroll5);
        //ACT
        Payroll result = payrollService.savePayroll(payroll5);
        //ASSERT
        verify(payrollRepository).save(any(Payroll.class));
        assertEquals(payroll5, result);

    }

    //payroll1(1L, "Dondon", new BigDecimal(30000), new BigDecimal(1500), PayrollTypes.MANAGERIAL);
    //payroll2(2L, "Michael", new BigDecimal(50000), new BigDecimal(2500), PayrollTypes.RANK_AND_FILE);
    //payroll3(3L, "Vonn", new BigDecimal(40000), new BigDecimal(2500), PayrollTypes.RANK_AND_FILE);
    //payroll4(4L, "Marvin", new BigDecimal(60000), new BigDecimal(3500), PayrollTypes.MANAGERIAL);
    //payroll5(5L, "Ali", new BigDecimal(45000), new BigDecimal(2500), PayrollTypes.RANK_AND_FILE);
    @Test
    @DisplayName("" +
            "Given Payroll with the setup above " +
            "When getAllPayroll() is executed " +
            "Then result should return payroll1, payroll2, payroll3, payroll4, payroll5")
    public void getAllPayroll() {
        //ARRANGE
        when(payrollRepository.findAll(PageRequest.of(0, 20))).thenReturn(new PageImpl<>(allPayroll));
        //ACT
        Page<Payroll> result = payrollService.getAllPayroll(PageRequest.of(0, 20));
        //ASSERT
        verify(payrollRepository).findAll(PageRequest.of(0, 20));
        assertEquals(allPayroll, result.getContent());

    }

    //payroll1(1L, "Dondon", new BigDecimal(30000), new BigDecimal(1500), PayrollTypes.MANAGERIAL);
    //payroll2(2L, "Michael", new BigDecimal(50000), new BigDecimal(2500), PayrollTypes.RANK_AND_FILE);
    //payroll3(3L, "Vonn", new BigDecimal(40000), new BigDecimal(2500), PayrollTypes.RANK_AND_FILE);
    //payroll4(4L, "Marvin", new BigDecimal(60000), new BigDecimal(3500), PayrollTypes.MANAGERIAL);
    //payroll5(5L, "Ali", new BigDecimal(45000), new BigDecimal(2500), PayrollTypes.RANK_AND_FILE);
    @Test
    @DisplayName("" +
            "Given Payroll with the setup above " +
            "When updatePayroll(Payroll, Long) is executed " +
            "Then result should return updated payroll3")
    public void updatePayroll() throws RecordNotFoundException {
        //ARRANGE
        Payroll update = payroll3;
        update.setName("updateName");
        update.setType(PayrollTypes.MANAGERIAL);
        // Mocking payroll methods for service method dependency
        when(payrollRepository.findById(anyLong())).thenReturn(Optional.ofNullable(payroll3));
        when(payrollRepository.save(any(Payroll.class))).thenReturn(update);
        //ACT
        Payroll result = payrollService.updatePayroll(update, 3L);
        //ASSERT
        // verify if payrollRepository.save() is use
        verify(payrollRepository).save(any(Payroll.class));
        assertEquals(payroll3, result);
    }

    //payroll1(1L, "Dondon", new BigDecimal(30000), new BigDecimal(1500), PayrollTypes.MANAGERIAL);
    //payroll2(2L, "Michael", new BigDecimal(50000), new BigDecimal(2500), PayrollTypes.RANK_AND_FILE);
    //payroll3(3L, "Vonn", new BigDecimal(40000), new BigDecimal(2500), PayrollTypes.RANK_AND_FILE);
    //payroll4(4L, "Marvin", new BigDecimal(60000), new BigDecimal(3500), PayrollTypes.MANAGERIAL);
    //payroll5(5L, "Ali", new BigDecimal(45000), new BigDecimal(2500), PayrollTypes.RANK_AND_FILE);
    @Test
    @DisplayName("" +
            "Given Payroll with the setup above " +
            "When deletePayroll() is executed " +
            "Then Mockito should verify if the method was executed")
    public void deletePayroll() throws RecordNotFoundException {
        //ARRANGE
        // Mocking payroll methods for service method dependency
        when(payrollRepository.findById(anyLong())).thenReturn(Optional.of(payroll2));

        //ACT
        payrollService.deletePayroll(payroll2.getId());

        //ASSERT
        // verify if payroll2 is deleted
        verify(payrollRepository).delete(payroll2);
    }

}