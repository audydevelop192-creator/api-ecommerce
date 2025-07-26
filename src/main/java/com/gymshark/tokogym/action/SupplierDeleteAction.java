package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.SupplierDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.SupplierDeleteRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.SupplierDeleteResponse;

import java.util.List;

public class SupplierDeleteAction extends ActionAbstract<SupplierDeleteRequest>{

    private SupplierDao supplierDao = new SupplierDao();

    @Override
    protected boolean isLogin() {
        return true;
    }

    @Override
    protected List<Integer> allowedUser() {
        return List.of(RolesConstant.ADMIN);
    }

    protected SupplierDeleteAction(){
        super(SupplierDeleteRequest.class);
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, SupplierDeleteRequest request) {
        SupplierDeleteResponse supplierListResponse = new SupplierDeleteResponse();
        if (request.getId() == null){
            supplierListResponse.setMessage("Id Wajib Diisi");
            return supplierListResponse;
        }
        supplierDao.deleteSupplier(request.getId());
        supplierListResponse.setMessage("Berhasil Delete");
        return supplierListResponse;
    }
}
