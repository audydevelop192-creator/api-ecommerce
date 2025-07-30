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
            case ActionConstant.MEMBERSHIP_CREATE -> new MembershipCreateAction();
            case ActionConstant.MEMBERSHIP_LIST -> new MembershipListAction();
            case ActionConstant.MEMBERSHIP_DELETE -> new MembershipDeleteAction();
            case ActionConstant.MEMBERSHIP_UPDATE -> new MembershipUpdateAction();
            case ActionConstant.PAYMENT_CREATE -> new PaymentCreateAction();
            case ActionConstant.PAYMENT_LIST -> new PaymentListAction();
            case ActionConstant.PAYMENT_UPDATE -> new PaymentUpdateAction();
            case ActionConstant.PAYMENT_DELETE -> new PaymentDeleteAction();
            case ActionConstant.SUPPLIER_CREATE -> new SupplierCreateAction();
            case ActionConstant.SUPPLIER_LIST -> new SupplierListAction();
            case ActionConstant.SUPPLIER_DELETE -> new SupplierDeleteAction();
            case ActionConstant.SUPPLIER_UPDATE -> new SupplierUpdateAction();
            case ActionConstant.PRODUCT_CREATE -> new ProductCreateAction();
            case ActionConstant.PRODUCT_UPDATE_STOCK -> new ProductUpdateStockAction();
            case ActionConstant.PRODUCT_UPDATE -> new ProductUpdateAction();
            case ActionConstant.PRODUCT_LIST -> new ProductListAction();
            case ActionConstant.PRODUCT_DELETE -> new ProductDeleteAction();
            case ActionConstant.CART_CREATE -> new CartCreateAction();
            case ActionConstant.CART_LIST -> new CartListAction();
            case ActionConstant.COMMENT_CREATE -> new CommentCreateAction();
            case ActionConstant.COMMENT_LIST -> new CommentListAction();
            case ActionConstant.TRANSACTION_CREATE -> new TransactionCreateAction();
            case ActionConstant.TRANSACTION_CHANGE_STATUS -> new TransactionChangeStatusAction();
            case ActionConstant.TRANSACTION_ADD_TRACKING_NUMBER -> new TransactionAddTrackingNumberAction();
            case ActionConstant.TRANSACTION_HISTORY -> new TransactionHistoryAction();
            case ActionConstant.TRANSACTION_HISTORY_DETAIL -> new TransactionHistoryDetailAction();
            case ActionConstant.CREATE_TRANSACTION_MEMBERSHIP -> new CreateTransactionMembershipAction();
            case ActionConstant.TRANSACTION_CHANGE_STATUS_MEMBERSHIP -> new TransactionChangeStatusMembershipAction();

            default -> throw new IllegalStateException("Unexpected value: " + action);
        };
        return actionAbstract.proses(actionDto);
    }
}
