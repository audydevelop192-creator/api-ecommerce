package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.MembershipDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.MembershipCreateRequest;
import com.gymshark.tokogym.dto.request.MembershipDeleteRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.MembershipDeleteResponse;

import java.util.List;

public class MembershipDeleteAction extends ActionAbstract<MembershipDeleteRequest>{

    private static final MembershipDao membershipDao = new MembershipDao();

    @Override
    protected boolean isLogin() {
        return true;

    }

    @Override
    protected List<Integer> allowedUser() {
        return List.of(RolesConstant.ADMIN);
    }

    protected MembershipDeleteAction() {
        super(MembershipDeleteRequest.class);
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, MembershipDeleteRequest request) {
        MembershipDeleteResponse membershipDeleteResponse = new MembershipDeleteResponse();
        if (request.getId() == null){
            membershipDeleteResponse.setMessage("Id Wajib Diisi");
            return membershipDeleteResponse;
        }
        membershipDao.deleteMembership(request.getId());
        membershipDeleteResponse.setMessage("Sukses Delete");
        return membershipDeleteResponse;
    }
}
