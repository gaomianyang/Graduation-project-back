package com.example.sqltest.vo;

import java.util.List;

/**
 * Created by T016 on 2019/4/25.
 */
public class InvolvedVo {

    private String type;

    private String taskId;

    private List<String> invoUserIds;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getInvoUserIds() {
        return invoUserIds;
    }

    public void setInvoUserIds(List<String> invoUserIds) {
        this.invoUserIds = invoUserIds;
    }
}
