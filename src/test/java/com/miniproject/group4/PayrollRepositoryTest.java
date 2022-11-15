package com.miniproject.group4;

import com.miniproject.group4.enums.PayrollTypes;
import com.miniproject.group4.model.Payroll;
import com.miniproject.group4.repository.PayrollRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PayrollRepositoryTest {
    
    @Autowired
    private PayrollRepository payrollRepository;

    @Test
    @DisplayName("" +
            "Given " +
            "When " +
            "Then ")
    public void testFindAll() {
        //ARRANGE

        List<Payroll> expected = List.of(
                new Payroll(1L,"Name", new BigDecimal(50000), new BigDecimal(1500), PayrollTypes.RANK_AND_FILE),
                new Payroll(2L,"Name1", new BigDecimal(150000), new BigDecimal(5500), PayrollTypes.MANAGERIAL),
                new Payroll(3L,"Name2", new BigDecimal(40000), new BigDecimal(1000), PayrollTypes.RANK_AND_FILE)
        );
        payrollRepository.saveAll(expected);
        //ACT
        List<Payroll> result = payrollRepository.findAll();
        //ASSERT
        assertEquals(expected, result);

    }

    @Test
    @DisplayName("" +
            "Given " +
            "When " +
            "Then ")
    public void testFindById() {
        //ARRANGE
        Payroll expected = new Payroll(1L,"Name", new BigDecimal(50000), new BigDecimal(1500), PayrollTypes.RANK_AND_FILE);
        payrollRepository.save(expected);
        //ACT
        Payroll result = payrollRepository.findById(1L).get();
        //ASSERT
        assertEquals(expected, result);

    }

    @Test
    @DisplayName("" +
            "Given " +
            "When " +
            "Then ")
    public void testSave() {
        //ARRANGE
        Payroll expected = new Payroll(1L,"Name", new BigDecimal(50000), new BigDecimal(1500), PayrollTypes.RANK_AND_FILE);
        //ACT
        Payroll result = payrollRepository.save(expected);
        //ASSERT
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("" +
            "Given " +
            "When " +
            "Then ")
    public void testDelete() {
        //ARRANGE
        Payroll expected = new Payroll(1L,"Name", new BigDecimal(50000), new BigDecimal(1500), PayrollTypes.RANK_AND_FILE);
        payrollRepository.save(expected);
        //ACT
        payrollRepository.delete(expected);
        Optional<Payroll> result = payrollRepository.findById(1L);
        //ASSERT
        assertEquals(Optional.empty(),result);
    }
}