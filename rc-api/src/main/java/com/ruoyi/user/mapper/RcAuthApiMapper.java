package com.ruoyi.user.mapper;

import com.ruoyi.common.json.JSONObject;
import com.ruoyi.user.domain.RcApiAuth;

/**
 * 用户认证api Mapper接口
 * 
 * @author xiaoyu
 * @date 2020-10-30
 */
public interface RcAuthApiMapper
{
    int insertRcAuth(RcApiAuth domain);
    JSONObject selectRcAuth(Long id);
}
