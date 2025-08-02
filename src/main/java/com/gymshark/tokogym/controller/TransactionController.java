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

    @PostMapping("/transactionTrackingNumber")
    public DefaultResponse transactionTrackingNumber(@RequestBody String request,
                                                     @RequestHeader(name = "x-auth", required = false)
                                                     String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.TRANSACTION_ADD_TRACKING_NUMBER, actionDto);
    }

    @GetMapping("/transactionHistory")
    public DefaultResponse transactionHistory(@RequestBody String request,
                                              @RequestHeader(name = "x-auth", required = false)
                                              String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.TRANSACTION_HISTORY, actionDto);
    }

    @PostMapping("/transactionHistoryDetail")
    public DefaultResponse transactionHistoryDetail(@RequestBody String request,
                                                    @RequestHeader(name = "x-auth", required = false)
                                                    String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.TRANSACTION_HISTORY_DETAIL, actionDto);
    }

    @GetMapping("/transactionDetailMembership")
    public DefaultResponse transactionDetailMembership(@RequestBody String request,
                                                    @RequestHeader(name = "x-auth", required = false)
                                                    String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.TRANSACTION_DETAIL_MEMBERSHIP, actionDto);
    }
}
