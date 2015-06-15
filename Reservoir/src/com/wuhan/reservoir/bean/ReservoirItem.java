package com.wuhan.reservoir.bean;

/**
 * reservoir information item
 * 
 * {"STCD":"","RZ":"","STNM":"","OWPTN":"","DYP":"","TM":"","AMFLAG":"","PICURL"
 * :""},
 * 
 * @author chenyu
 * 
 */
public class ReservoirItem extends BaseInfo {

    /**
     * 资讯的图片链接
     */
    private String PICURL;

    public void setPICURL(String picurl) {
        this.PICURL = picurl;
    }

    public String getPICURL() {
        return this.PICURL;
    }
}
