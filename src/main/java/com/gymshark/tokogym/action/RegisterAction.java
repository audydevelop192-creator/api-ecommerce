package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.UserDao;
import com.gymshark.tokogym.dto.request.RegisterRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.RegisterResponse;
import com.gymshark.tokogym.model.User;
import com.gymshark.tokogym.util.SHA256Util;

public class RegisterAction extends ActionAbstract<RegisterRequest> {

    private static final UserDao userDao = new UserDao();

    protected RegisterAction() {
        super(RegisterRequest.class);
    }

    @Override
    protected boolean isLogin() {
        return false;
    }

    @Override
    protected DefaultResponse execute(RegisterRequest request) {
        RegisterResponse registerResponse = new RegisterResponse();
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            registerResponse.setMessage("Email Wajib Diisi");
            return registerResponse;
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            registerResponse.setMessage("Password Wajib Diisi");
            return registerResponse;
        }

        if (request.getName() == null || request.getName().isEmpty()){
            registerResponse.setMessage("Nama Wajib Diisi");
            return registerResponse;
        }

        boolean isExist = userDao.checkUserExist(request.getEmail());
        if (isExist){
            registerResponse.setMessage("Email Sudah Terdaftar");
            return registerResponse;
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(RolesConstant.CUSTOMER);
        user.setPassword(SHA256Util.encrypt(request.getPassword()));
        userDao.insertUser(user);
        registerResponse.setMessage("Berhasil Register");
        return registerResponse;
    }


}
