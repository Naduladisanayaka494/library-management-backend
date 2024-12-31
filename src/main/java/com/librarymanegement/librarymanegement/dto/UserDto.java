package com.librarymanegement.librarymanegement.dto;

import com.librarymanegement.librarymanegement.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {



    private Long id;
    private String name;
    private String email;

    private UserRole userRole;

}
