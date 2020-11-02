package com.ruoyi.user.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.Constants;
import com.ruoyi.common.Result;
import com.ruoyi.common.jms.JmsConstant;
import com.ruoyi.common.jms.SenderService;
import com.ruoyi.common.push.PushService;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.framework.web.service.ConfigService;
import com.ruoyi.framework.web.service.DictService;
import com.ruoyi.order.domain.FrenchCurrencyOrder;
import com.ruoyi.order.domain.Profit;
import com.ruoyi.order.domain.RcFrenchCurrencyOrder;
import com.ruoyi.order.domain.RcFrenchCurrencyOrderRelease;
import com.ruoyi.order.mapper.LegalCurrencyMapper;
import com.ruoyi.order.mapper.RcFrenchCurrencyOrderMapper;
import com.ruoyi.order.mapper.RcFrenchCurrencyOrderReleaseMapper;
import com.ruoyi.order.service.LegalCurrencyService;
import com.ruoyi.order.service.impl.LegalCurrencyServiceImpl;
import com.ruoyi.system.domain.SysDictData;
import com.ruoyi.system.domain.SysDictType;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.ISysDictTypeService;
import com.ruoyi.user.domain.RcAccount;
import com.ruoyi.user.domain.RcUser;
import com.ruoyi.user.mapper.CollectionMapper;
import com.ruoyi.user.service.ICollectionService;
import com.ruoyi.user.service.IRcUserService;
import com.ruoyi.user.service.IUserMoneyService;
import com.ruoyi.user.service.IUserProfitService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class CollectionServiceImpl implements ICollectionService {

    @Autowired
    private static final Logger log = LoggerFactory.getLogger(CollectionServiceImpl.class);

    @Autowired
    private CollectionMapper collectionMapper;

    @Autowired
    private DictService dictService;

    @Autowired
    private ISysDictTypeService iSysDictTypeService;

    @Override
    public Result getPersonalCollectionAccountList(Long userId) {
        log.info("调用个人收款账户列表接口");
        List<RcAccount> rcAccountList = collectionMapper.getPersonalCollectionAccountList(userId);
        return new Result().isOk().data(rcAccountList);
    }

    @Override
    public Result getCollectionAccountTypeList() {
        log.info("调用收款账户类型列表接口");
        List<SysDictData> sysDictDataList = dictService.getType("rc_collection_account_type");
        List list = new ArrayList();
        for (SysDictData sysDictData : sysDictDataList) {

            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("dictType", sysDictData.getDictType());
            jsonObject.put("id", sysDictData.getDictValue());
            jsonObject.put("text", sysDictData.getDictLabel());
            jsonObject.put("default", sysDictData.getDefault());
            list.add(jsonObject);
        }
        return new Result().isOk().data(list);
    }


    @Override
    public Result getCollectionAccountTypeAttributeList(String dictValue) {
        log.info("调用收款账户类型属性列表接口");
        List<SysDictData> sysDictDataList = dictService.getType("rc_attribute_" + dictValue);
        List list = new ArrayList();
        for (SysDictData sysDictData : sysDictDataList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dictType", sysDictData.getDictType());
            jsonObject.put("id", sysDictData.getDictValue());
            jsonObject.put("text", sysDictData.getDictLabel());
            list.add(jsonObject);
        }
        return new Result().isOk().data(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result addPersonalCollection(Long userId, String dictType, String json, String qrcode) {
        log.info("调用新增个人收款账户接口");
        JSONObject jsonObject = JSONArray.parseObject(json);
        SysDictType sysDictType = iSysDictTypeService.selectDictTypeByType(dictType);
        String region = sysDictType.getRemark();
        Integer state = collectionMapper.addPersonalCollection(userId, json, qrcode, region);
        if (state <= 0) {
            return new Result().isFail().msg("提交失败");
        }
        return new Result().isOk().data("提交成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updatePersonalCollection(Long userId, Long id, String dictType, String json, String qrcode) {
        log.info("调用修改个人收款账户接口");
        JSONObject jsonObject = JSONArray.parseObject(json);
        SysDictType sysDictType = iSysDictTypeService.selectDictTypeByType(dictType);
        String region = sysDictType.getRemark();
        Integer state = collectionMapper.updatePersonalCollection(userId, id, json, qrcode, region);
        if (state <= 0) {
            return new Result().isFail().msg("提交失败");
        }
        return new Result().isOk().data("提交成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result deletePersonalCollection(Long userId, Long id) {
        log.info("调用删除个人收款账户接口");
        Integer state = collectionMapper.deletePersonalCollection(userId, id);
        if (state <= 0) {
            return new Result().isFail().msg("提交失败");
        }
        return new Result().isOk().data("提交成功");
    }

}
