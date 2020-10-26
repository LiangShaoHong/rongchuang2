package com.ruoyi.user.domain;

import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户注册对象 rc_user
 * 
 * @author ruoyi
 * @date 2020-10-22
 */
public class RcUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */

    private Long id;

    /** 手机号 */
    @Excel(name = "手机号")
    private String mobile;

    /** 信用分 */
    @Excel(name = "信用分")
    private Long credit;

    /** 钱包金额 */
    @Excel(name = "钱包金额")
    private BigDecimal money;

    /** 账号 */
    @Excel(name = "账号")
    private String account;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    /** 密钥 */
    @Excel(name = "密钥")
    private String salt;

    /** 安全码 */
    @Excel(name = "安全码")
    private Long safeword;

    /** 选择的语言 */
    @Excel(name = "选择的语言")
    private String language;

    /** 是否在线 */
    @Excel(name = "是否在线")
    private Integer online;

    /** 注册时间 */
    @Excel(name = "注册时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date registertime;

    /** 是否派单 */
    @Excel(name = "是否派单")
    private Integer paidan;

    /** 上级id */
    @Excel(name = "上级id")
    private Long parentid;

    /** 身份 */
    @Excel(name = "身份")
    private String identity;

    /** 邀请码 */
    @Excel(name = "邀请码")
    private String invitation;

    /** 用户token */
    @Excel(name = "用户token")
    private String token;

    /** 平台id */
    @Excel(name = "平台id")
    private String platformId;



    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMobile(String mobile) 
    {
        this.mobile = mobile;
    }

    public String getMobile() 
    {
        return mobile;
    }
    public void setCredit(Long credit) 
    {
        this.credit = credit;
    }

    public Long getCredit() 
    {
        return credit;
    }
    public void setMoney(BigDecimal money) 
    {
        this.money = money;
    }

    public BigDecimal getMoney() 
    {
        return money;
    }
    public void setAccount(String account) 
    {
        this.account = account;
    }

    public String getAccount() 
    {
        return account;
    }
    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getPassword() 
    {
        return password;
    }
    public void setSalt(String salt) 
    {
        this.salt = salt;
    }

    public String getSalt() 
    {
        return salt;
    }
    public void setSafeword(Long safeword) 
    {
        this.safeword = safeword;
    }

    public Long getSafeword() 
    {
        return safeword;
    }
    public void setLanguage(String language) 
    {
        this.language = language;
    }

    public String getLanguage() 
    {
        return language;
    }
    public void setOnline(Integer online) 
    {
        this.online = online;
    }

    public Integer getOnline() 
    {
        return online;
    }
    public void setRegistertime(Date registertime) 
    {
        this.registertime = registertime;
    }

    public Date getRegistertime() 
    {
        return registertime;
    }
    public void setPaidan(Integer paidan) 
    {
        this.paidan = paidan;
    }

    public Integer getPaidan() 
    {
        return paidan;
    }
    public void setParentid(Long parentid) 
    {
        this.parentid = parentid;
    }

    public Long getParentid() 
    {
        return parentid;
    }
    public void setIdentity(String identity) 
    {
        this.identity = identity;
    }

    public String getIdentity() 
    {
        return identity;
    }
    public void setInvitation(String invitation) 
    {
        this.invitation = invitation;
    }

    public String getInvitation() 
    {
        return invitation;
    }
    public void setToken(String token) 
    {
        this.token = token;
    }

    public String getToken() 
    {
        return token;
    }

    public RcUser(Long id, String mobile, Long credit, BigDecimal money, String account, String password, String salt, Long safeword, String language, Integer online, Date registertime, Integer paidan, Long parentid, String identity, String invitation, String token, String platformId) {
        this.id = id;
        this.mobile = mobile;
        this.credit = credit;
        this.money = money;
        this.account = account;
        this.password = password;
        this.salt = salt;
        this.safeword = safeword;
        this.language = language;
        this.online = online;
        this.registertime = registertime;
        this.paidan = paidan;
        this.parentid = parentid;
        this.identity = identity;
        this.invitation = invitation;
        this.token = token;
        this.platformId = platformId;
    }

    public void setPlatformId(String platformId)
    {
        this.platformId = platformId;
    }

    public String getPlatformId() 
    {
        return platformId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("mobile", getMobile())
            .append("credit", getCredit())
            .append("money", getMoney())
            .append("account", getAccount())
            .append("password", getPassword())
            .append("salt", getSalt())
            .append("safeword", getSafeword())
            .append("language", getLanguage())
            .append("online", getOnline())
            .append("registertime", getRegistertime())
            .append("paidan", getPaidan())
            .append("parentid", getParentid())
            .append("identity", getIdentity())
            .append("invitation", getInvitation())
            .append("token", getToken())
            .append("platformId", getPlatformId())
            .toString();
    }
}
