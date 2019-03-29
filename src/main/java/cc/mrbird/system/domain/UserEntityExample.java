package cc.mrbird.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: UserEntity
 * @Description: 用戶实体类
 * @author:
 * @date:
 */
@Entity
@Table(name = "t_pm_user")

public class UserEntityExample {

    private static final long serialVersionUID = 1L;

    /**
     * 账户状态
     */
    public static final String FISFORBIDDEN_VALID = "1";

    public static final String FISFORBIDDEN_LOCK = "0";

    @Id
    @Column(name = "FID")
    private String fid;

    @Transient
    private String username;

    @Transient
    private String passWord;

    //角色名称
    @Column(name = "FNUMBER")
    private String fnumber;

    //姓名
    @Column(name = "FNAME")
    private String fname;

    //描述
    @Column(name = "FDESCRIPTION")
    private String fdescription;

    //加密后密码
    @Column(name = "FPASSWORD")
    private String fpassword;

    //删除标记
    @Column(name = "FISDELETE")
    private Integer fisdelete;

    //是否禁用   (0 禁用   1 启用)
    @Column(name = "FISFORBIDDEN")
    private Integer fisforbidden;

    //生效时间
    @Column(name = "FEFFECTIVEDATE")
    private Date feffectivedate;

    //失效时间
    @Column(name = "FINVALIDATIONDATE")
    private Date finvalidationdate;

    //关联职员ID
    @Column(name = "FPERSONID")
    private String fpersonid;

    //关联职员名称
    @Column(name = "FPERSONNAME")
    private String fpersonname;

    //密码生效日期
    @Column(name = "FPWEFFECTIVEDATE")
    private Date fpweffectivedate;

    //缺省组织ID
    @Column(name = "FDEFORGUNITID")
    private String fdeforgunitid;

    //缺省组织名称
    @Column(name = "FDEFORGUNITNAME")
    private String fdeforgunitname;

    //手机号
    @Column(name = "FCELL")
    private String fcell;

    //创建时间
    @Column(name = "FCREATETIME")
    private Date fcreatetime;

    //最后更新时间
    @Column(name = "FLASTUPDATETIME")
    private Date flastupdatetime;

    //EAS提交时间
    @Column(name = "FEASTIME")
    private Date feastime;

    //OMS提取标志，提取后填写
    @Column(name = "FOMSFLAG")
    private Integer fomsflag;

    //OMS提取时间
    @Column(name = "FOMSTIME")
    private Date fomstime;

    //CRM提取标志，提取后填写
    @Column(name = "FCRMFLAG")
    private Integer fcrmflag;

    //CRM提取时间
    @Column(name = "FCRMTIME")
    private Date fcrmtime;

    //新增、修改标志
    @Column(name = "CUFLAG")
    private Integer cuflag;

    //新增、修改标志
    @Column(name = "FTYPE")
    private String ftype;

    @JsonIgnore
    public String getPk() {
        return getFid();
    }

    public String getFtype() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype;
    }

    public Integer getCuflag() {
        return cuflag;
    }

    public void setCuflag(Integer cuflag) {
        this.cuflag = cuflag;
    }

    public Date getFcrmtime() {
        return fcrmtime;
    }

    public void setFcrmtime(Date fcrmtime) {
        this.fcrmtime = fcrmtime;
    }

    public Integer getFcrmflag() {
        return fcrmflag;
    }

    public void setFcrmflag(Integer fcrmflag) {
        this.fcrmflag = fcrmflag;
    }

    public Date getFomstime() {
        return fomstime;
    }

    public void setFomstime(Date fomstime) {
        this.fomstime = fomstime;
    }

    public Integer getFomsflag() {
        return fomsflag;
    }

    public void setFomsflag(Integer fomsflag) {
        this.fomsflag = fomsflag;
    }

    public Date getFeastime() {
        return feastime;
    }

    public void setFeastime(Date feastime) {
        this.feastime = feastime;
    }

    public Date getFlastupdatetime() {
        return flastupdatetime;
    }

    public void setFlastupdatetime(Date flastupdatetime) {
        this.flastupdatetime = flastupdatetime;
    }

    public Date getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(Date fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    public String getFcell() {
        return fcell;
    }

    public void setFcell(String fcell) {
        this.fcell = fcell;
    }

    public String getFdeforgunitname() {
        return fdeforgunitname;
    }

    public void setFdeforgunitname(String fdeforgunitname) {
        this.fdeforgunitname = fdeforgunitname;
    }

    public String getFdeforgunitid() {
        return fdeforgunitid;
    }

    public void setFdeforgunitid(String fdeforgunitid) {
        this.fdeforgunitid = fdeforgunitid;
    }

    public Date getFpweffectivedate() {
        return fpweffectivedate;
    }

    public void setFpweffectivedate(Date fpweffectivedate) {
        this.fpweffectivedate = fpweffectivedate;
    }

    public String getFpersonname() {
        return fpersonname;
    }

    public void setFpersonname(String fpersonname) {
        this.fpersonname = fpersonname;
    }

    public String getFpersonid() {
        return fpersonid;
    }

    public void setFpersonid(String fpersonid) {
        this.fpersonid = fpersonid;
    }

    public Date getFinvalidationdate() {
        return finvalidationdate;
    }

    public void setFinvalidationdate(Date finvalidationdate) {
        this.finvalidationdate = finvalidationdate;
    }

    public Date getFeffectivedate() {
        return feffectivedate;
    }

    public void setFeffectivedate(Date feffectivedate) {
        this.feffectivedate = feffectivedate;
    }

    public Integer getFisforbidden() {
        return fisforbidden;
    }

    public void setFisforbidden(Integer fisforbidden) {
        this.fisforbidden = fisforbidden;
    }

    public Integer getFisdelete() {
        return fisdelete;
    }

    public void setFisdelete(Integer fisdelete) {
        this.fisdelete = fisdelete;
    }

    public String getFpassword() {
        return fpassword;
    }

    public void setFpassword(String fpassword) {
        this.fpassword = fpassword;
    }

    public String getFdescription() {
        return fdescription;
    }

    public void setFdescription(String fdescription) {
        this.fdescription = fdescription;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFnumber() {
        return fnumber;
    }

    public void setFnumber(String fnumber) {
        this.fnumber = fnumber;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }
}
