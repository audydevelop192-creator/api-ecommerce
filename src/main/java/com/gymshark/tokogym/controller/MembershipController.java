package com.gymshark.tokogym.controller;

import com.gymshark.tokogym.action.ActionFactory;
import com.gymshark.tokogym.constant.ActionConstant;
import com.gymshark.tokogym.dto.ActionDto;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/membership")
public class MembershipController {

    private final ActionFactory actionFactory = new ActionFactory();

    @PostMapping("/membershipCreate")
    public DefaultResponse membershipCreate(@RequestBody String request,
                                            @RequestHeader(name = "x-auth", required = false)
                                            String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.MEMBERSHIP_CREATE, actionDto);
    }

    @GetMapping("/membershipList")
    public DefaultResponse membershipList(@RequestBody String request,
                                            @RequestHeader(name = "x-auth", required = false)
                                            String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.MEMBERSHIP_LIST, actionDto);
    }

    @PostMapping("/membershipDelete")
    public DefaultResponse membershipDelete(@RequestBody String request,
                                            @RequestHeader(name = "x-auth", required = false)
                                            String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.MEMBERSHIP_DELETE, actionDto);
    }

    @PostMapping("/membershipUpdate")
    public DefaultResponse membershipUpdate(@RequestBody String request,
                                            @RequestHeader(name = "x-auth", required = false)
                                            String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.MEMBERSHIP_UPDATE, actionDto);
    }
}
