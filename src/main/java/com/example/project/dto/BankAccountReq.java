package com.example.project.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BankAccountReq {
    private String accountNumber;
    private String accountHolder;
    private String bankName;
   
}
