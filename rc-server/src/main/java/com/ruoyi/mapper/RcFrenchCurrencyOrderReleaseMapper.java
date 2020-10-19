<<<<<<< HEAD:rc-server/src/main/java/com/ruoyi/mapper/RcFrenchCurrencyOrderReleaseMapper.java
package com.ruoyi.mapper;

import com.ruoyi.domain.RcFrenchCurrencyOrderRelease;

import java.util.List;
=======
package com.ruoyi.order.mapper;

import java.util.List;
import com.ruoyi.order.domain.RcFrenchCurrencyOrderRelease;
>>>>>>> cf72129ef6177bb8af29baaae1c428c6dfb7051b:rc-server/src/main/java/com/ruoyi/order/mapper/RcFrenchCurrencyOrderReleaseMapper.java

/**
 * 法币订单发布Mapper接口
 * 
 * @author ruoyi
 * @date 2020-10-19
 */
public interface RcFrenchCurrencyOrderReleaseMapper 
{
    /**
     * 查询法币订单发布
     * 
     * @param id 法币订单发布ID
     * @return 法币订单发布
     */
    public RcFrenchCurrencyOrderRelease selectRcFrenchCurrencyOrderReleaseById(Long id);

    /**
     * 查询法币订单发布列表
     * 
     * @param rcFrenchCurrencyOrderRelease 法币订单发布
     * @return 法币订单发布集合
     */
    public List<RcFrenchCurrencyOrderRelease> selectRcFrenchCurrencyOrderReleaseList(RcFrenchCurrencyOrderRelease rcFrenchCurrencyOrderRelease);

    /**
     * 新增法币订单发布
     * 
     * @param rcFrenchCurrencyOrderRelease 法币订单发布
     * @return 结果
     */
    public int insertRcFrenchCurrencyOrderRelease(RcFrenchCurrencyOrderRelease rcFrenchCurrencyOrderRelease);

    /**
     * 修改法币订单发布
     * 
     * @param rcFrenchCurrencyOrderRelease 法币订单发布
     * @return 结果
     */
    public int updateRcFrenchCurrencyOrderRelease(RcFrenchCurrencyOrderRelease rcFrenchCurrencyOrderRelease);

    /**
     * 删除法币订单发布
     * 
     * @param id 法币订单发布ID
     * @return 结果
     */
    public int deleteRcFrenchCurrencyOrderReleaseById(Long id);

    /**
     * 批量删除法币订单发布
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRcFrenchCurrencyOrderReleaseByIds(String[] ids);
}
