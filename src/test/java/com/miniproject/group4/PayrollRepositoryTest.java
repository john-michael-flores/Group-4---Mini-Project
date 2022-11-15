package com.miniproject.group4;

import com.miniproject.group4.enums.PayrollTypes;
import com.miniproject.group4.model.Payroll;
import com.miniproject.group4.repository.PayrollRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

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
            "Given interface PayrollRepository  " +
            "When testFindAll() is executed, payroll1, payroll2, payroll3 should be save to database " +
            "Then result should return payroll1, payroll2, payroll3")
    public void testFindAll() {
        //ARRANGE

        List<Payroll> expected = List.of(
                new Payroll(1L,"Name", new BigDecimal(50000), new BigDecimal(1500), PayrollTypes.RANK_AND_FILE),
                new Payroll(2L,"Name1", new BigDecimal(150000), new BigDecimal(5500), PayrollTypes.MANAGERIAL),
                new Payroll(3L,"Name2", new BigDecimal(40000), new BigDecimal(1000), PayrollTypes.RANK_AND_FILE)
        );
        payrollRepository.saveAll(expected);
        //ACT
        Page<Payroll> result = payrollRepository.findAll(PageRequest.of(0, 20));
        //ASSERT
        assertEquals(expected, result.getContent());

    }

    @Test
    @DisplayName("" +
            "Given interface PayrollRepository  " +
            "When testFindById() is executed, payroll1 should be save to database " +
            "Then result will return payroll1")
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
            "Given Payroll with the setup above " +
            "When testSave() is executed, payroll2 should be save to database " +
            "Then result will return payroll2")
    public void testSave() {
        //ARRANGE
        Payroll expected = new Payroll(2L,"Name", new BigDecimal(50000), new BigDecimal(1500), PayrollTypes.RANK_AND_FILE);
        //ACT
        Payroll result = payrollRepository.save(expected);
        //ASSERT
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("" +
            "Given Payroll with the setup above " +
            "When testDelete() is executed, payroll3 should be save to database " +
            "Then result payroll3 should be deleted and will return Optional.empty()")
    public void testDelete() {
        //ARRANGE
        Payroll expected = new Payroll(3L,"Name", new BigDecimal(50000), new BigDecimal(1500), PayrollTypes.RANK_AND_FILE);
        payrollRepository.save(expected);
        //ACT
        payrollRepository.deleteById(3L);
        Optional<Payroll> result = payrollRepository.findById(3L);
        //ASSERT
        assertEquals(Optional.empty(),result);
    }
}
