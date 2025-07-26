package com.gymshark.tokogym.controller;

import com.gymshark.tokogym.action.ActionFactory;
import com.gymshark.tokogym.constant.ActionConstant;
import com.gymshark.tokogym.dto.ActionDto;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final ActionFactory actionFactory = new ActionFactory();


    @PostMapping("/paymentCreate")
    public DefaultResponse paymentCreate(@RequestBody String request,
                                            @RequestHeader(name = "x-auth", required = false)
                                            String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.PAYMENT_CREATE, actionDto);
    }

    @GetMapping("/paymentList")
    public DefaultResponse paymentList(@RequestBody String request,
                                         @RequestHeader(name = "x-auth", required = false)
                                         String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.PAYMENT_LIST, actionDto);
    }

    @PostMapping("/paymentUpdate")
    public DefaultResponse paymentUpdate(@RequestBody String request,
                                            @RequestHeader(name = "x-auth", required = false)
                                            String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.PAYMENT_UPDATE, actionDto);
    }

    @PostMapping("/paymentDelete")
    public DefaultResponse paymentDelete(@RequestBody String request,
                                         @RequestHeader(name = "x-auth", required = false)
                                         String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.PAYMENT_DELETE, actionDto);
    }




}
