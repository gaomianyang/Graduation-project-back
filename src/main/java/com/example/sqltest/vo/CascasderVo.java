package com.example.sqltest.vo;

import java.util.List;
import java.util.Map;

/**
 * Created by T016 on 2019/3/15.
 */
public class CascasderVo {

    private String value;
    private String label;
    private List<CascasderVo> children;
    private Map<String, CascasderVo> testMap;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<CascasderVo> getChildren() {
        return children;
    }

    public void setChildren(List<CascasderVo> children) {
        this.children = children;
    }

    public Map<String, CascasderVo> getTestMap() {
        return testMap;
    }

    public void setTestMap(Map<String, CascasderVo> testMap) {
        this.testMap = testMap;
    }
}
