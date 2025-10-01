package com.ecommerce.api.voucher;

import com.ecommerce.api.dto.request.AddAddressRequest;
import com.ecommerce.api.dto.request.AddVoucherRequest;
import com.ecommerce.api.dto.response.AddAddressResponse;
import com.ecommerce.api.dto.response.AddVoucherResponse;
import com.ecommerce.api.dto.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vouchers")
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<AddVoucherResponse>> addProduct(@RequestBody AddVoucherRequest request) {
        BaseResponse<AddVoucherResponse> response = voucherService.addVoucher(request);
        return ResponseEntity.ok(response);
    }
}
