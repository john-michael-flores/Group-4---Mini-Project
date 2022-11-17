package com.miniproject.group4;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniproject.group4.model.Payroll;
import com.miniproject.group4.enums.PayrollTypes;
import com.miniproject.group4.service.PayrollServiceImpl;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringBootTest(classes = Group4Application.class)
public class PayrollControllerTest {
    @MockBean
    private PayrollServiceImpl payrollService;


    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    Payroll payroll1, payroll2;

    static final String CLIENT_ID = "devglan-client";
    static final String CLIENT_SECRET = "devglan-secret";
    static final String GRANT_TYPE_PASSWORD = "password";

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .addFilter(springSecurityFilterChain).build();

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

    private String getAccessToken(String username, String password) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", GRANT_TYPE_PASSWORD);
        params.add("client_id", CLIENT_ID);
        params.add("username", username);
        params.add("password", password);

        ResultActions result
                = mockMvc.perform(post("/oauth/token")
                        .params(params)
                        .with(httpBasic(CLIENT_ID, CLIENT_SECRET))
                        .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    @Test
    @DisplayName("" +
            "Given a get request with mapping of /payrolls," +
            "When payrollController execute a get request " +
            "Then response should return unauthorized or 401 status")
    public void givenNoToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/payrolls"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("" +
            "Given a post request with mapping of /payrolls," +
            "When payrollController execute a post request " +
            "Then response should return forbidden or 403 status")
    public void givenNotAuthorizedRole_whenPostSecureRequest_thenForbidden() throws Exception {
        final String accessToken = getAccessToken("Michael", "password");
        mockMvc.perform(post("/payrolls")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payroll1)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("" +
            "Given a post request with mapping of /payrolls," +
            "When payrollController execute payrollService.savePayroll(payroll1) " +
            "Then response should return json of payroll1")
    void savePayroll() throws Exception {

        //Arrange
        final String accessToken = getAccessToken("Dondon", "password");
        when(payrollService.savePayroll(any(Payroll.class))).thenReturn(payroll1);

        //Act
        mockMvc.perform(post("/payrolls")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payroll1)))
                .andExpect(status().isCreated())
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
    void getAllPayrollByUser() throws Exception {
        //Arrange
        final String accessToken = getAccessToken("Michael", "password");
        when(payrollService.getAllPayroll(PageRequest.of(0, 20))).thenReturn(new PageImpl<>(List.of(payroll1, payroll2)));

        //Act
        mockMvc.perform(get("/payrolls")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", CoreMatchers.is(List.of(payroll1, payroll2).size())))
                .andExpect(jsonPath("$.content[0].name", CoreMatchers.is(payroll1.getName())))
                .andExpect(jsonPath("$.content[1].name", CoreMatchers.is(payroll2.getName())));
    }

    @Test
    @DisplayName("" +
            "Given a get request with mapping of /payrolls," +
            "When payrollController execute payrollService.getAllPayroll() " +
            "Then response should return json of payroll1, payroll2")
    void getAllPayrollByAdmin() throws Exception {
        //Arrange
        final String accessToken = getAccessToken("Dondon", "password");
        when(payrollService.getAllPayroll(PageRequest.of(0, 20))).thenReturn(new PageImpl<>(List.of(payroll1, payroll2)));

        //Act
        mockMvc.perform(get("/payrolls")
                        .header("Authorization", "Bearer " + accessToken)
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
        final String accessToken = getAccessToken("Michael", "password");
        when(payrollService.getPayrollById(anyLong())).thenReturn(payroll1);

        //Act
        mockMvc.perform(get("/payrolls/{id}", 1L)
                        .header("Authorization", "Bearer " + accessToken)
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
        final String accessToken = getAccessToken("Dondon", "password");
        payroll2.setAllowances(BigDecimal.valueOf(7090));
        when(payrollService.updatePayroll(any(Payroll.class), anyLong())).thenReturn(payroll2);

        //Act
        mockMvc.perform(put("/payrolls/{id}", 2L)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payroll2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.allowances", CoreMatchers.is(payroll2.getAllowances().intValue())));
    }

    @Test
    @DisplayName("" +
            "Given a delete request with mapping of /payrolls/1," +
            "When payrollController execute payrollService.deletePayroll() " +
            "Then response should response of 200")
    void deletePayroll() throws Exception {
        //Arrange
        final String accessToken = getAccessToken("Dondon", "password");
        doNothing().when(payrollService).deletePayroll(payroll1.getId());
        //Act
        mockMvc.perform(delete("/payrolls/{id}", 1L)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
