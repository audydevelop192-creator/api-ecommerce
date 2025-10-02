package com.ecommerce.api.voucher;

import com.ecommerce.api.dto.request.AddAddressRequest;
import com.ecommerce.api.dto.request.AddVoucherRequest;
import com.ecommerce.api.dto.request.ListVoucherRequest;
import com.ecommerce.api.dto.response.AddAddressResponse;
import com.ecommerce.api.dto.response.AddVoucherResponse;
import com.ecommerce.api.dto.response.BaseResponse;
import com.ecommerce.api.dto.response.ListVoucherResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping
    public ResponseEntity<BaseResponse<ListVoucherResponse>> listProduct(@RequestBody ListVoucherRequest request) {
        BaseResponse<ListVoucherResponse> response = voucherService.listVoucher(request);
        return ResponseEntity.ok(response);
    }
}
