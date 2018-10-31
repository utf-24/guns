package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.user.UserApi;
import com.stylefeng.guns.api.user.vo.UserInfoModel;
import com.stylefeng.guns.api.user.vo.UserModel;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = UserApi.class)
public class UserApiImpl implements UserApi {

    @Override
    public int login(String username, String password) {
        return 0;
    }

    @Override
    public boolean register(UserModel userModel) {
        return false;
    }

    @Override
    public boolean checkUsername(String username) {
        return false;
    }

    @Override
    public UserInfoModel getUserInfo(int uuid) {
        return null;
    }

    @Override
    public UserInfoModel updateUserInfo(UserInfoModel userInfoModel) {
        return null;
    }
}
