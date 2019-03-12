package com.example.sqltest.ctrl;

import com.example.sqltest.service.UserSer;
import com.example.sqltest.vo.InfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by T016 on 2019/2/28.
 */
@RestController
@RequestMapping("/function")
public class FunctionCtrl {

    @Autowired
    private UserSer userSer;

    @GetMapping("/testHeader")
    public void testHeader(@RequestHeader String Authorization){
        if(userSer.testJjwt(Authorization)) {
            System.out.println("OK");
        }
    }

    @GetMapping("/getInfo")
    public InfoVo getInfo(@RequestHeader String Authorization){
        return userSer.getInfo(Authorization);
    }
}
