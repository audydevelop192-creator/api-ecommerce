package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.SupplierDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.SupplierCreateRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.SupplierCreateResponse;
import com.gymshark.tokogym.model.Supplier;

import java.util.List;

public class SupplierCreateAction extends ActionAbstract<SupplierCreateRequest>{

    private static final SupplierDao supplierDao = new SupplierDao();

    @Override
    protected boolean isLogin() {
        return true;

    }

    @Override
    protected List<Integer> allowedUser() {
        return List.of(RolesConstant.ADMIN);
    }

    protected SupplierCreateAction() {
        super(SupplierCreateRequest.class);
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, SupplierCreateRequest request) {
        SupplierCreateResponse supplierCreateResponse = new SupplierCreateResponse();
        if (request.getName() == null) {
            supplierCreateResponse.setMessage("Name Wajib Diisi");
            return supplierCreateResponse;
        }

        if (request.getCode() == null|| request.getCode().isEmpty()) {
            supplierCreateResponse.setMessage("Kode Wajib Diisi");
            return supplierCreateResponse;
        }

        if (supplierDao.isCodeExist(request.getCode())){
            supplierCreateResponse.setMessage("Code Sudah Digunakan");
            return supplierCreateResponse;
        }


        if (request.getAddress() == null) {
            supplierCreateResponse.setMessage("Alamat Wajib Diisi");
            return supplierCreateResponse;
        }
        if (request.getPhoneNumber() == null) {
            supplierCreateResponse.setMessage("Nomer Telepon Wajib Diisi");
            return supplierCreateResponse;
        }

        Supplier supplierModel = new Supplier();
        supplierModel.setName(request.getName());
        supplierModel.setCode(request.getCode());
        supplierModel.setAddress(request.getAddress());
        supplierModel.setPhoneNumber(request.getPhoneNumber());

        supplierDao.insertSupplier(supplierModel);
        supplierCreateResponse.setMessage("Berhasil Membuat Supplier");
        return supplierCreateResponse;
    }
}
