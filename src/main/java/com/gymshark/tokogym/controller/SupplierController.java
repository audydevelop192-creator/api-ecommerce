package com.gymshark.tokogym.controller;

import com.gymshark.tokogym.action.ActionFactory;
import com.gymshark.tokogym.constant.ActionConstant;
import com.gymshark.tokogym.dto.ActionDto;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    private final ActionFactory actionFactory = new ActionFactory();


    @PostMapping("/supplierCreate")
    public DefaultResponse supplierCreate(@RequestBody String request,
                                         @RequestHeader(name = "x-auth", required = false)
                                         String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.SUPPLIER_CREATE, actionDto);
    }

    @GetMapping("/supplierList")
    public DefaultResponse supplierList(@RequestBody String request,
                                       @RequestHeader(name = "x-auth", required = false)
                                       String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.SUPPLIER_LIST, actionDto);
    }

    @PostMapping("/supplierDelete")
    public DefaultResponse supplierDelete(@RequestBody String request,
                                        @RequestHeader(name = "x-auth", required = false)
                                        String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.SUPPLIER_DELETE, actionDto);
    }

    @PostMapping("/supplierUpdate")
    public DefaultResponse supplierUpdate(@RequestBody String request,
                                          @RequestHeader(name = "x-auth", required = false)
                                          String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.SUPPLIER_UPDATE, actionDto);
    }
}
