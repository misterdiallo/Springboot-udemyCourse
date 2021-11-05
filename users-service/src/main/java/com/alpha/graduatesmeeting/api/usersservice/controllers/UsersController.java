package com.alpha.graduatesmeeting.api.usersservice.controllers;

import com.alpha.graduatesmeeting.api.usersservice.data.UserEntity;
import com.alpha.graduatesmeeting.api.usersservice.model.CreateUserRequestModel;
import com.alpha.graduatesmeeting.api.usersservice.model.CreateUserResponseModel;
import com.alpha.graduatesmeeting.api.usersservice.model.LoginUserRequestModel;
import com.alpha.graduatesmeeting.api.usersservice.model.LoginUserResponseModel;
import com.alpha.graduatesmeeting.api.usersservice.services.UsersService;
import com.alpha.graduatesmeeting.api.usersservice.shared.UserDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private Environment env;

    @Autowired
    UsersService usersService;

    @GetMapping(
            path = "/check/status"
    )
    public String CheckStatus() {

        return "USERS services working on port: " + env.getProperty("local.server.port") + ", with token = " + env.getProperty("token.secret");
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE} ,
            consumes = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CreateUserResponseModel> addUser(@Valid @RequestBody CreateUserRequestModel createUserRequestModel) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDTO userDTO = modelMapper.map(createUserRequestModel, UserDTO.class);
        UserDTO createUser = usersService.createUser(userDTO);
        CreateUserResponseModel responseModel = modelMapper.map(createUser, CreateUserResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }


//    @PostMapping(path = "login")
//    public ResponseEntity<LoginUserResponseModel> login(@Valid @RequestBody LoginUserRequestModel loginUserRequestModel) {
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        UserDTO userDTO = modelMapper.map(loginUserRequestModel, UserDTO.class);
//        UserDetails loginUser = usersService.loadUserByUsername(loginUserRequestModel.getUserId());
//        userDTO = modelMapper.map(loginUser, UserDTO.class);
//        UserDTO responseModel0 = modelMapper.map(userDTO, UserDTO.class);
//        LoginUserResponseModel responseModel = modelMapper.map(userDTO, LoginUserResponseModel.class);
//        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
//    }

}
