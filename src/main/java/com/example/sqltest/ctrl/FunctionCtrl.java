package com.example.sqltest.ctrl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by T016 on 2019/2/28.
 */
@RestController
@RequestMapping("/function")
public class FunctionCtrl {

    @GetMapping("/testHeader")
    public void testHeader(){
        System.out.println("OK");
    }
}
