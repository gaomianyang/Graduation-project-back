package com.example.sqltest.vo;

import com.example.sqltest.pojo.FormOptionPojo;

import java.util.List;

/**
 * Created by T016 on 2019/4/11.
 */
public class FormVo {

    private String formId;
    private String id;
    private String value;
    private String name;
    private String type;
    private String required;
    private String readOnly;
    private List<FormOptionPojo> options;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(String readOnly) {
        this.readOnly = readOnly;
    }

    public List<FormOptionPojo> getOptions() {
        return options;
    }

    public void setOptions(List<FormOptionPojo> options) {
        this.options = options;
    }
}
