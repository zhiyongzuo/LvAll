package com.epro.roomdb;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author zhijiang
 * @Date 2019/6/25
 */
@Entity(tableName = "user_info")
public class UserInfoBean {

    /**
     * MM : QAZ@010578
     * GH : 010578
     * IS_WWD : 0
     * XM : 郭沐TEST
     * CSRLOCK : 0
     * FWZID : 001811
     * FWZ : (东区)天河营业厅南厅
     * ID : 010578
     * BM : (东区)天河营业厅南厅维修班
     */

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "csrid")
    private String ID;//员工登录ID
    @ColumnInfo(name = "gh")
    private String GH;//员工工号
    @ColumnInfo(name = "mm")
    private String MM;//密码
    @ColumnInfo(name = "iswwd")
    private String IS_WWD;//外委队标志
    @ColumnInfo(name = "xm")
    private String XM;//人员姓名
    @ColumnInfo(name = "csrlock")
    private String CSRLOCK;//锁定标志
    @ColumnInfo(name = "fwzid")
    private String FWZID;//管理站ID
    @ColumnInfo(name = "fwz")
    private String FWZ;//管理站名称
    @ColumnInfo(name = "bm")
    private String BM;//部门名称
    @ColumnInfo(name = "issavepwd")
    private String isSavePwd;//记住密码标识，1记住、0不记住
    @ColumnInfo(name = "overduedate")
    private String overdueDate;//密码过期日期 （初次登录日期+7，超过该日期，等到再次联网登录的时候就会修改密码过期日期）
    @ColumnInfo(name = "logindate")
    private String loginDate;//登录日期（每次联网登录成功都会修改登录日期）
    @ColumnInfo(name = "token")
    private String token;//登录TOKEN

    @NonNull
    public String getID() {
        return ID;
    }

    public void setID(@NonNull String ID) {
        this.ID = ID;
    }

    public String getGH() {
        return GH;
    }

    public void setGH(String GH) {
        this.GH = GH;
    }

    public String getMM() {
        return MM;
    }

    public void setMM(String MM) {
        this.MM = MM;
    }

    public String getIS_WWD() {
        return IS_WWD;
    }

    public void setIS_WWD(String IS_WWD) {
        this.IS_WWD = IS_WWD;
    }

    public String getXM() {
        return XM;
    }

    public void setXM(String XM) {
        this.XM = XM;
    }

    public String getCSRLOCK() {
        return CSRLOCK;
    }

    public void setCSRLOCK(String CSRLOCK) {
        this.CSRLOCK = CSRLOCK;
    }

    public String getFWZID() {
        return FWZID;
    }

    public void setFWZID(String FWZID) {
        this.FWZID = FWZID;
    }

    public String getFWZ() {
        return FWZ;
    }

    public void setFWZ(String FWZ) {
        this.FWZ = FWZ;
    }

    public String getBM() {
        return BM;
    }

    public void setBM(String BM) {
        this.BM = BM;
    }

    public String getIsSavePwd() {
        return isSavePwd;
    }

    public void setIsSavePwd(String isSavePwd) {
        this.isSavePwd = isSavePwd;
    }

    public String getOverdueDate() {
        return overdueDate;
    }

    public void setOverdueDate(String overdueDate) {
        this.overdueDate = overdueDate;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
