package com.alpha.graduatesmeeting.api.usersservice.services.impl;

import com.alpha.graduatesmeeting.api.usersservice.data.UserEntity;
import com.alpha.graduatesmeeting.api.usersservice.data.UserRepository;
import com.alpha.graduatesmeeting.api.usersservice.services.UsersService;
import com.alpha.graduatesmeeting.api.usersservice.shared.UserDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Service
public class UsersServiceImplementation implements UsersService {

    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public UsersServiceImplementation(
            UserRepository userRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        userDTO.setCreatedAt(Date.from(Instant.now()));
        userDTO.setUpdatedAt(Date.from(Instant.now()));
        userDTO.setUserId(UUID.randomUUID().toString());
        userDTO.setEncryptedPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        userRepository.save(userEntity);
        UserDTO createdUser = modelMapper.map(userEntity, UserDTO.class);
        return createdUser;
    }

    @Override
    public UserDTO getUserDetailsByUserId(String userId) {
        UserEntity entity = userRepository.findByUserId(userId);
        if(entity == null) throw new UsernameNotFoundException(userId);
        return new ModelMapper().map(entity, UserDTO.class);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity entity = userRepository.findByUserIdOrEmailOrPhone(s,s,s);
        if(entity == null) throw new UsernameNotFoundException(s);

        return new User(
                entity.getUserId(),
                entity.getEncryptedPassword(),
                true,
                true,
                true,
                true,
                new ArrayList<>()
        );
    }



}
