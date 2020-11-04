package com.ruoyi.user.service;

import com.ruoyi.common.Result;
import com.ruoyi.user.domain.RcUser;

public interface ICollectionService {


    /**
     * 个人收款账户列表接口
     *
     * @param userId 用户id
     * @return
     */
    public Result getPersonalCollectionAccountList(Long userId);


    /**
     * 收款账户类型列表接口
     *
     * @return
     */
    public Result getCollectionAccountTypeList();

    /**
     * 收款账户类型属性列表接口
     *
     * @param dictValue 字典类型
     * @return
     */
    public Result getCollectionAccountTypeAttributeList(String dictValue);

    /**
     * 新增个人收款账户接口
     *
     * @param userId 用户id
     * @param json   json
     * @param qrcode 二维码
     * @return
     */
    public Result addPersonalCollection(Long userId, String dictType, String json, String qrcode);


    /**
     * 获取个人收款账户详情接口
     *
     * @param userId 用户id
     * @param id     业务id
     * @return
     */
    public Result getPersonalCollectionDetails(Long userId, Long id);


    /**
     * 修改个人收款账户接口
     *
     * @param userId 用户id
     * @param id     业务id
     * @param json   json
     * @param qrcode 二维码截图
     * @return
     */
    public Result updatePersonalCollection(Long userId, Long id, String dictType, String json, String qrcode);

    /**
     * 删除个人收款账户接口
     *
     * @param userId 用户id
     * @param id     业务id
     * @return
     */
    public Result deletePersonalCollection(Long userId, Long id);


}
