package com.ruoyi.user.mapperplus;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.user.domain.RcUser;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;


public interface RcUserMapperPlus extends BaseMapper<RcUser> {


    @Override
    Integer selectCount(Wrapper<RcUser> queryWrapper);
}
