package com.example.project.dto;

import java.util.List;

import lombok.Data;


@Data
public class UserSignUpReq {

	
	private String name;
    private String email;
    private String password;
    private String address;
    private String mobileNumber;
    private String accountNumber;
    private String accountHolder;
    private String bankName; 
    private List<BankAccountReq> accounts;


}
