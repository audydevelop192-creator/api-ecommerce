package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.ActionConstant;
import com.gymshark.tokogym.dto.ActionDto;
import com.gymshark.tokogym.dto.response.DefaultResponse;

public class ActionFactory {

    public DefaultResponse process(String action, ActionDto actionDto){
        ActionAbstract<?> actionAbstract = switch (action){
            case ActionConstant.REGISTER -> new RegisterAction();
            case ActionConstant.LOGIN -> new LoginAction();
            case ActionConstant.PROFILE -> new ProfileAction();


            default -> throw new IllegalStateException("Unexpected value: " + action);
        };
        return actionAbstract.proses(actionDto);
    }
}
