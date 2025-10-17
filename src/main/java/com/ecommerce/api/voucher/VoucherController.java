package com.ecommerce.api.voucher;

import com.ecommerce.api.dto.request.*;
import com.ecommerce.api.dto.response.*;
import org.springframework.data.relational.core.sql.Delete;
import org.springframework.data.relational.core.sql.Update;
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
    public ResponseEntity<BaseResponse<AddVoucherResponse>> addVoucher(@RequestBody AddVoucherRequest request) {
        BaseResponse<AddVoucherResponse> response = voucherService.addVoucher(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<BaseResponse<ListVoucherResponse>> listVoucher() {
        BaseResponse<ListVoucherResponse> response = voucherService.listVoucher();
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<UpdateVoucherResponse>> updateVoucher(@RequestBody UpdateVoucherRequest request,
                                                                           @PathVariable Integer id) {
        BaseResponse<UpdateVoucherResponse> response = voucherService.updateVoucher(id, request);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<DeleteVoucherResponse>> deleteVoucher(@RequestBody DeleteVoucherRequest request,
                                                                              @PathVariable Integer id) {
        BaseResponse<DeleteVoucherResponse> response = voucherService.deleteVoucher(id, request);
        return ResponseEntity.ok(response);

    }

}
