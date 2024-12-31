package com.librarymanegement.librarymanegement.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String email;
    private String password;


}
