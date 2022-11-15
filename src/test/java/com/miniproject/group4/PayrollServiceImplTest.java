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

    @Test
    @DisplayName("" +
            "Given " +
            "When " +
            "Then ")
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

    @Test
    @DisplayName("" +
            "Given " +
            "When " +
            "Then ")
    public void savePayroll() {
        //ARRANGE
        when(payrollRepository.save(any(Payroll.class))).thenReturn(payroll5);
        //ACT
        Payroll result = payrollService.savePayroll(payroll5);
        //ASSERT
        verify(payrollRepository).save(any(Payroll.class));
        assertEquals(payroll5, result);

    }

    @Test
    @DisplayName("" +
            "Given " +
            "When " +
            "Then ")
    public void getAllPayroll() {
        //ARRANGE
        when(payrollRepository.findAll()).thenReturn(allPayroll);
        //ACT
        List<Payroll> result = payrollService.getAllPayroll();
        //ASSERT
        verify(payrollRepository).findAll();
        assertEquals(allPayroll, result);

    }

    @Test
    @DisplayName("" +
            "Given " +
            "When " +
            "Then ")
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

    @Test
    @DisplayName("" +
            "Given " +
            "When " +
            "Then ")
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