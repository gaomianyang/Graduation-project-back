package com.example.sqltest.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.sqltest.bean.ActDeModelBean;
import com.example.sqltest.bean.ActReDeploymentBean;
import com.example.sqltest.bean.ActReProcdefBean;
import com.example.sqltest.bean.UserBean;
import com.example.sqltest.util.HttpUtil;
import com.example.sqltest.util.RedisUtil;
import com.example.sqltest.vo.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by T016 on 2019/3/29.
 */
@Service
public class FunctionSer {

    @Autowired
    private ActDeModelSer actDeModelSer;

    @Autowired
    private ActReDeploymentSer actReDeploymentSer;

    @Autowired
    private ActReProcdefSer actReProcdefSer;

    @Autowired
    private UserSer userSer;

    public List<ProcessModelVo> getAllProcessModel(){
        List<ActReDeploymentBean> actReDeploymentBeans = actReDeploymentSer.findAll();
        List<String> keys = actReDeploymentBeans.stream().map(e -> e.getKey()).collect(Collectors.toList());
        List<String> ids = actReDeploymentBeans.stream().map(e -> e.getId()).collect(Collectors.toList());
        List<ActDeModelBean> actDeModelBeans = actDeModelSer.findByModelKeys(keys);
        List<ActDeModelBean> orderactDeModelBeans = new ArrayList<>();
        for (String key : keys){
            for (ActDeModelBean actDeModelBean : actDeModelBeans){
                if (key.equals(actDeModelBean.getModelKey())){
                    orderactDeModelBeans.add(actDeModelBean);
                    actDeModelBeans.remove(actDeModelBean);
                    break;
                }
            }
        }
        List<ActReProcdefBean> actReProcdefBeans = actReProcdefSer.findByIds(ids);
        List<ProcessModelVo> processModelVos = new ArrayList<>();
        for(int i = 0; i < actReDeploymentBeans.size(); i++){
            ProcessModelVo processModelVo = new ProcessModelVo();
            BeanUtils.copyProperties(actReDeploymentBeans.get(i), processModelVo);
            processModelVo.setProcdefId(actReProcdefBeans.get(i).getId());
            BeanUtils.copyProperties(orderactDeModelBeans.get(i), processModelVo);
            processModelVos.add(processModelVo);
        }
        return processModelVos;
    }

