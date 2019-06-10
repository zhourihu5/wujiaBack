package com.wj.admin.utils;

/**
 *
 * 本地日志枚举
 * 由于配置了4个文件存放不同日志，分别为平台日志（${project.name}_platform.log）、 业务日志（${project.name}_bussiness.log）、错误日志（${project.name}_exception.log）、DB 日志（${project.name}_db.log），
 * 文件夹外面为运行日志，不同文件日志级别不一样，因此程序员在开发时候需要注意引入不同日志，
 * 也就是说开发出现的日志，程序员可以进行分类，分别调用公共方法即可。
 * 公共类编辑如下；
 *
 * @author 1632369
 *
 */
public enum LogEnum {

    BUSSINESS("bussiness"),PLATFORM("platform"),DB("db"),EXCEPTION("exception");


    private String category;


    LogEnum(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



}