package com.gymshark.tokogym.action;


import com.gymshark.tokogym.dao.UserDao;
import com.gymshark.tokogym.dto.AuthDto;
import com.gymshark.tokogym.dto.request.LoginRequest;
import com.gymshark.tokogym.dto.request.ProfileRequest;
import com.gymshark.tokogym.dto.response.DefaultResponse;
import com.gymshark.tokogym.dto.response.ProfileResponse;
import com.gymshark.tokogym.model.Profile;

public class ProfileAction extends ActionAbstract<ProfileRequest>{

    private UserDao userDao = new UserDao();

    @Override
    protected boolean isLogin() {
        return true;
    }

    protected ProfileAction() {
        super(ProfileRequest.class);
    }

    @Override
    protected DefaultResponse execute(AuthDto authDto, ProfileRequest request) {
        Profile profile = userDao.findProfileByUserId(authDto.getUserId());
        ProfileResponse profileResponse = new ProfileResponse();
        profileResponse.setName(profile.getName());
        profileResponse.setEmail(profile.getEmail());
        profileResponse.setExpiredDate(profile.getExpiredDate());
        profileResponse.setMembershipName(profile.getMembershipName());
        profileResponse.setMembershipRef(profile.getMembershipRef());
        return profileResponse;
    }


}
