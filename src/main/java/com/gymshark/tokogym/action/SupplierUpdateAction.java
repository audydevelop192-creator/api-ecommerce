package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.SupplierDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.SupplierUpdateRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.SupplierUpdateResponse;
import com.gymshark.tokogym.model.Supplier;

import java.util.List;

public class SupplierUpdateAction extends ActionAbstract<SupplierUpdateRequest>{
    private final SupplierDao supplierDao = new SupplierDao();

    @Override
    protected boolean isLogin() {
        return true;

    }

    @Override
    protected List<Integer> allowedUser() {
        return List.of(RolesConstant.ADMIN);
    }

    protected SupplierUpdateAction() {
        super(SupplierUpdateRequest.class);
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, SupplierUpdateRequest request) {
        SupplierUpdateResponse supplierUpdateResponse = new SupplierUpdateResponse();
        if (request.getId() == null) {
            supplierUpdateResponse.setMessage("Id Wajib Diisi");
            return supplierUpdateResponse;
        }

        if (request.getCode() == null) {
            supplierUpdateResponse.setMessage("Kode Supplier Wajib diisi");
            return supplierUpdateResponse;

        }

        if (supplierDao.isCodeExist(request.getCode())){
            supplierUpdateResponse.setMessage("Code Sudah Digunakan");
            return supplierUpdateResponse;
        }

        if (request.getName() == null) {
            supplierUpdateResponse.setMessage("Nama Supplier Wajib Diisi");
            return supplierUpdateResponse;
        }

        if (request.getAddress() == null) {
            supplierUpdateResponse.setMessage("Alamat Wajib diisi");
            return supplierUpdateResponse;
        }
        if (request.getPhoneNumber() == null) {
            supplierUpdateResponse.setMessage("Nomer Telepon Wajib diisi");
            return supplierUpdateResponse;
        }

        Supplier supplier = new Supplier();
        supplier.setCode(request.getCode());
        supplier.setName(request.getName());
        supplier.setAddress(request.getAddress());
        supplier.setPhoneNumber(request.getPhoneNumber());
        supplier.setId(request.getId());
        supplierDao.updateSupplier(supplier);

        supplierUpdateResponse.setMessage("Sukses Update");
        return supplierUpdateResponse;
    }
}
