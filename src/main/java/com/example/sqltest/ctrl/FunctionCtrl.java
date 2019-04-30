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
    public Object getTasks(@RequestHeader String Authorization,String processId, String getType, String taskId) throws IOException {

        return functionSer.getTasks(userSer.getUserId(Authorization), processId, getType, taskId);
    }

    @GetMapping("/form")
    public List<FormVo> getForm (@RequestHeader String Authorization, String taskId) throws IOException {
        return functionSer.getForm(userSer.getUserId(Authorization), taskId);
    }

    @PutMapping("/tasks")
    public void taskComplete(@RequestHeader String Authorization, @RequestBody JSONObject jsonObject, String taskId) throws IOException {
        functionSer.completeTask(jsonObject, userSer.getUserId(Authorization), taskId);
    }

    @GetMapping("/process")
    public List<ProcessVo> getProcesses(@RequestHeader String Authorization, String getType) throws IOException {
        return functionSer.getProcess(userSer.getUserId(Authorization), getType);
    }

    @GetMapping("/user")
    public Object getTaskUser(@RequestHeader String Authorization, String excludeTaskId, String filter) throws IOException {
        return functionSer.getTaskUser(userSer.getUserId(Authorization), excludeTaskId, filter);
    }

    @PutMapping("/involved")
    public void involvedUser(@RequestHeader String Authorization,@RequestBody InvolvedVo involvedVo) throws IOException {
        functionSer.involvedUser(userSer.getUserId(Authorization), involvedVo);
    }

    @GetMapping("/comAuthentication")
    public boolean comAuthentication(@RequestHeader String Authorization, String userId){
        return functionSer.getUserName(Authorization, userId);
    }

    @PutMapping("/claim")
    public void claim(@RequestHeader String Authorization, String taskId) throws IOException {
        functionSer.claim(userSer.getUserId(Authorization), taskId);
    }
}
