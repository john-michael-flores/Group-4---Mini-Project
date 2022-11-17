package com.miniproject.group4.model;

import com.miniproject.group4.enums.PayrollTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Payroll {

    /*
    - Name - String
    - Basic Pay - Numeric
    - Allowances - Numeric
    - Type (Rank and File, Managerial) - String
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private BigDecimal basicPay;
    @Column
    private BigDecimal allowances;
    @Enumerated(value = EnumType.STRING)
    private PayrollTypes type;
}
