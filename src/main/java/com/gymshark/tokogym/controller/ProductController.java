package com.gymshark.tokogym.controller;

import com.gymshark.tokogym.action.ActionFactory;
import com.gymshark.tokogym.constant.ActionConstant;
import com.gymshark.tokogym.dto.ActionDto;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ActionFactory actionFactory = new ActionFactory();


    @PostMapping("/productCreate")
    public DefaultResponse productCreate(@RequestBody String request,
                                          @RequestHeader(name = "x-auth", required = false)
                                          String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.PRODUCT_CREATE, actionDto);
    }

    @PostMapping("/productUpdateStock")
    public DefaultResponse productUpdateStock(@RequestBody String request,
                                          @RequestHeader(name = "x-auth", required = false)
                                          String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.PRODUCT_UPDATE_STOCK, actionDto);
    }

    @PostMapping("/productUpdate")
    public DefaultResponse productUpdate(@RequestBody String request,
                                              @RequestHeader(name = "x-auth", required = false)
                                              String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.PRODUCT_UPDATE, actionDto);
    }

    @GetMapping("/productList")
    public DefaultResponse productList(@RequestBody String request,
                                              @RequestHeader(name = "x-auth", required = false)
                                              String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.PRODUCT_LIST, actionDto);
    }

    @PostMapping("/productDelete")
    public DefaultResponse productDelete(@RequestBody String request,
                                       @RequestHeader(name = "x-auth", required = false)
                                       String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.PRODUCT_DELETE, actionDto);
    }
}
