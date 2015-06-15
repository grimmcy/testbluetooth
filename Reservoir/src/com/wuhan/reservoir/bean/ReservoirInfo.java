package com.wuhan.reservoir.bean;

import java.util.List;

public class ReservoirInfo {
    
    
    
    public static String pkg = "PKGTYPE";

    /**
     * 包类型
     */
    private int PKGTYPE;

    /**
     * 请求索引，第几次请求
     */
    private int GROUPCOUNT;
    
    /**
     * 用户账号
     */
    private String ACCOUNT;



    /**
     * 数据-数组 ReservoirItem
     */
    private List<ReservoirItem> DATA;

    public void setPKGTYPE(int type) {
        this.PKGTYPE = type;
    }

    public int getPKGTYPE() {
        return this.PKGTYPE;
    }

    public void setAccount(String accout) {
        this.ACCOUNT = accout;
    }

    public String getAccount() {
        return this.ACCOUNT;
    }

    public void setGrounpcount(int grounpcount) {
        this.GROUPCOUNT = grounpcount;
    }

    public int getGrounpcount() {
        return this.GROUPCOUNT;
    }

    public void setData(List<ReservoirItem> data) {
        this.DATA = data;
    }

    public List<ReservoirItem> getData() {
        return this.DATA;
    }
}
