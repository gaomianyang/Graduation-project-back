package com.example.sqltest.vo;

import java.util.List;

/**
 * Created by T016 on 2019/4/12.
 */
public class FormValueVo {

    private List<String> values;
    private String formId;
    private String taskId;

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
