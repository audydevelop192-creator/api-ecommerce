package com.gymshark.tokogym.controller;

import com.gymshark.tokogym.action.ActionFactory;
import com.gymshark.tokogym.constant.ActionConstant;
import com.gymshark.tokogym.dto.ActionDto;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class CreateTransactionMembershipController {

    private final ActionFactory actionFactory = new ActionFactory();


    @PostMapping("/createTransactionMembership")
    public DefaultResponse createTransactionMembership(@RequestBody String request,
                                          @RequestHeader(name = "x-auth", required = false)
                                          String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.CREATE_TRANSACTION_MEMBERSHIP, actionDto);
    }

    @PostMapping("/transactionChangeMembership")
    public DefaultResponse transactionChangeMembershipAction(@RequestBody String request,
                                                       @RequestHeader(name = "x-auth", required = false)
                                                       String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.TRANSACTION_CHANGE_STATUS_MEMBERSHIP, actionDto);
    }
}
