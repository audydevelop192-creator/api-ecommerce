package com.gymshark.tokogym.controller;

import com.gymshark.tokogym.action.ActionFactory;
import com.gymshark.tokogym.constant.ActionConstant;
import com.gymshark.tokogym.dto.ActionDto;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
public class ReportHistoryStockController {

    private final ActionFactory actionFactory = new ActionFactory();

    @PostMapping("/reportHistoryStock")
    public DefaultResponse reportHistoryStock(@RequestBody String request,
                                                    @RequestHeader(name = "x-auth", required = false)
                                                    String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.REPORT_HISTORY_STOCK, actionDto);
    }

    @PostMapping("/transactionReport")
    public DefaultResponse transactionReport(@RequestBody String request,
                                              @RequestHeader(name = "x-auth", required = false)
                                              String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.TRANSACTION_REPORT, actionDto);
    }
}
