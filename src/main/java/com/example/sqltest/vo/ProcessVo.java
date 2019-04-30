package com.example.sqltest.vo;

import com.example.sqltest.pojo.ProcessUserInfoPojo;

/**
 * Created by T016 on 2019/4/16.
 */
public class ProcessVo {

    private String id;
    private String name;
    private String started;
    private ProcessUserInfoPojo startedBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStarted() {
        return started;
    }

    public void setStarted(String started) {
        this.started = started;
    }

    public ProcessUserInfoPojo getStartedBy() {
        return startedBy;
    }

    public void setStartedBy(ProcessUserInfoPojo startedBy) {
        this.startedBy = startedBy;
    }
}
