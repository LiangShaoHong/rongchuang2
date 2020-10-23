package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 用户对象 user
 * 
 * @author csm
 * @date 2020-08-10
 */
public class User extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private String id;

    /** 平台id */
    @Excel(name = "平台id")
    private String platformId;

    /** 显示给外界的id,也作为推荐码使用 */
    @Excel(name = "显示给外界的id,也作为推荐码使用")
    private String showId;

    /** 昵称 */
    @Excel(name = "昵称")
    private String name;

    /** 账号 */
    @Excel(name = "账号")
    private String account;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String tel;

    /** 密码 */
    @Excel(name = "密码")
    private String pass;

    /** 支付密码 */
    @Excel(name = "支付密码")
    private String safePass;

    /** 头像 */
    @Excel(name = "头像")
    private String headImg;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 性别1男2女 */
    @Excel(name = "性别1男2女")
    private Integer gender;

    /** 等级 */
    @Excel(name = "等级id")
    private String rankId;

    /** 周期（-1为无期限，大于0则有期限，0代表已过期） */
    @Excel(name = "周期", readConverterExp = "-=1为无期限，大于0则有期限，0代表已过期")
    private Integer times;

    /** 是否虚拟号(0否1是) */
    @Excel(name = "是否虚拟号(0否1是)")
    private Integer isXuNi;

    /** 是否可用(0否1是) */
    @Excel(name = "是否可用(0否1是)")
    private Integer isUse;

    /** 是否删除(0否1是) */
    @Excel(name = "是否删除(0否1是)")
    private Integer isDel;

    /** 修改时间 */
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date editTime;

    /** ip */
    @Excel(name = "ip")
    private String ip;

    /** 登录时间 */
    @Excel(name = "登录时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date loginTime;

    /** 备注 */
    @Excel(name = "备注")
    private String memo;

    /** 过期时间 */
    @Excel(name = "过期时间")
    private Date expireTime;

    /** 可领任务数量 */
    @Excel(name = "可领任务数量")
    private Integer taskLimit;

    private Integer agentId;

    private String agentName;

    private Integer travellingTrader;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", platformId='" + platformId + '\'' +
                ", showId='" + showId + '\'' +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", tel='" + tel + '\'' +
                ", pass='" + pass + '\'' +
                ", safePass='" + safePass + '\'' +
                ", headImg='" + headImg + '\'' +
                ", address='" + address + '\'' +
                ", gender=" + gender +
                ", rankId='" + rankId + '\'' +
                ", times=" + times +
                ", isXuNi=" + isXuNi +
                ", isUse=" + isUse +
                ", isDel=" + isDel +
                ", editTime=" + editTime +
                ", ip='" + ip + '\'' +
                ", loginTime=" + loginTime +
                ", memo='" + memo + '\'' +
                ", expireTime=" + expireTime +
                ", taskLimit=" + taskLimit +
                ", agentId=" + agentId +
                ", agentName='" + agentName + '\'' +
                ", travellingTrader=" + travellingTrader +
                '}';
    }

    public Integer getTravellingTrader() {
        return travellingTrader;
    }

    public void setTravellingTrader(Integer travellingTrader) {
        this.travellingTrader = travellingTrader;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public void setShowId(String showId)
    {
        this.showId = showId;
    }

    public String getShowId() 
    {
        return showId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setAccount(String account) 
    {
        this.account = account;
    }

    public String getAccount() 
    {
        return account;
    }
    public void setTel(String tel) 
    {
        this.tel = tel;
    }

    public String getTel() 
    {
        return tel;
    }
    public void setPass(String pass) 
    {
        this.pass = pass;
    }

    public String getPass() 
    {
        return pass;
    }
    public void setSafePass(String safePass) 
    {
        this.safePass = safePass;
    }

    public String getSafePass() 
    {
        return safePass;
    }
    public void setHeadImg(String headImg) 
    {
        this.headImg = headImg;
    }

    public String getHeadImg() 
    {
        return headImg;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setGender(Integer gender) 
    {
        this.gender = gender;
    }

    public Integer getGender() 
    {
        return gender;
    }

    public String getRankId() {
        return rankId;
    }

    public void setRankId(String rankId) {
        this.rankId = rankId;
    }

    public void setTimes(Integer times)
    {
        this.times = times;
    }

    public Integer getTimes() 
    {
        return times;
    }
    public void setIsXuNi(Integer isXuNi) 
    {
        this.isXuNi = isXuNi;
    }

    public Integer getIsXuNi() 
    {
        return isXuNi;
    }
    public void setIsUse(Integer isUse) 
    {
        this.isUse = isUse;
    }

    public Integer getIsUse() 
    {
        return isUse;
    }
    public void setIsDel(Integer isDel) 
    {
        this.isDel = isDel;
    }

    public Integer getIsDel() 
    {
        return isDel;
    }
    public void setEditTime(Date editTime) 
    {
        this.editTime = editTime;
    }

    public Date getEditTime() 
    {
        return editTime;
    }
    public void setIp(String ip) 
    {
        this.ip = ip;
    }

    public String getIp() 
    {
        return ip;
    }
    public void setLoginTime(Date loginTime) 
    {
        this.loginTime = loginTime;
    }

    public Date getLoginTime() 
    {
        return loginTime;
    }
    public void setMemo(String memo) 
    {
        this.memo = memo;
    }

    public String getMemo() 
    {
        return memo;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getTaskLimit() {
        return taskLimit;
    }

    public void setTaskLimit(Integer taskLimit) {
        this.taskLimit = taskLimit;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }


    //全参构造法
    public User(String id, String platformId, String showId, String name, String account, String tel, String pass, String safePass, String headImg, String address, Integer gender, String rankId, Integer times, Integer isXuNi, Integer isUse, Integer isDel, Date editTime, String ip, Date loginTime, String memo, Date expireTime, Integer taskLimit) {
        this.id = id;
        this.platformId = platformId;
        this.showId = showId;
        this.name = name;
        this.account = account;
        this.tel = tel;
        this.pass = pass;
        this.safePass = safePass;
        this.headImg = headImg;
        this.address = address;
        this.gender = gender;
        this.rankId = rankId;
        this.times = times;
        this.isXuNi = isXuNi;
        this.isUse = isUse;
        this.isDel = isDel;
        this.editTime = editTime;
        this.ip = ip;
        this.loginTime = loginTime;
        this.memo = memo;
        this.expireTime = expireTime;
        this.taskLimit = taskLimit;
    }

    public User() {
    }
}
