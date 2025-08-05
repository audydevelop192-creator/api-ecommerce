package com.gymshark.tokogym.action;

import com.gymshark.tokogym.constant.RolesConstant;
import com.gymshark.tokogym.dao.MembershipDao;
import com.gymshark.tokogym.dao.UserDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.MembershipCreateRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.MembershipCreateResponse;
import com.gymshark.tokogym.model.Membership;

import java.math.BigDecimal;
import java.util.List;

public class MembershipCreateAction extends ActionAbstract<MembershipCreateRequest> {

    private static final MembershipDao membershipDao = new MembershipDao();

    private static final List<String> typeDuration = List.of("Minutes", "Hour", "Day", "Month");

    @Override
    protected boolean isLogin() {
        return true;

    }

    @Override
    protected List<Integer> allowedUser() {
        return List.of(RolesConstant.ADMIN);
    }

    protected MembershipCreateAction() {
        super(MembershipCreateRequest.class);
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, MembershipCreateRequest request) {
        MembershipCreateResponse membershipCreateResponse = new MembershipCreateResponse();

        if (request.getTypeDuration() == null) {
            membershipCreateResponse.setMessage("Tipe Duration Tidak Boleh Kosong");
            return membershipCreateResponse;
        }

        if (!typeDuration.contains(request.getTypeDuration())) {
            membershipCreateResponse.setMessage("Tipe Durasi Hanya Boleh Minutes, Hour, Day, Month");
            return membershipCreateResponse;
        }



        if (request.getDuration() < 1) {
            membershipCreateResponse.setMessage("Durasi tidak boleh lebih kecil dari 1");
            return membershipCreateResponse;
        }

        if (request.getPrice() == null) {
            membershipCreateResponse.setMessage("Harga Wajib diisi");
            return membershipCreateResponse;

        }

        if (request.getPrice().compareTo(BigDecimal.ONE) < 0) {
            membershipCreateResponse.setMessage("Harga Tidak Boleh Lebih Kecil Dari 1");
            return membershipCreateResponse;
        }

        if (request.getName() == null) {
            membershipCreateResponse.setMessage("Nama Wajib Diisi");
            return membershipCreateResponse;
        }

        if (request.getDescription() == null) {
            membershipCreateResponse.setMessage("Deskripsi Wajib diisi");
            return membershipCreateResponse;
        }

        Membership membershipModel = new Membership();
        membershipModel.setName(request.getName());
        membershipModel.setDescription(request.getDescription());
        membershipModel.setDuration(request.getDuration());
        membershipModel.setTypeDuration(request.getTypeDuration());
        membershipModel.setPrice(request.getPrice());
        membershipDao.insertMembership(membershipModel);
        membershipCreateResponse.setMessage("Berhasil Membuat Membership");
        return membershipCreateResponse;
    }
}
