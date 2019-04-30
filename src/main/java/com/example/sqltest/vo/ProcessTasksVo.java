package com.example.sqltest.vo;

import java.util.List;

/**
 * Created by T016 on 2019/4/17.
 */
public class ProcessTasksVo {

    private String taskType;
    private List<TaskVo> data;

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public List<TaskVo> getData() {
        return data;
    }

    public void setData(List<TaskVo> data) {
        this.data = data;
    }
}
