package com.ecommerce.api.address;

import com.ecommerce.api.config.AuthenticatedUser;
import com.ecommerce.api.dto.request.AddAddressRequest;
import com.ecommerce.api.dto.request.ListAddressRequest;
import com.ecommerce.api.dto.request.UpdateAddressRequest;
import com.ecommerce.api.dto.response.AddAddressResponse;
import com.ecommerce.api.dto.response.BaseResponse;
import com.ecommerce.api.dto.response.ListAddressResponse;
import com.ecommerce.api.dto.response.UpdateAddressResponse;
import com.ecommerce.api.model.Address;
import com.ecommerce.api.utils.JwtUtils;
import com.ecommerce.api.utils.SecurityUtils;
import org.springframework.data.relational.core.sql.Update;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtUtils jwtUtils;

    public AddressService(AddressRepository addressRepository, JwtUtils jwtUtils) {
        this.addressRepository = addressRepository;
        this.jwtUtils = jwtUtils;
    }

    public BaseResponse<AddAddressResponse> addAddress(String token, AddAddressRequest request) {

        if (token == null || !jwtUtils.validateToken(token)) {
            return new BaseResponse<>("error", "Invalid or missing token", null);
        }

        Integer userId = jwtUtils.getUserIdFromToken(token);

        if (request.getRecipientName() == null) {
            return new BaseResponse<>("error", "Recipient name is required", null);
        }

        if (request.getPhoneNumber() == null) {
            return new BaseResponse<>("error", "Phone number is required", null);
        }

        if (request.getAddressLine() == null) {
            return new BaseResponse<>("error", "Address line is required", null);
        }
        if (request.getCity() == null) {
            return new BaseResponse<>("error", "City is required", null);
        }
        if (request.getPostalCode() == null) {
            return new BaseResponse<>("error", "Postal code is required", null);
        }

        if (request.isDefault()) {
            addressRepository.resetDefault(userId);
        }

        Address address = new Address();
        address.setUserId(userId);
        address.setAddressLine(request.getAddressLine());
        address.setCity(request.getCity());
        address.setPostalCode(request.getPostalCode());
        address.setRecipientName(request.getRecipientName());
        address.setPhoneNumber(request.getPhoneNumber());
        address.setDefault(request.isDefault());

        Address saved = addressRepository.addAddress(address);

        AddAddressResponse response = new AddAddressResponse();
        response.setId(saved.getId());
        response.setRecipientName(saved.getRecipientName());
        response.setPhoneNumber(saved.getPhoneNumber());
        response.setAddressLine(saved.getAddressLine());
        response.setCity(saved.getCity());
        response.setPostalCode(saved.getPostalCode());
        response.setDefault(saved.isDefault());

        return new BaseResponse<>("success", "Address added successfully", response);
    }

    public BaseResponse<ListAddressResponse>listAddress(ListAddressRequest request) {
        AuthenticatedUser authenticatedUser= SecurityUtils.getCurrentUser();

        if (authenticatedUser == null) {
            return new BaseResponse<>("error", "Invalid or expired token", null);
        }

        List<Address> addressList=addressRepository.findAll();
        ListAddressResponse response = new ListAddressResponse();
        for (Address address : addressList) {
            ListAddressResponse.AddressList addressListResponse = new ListAddressResponse.AddressList();
            addressListResponse.setId(address.getId());
            addressListResponse.setRecipientName(address.getRecipientName());
            addressListResponse.setPhoneNumber(address.getPhoneNumber());
            addressListResponse.setAddressLine(address.getAddressLine());
            addressListResponse.setCity(address.getCity());
            addressListResponse.setPostalCode(address.getPostalCode());
            addressListResponse.setDefault(address.isDefault());
            response.getAddresses().add(addressListResponse);
        }

        return new BaseResponse<>("success", "Addresses retrieved successfully", response);

    }
    public BaseResponse<UpdateAddressResponse>updateAddress(UpdateAddressRequest request,Integer id) {
        AuthenticatedUser authenticatedUser= SecurityUtils.getCurrentUser();
        if (authenticatedUser == null) {
            return new BaseResponse<>("error", "Invalid or expired token", null);
        }
        if (request.getRecipientName() == null) {
            return new BaseResponse<>("error", "Recipient name is required", null);
        }
        if (request.getPhoneNumber() == null) {
            return new BaseResponse<>("error", "Phone number is required", null);
        }
        if (request.getAddressLine() == null) {
            return new BaseResponse<>("error", "Address line is required", null);
        }
        if (request.getCity() == null) {
            return new BaseResponse<>("error", "City is required", null);
        }
        if (request.getPostalCode() == null) {
            return new BaseResponse<>("error", "Postal code is required", null);
        }
        Optional<Address>address = addressRepository.findById(id);
        if (address.isEmpty()) {
            return new BaseResponse<>("error", "Address not found", null);
        }
        Address saved = address.get();
        if (saved.getUserId() == null || !saved.getUserId().equals(authenticatedUser.getUserId())) {
            return new BaseResponse<>("error", "User not allowed to update", null);
        }

        saved.setRecipientName(request.getRecipientName());
        saved.setPhoneNumber(request.getPhoneNumber());
        saved.setAddressLine(request.getAddressLine());
        saved.setCity(request.getCity());
        saved.setPostalCode(request.getPostalCode());
        saved.setDefault(request.isDefault());

        int updateAddress  = addressRepository.updateAddress(saved);
        if (updateAddress == 0) {
            return new BaseResponse<>("error", "Address update failed", null);

        }

        UpdateAddressResponse response = new UpdateAddressResponse();
        response.setId(saved.getId());
        response.setRecipientName(saved.getRecipientName());
        response.setPhoneNumber(saved.getPhoneNumber());
        response.setAddressLine(saved.getAddressLine());
        response.setCity(saved.getCity());
        response.setPostalCode(saved.getPostalCode());
        response.setDefault(saved.isDefault());
        return new BaseResponse<>("success", "Address updated successfully", response);
    }
}
