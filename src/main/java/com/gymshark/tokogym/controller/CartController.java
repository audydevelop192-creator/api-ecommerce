package com.gymshark.tokogym.controller;

import com.gymshark.tokogym.action.ActionFactory;
import com.gymshark.tokogym.constant.ActionConstant;
import com.gymshark.tokogym.dto.ActionDto;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final ActionFactory actionFactory = new ActionFactory();

    @PostMapping("/cartAdd")
    public DefaultResponse cartAdd(@RequestBody String request,
                                            @RequestHeader(name = "x-auth", required = false)
                                            String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.CART_CREATE, actionDto);
    }

    @GetMapping("/cartList")
    public DefaultResponse cartList(@RequestBody String request,
                                          @RequestHeader(name = "x-auth", required = false)
                                          String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.CART_LIST, actionDto);
    }

}
