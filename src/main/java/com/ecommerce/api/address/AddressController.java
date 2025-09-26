package com.ecommerce.api.address;

import com.ecommerce.api.auth.AuthService;
import com.ecommerce.api.dto.request.AddAddressRequest;
import com.ecommerce.api.dto.request.ListAddressRequest;
import com.ecommerce.api.dto.request.RegisterRequest;
import com.ecommerce.api.dto.response.AddAddressResponse;
import com.ecommerce.api.dto.response.BaseResponse;
import com.ecommerce.api.dto.response.ListAddressResponse;
import com.ecommerce.api.dto.response.RegisterResponse;
import com.ecommerce.api.model.Address;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }


    @PostMapping
    public ResponseEntity<BaseResponse<AddAddressResponse>> addAddress(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody AddAddressRequest request) {

        // biasanya format header = "Bearer <token>"
        String token = authHeader.replace("Bearer ", "");

        BaseResponse<AddAddressResponse> response = addressService.addAddress(token, request);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<BaseResponse<ListAddressResponse>>listAddresses(@RequestBody ListAddressRequest request) {
        BaseResponse<ListAddressResponse> response = addressService.listAddress(request);
        return ResponseEntity.ok(response);
    }
}
