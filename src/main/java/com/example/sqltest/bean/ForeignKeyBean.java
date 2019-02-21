package com.example.sqltest.bean;

import java.util.List;

/**
 * Created by T016 on 2019/2/13.
 */
public class ForeignKeyBean {

    private String table;
    private String referencesTable;
    private List<String> keyColumns;
    private List<String> referencesColumns;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getReferencesTable() {
        return referencesTable;
    }

    public void setReferencesTable(String referencesTable) {
        this.referencesTable = referencesTable;
    }

    public List<String> getKeyColumns() {
        return keyColumns;
    }

    public void setKeyColumns(List<String> keyColumns) {
        this.keyColumns = keyColumns;
    }

    public List<String> getReferencesColumns() {
        return referencesColumns;
    }

    public void setReferencesColumns(List<String> referencesColumns) {
        this.referencesColumns = referencesColumns;
    }
}
