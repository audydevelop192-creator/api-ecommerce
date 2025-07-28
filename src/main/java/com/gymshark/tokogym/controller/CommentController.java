package com.gymshark.tokogym.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymshark.tokogym.action.ActionFactory;
import com.gymshark.tokogym.constant.ActionConstant;
import com.gymshark.tokogym.dto.ActionDto;
import com.gymshark.tokogym.dto.request.CommentListRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final ActionFactory actionFactory = new ActionFactory();

    @PostMapping("/commentCreate")
    public DefaultResponse cartAdd(@RequestBody String request,
                                   @RequestHeader(name = "x-auth", required = false)
                                   String token) {
        ActionDto actionDto = new ActionDto();
        actionDto.setData(request);
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.COMMENT_CREATE, actionDto);
    }

    @GetMapping("/commentList")
    public DefaultResponse cartList(@RequestParam Integer productId,
                                    @RequestHeader(name = "x-auth", required = false)
                                    String token) throws JsonProcessingException {
        ActionDto actionDto = new ActionDto();
        CommentListRequest commentListRequest = new CommentListRequest();
        commentListRequest.setProductId(productId);
        ObjectMapper objectMapper = new ObjectMapper();
        actionDto.setData(objectMapper.writeValueAsString(commentListRequest));
        actionDto.setToken(token);
        return actionFactory.process(ActionConstant.COMMENT_LIST, actionDto);
    }
}
