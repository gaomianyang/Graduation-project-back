package com.example.sqltest.enums;

/**
 * Created by T016 on 2019/3/12.
 * @author T016
 */
public enum UserEnum {
    /**
     * 用于判断是否是admin分组
     */
    ADMIN("security-role", 1),PROCESSVALUE("gsdf馘hcvcvbcxsrtgbdgbcg瀓fcxg", 2);
    // 成员变量
    private String name;
    private int index;

    // 构造方法
    private UserEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (UserEnum c : UserEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
