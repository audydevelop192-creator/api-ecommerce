package com.gymshark.tokogym.controller;

import com.gymshark.tokogym.action.ActionFactory;
import com.gymshark.tokogym.constant.ActionConstant;
import com.gymshark.tokogym.dto.ActionDto;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final ActionFactory actionFactory = new ActionFactory();


    @PostMapping("/transactionCreate")
    public DefaultResponse supplierCreate(@RequestBody String request,
                                          @RequestHeader(name = "x-auth", required = false)
                                          String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.TRANSACTION_CREATE, actionDto);
    }

    @PostMapping("/transactionChange")
    public DefaultResponse transactionChangeStatus(@RequestBody String request,
                                          @RequestHeader(name = "x-auth", required = false)
                                          String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.TRANSACTION_CHANGE_STATUS, actionDto);
    }
}
