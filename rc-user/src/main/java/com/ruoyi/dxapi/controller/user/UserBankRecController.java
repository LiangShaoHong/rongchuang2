package com.ruoyi.dxapi.controller.user;

import com.ruoyi.common.constant.MsgConstants;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.common.utils.SnowflakeIdWorker;
import com.ruoyi.dx.domain.User;
import com.ruoyi.dx.domain.UserBankRec;
import com.ruoyi.dxapi.common.Result;
import com.ruoyi.dxapi.common.SystemUtil;
import com.ruoyi.dxservice.service.UserBankRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 用户银行卡信息接口层
 */
@RestController("dxUserBankRecController")
public class UserBankRecController {

    @Autowired
    UserBankRecService userBankRecService;
    @Resource
    private SystemUtil systemUtil;


    /**
     * 新增收款账户
     * @param keyCode：标识（1银行，2支付宝，3微信）
     * @param bankName：银行名称
     * @param bankOpenName：开户行
     * @param bankCode：银行卡号
     * @param userName：开户名称
     * @param qrCode：二维码
     * @param isMoren：是否默认
     * @param memo：备注
     * @return
     */
    @RequestMapping("/dx-api/user/addBank")
    public Result addBank(@RequestParam("keyCode")Integer keyCode, @RequestParam("bankName")String bankName, @RequestParam("bankOpenName")String bankOpenName,
                          @RequestParam("bankCode")String bankCode, @RequestParam("userName")String userName, @RequestParam(value = "qrCode",required = false)String qrCode,
                          @RequestParam(value = "memo",required = false)String memo, @RequestParam("isMoren")Integer isMoren,HttpServletRequest request){
        User user = systemUtil.getPlatformIdAndUserId(request);
        UserBankRec userBankRec = new UserBankRec(SnowflakeIdWorker.genIdStr(),user.getPlatformId(), isMoren,user.getId(),keyCode,bankName,bankOpenName,bankCode,userName,qrCode,MsgConstants.IS_NO,memo);
        Map map = userBankRecService.insertUserBankRec(userBankRec);
        if (map.containsKey(1)){
            return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS);
        }
        return Result.isFail(map.get(0).toString());
    }

    /**
     * 获取会员收款账户
     * @return
     */
    @RequestMapping("/dx-api/user/getBankList")
    public Result getBankList(HttpServletRequest request){
        User user = systemUtil.getPlatformIdAndUserId(request);
        List<JSONObject> bankList = userBankRecService.getBankList(user.getPlatformId(), user.getId());
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(bankList);
    }

    /**
     * 更新收款账户
     * @param keyCode：标识（1银行，2支付宝，3微信）
     * @param bankName：银行名称
     * @param bankOpenName：开户行
     * @param bankCode：银行卡号
     * @param userName：开户名称
     * @param qrCode：二维码
     * @param memo：备注
     * @param isMoren：是否默认
     * @param id：主键id
     * @return
     */
    @RequestMapping("/dx-api/user/updateBank")
    public Result updateBank(@RequestParam("keyCode")Integer keyCode,@RequestParam("bankName")String bankName,@RequestParam("bankOpenName")String bankOpenName,
                          @RequestParam("bankCode")String bankCode,@RequestParam("userName")String userName,@RequestParam(value = "qrCode",required = false)String qrCode,
                          @RequestParam(value = "memo",required = false)String memo,@RequestParam("id")String id,@RequestParam("isMoren")Integer isMoren,HttpServletRequest request){
        User user = systemUtil.getPlatformIdAndUserId(request);
        UserBankRec userBankRec = new UserBankRec(id,null, isMoren,user.getId(),keyCode,bankName,bankOpenName,bankCode,userName,qrCode,MsgConstants.IS_NO,memo);
        //更新银行卡信息，因为是否默认也在其中，所以要检验更新银行卡的信息是否已经有过一张了，还要设置默认流程
        Map map = userBankRecService.updateUserBankRec(userBankRec,user.getPlatformId(),MsgConstants.IS_NO);
        if (map.containsKey(1)){
            return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS);
        }
        return Result.isFail(map.get(0).toString());
    }

    /**
     * 删除会员收款账户
     * @param id：银行卡id
     * @return
     */
    @RequestMapping("/dx-api/user/deleteBank")
    public Result deleteBank(@RequestParam("id")String id,HttpServletRequest request){
        User user = systemUtil.getPlatformIdAndUserId(request);
        int isDelete = userBankRecService.deleteBank(id,user.getId());
        if (isDelete > 0)return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS);
        return Result.isFail(MsgConstants.OPERATOR_FAIL);
    }

    /**
     * 修改银行卡信息
     * @param id：银行卡id
     * @return
     */
    @RequestMapping("/dx-api/user/editBankMessage")
    public Result editBankMoren(@RequestParam("id")String id,HttpServletRequest request){
        User user = systemUtil.getPlatformIdAndUserId(request);
        boolean isUpdate = userBankRecService.editBankMoren(id,user.getId());
        if (isUpdate){
            return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS);
        }
        return Result.isFail(MsgConstants.OPERATOR_FAIL);
    }

    /**
     * 获取可设置的银行卡类别
     * @return
     */
    @RequestMapping("/dx-api/user/getUserBankTemplate")
    public Result getUserBankTemplate(HttpServletRequest request){
        User user = systemUtil.getPlatformIdAndUserId(request);
        List<JSONObject> sysBankTypeList = userBankRecService.getSysBankTypeList(MsgConstants.IS_NO, MsgConstants.IS_NO, user.getPlatformId());
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(sysBankTypeList);
    }

    /**
     * 根据银行卡类别查找对应下面的银行卡列表
     * @param request
     * @return
     */
    @RequestMapping("/dx-api/user/getUserBankTemplateByKeyCode")
    public Result getUserBankTemplateByKeyCode(HttpServletRequest request){
        User user = systemUtil.getPlatformIdAndUserId(request);
        List<JSONObject> sysBankTemplateList = userBankRecService.sysBankTemplateList(MsgConstants.IS_NO, user.getPlatformId());
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(sysBankTemplateList);
    }

    /**
     * 根据id获取收款信息
     * @param id：收款id 主键
     * @return
     */
    @RequestMapping("/dx-api/user/getUserBankDetail")
    public Result getUserBankDetail(@RequestParam("id")String id){
        JSONObject userBankDetail = userBankRecService.getUserBankDetail(id);
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(userBankDetail);
    }


//    /**
//     * 获取默认银行卡
//     * @return
//     */
//    @RequestMapping("/dx-api/user/getMorenBank")
//    public Result getMorenBank(HttpServletRequest request){
//        User user = systemUtil.getPlatformIdAndUserId(request);
//        //拿到默认的银行卡
//        JSONObject morenBank = userBankRecService.getBankInfo(user.getId());
//        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(morenBank);
//    }

}