    public void createProcess(String userId, CreateProcessVo createProcessVo) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String cookie = getCookie(userId);
        if(!StringUtils.isEmpty(cookie)) {
            Map<String, String> header = new HashMap((int)(1/0.75F + 1.0F));
            Map<String, String> param = new HashMap((int)(2/0.75F + 1.0F));
            header.put("Cookie", cookie);
            param.put("name", createProcessVo.getName() + " " + sdf.format(new Date()));
            param.put("processDefinitionId", createProcessVo.getProcdefId());
            HttpUtil.jsonPostNotResponse("http://localhost:8080/activiti-app/app/rest/process-instances", param, header);
        }
    }

    public String getCookie(String userId) throws IOException {
        String cookie = RedisUtil.getValueByKey(userId);
        if(!StringUtils.isEmpty(cookie)){
            return cookie;
        }
        UserBean userBean = userSer.findById(userId);
        Map<String, String> param = new HashMap((int)(4/0.75F + 1.0F));
        param.put("j_username", userBean.getUserName());
        param.put("j_password", userBean.getPassword());
        param.put("_spring_security_remember_me", "true");
        param.put("submit", "Login");
        CloseableHttpResponse loginResponse = HttpUtil.formPost("http://localhost:8080/activiti-app/app/authentication", param, null);
        cookie = HttpUtil.getCookie(loginResponse);
        RedisUtil.setKeyValue(userId, cookie, 60*300);
        return cookie;
    }

    public Object getTasks(String userId, String processId, String getType, String taskId) throws IOException {
        String cookie = getCookie(userId);
        List<TaskVo> taskVos = new ArrayList<>();
        if(StringUtils.isEmpty(processId) && StringUtils.isEmpty(taskId)){
            if(!StringUtils.isEmpty(cookie)) {
                Map<String, String> header = new HashMap((int)(1/0.75F + 1.0F));
                Map<String, String> param = new HashMap((int)(5/0.75F + 1.0F));
                header.put("Cookie", cookie);
                param.put("assignee", userSer.getUserName(userId));
                param.put("page", "0");
                param.put("state", getType);
                param.put("assignment", "involved");
                param.put("sort", "created-desc");
                CloseableHttpResponse response = HttpUtil.jsonPost("http://localhost:8080/activiti-app/app/rest/query/tasks", param, header);
                String strResult = EntityUtils.toString(response.getEntity());
                JSONObject responseBody = JSONObject.parseObject(strResult);
                taskVos = JSONArray.parseArray(JSONObject.toJSONString(responseBody.get("data"))).toJavaList(TaskVo.class);
                HttpUtil.closeResponse(response);
            }
        }
        else if (StringUtils.isEmpty(processId)){
            if(!StringUtils.isEmpty(cookie)) {
                Map<String, String> header = new HashMap((int)(1/0.75F + 1.0F));
                header.put("Cookie", cookie);
                CloseableHttpResponse response = HttpUtil.get("http://localhost:8080/activiti-app/app/rest/tasks/" + taskId, header);
                String strResult = EntityUtils.toString(response.getEntity());
                TaskVo taskVo = JSONObject.parseObject(strResult, TaskVo.class);
                HttpUtil.closeResponse(response);
                return taskVo;
            }
        }
        else{
            if(!StringUtils.isEmpty(cookie)) {
                Map<String, String> header = new HashMap((int)(1/0.75F + 1.0F));
                Map<String, String> param = new HashMap((int)(5/0.75F + 1.0F));
                header.put("Cookie", cookie);
                param.put("processInstanceId", processId);
                param.put("state", getType);
                CloseableHttpResponse response = HttpUtil.jsonPost("http://localhost:8080/activiti-app/app/rest/query/tasks", param, header);
                String strResult = EntityUtils.toString(response.getEntity());
                JSONObject responseBody = JSONObject.parseObject(strResult);
                taskVos = JSONArray.parseArray(JSONObject.toJSONString(responseBody.get("data"))).toJavaList(TaskVo.class);
                HttpUtil.closeResponse(response);
            }
        }
        return taskVos;
    }

    public List<FormVo> getForm(String userId, String taskId) throws IOException {
        String cookie = getCookie(userId);
        List<FormVo> formVos = new ArrayList<>();
        if(!StringUtils.isEmpty(cookie)) {
            Map<String, String> header = new HashMap((int)(1/0.75F + 1.0F));
            header.put("Cookie", cookie);
            CloseableHttpResponse response = HttpUtil.get("http://localhost:8080/activiti-app/app/rest/task-forms/" + taskId, header);
            String strResult = EntityUtils.toString(response.getEntity());
            JSONObject responseBody = JSONObject.parseObject(strResult);
            formVos = JSONArray.parseArray(JSONObject.toJSONString(responseBody.get("fields"))).toJavaList(FormVo.class);
            String id = JSONObject.toJSONString(responseBody.get("id"));
            id = id.substring(1, id.length()-1);
            formVos.get(0).setFormId(id);
            HttpUtil.closeResponse(response);
        }
        return formVos;
    }

    public void completeTask(JSONObject jsonObject, String userId, String taskId) throws IOException {
        String cookie = getCookie(userId);
        if(!StringUtils.isEmpty(cookie)) {
            Map<String, String> header = new HashMap((int) (1 / 0.75F + 1.0F));
            header.put("Cookie", cookie);
            System.out.println(jsonObject);
            if (null != jsonObject && jsonObject.size() > 0) {
                HttpUtil.jsonPostNotResponse("http://localhost:8080/activiti-app/app/rest/task-forms/" + taskId, jsonObject, header);
            } else {
                HttpUtil.jsonPutNotResponse("http://localhost:8080/activiti-app/app/rest/tasks/" + taskId + "/action/complete", null, header);
            }
        }
    }

    public List<ProcessVo> getProcess(String userId, String getType) throws IOException {
        String cookie = getCookie(userId);
        List<ProcessVo> processVos = new ArrayList<>();
        if(!StringUtils.isEmpty(cookie)) {
            Map<String, String> header = new HashMap((int)(1/0.75F + 1.0F));
            Map<String, String> param = new HashMap((int)(5/0.75F + 1.0F));
            header.put("Cookie", cookie);
            param.put("sort", "created-desc");
            param.put("page", "0");
            param.put("state", getType);
            CloseableHttpResponse response = HttpUtil.jsonPost("http://localhost:8080/activiti-app/app/rest/query/process-instances", param, header);
            String strResult = EntityUtils.toString(response.getEntity());
            JSONObject responseBody = JSONObject.parseObject(strResult);
            processVos = JSONArray.parseArray(JSONObject.toJSONString(responseBody.get("data"))).toJavaList(ProcessVo.class);
            HttpUtil.closeResponse(response);
        }
        return processVos;
    }

    public Object getTaskUser(String userId, String excludeTaskId, String filter) throws IOException {
        filter = filter.replace(" ","+");
        String cookie = getCookie(userId);
        Object returnObject = new Object();
        if(!StringUtils.isEmpty(cookie)) {
            Map<String, String> header = new HashMap((int)(1/0.75F + 1.0F));
            header.put("Cookie", cookie);
            CloseableHttpResponse response = HttpUtil.
                    get("http://localhost:8080/activiti-app/app/rest/workflow-users?excludeTaskId="+ excludeTaskId +"&filter="+ filter, header);
            String strResult = EntityUtils.toString(response.getEntity());
            returnObject = JSONObject.parseObject(strResult);
            HttpUtil.closeResponse(response);
        }
        return returnObject;
    }

    public void involvedUser(String userId, InvolvedVo involvedVo) throws IOException {
        String cookie = getCookie(userId);
        if(!StringUtils.isEmpty(cookie)) {
            Map<String, String> header = new HashMap((int)(1/0.75F + 1.0F));
            Map<String, String> param = new HashMap((int)(1/0.75F + 1.0F));
            header.put("Cookie", cookie);
            for(String invoUserId : involvedVo.getInvoUserIds()) {
                param.put("userId", invoUserId);
                HttpUtil.jsonPutNotResponse("http://localhost:8080/activiti-app/app/rest/tasks/" + involvedVo.getTaskId() + "/action/" + involvedVo.getType(), param ,header);
                param.clear();
            }
        }
    }

    public boolean getUserName(String Authorization, String userId){
        return userSer.findById(userSer.getUserId(Authorization)).getUserName().equals(userId);
    }

    public void claim(String userId, String taskId) throws IOException {
        String cookie = getCookie(userId);
        if(!StringUtils.isEmpty(cookie)) {
            Map<String, String> header = new HashMap((int)(1/0.75F + 1.0F));
            header.put("Cookie", cookie);
            HttpUtil.jsonPutNotResponse("http://localhost:8080/activiti-app/app/rest/tasks/" + taskId + "/action/claim", null ,header);
        }
    }
}
