package com.example.sqltest.ctrl;

import com.alibaba.fastjson.JSONObject;
import com.example.sqltest.service.*;
import com.example.sqltest.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by T016 on 2019/2/28.
 */
@RestController
@RequestMapping("/function")
public class FunctionCtrl {

    @Autowired
    private UserSer userSer;

    @Autowired
    private ActIdGroupSer actIdGroupSer;

    @Autowired
    private FunctionSer functionSer;

    @GetMapping("/info")
    public InfoVo getInfo(@RequestHeader String Authorization){
        return userSer.getInfo(userSer.getUserId(Authorization));
    }

    @GetMapping("/groupList")
    public List<CascasderVo> testList(){
        return actIdGroupSer.findGroupList();
    }

    @PutMapping("/info")
    public void updateInfo(@RequestBody UpdateInfoVo updateInfoVo, @RequestHeader String Authorization) throws Exception {
        userSer.updateInfo(updateInfoVo, userSer.getUserId(Authorization));
    }

    @GetMapping("/processModelList")
    public List<ProcessModelVo> processModelList(){
        return functionSer.getAllProcessModel();
    }

    @PostMapping("/process")
    public void createProcess(@RequestHeader String Authorization, @RequestBody CreateProcessVo createProcessVo) throws IOException {
        functionSer.createProcess(userSer.getUserId(Authorization), createProcessVo);
    }

    @GetMapping("/tasks")
    public List<TaskVo> getTasks(@RequestHeader String Authorization, String getType) throws IOException, IllegalAccessException {
        return functionSer.getTasks(userSer.getUserId(Authorization), getType);
    }

    @GetMapping("/form")
    public List<FormVo> getForm (@RequestHeader String Authorization, String taskId) throws IOException {
        return functionSer.getForm(userSer.getUserId(Authorization), taskId);
    }

    @PutMapping("/tasks")
    public void taskComplete(@RequestHeader String Authorization, @RequestBody JSONObject jsonObject, String taskId) throws IOException {
        functionSer.completeTask(jsonObject, userSer.getUserId(Authorization), taskId);
    }
}
