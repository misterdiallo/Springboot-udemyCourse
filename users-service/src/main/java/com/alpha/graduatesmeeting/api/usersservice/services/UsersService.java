package com.alpha.graduatesmeeting.api.usersservice.services;

import com.alpha.graduatesmeeting.api.usersservice.shared.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO getUserDetailsByUserId(String userId);

}
