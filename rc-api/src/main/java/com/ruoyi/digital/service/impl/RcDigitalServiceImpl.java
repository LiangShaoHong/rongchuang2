package com.ruoyi.digital.service.impl;

import com.ruoyi.common.utils.ResultDto;
import com.ruoyi.digital.domain.RcExchangeRateDigital;
import com.ruoyi.digital.mapper.RcDigitalMapper;
import com.ruoyi.digital.service.IRcDigitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * @author xiaoxia
 */
@Service
public class RcDigitalServiceImpl implements IRcDigitalService {

    @Autowired
    private RcDigitalMapper rcDigitalMapper;

    @Override
    public ResultDto selectRcExchangeRateList() {
        List<RcExchangeRateDigital> profit = rcDigitalMapper.selectRcExchangeRateList();
        ResultDto resultDto = new ResultDto(profit);
        return resultDto;
    }

}
