package com.bin.yuan.fpsdancer;

/**
 * Created by yuanbin on 2018/3/27.
 */

public class ActivityInfo {
    String id;//开发者id

    String name;//名称

    String departmentId;//部门id

    String departmentName;//部门名称

    String description;//描述

    String formatStr;//json格式数据

    boolean useFormatStr;

    String className;

    String classSimpleName;


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

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormatStr() {
        return formatStr;
    }

    public void setFormatStr(String formatStr) {
        this.formatStr = formatStr;
    }

    public boolean isUseFormatStr() {
        return useFormatStr;
    }

    public void setUseFormatStr(boolean useFormatStr) {
        this.useFormatStr = useFormatStr;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassSimpleName() {
        return classSimpleName;
    }

    public void setClassSimpleName(String classSimpleName) {
        this.classSimpleName = classSimpleName;
    }
}
