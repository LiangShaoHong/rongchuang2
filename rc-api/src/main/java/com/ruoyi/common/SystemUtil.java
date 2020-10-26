package com.ruoyi.common;



import com.ruoyi.common.utils.JWTUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.redis.RedisService;

import com.ruoyi.user.domain.RcUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class SystemUtil {
    @Autowired
    private RedisService redisService;

    /**
     * 获取会员信息
     * @param request
     * @return
     */
    public RcUser getPlatformIdAndUserId(HttpServletRequest request) {
        // 获取请求的token
        String token = request.getHeader(Constants.X_TOKEN);
        if (!StringUtils.isEmpty(token)) {
            // 获取账号
            String userAccount = JWTUtil.getUsername(token);
            String userKey = Constants.DB_USER + userAccount;
             //判断是否存在该key
            if (redisService.exists(userKey, Constants.DB_USER)) {
                // 获取用户信息
                RcUser user = (RcUser) redisService.get(userKey, Constants.DB_USER);
                if (null != user) {
                    if (null == user.getId() || null == user.getPlatformId()){
                        return null;
                    }
                    return user;
                }
                return null;
            }
            return null;
        }
        return null;
    }
}
