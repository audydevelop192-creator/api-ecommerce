package com.ecommerce.api.voucher;

import com.ecommerce.api.config.AuthenticatedUser;
import com.ecommerce.api.dto.request.AddVoucherRequest;
import com.ecommerce.api.dto.response.AddVoucherResponse;
import com.ecommerce.api.dto.response.BaseResponse;
import com.ecommerce.api.model.Voucher;
import com.ecommerce.api.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public BaseResponse<AddVoucherResponse> addVoucher(AddVoucherRequest addVoucherRequest) {

        AuthenticatedUser authenticatedUser = SecurityUtils.getCurrentUser();
        if (authenticatedUser == null) {
            return new BaseResponse<>("error", "Invalid or expired token", null);

        }
        if (!authenticatedUser.getRole().equalsIgnoreCase("ADMIN")) {
            return new BaseResponse<>("erorr", "Invalid user access", null);
        }
        if (addVoucherRequest.getCode() == null || addVoucherRequest.getCode().isBlank()) {
            return new BaseResponse<>("erorr", "Invalid code", null);
        }
        if (voucherRepository.existsByCode(addVoucherRequest.getCode())) {
            return new BaseResponse<>("erorr", "Voucher already exists", null);
        }
        if (addVoucherRequest.getDiscountValue() == null || addVoucherRequest.getDiscountValue().compareTo(BigDecimal.ZERO) <= 0) {

            return new BaseResponse<>("erorr", "discount cannot be smaller than 0 or cannot be null", null);
        }
        if (addVoucherRequest.getDiscountType() == null) {
            return new BaseResponse<>("erorr", "discountType is required", null);
        }
        if (!(addVoucherRequest.getMaxUsage() >= 100)) {
            return new BaseResponse<>("erorr", "maxUsage should be less than 100", null);
        }
        if (addVoucherRequest.getExpiredAt() == null || addVoucherRequest.getExpiredAt().isBefore(LocalDateTime.now())) {
            return new BaseResponse<>("erorr", "Expiration date cannot be in the past", null);

        }
       Voucher voucher = new Voucher();
        voucher.setCode(addVoucherRequest.getCode());
        voucher.setDiscountValue(addVoucherRequest.getDiscountValue());
        voucher.setDiscountType(addVoucherRequest.getDiscountType());
        voucher.setMaxUsage(addVoucherRequest.getMaxUsage());
        voucher.setExpiredAt(addVoucherRequest.getExpiredAt());

        voucherRepository.addVoucher(voucher);

        AddVoucherResponse addVoucherResponse = new AddVoucherResponse();
        addVoucherResponse.setId(voucher.getId());
        addVoucherResponse.setCode(voucher.getCode());
        addVoucherResponse.setDiscountType(voucher.getDiscountType());
        addVoucherResponse.setDiscountValue(voucher.getDiscountValue());

        return new BaseResponse<>("success","Voucher added successfully",addVoucherResponse);

    }

}
