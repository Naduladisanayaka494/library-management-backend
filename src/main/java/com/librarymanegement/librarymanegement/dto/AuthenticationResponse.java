package com.librarymanegement.librarymanegement.dto;


import com.librarymanegement.librarymanegement.enums.UserRole;
import lombok.Data;


@Data
public class AuthenticationResponse {
    private String jwt;
    private UserRole userRole;
    private Long userId;
}