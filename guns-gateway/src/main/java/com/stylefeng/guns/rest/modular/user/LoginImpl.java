package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.user.Login;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = Login.class)
public class LoginImpl implements Login {
    @Override
    public boolean ifLogin(String username, String password) {
        return false;
    }
}
