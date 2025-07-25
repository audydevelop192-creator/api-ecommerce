package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.MembershipDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.MembershipUpdateRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.MembershipUpdateResponse;
import com.gymshark.tokogym.model.Membership;

import java.math.BigDecimal;
import java.util.List;

public class MembershipUpdateAction extends ActionAbstract<MembershipUpdateRequest>{

    private MembershipDao membershipDao = new MembershipDao();

    private List<String> typeDuration = List.of("Minutes", "Hour", "Day", "Month");

    @Override
    protected boolean isLogin() {
        return true;

    }

    @Override
    protected List<Integer> allowedUser() {
        return List.of(RolesConstant.ADMIN);
    }

    protected MembershipUpdateAction() {
        super(MembershipUpdateRequest.class);
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, MembershipUpdateRequest request) {
        MembershipUpdateResponse membershipUpdateResponse = new MembershipUpdateResponse();
        if (request.getId() == null){
            membershipUpdateResponse.setMessage("Id Wajib Diisi");
            return membershipUpdateResponse;
        }

        if (request.getTypeDuration() == null) {
            membershipUpdateResponse.setMessage("Tipe Duration Tidak Boleh Kosong");
            return membershipUpdateResponse;
        }

        if (!typeDuration.contains(request.getTypeDuration())) {
            membershipUpdateResponse.setMessage("Tipe Durasi Hanya Boleh Minutes, Hour, Day, Month");
            return membershipUpdateResponse;
        }



        if (request.getDuration() < 1) {
            membershipUpdateResponse.setMessage("Durasi tidak boleh lebih kecil dari 1");
            return membershipUpdateResponse;
        }

        if (request.getPrice() == null) {
            membershipUpdateResponse.setMessage("Harga Wajib diisi");
            return membershipUpdateResponse;

        }

        if (request.getPrice().compareTo(BigDecimal.ONE) < 0) {
            membershipUpdateResponse.setMessage("Harga Tidak Boleh Lebih Kecil Dari 1");
            return membershipUpdateResponse;
        }

        if (request.getName() == null) {
            membershipUpdateResponse.setMessage("Nama Wajib Diisi");
            return membershipUpdateResponse;
        }

        if (request.getDescription() == null) {
            membershipUpdateResponse.setMessage("Ddeskripsi Wajib diisi");
            return membershipUpdateResponse;
        }

        Membership membership = new Membership();
        membership.setName(request.getName());
        membership.setDescription(request.getDescription());
        membership.setDuration(request.getDuration());
        membership.setTypeDuration(request.getTypeDuration());
        membership.setPrice(request.getPrice());
        membership.setId(request.getId());
        membershipDao.updateMembership(membership);

        membershipUpdateResponse.setMessage("Sukses Update");
        return membershipUpdateResponse;
    }

}
