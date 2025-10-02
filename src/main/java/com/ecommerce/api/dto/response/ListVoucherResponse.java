package com.ecommerce.api.dto.response;

import com.ecommerce.api.model.Voucher;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ListVoucherResponse {

    private List<ListVoucher> vouchers=new ArrayList<>();


    @Getter
    @Setter
    public static class ListVoucher {

        private Integer id;
        private String code;
        private String discountType;
        private BigDecimal discountValue;
    }

}
