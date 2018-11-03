package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.user.UserApi;
import com.stylefeng.guns.api.user.vo.UserModel;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author young
 * @description
 * @date 2018/11/3 23:11
 **/
@RequestMapping("/user/")
@RestController
public class UserController {
    @Reference(interfaceClass = UserApi.class,check = false)
    private UserApi userApi;

    @RequestMapping(value = "register",method = RequestMethod.POST)
    public ResponseVO register(UserModel userModel){
        if(userModel.getUserName() == null || userModel.getUserName().trim().length() == 0){
            return ResponseVO.serviceFail("用户名不能为空");
        }
        if(userModel.getPassword() == null || userModel.getPassword().trim().length() == 0){
            return ResponseVO.serviceFail("密码不能为空");
        }
        boolean isSuccess = userApi.register(userModel);
        if(isSuccess){
            return ResponseVO.success("注册成功");
        }else{
            return ResponseVO.serviceFail("注册失败");
        }
    }

    @RequestMapping(value = "check",method = RequestMethod.POST)
    public ResponseVO check(String userName){
        if(null!=userName &&userName.trim().length()>0){
            boolean notExists = userApi.checkUsername(userName);
            if(notExists){
                return ResponseVO.success("用户名不存在");
            }else {
                return ResponseVO.serviceFail("用户名已存在");
            }
        }else{
            return ResponseVO.serviceFail("用户名不能为空");
        }
    }

    @RequestMapping(value="logout",method = RequestMethod.GET)
    public ResponseVO logout(){
        /*
            应用：
                1、前端存储JWT 【七天】 ： JWT的刷新
                2、服务器端会存储活动用户信息【30分钟】
                3、JWT里的userId为key，查找活跃用户
            退出：
                1、前端删除掉JWT
                2、后端服务器删除活跃用户缓存
            现状：
                1、前端删除掉JWT
         */


        return ResponseVO.success("用户退出成功");
    }

}
