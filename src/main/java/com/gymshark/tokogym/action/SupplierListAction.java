package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.SupplierDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.SupplierListRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.SupplierListResponse;
import com.gymshark.tokogym.model.Supplier;

import java.util.List;

public class SupplierListAction extends ActionAbstract<SupplierListRequest>{

    private SupplierDao supplierDao = new SupplierDao();

    @Override
    protected boolean isLogin() {
        return true;

    }

    @Override
    protected List<Integer> allowedUser() {
        return List.of(RolesConstant.ADMIN);
    }

    protected SupplierListAction() {
        super(SupplierListRequest.class);
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, SupplierListRequest request) {
        SupplierListResponse supplierListResponse = new SupplierListResponse();
        List<Supplier> suppliers = supplierDao.findAll();
        for (Supplier supplier : suppliers) {
            SupplierListResponse.SupplierList supplierList = new SupplierListResponse.SupplierList();
            supplierList.setId((supplier.getId()));
            supplierList.setCode(supplier.getCode());
            supplierList.setName(supplier.getName());
            supplierList.setAddress(supplier.getAddress());
            supplierList.setPhoneNumber(supplier.getPhoneNumber());
            supplierListResponse.getSupplierLists().add(supplierList);
        }
        supplierListResponse.setMessage("Berhasil Mendapatkan Data");
        return supplierListResponse;
    }
}
