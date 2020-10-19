<<<<<<< HEAD:rc-server/src/main/java/com/ruoyi/service/impl/RcFrenchCurrencyOrderReleaseServiceImpl.java
package com.ruoyi.service.impl;
=======
package com.ruoyi.order.service.impl;
>>>>>>> cf72129ef6177bb8af29baaae1c428c6dfb7051b:rc-server/src/main/java/com/ruoyi/order/service/impl/RcFrenchCurrencyOrderReleaseServiceImpl.java

import com.ruoyi.domain.RcFrenchCurrencyOrderRelease;
import com.ruoyi.mapper.RcFrenchCurrencyOrderReleaseMapper;
import com.ruoyi.service.IRcFrenchCurrencyOrderReleaseService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
<<<<<<< HEAD:rc-server/src/main/java/com/ruoyi/service/impl/RcFrenchCurrencyOrderReleaseServiceImpl.java

import java.util.List;
=======
import com.ruoyi.order.mapper.RcFrenchCurrencyOrderReleaseMapper;
import com.ruoyi.order.domain.RcFrenchCurrencyOrderRelease;
import com.ruoyi.order.service.IRcFrenchCurrencyOrderReleaseService;
import com.ruoyi.common.core.text.Convert;
>>>>>>> cf72129ef6177bb8af29baaae1c428c6dfb7051b:rc-server/src/main/java/com/ruoyi/order/service/impl/RcFrenchCurrencyOrderReleaseServiceImpl.java

/**
 * 法币订单发布Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-10-19
 */
@Service
public class RcFrenchCurrencyOrderReleaseServiceImpl implements IRcFrenchCurrencyOrderReleaseService 
{
    @Autowired
    private RcFrenchCurrencyOrderReleaseMapper rcFrenchCurrencyOrderReleaseMapper;

    /**
     * 查询法币订单发布
     * 
     * @param id 法币订单发布ID
     * @return 法币订单发布
     */
    @Override
    public RcFrenchCurrencyOrderRelease selectRcFrenchCurrencyOrderReleaseById(Long id)
    {
        return rcFrenchCurrencyOrderReleaseMapper.selectRcFrenchCurrencyOrderReleaseById(id);
    }

    /**
     * 查询法币订单发布列表
     * 
     * @param rcFrenchCurrencyOrderRelease 法币订单发布
     * @return 法币订单发布
     */
    @Override
    public List<RcFrenchCurrencyOrderRelease> selectRcFrenchCurrencyOrderReleaseList(RcFrenchCurrencyOrderRelease rcFrenchCurrencyOrderRelease)
    {
        return rcFrenchCurrencyOrderReleaseMapper.selectRcFrenchCurrencyOrderReleaseList(rcFrenchCurrencyOrderRelease);
    }

    /**
     * 新增法币订单发布
     * 
     * @param rcFrenchCurrencyOrderRelease 法币订单发布
     * @return 结果
     */
    @Override
    public int insertRcFrenchCurrencyOrderRelease(RcFrenchCurrencyOrderRelease rcFrenchCurrencyOrderRelease)
    {
        return rcFrenchCurrencyOrderReleaseMapper.insertRcFrenchCurrencyOrderRelease(rcFrenchCurrencyOrderRelease);
    }

    /**
     * 修改法币订单发布
     * 
     * @param rcFrenchCurrencyOrderRelease 法币订单发布
     * @return 结果
     */
    @Override
    public int updateRcFrenchCurrencyOrderRelease(RcFrenchCurrencyOrderRelease rcFrenchCurrencyOrderRelease)
    {
        return rcFrenchCurrencyOrderReleaseMapper.updateRcFrenchCurrencyOrderRelease(rcFrenchCurrencyOrderRelease);
    }

    /**
     * 删除法币订单发布对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRcFrenchCurrencyOrderReleaseByIds(String ids)
    {
        return rcFrenchCurrencyOrderReleaseMapper.deleteRcFrenchCurrencyOrderReleaseByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除法币订单发布信息
     * 
     * @param id 法币订单发布ID
     * @return 结果
     */
    @Override
    public int deleteRcFrenchCurrencyOrderReleaseById(Long id)
    {
        return rcFrenchCurrencyOrderReleaseMapper.deleteRcFrenchCurrencyOrderReleaseById(id);
    }
}
