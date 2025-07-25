package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.MembershipDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.MembershipCreateRequest;
import com.gymshark.tokogym.dto.request.MembershipListRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.MembershipListResponse;
import com.gymshark.tokogym.model.Membership;

import java.util.List;

public class MembershipListAction extends ActionAbstract<MembershipListRequest>{


    private MembershipDao membershipDao = new MembershipDao();

    @Override
    protected boolean isLogin() {
        return true;

    }

    @Override
    protected List<Integer> allowedUser() {
        return List.of(RolesConstant.ADMIN, RolesConstant.CUSTOMER);
    }

    protected MembershipListAction() {
        super(MembershipListRequest.class);
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, MembershipListRequest request) {
        MembershipListResponse membershipListResponse = new MembershipListResponse();
        List<Membership> memberships = membershipDao.findAll();
        for (Membership membership : memberships) {
            MembershipListResponse.MembershipList membershipList = new MembershipListResponse.MembershipList();
            membershipList.setId(membership.getId());
            membershipList.setName(membership.getName());
            membershipList.setDescription(membership.getDescription());
            membershipList.setDuration(membership.getDuration());
            membershipList.setTypeDuration(membership.getTypeDuration());
            membershipList.setPrice(membership.getPrice());
            membershipListResponse.getMembershipLists().add(membershipList);
        }
        return membershipListResponse;
    }
}

