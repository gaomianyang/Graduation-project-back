package com.example.sqltest.bean;

import java.util.List;

/**
 * Created by T016 on 2019/2/15.
 */
public class IndexBean {

    private String table;
    private String type;
    private boolean isunique;
    private List<String> columns;
    private String comment;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isIsunique() {
        return isunique;
    }

    public void setIsunique(boolean isunique) {
        this.isunique = isunique;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
