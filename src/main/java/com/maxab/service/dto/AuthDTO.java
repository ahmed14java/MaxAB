package com.maxab.service.dto;

import lombok.Data;

@Data
public class AuthDTO {
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private int statusCode;
    private String status;
}
