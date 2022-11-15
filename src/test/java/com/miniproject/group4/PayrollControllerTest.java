package com.miniproject.group4;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniproject.group4.model.Payroll;
import com.miniproject.group4.enums.PayrollTypes;
import com.miniproject.group4.service.PayrollServiceImpl;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
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
        payroll1.setBasicPay(new BigDecimal(30000d));
        payroll1.setAllowances(new BigDecimal(3066d));
        payroll1.setType(PayrollTypes.RANK_AND_FILE);

        payroll2 = new Payroll();
        payroll2.setName("Itachi");
        payroll2.setBasicPay(new BigDecimal(23000d));
        payroll2.setAllowances(new BigDecimal(5080d));
        payroll2.setType(PayrollTypes.MANAGERIAL);
    }

    @Test
    @DisplayName("" +
            "Given a post request with mapping of /payrolls," +
            "When payrollController execute payrollService.savePayroll(payroll1) " +
            "Then response should return json of payroll1")
    void savePayroll() throws Exception {
        //Arrange
        when(payrollService.savePayroll(any(Payroll.class))).thenReturn(payroll1);

        //Act
        mockMvc.perform(post("/payrolls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payroll1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", CoreMatchers.is(payroll1.getName())))
                .andExpect(jsonPath("$.basicPay", CoreMatchers.is(payroll1.getBasicPay().intValue())))
                .andExpect(jsonPath("$.allowances", CoreMatchers.is(payroll1.getAllowances().intValue())))
                .andExpect(jsonPath("$.type", CoreMatchers.is(payroll1.getType().name())));

    }

    @Test
    @DisplayName("" +
            "Given a get request with mapping of /payrolls," +
            "When payrollController execute payrollService.getAllPayroll() " +
            "Then response should return json of payroll1, payroll2")
    void getAllPayroll() throws Exception {
        //Arrange
        when(payrollService.getAllPayroll(PageRequest.of(0, 20))).thenReturn(new PageImpl<>(List.of(payroll1, payroll2)));

        //Act
        mockMvc.perform(get("/payrolls")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", CoreMatchers.is(List.of(payroll1, payroll2).size())))
                .andExpect(jsonPath("$.content[0].name", CoreMatchers.is(payroll1.getName())))
                .andExpect(jsonPath("$.content[1].name", CoreMatchers.is(payroll2.getName())));
    }

    @Test
    @DisplayName("" +
            "Given a get request with mapping of /payrolls/1," +
            "When payrollController execute payrollService.getPayrollById(1) " +
            "Then response should return json of payroll1")
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
    @DisplayName("" +
            "Given a put request with mapping of /payrolls/2," +
            "When payrollController execute payrollService.updatePayroll(payroll2) " +
            "Then response should return json of updated payroll2")
    void updatePayroll() throws Exception {
        //Arrange
        payroll2.setAllowances(BigDecimal.valueOf(7090d));
        when(payrollService.updatePayroll(any(Payroll.class), anyLong())).thenReturn(payroll2);

        //Act
        mockMvc.perform(put("/payrolls/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payroll2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.allowances", CoreMatchers.is(payroll2.getAllowances().doubleValue())));
    }

    @Test
    @DisplayName("" +
            "Given a delete request with mapping of /payrolls/1," +
            "When payrollController execute payrollService.deletePayroll() " +
            "Then response should response of 204")
    void deletePayroll() throws Exception {
        //Arrange
        doNothing().when(payrollService).deletePayroll(payroll1.getId());
        //Act
        mockMvc.perform(delete("/payrolls/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
