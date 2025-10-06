package com.ecommerce.api.address;

import com.ecommerce.api.auth.AuthService;
import com.ecommerce.api.dto.request.*;
import com.ecommerce.api.dto.response.*;
import com.ecommerce.api.model.Address;
import org.springframework.data.relational.core.sql.Delete;
import org.springframework.data.relational.core.sql.Update;
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
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<UpdateAddressResponse>>updateAddresses(@RequestBody UpdateAddressRequest request,
                                                                              @PathVariable Integer id) {
        BaseResponse<UpdateAddressResponse> response = addressService.updateAddress(request,id);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<DeleteAddressResponse>>deleteAddresses(@RequestBody DeleteAddressRequest request,
                                                                               @PathVariable Integer id) {
        BaseResponse<DeleteAddressResponse> response = addressService.deleteAddress(request,id);
        return ResponseEntity.ok(response);
    }


}

