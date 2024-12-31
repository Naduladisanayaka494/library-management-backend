package com.librarymanegement.librarymanegement.services.auth;

import com.librarymanegement.librarymanegement.dto.SignUpRequest;
import com.librarymanegement.librarymanegement.dto.UserDto;
import com.librarymanegement.librarymanegement.entity.User;
import com.librarymanegement.librarymanegement.enums.UserRole;
import com.librarymanegement.librarymanegement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;

    @Override
    public UserDto createdAdmin(SignUpRequest signuprequest) {
        User user = new User();
        user.setUsername(signuprequest.getName());
        user.setEmail(signuprequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signuprequest.getPassword()));
        user.setUserRole(UserRole.Admin);
        User createduser  =userRepository.save(user);
        UserDto userdto = new UserDto();
        userdto.setId(createduser.getId());
        return userdto;
    }



    @Override
    public boolean hasAdminwithemail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
