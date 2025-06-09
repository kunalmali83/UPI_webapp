package com.example.project.dto;

import lombok.Data;


@Data
public class UserSignUpReq {

	
	private String name;
    private String email;
    private String upiId;
    private String password;
    private String address;
    private String mobileNumber;
    private String accountNumber;
}
