package com.gymshark.tokogym.action;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymshark.tokogym.dto.ActionDto;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.util.JwtUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class ActionAbstract<R> {

    private final Class<R> requestClass;
    private final ObjectMapper objectMapper = new ObjectMapper();

    protected ActionAbstract(Class<R> requestClass) {
        this.requestClass = requestClass;
    }

    protected boolean isLogin() {
        return true;
    }

    protected List<Integer> allowedUser() {
        return new ArrayList<>();
    }
    public DefaultResponse proses(ActionDto actionDto) {
        DefaultResponse defaultResponse = new DefaultResponse();
        try {
            R request = objectMapper.readValue(actionDto.getData(), requestClass);

            if (isLogin()) {
                JwtUtil jwtUtil = new JwtUtil();

                if (!jwtUtil.isTokenValid(actionDto.getToken())) {
                    defaultResponse.setMessage("Token tidak valid");
                    return defaultResponse;
                }
                AuthDto authDto = objectMapper.readValue(jwtUtil.extractUsername(actionDto.getToken()), AuthDto.class);
                if (!allowedUser().isEmpty()) {
                    if (!allowedUser().contains(authDto.getRole())) {
                        defaultResponse.setMessage("Akses tidak valid");
                        return defaultResponse;
                    }
                }
                return execute(authDto,request);
            }
            return execute(request);

        } catch (Exception e) {
            defaultResponse.setMessage("Terjadi kesalahan saat memproses data: " + e.getMessage());
            return defaultResponse;
        }
    }

    protected  DefaultResponse execute(AuthDto authDto, R request){
        return new DefaultResponse();
    };

    protected  DefaultResponse execute(R request){
        return new DefaultResponse();
    }
}
