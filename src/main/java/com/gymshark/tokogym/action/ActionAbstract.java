package com.gymshark.tokogym.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymshark.tokogym.dto.ActionDto;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.util.JwtUtil;

import java.util.ArrayList;
import java.util.List;

//ini adalah tamplate untuk sub classnya
public abstract class ActionAbstract<R> {

    //ipe data request yang dikirim ke action ini
    private final Class<R> requestClass;
    //digunakan untuk mengubah JSON ke objek Java
    private final ObjectMapper objectMapper = new ObjectMapper();

    //Ini constructor untuk menyimpan class dari tipe data request yang nanti akan dipakai untuk convert JSON
    protected ActionAbstract(Class<R> requestClass) {
        this.requestClass = requestClass;
    }

    //Apakah action ini butuh login
    protected boolean isLogin() {
        return true;
    }

    //Siapa aja yang boleh akses action ini (berdasarkan role)
    protected List<Integer> allowedUser() {
        return new ArrayList<>();
    }


    public DefaultResponse proses(ActionDto actionDto) {
        DefaultResponse defaultResponse = new DefaultResponse();
        try {
            //Ini mengubah JSON string (yang dikirim user) jadi objek Java (request)
            R request = objectMapper.readValue(actionDto.getData(), requestClass);

            //cek login
            if (isLogin()) {
                JwtUtil jwtUtil = new JwtUtil();
                if (!jwtUtil.isTokenValid(actionDto.getToken())) {
                    defaultResponse.setMessage("Token tidak valid");
                    return defaultResponse;
                }

                //cek rolenya apa
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

    //Kalau butuh login
    protected  DefaultResponse execute(AuthDto authDto, R request){
        return new DefaultResponse();
    };

    //Kalau tidak butuh login
    protected  DefaultResponse execute(R request){
        return new DefaultResponse();
    }
}
