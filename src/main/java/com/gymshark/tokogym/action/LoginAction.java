package com.gymshark.tokogym.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymshark.tokogym.dao.UserDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.LoginRequest;
import com.gymshark.tokogym.dto.request.RegisterRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.LoginResponse;
import com.gymshark.tokogym.dto.response.RegisterResponse;
import com.gymshark.tokogym.model.User;
import com.gymshark.tokogym.util.JwtUtil;
import com.gymshark.tokogym.util.SHA256Util;
import lombok.SneakyThrows;

public class LoginAction extends ActionAbstract<LoginRequest>{

    private static final UserDao userDao = new UserDao();

    @Override
    protected boolean isLogin() {
        return false;
    }

    protected LoginAction() {
        super(LoginRequest.class);
    }

    @SneakyThrows
    @Override
    protected DefaultResponse execute(LoginRequest request) {
        LoginResponse loginResponse = new LoginResponse();
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            loginResponse.setMessage("Email Wajib Diisi");
            return loginResponse;
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            loginResponse.setMessage("Password Wajib Diisi");
            return loginResponse;
        }

        User user = userDao.findUserByEmail(request.getEmail());
        if (user == null){
            loginResponse.setMessage("Email Tidak Ditemukan");
            return loginResponse;
        }
         String password = SHA256Util.encrypt(request.getPassword());
        if (!password.equals(user.getPassword())){
            loginResponse.setMessage("Password Salah");
            return loginResponse;
        }

        AuthDto authDto = new AuthDto();
        authDto.setEmail(user.getEmail());
        authDto.setRole(user.getRole());
        authDto.setUserId(user.getUserId());
        ObjectMapper objectMapper = new ObjectMapper();
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateToken(objectMapper.writeValueAsString(authDto));
        loginResponse.setMessage("Berhasil Login");
        loginResponse.setToken(token);
        return loginResponse;
    }
}
