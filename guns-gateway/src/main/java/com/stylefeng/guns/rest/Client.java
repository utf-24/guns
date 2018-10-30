package com.stylefeng.guns.rest;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.user.UserApi;
import org.springframework.stereotype.Component;

@Component
public class Client {

    @Reference(interfaceClass = UserApi.class)
    private UserApi userApi;

    public void run(){
        userApi.ifLogin("young","11");
    }
}
