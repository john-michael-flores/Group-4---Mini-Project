package com.miniproject.group4;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniproject.group4.model.Payroll;
import com.miniproject.group4.enums.PayrollTypes;
import com.miniproject.group4.service.PayrollServiceImpl;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class PayrollControllerTest {
    @MockBean
    private PayrollServiceImpl payrollService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    Payroll payroll1, payroll2;

    @BeforeEach
    void setup() {
        payroll1 = new Payroll();
        payroll1.setName("Kaneki");
        payroll1.setBasicPay(BigDecimal.valueOf(30000));
        payroll1.setAllowances(BigDecimal.valueOf(3066));
        payroll1.setType(PayrollTypes.RANK_AND_FILE);

        payroll2 = new Payroll();
        payroll2.setName("Itachi");
        payroll2.setBasicPay(BigDecimal.valueOf(23000d));
        payroll2.setAllowances(BigDecimal.valueOf(5080d));
        payroll2.setType(PayrollTypes.MANAGERIAL);
    }

    @Test
    void savePayroll() throws Exception {
        //Arrange
        when(payrollService.savePayroll(any(Payroll.class))).thenReturn(payroll1);

        //Act
        mockMvc.perform(post("/payrolls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payroll1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", CoreMatchers.is(payroll1.getName())))
                .andExpect(jsonPath("$.basicPay", CoreMatchers.is(payroll1.getBasicPay())))
                .andExpect(jsonPath("$.allowances", CoreMatchers.is(payroll1.getAllowances())))
                .andExpect(jsonPath("$.type", CoreMatchers.is(payroll1.getType().name())));

    }

    @Test
    void getAllPayroll() throws Exception {
        //Arrange
        when(payrollService.getAllPayroll()).thenReturn(List.of(payroll1, payroll2));

        //Act
        mockMvc.perform(get("/payrolls")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", CoreMatchers.is(List.of(payroll1, payroll2).size())))
                .andExpect(jsonPath("$.[0].name", CoreMatchers.is(payroll1.getName())))
                .andExpect(jsonPath("$.[1].name", CoreMatchers.is(payroll2.getName())));
    }

    @Test
    void getPayrollById() throws Exception {
        //Arrange
        when(payrollService.getPayrollById(anyLong())).thenReturn(payroll1);

        //Act
        mockMvc.perform(get("/payrolls/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", CoreMatchers.is(payroll1.getName())));
    }

    @Test
    void updatePayroll() throws Exception {
        //Arrange
        payroll2.setAllowances(BigDecimal.valueOf(7090d));
        when(payrollService.updatePayroll(any(Payroll.class), anyLong())).thenReturn(payroll2);

        //Act
        mockMvc.perform(put("/payrolls/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payroll2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.allowances", CoreMatchers.is(payroll2.getAllowances())));
    }

    @Test
    void deletePayroll() throws Exception {
        //Arrange
        doNothing().when(payrollService).deletePayroll(payroll1.getId());

        //Act
        mockMvc.perform(delete("/payrolls/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
