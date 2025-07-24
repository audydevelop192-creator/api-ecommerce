package com.gymshark.tokogym.controller;

import com.gymshark.tokogym.action.ActionFactory;
import com.gymshark.tokogym.constant.ActionConstant;
import com.gymshark.tokogym.dto.ActionDto;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {


    private final ActionFactory actionFactory = new ActionFactory();

    @PostMapping("/register")
    public DefaultResponse register(@RequestBody String request) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        return actionFactory.process(ActionConstant.REGISTER, actionDto);
    }

    @PostMapping("/login")
    public DefaultResponse login(@RequestBody String request) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        return actionFactory.process(ActionConstant.LOGIN, actionDto);
    }

    @GetMapping("/profile")
    public DefaultResponse profile(@RequestBody String request,
                                   @RequestHeader(name = "x-auth", required = false)
                                   String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.PROFILE, actionDto);
    }


}
