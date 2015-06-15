package com.wuhan.reservoir.bean;

/**
 * 
 * basic bean for reservoir
 * 
 * @author chenyu
 * 
 */
public class BaseInfo {
    /**
     * 水库水情表
     * */
    private String TableName_1 = "ST_RSVR_R";

    /**
     * 降水量表
     * **/
    private String TableName_2 = "ST_PPTN_R";

    /**
     * 测站基本属性表
     * */
    private String TableName_3 = "ST_STBPRP_B";

    /**
     * 行政区域代码表表结构
     * **/
    private String TableName_4 = "ST_ADDVCD_D";

    /**
     * 库（湖）站防洪指标表
     * **/
    private String TableName_5 = "ST_RSVRFCCH_B";

    /**
     * 包类型
     */
    private int PKGTYPE;// 包类型

    /**
     * 测站编码
     */
    private String STCD;

    /**
     * 水库水位（米）
     */
    private int RZ;

    /**
     * 汛限水位（米）
     */
    private int FSLTDZ;

    /**
     * 蓄水量（库容）（万立方米）
     */
    private int W;

    /**
     * 入库流量（立方米/秒）
     */
    private int INQ;

    /**
     * 出库流量（立方米/秒）
     */
    private int OTQ;

    /**
     * 测站名称（水库名称）
     */
    private String STNM;

    /**
     * 库水势 **落：4 涨：5 平：6
     */
    private int OWPTN;

    /**
     * 水位监测时间
     */
    private int TM;

    /**
     * 预警标志 正常 0 ；超汛限 1
     */
    private int AMFLAG;

    /**
     * 时段降水量 (需要24小时降水量,时段查询累加)
     */
    private int DRP;

    /**
     * 日降水量(昨天8点到今天8点，24小时) 毫米
     */
    private int DYP;

    /**
     * 行政区划码
     */
    private String ADDVCD;

    /**
     * 行政区划名 目前未确定区域划分层级，国标GB/T2260
     */
    private String ADDVNM;

    /**
     * 站类 气象站 蒸发站 堰闸水文站 潮位站 泵站 墒情站 MM BB DD TT DP SS 雨量站 河道水文站 水库水文站 地下水站 分洪水位站
     * PP ZZ RR ZG ZB
     */
    private String STTP;

    /**
     * 小二型 小一型 中型 大二型 大一型
     * 
     * 1 2 3 4 5
     */
    private int RSVRTP;// :水库类型

    /**
     * 设置水库类型
     * 
     * @param rsvstp
     */
    public void setRSVRTP(int rsvstp) {
        this.RSVRTP = rsvstp;
    }

    public int getRSVRTP() {
        return this.RSVRTP;
    }

    /** 包类型的设置与取值 */
    public void setPKGTYPE(int pkgtype) {
        this.PKGTYPE = pkgtype;
    }

    public int getPKGTYPE() {
        return this.PKGTYPE;
    }

    /**
     * 设置测站编码
     * 
     * @param stcd
     */
    public void setSTCD(String stcd) {
        this.STCD = stcd;
    }

    public String getSTCD() {
        return this.STCD;
    }

    /**
     * 设置水库水位
     * 
     * @param rz
     *            int
     */
    public void setRZ(int rz) {
        this.RZ = rz;
    }

    public int getRZ() {
        return this.RZ;
    }

    /**
     * 设置汛限水位
     * 
     * @param fsltdz
     */
    public void setFSLTDZ(int fsltdz) {
        this.FSLTDZ = fsltdz;
    }

    public int getFSLTDZ() {
        return this.FSLTDZ;
    }

    /**
     * 获取水库水位
     * 
     * @param w
     */
    public void setW(int w) {
        this.W = w;
    }

    public int getW() {
        return this.W;
    }

    /**
     * 设置入库流量
     * 
     * @param inq
     */
    public void setINQ(int inq) {
        this.INQ = inq;
    }

    public int getINQ() {
        return this.INQ;
    }

    /**
     * 设置出库流量
     * 
     * @param otq
     */
    public void setOTQ(int otq) {
        this.OTQ = otq;
    }

    public int getOTQ() {
        return this.OTQ;
    }

    /**
     * 设置测站名称
     * 
     * @param stnm
     */
    public void setSTNM(String stnm) {
        this.STNM = stnm;
    }

    public String getSTNM() {
        return this.STNM;
    }

    /**
     * 设置库水势
     */
    public void setOWPTN(int owptn) {
        this.OWPTN = owptn;
    }

    public int getOWPTN() {
        return this.OWPTN;
    }

    /**
     * 设置站类
     * 
     * @param sttp
     */
    public void setSTTP(String sttp) {
        this.STTP = sttp;
    }

    public String getSTTP() {
        return this.STTP;
    }

    /**
     * 设置水位监测时间
     * 
     * @param tm
     */
    public void setTM(int tm) {
        this.TM = tm;
    }

    public int getTM() {
        return this.TM;
    }

    /**
     * 设置预警标志
     * 
     * @param amflag
     */
    public void setAMFLAG(int amflag) {
        this.AMFLAG = amflag;
    }

    public int getAMFLAG() {
        return this.AMFLAG;
    }

    /**
     * 设置时段降水量
     * 
     * @param drp
     */
    public void setDRP(int drp) {
        this.DRP = drp;
    }

    public int getDRP() {
        return this.DRP;
    }

    /**
     * 设置行政区划名
     * 
     * @param addvnm
     */
    public void setADDVNM(String addvnm) {
        this.ADDVNM = addvnm;
    }

    public String getADDVNM() {
        return this.ADDVNM;
    }

    /**
     * 设置行政区划码
     * 
     * @param addvcd
     */
    public void setADDVCD(String addvcd) {
        this.ADDVCD = addvcd;
    }

    public String getADDVCD() {
        return this.ADDVCD;
    }

    /**
     * 设置日降水量
     * 
     * @param dyp
     */
    public void setDYP(int dyp) {
        this.DYP = dyp;
    }

    public int getDYP() {
        return this.DYP;
    }

}
