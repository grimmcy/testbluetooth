package com.wuhan.reservoir.bean;

public class RequestResult {
    
    private int retCode;
    private String retMsg;
    
    public void setRetCode(int retCode){
        this.retCode = retCode;
    }
    
    public int getRetCode(){
        return this.retCode;
    }
    
    public void setRetMsg(String retMsg){
        this.retMsg = retMsg;
    }
    
    public String getRetMsg(String retMsg){
        return this.retMsg;
    }
    
}
