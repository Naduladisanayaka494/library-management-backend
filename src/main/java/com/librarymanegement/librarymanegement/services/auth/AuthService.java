package com.librarymanegement.librarymanegement.services.auth;


import com.librarymanegement.librarymanegement.dto.SignUpRequest;
import com.librarymanegement.librarymanegement.dto.UserDto;

public interface AuthService {
    UserDto createdAdmin(SignUpRequest signuprequest);
    boolean hasAdminwithemail(String email);
}
