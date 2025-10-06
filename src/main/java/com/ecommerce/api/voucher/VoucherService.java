package com.ecommerce.api.voucher;

import com.ecommerce.api.config.AuthenticatedUser;
import com.ecommerce.api.dto.request.AddVoucherRequest;
import com.ecommerce.api.dto.request.ListVoucherRequest;
import com.ecommerce.api.dto.request.UpdateVoucherRequest;
import com.ecommerce.api.dto.response.AddVoucherResponse;
import com.ecommerce.api.dto.response.BaseResponse;
import com.ecommerce.api.dto.response.ListVoucherResponse;
import com.ecommerce.api.dto.response.UpdateVoucherResponse;
import com.ecommerce.api.model.Voucher;
import com.ecommerce.api.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.swing.text.html.ListView;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public BaseResponse<ListVoucherResponse>listVoucher(ListVoucherRequest request) {
        AuthenticatedUser authenticatedUser = SecurityUtils.getCurrentUser();
        if (authenticatedUser == null) {
            return new BaseResponse<>("error", "Invalid or expired token", null);
        }
        List<Voucher>vouchers=voucherRepository.findAll();

        ListVoucherResponse listVoucherResponse = new ListVoucherResponse();
        for (Voucher voucher : vouchers) {
            ListVoucherResponse.ListVoucher listVoucher = new ListVoucherResponse.ListVoucher();
            listVoucher.setId(voucher.getId());
            listVoucher.setCode(voucher.getCode());
            listVoucher.setDiscountType(voucher.getDiscountType());
            listVoucher.setDiscountValue(voucher.getDiscountValue());

            listVoucherResponse.getVouchers().add(listVoucher);
        }
        return new BaseResponse<>("success","Vouchers retrieved successfully",listVoucherResponse);



    }
    public BaseResponse<UpdateVoucherResponse> updateVoucher(Integer id, UpdateVoucherRequest request) {
        AuthenticatedUser authenticatedUser = SecurityUtils.getCurrentUser();

        if (authenticatedUser == null) {
            return new BaseResponse<>("error", "Invalid or expired token", null);
        }


        if (!"ADMIN".equalsIgnoreCase(authenticatedUser.getRole())) {
            return new BaseResponse<>("error", "Invalid user access", null);
        }


        Optional<Voucher> voucherOpt = voucherRepository.findById(id);
        if (voucherOpt.isEmpty()) {
            return new BaseResponse<>("error", "Voucher not found", null);
        }

        if (request.getExpiredAt() != null && request.getExpiredAt().isBefore(LocalDateTime.now())) {
            return new BaseResponse<>("error", "Expiration date cannot be in the past", null);
        }


        Voucher voucher = voucherOpt.get();
        if (request.getCode() != null) voucher.setCode(request.getCode());
        if (request.getDiscountType() != null) voucher.setDiscountType(request.getDiscountType());
        if (request.getDiscountValue() != null) voucher.setDiscountValue(request.getDiscountValue());
        if (request.getExpiredAt() != null) voucher.setExpiredAt(request.getExpiredAt());
        if (request.getMaxUsage() != null) voucher.setMaxUsage(request.getMaxUsage());

        int rows = voucherRepository.updateVoucher(voucher);
        if (rows == 0) {
            return new BaseResponse<>("error", "Voucher update failed", null);
        }

        Voucher updated = voucherRepository.findById(id).orElse(voucher);

        UpdateVoucherResponse response = new UpdateVoucherResponse();
        response.setId(updated.getId());
        response.setCode(updated.getCode());
        response.setDiscountType(updated.getDiscountType());
        response.setDiscountValue(updated.getDiscountValue());

        return new BaseResponse<>("success", "Voucher updated successfully", response);
    }
}
