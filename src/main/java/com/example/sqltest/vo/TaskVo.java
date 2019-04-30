package com.example.sqltest.vo;

/**
 * Created by T016 on 2019/4/9.
 */
public class TaskVo {

    private String id;
    private String name;
    private String created;
    private String formKey;
    private String processInstanceId;
    private Object assignee;
    private Object involvedPeople;
    private String endDate;
    private boolean initiatorCanCompleteTask;
    private boolean memberOfCandidateUsers;
    private boolean memberOfCandidateGroup;

    public boolean isInitiatorCanCompleteTask() {
        return initiatorCanCompleteTask;
    }

    public void setInitiatorCanCompleteTask(boolean initiatorCanCompleteTask) {
        this.initiatorCanCompleteTask = initiatorCanCompleteTask;
    }

    public boolean isMemberOfCandidateUsers() {
        return memberOfCandidateUsers;
    }

    public void setMemberOfCandidateUsers(boolean memberOfCandidateUsers) {
        this.memberOfCandidateUsers = memberOfCandidateUsers;
    }

    public boolean isMemberOfCandidateGroup() {
        return memberOfCandidateGroup;
    }

    public void setMemberOfCandidateGroup(boolean memberOfCandidateGroup) {
        this.memberOfCandidateGroup = memberOfCandidateGroup;
    }

    public Object getInvolvedPeople() {
        return involvedPeople;
    }

    public void setInvolvedPeople(Object involvedPeople) {
        this.involvedPeople = involvedPeople;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Object getAssignee() {
        return assignee;
    }

    public void setAssignee(Object assignee) {
        this.assignee = assignee;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
