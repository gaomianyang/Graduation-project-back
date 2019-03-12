package com.example.sqltest.ctrl;

import com.example.sqltest.vo.ImgVo;
import com.example.sqltest.bean.UserBean;
import com.example.sqltest.service.UserSer;
import com.example.sqltest.vo.LoginVo;
import com.example.sqltest.vo.RegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/user")
public class UserCtrl {

    @Autowired
    private UserSer userSer;

    @PostMapping("/login")
    public Object login(@RequestBody LoginVo loginVo) throws Exception {
        return userSer.getKey(userSer.login(loginVo));
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterVo registerVo) throws Exception {
        userSer.register(registerVo);
        return "OK";
    }

    @GetMapping("/checkUserName")
    public boolean checkUserName(String userName){
        return userSer.checkUserName(userName);
    }

    @GetMapping("/getImg")
    public ImgVo getImg() throws IOException {
        return userSer.getImg();
    }
}