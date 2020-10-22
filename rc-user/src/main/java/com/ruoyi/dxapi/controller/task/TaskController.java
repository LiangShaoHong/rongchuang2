package com.ruoyi.dxapi.controller.task;

import com.github.pagehelper.PageHelper;
import com.ruoyi.common.constant.InfoConstants;
import com.ruoyi.common.constant.MsgConstants;
import com.ruoyi.common.dto.PageDTO;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.common.push.PushService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SnowflakeIdWorker;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.redis.RedisService;
import com.ruoyi.dx.domain.PlatformBase;
import com.ruoyi.dx.domain.TaskOrder;
import com.ruoyi.dx.domain.TaskUserAccount;
import com.ruoyi.dx.domain.User;
import com.ruoyi.dxapi.common.Result;
import com.ruoyi.dxapi.common.SystemUtil;
import com.ruoyi.dxapi.controller.BaseController;
import com.ruoyi.dxservice.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 任务平台控制层
 */
@RestController("dxTaskController")
public class TaskController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskComfromService taskComfromService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private TaskRecService taskRecService;
    @Autowired
    private TaskUserAccountService taskUserAccountService;
    @Autowired
    private SysPlatformInfoService sysPlatformInfoService;
    @Autowired
    private UserRankRecService userRankRecService;
    @Resource
    private SystemUtil systemUtil;

    @Autowired
    private PushService pushService;


    /**
     * 首页加载任务平台列表
     *
     * @return
     */
    @RequestMapping("/dx-api/task/getTaskPlatformList")
    public Result getTaskPlatformList(HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        List<JSONObject> comfromList = taskComfromService.getComfromList(user.getPlatformId(), MsgConstants.IS_NO, MsgConstants.IS_NO);
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(comfromList);
    }

    /**
     * 获取首页的推荐任务列表
     *
     * @param pageNumber:页码
     * @param limit:每页数量
     * @return
     */
    @RequestMapping("/dx-api/task/getNewTaskList")
    public Result getNewTaskList(@RequestParam("pageNumber") Integer pageNumber, @RequestParam("limit") Integer limit, HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        PageDTO dto = new PageDTO(pageNumber, limit);
        List<JSONObject> newTaskList = taskRecService.getNewTaskList(dto,user.getPlatformId(), MsgConstants.TASK_ON_SHELF, user.getId());
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(newTaskList);
    }

    /**
     * 获取任务列表
     *
     * @param pageNumber:页码
     * @param limit:每页数量
     * @param taskRank：任务级别,不传就查所有
     * @param taskComefrom：任务来源(抖音、快手那些)
     * @return
     */
    @RequestMapping("/dx-api/task/getTaskList")
    public Result getTaskList(@RequestParam("pageNumber") Integer pageNumber, @RequestParam("limit") Integer limit, @RequestParam(value = "offset", required = false) Integer offset,
                                 @RequestParam(value = "taskRank", required = false) String taskRank, @RequestParam(value = "taskComefrom", required = false) String taskComefrom,
                                 HttpServletRequest request) {
        if (null != offset && null != limit && offset.compareTo(limit) >= 0){
            return Result.isFail("任务列表获取失败");
        }
        User user = systemUtil.getPlatformIdAndUserId(request);
        PageDTO dto = null;
        if (null != offset){
            dto = new PageDTO(pageNumber, limit, offset);
        } else {
            dto = new PageDTO(pageNumber, limit);
        }
        List<JSONObject> newTaskList = taskRecService.getTaskList(dto,user.getPlatformId(), MsgConstants.TASK_ON_SHELF, taskRank, taskComefrom, user.getId());
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(newTaskList);
    }

    /**
     * 获取我的任务列表
     *
     * @param pageNumber:页码
     * @param limit:每页数量
     * @param status:任务操作状态
     * @return
     */
    @RequestMapping("/dx-api/task/getMyTaskList")
    public Result getMyTaskList(@RequestParam("pageNumber") Integer pageNumber, @RequestParam("limit") Integer limit,
                                @RequestParam("status") Integer status, HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        PageDTO dto = new PageDTO(pageNumber, limit);
        List<JSONObject> myTaskList = taskRecService.getMyTaskList(dto,status, user.getId());
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(myTaskList);
    }

    /**
     * 领取任务
     *
     * @param id:任务id
     * @return
     */
    @RequestMapping("/dx-api/task/receiveTask")
    public Result getMyTaskList(@RequestParam("id") String id, HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        Map map = taskRecService.receiveTask(id, user.getId(), user.getPlatformId());
        if (map.containsKey(1)) {
            return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS);
        }
        return Result.isFail().msg(map.get(0).toString());
    }

    /**
     * 获取绑定的平台列表
     *
     * @return
     */
    @RequestMapping("/dx-api/task/getTaskComfromUserList")
    public Result getTaskComfromUserList(HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        //任务平台列表
        List<JSONObject> comfromList = taskComfromService.getComfromList(user.getPlatformId(), MsgConstants.IS_NO, MsgConstants.IS_NO);
        if (0 == comfromList.size()){
            Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(comfromList);
        }
        //用户绑定平台的账户列表
        List<JSONObject> myTaskUserAccountList = taskComfromService.getMyTaskUserAccountList(user.getId(), MsgConstants.IS_NO);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("comfromId","");
        jsonObject.put("status","");
        jsonObject.put("account","");
        jsonObject.put("nickName","");
        jsonObject.put("img","");
        jsonObject.put("accountId","");
        for (int i = 0; i < comfromList.size(); i++) {
            if (0 == myTaskUserAccountList.size()){
                comfromList.get(i).putAll(jsonObject);
            }else {
                //用来判断是否需要插入空数据
                boolean isPut = false;
                for (int j = 0; j < myTaskUserAccountList.size(); j++) {
                    if (comfromList.get(i).getStr("id").equals(myTaskUserAccountList.get(j).getStr("comfromId"))){
                        comfromList.get(i).putAll(myTaskUserAccountList.get(j));
                        isPut = true;
                        break;
                    }
                }
                if (!isPut){
                    comfromList.get(i).putAll(jsonObject);
                }
            }

        }
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(comfromList);
    }


    /**
     * 绑定任务平台账号
     *
     * @param comfromId:任务平台的id(抖音快手那些)
     * @param account:对应平台上的账号
     * @param nickName:对应平台上的昵称
     * @param img:截图
     * @param request
     * @return
     */
    @RequestMapping("/dx-api/task/addComfromAccount")
    public Result addComfromAccount(@RequestParam("comfromId") String comfromId, @RequestParam("account") String account,
                                    @RequestParam("nickName") String nickName, @RequestParam(value = "img", required = false) String img, HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        // 判断是否绑定过该平台，以及审核失败后，提交重新审核
        JSONObject jsonObject = taskUserAccountService.checkTaskUserAccountExist(null, user.getId(), comfromId, MsgConstants.IS_NO, MsgConstants.CHECK_STATUS_CHECKING, MsgConstants.CHECK_STATUS_AGREE);
        if (null != jsonObject) {
            return Result.isFail().msg("已绑定过该平台的账号");
        }
        TaskUserAccount taskUserAccount = new TaskUserAccount(SnowflakeIdWorker.genIdStr(), user.getPlatformId(), user.getId(), comfromId, account, nickName, img, null, MsgConstants.CHECK_STATUS_CHECKING, null, MsgConstants.IS_NO, null);
        boolean isAdd = taskUserAccountService.addTaskUserAccount(taskUserAccount);
        if (isAdd) {
            return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS);
        }
        return Result.isFail().msg(MsgConstants.OPERATOR_FAIL);
    }

    /**
     * 获取任务平台账号信息
     *
     * @param id:
     * @param request
     * @return
     */
    @RequestMapping("/dx-api/task/getComfromAccountInfo")
    public Result getComfromAccountInfo(@RequestParam("id") String id, HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        JSONObject comfromAccountInfo = taskUserAccountService.getComfromAccountInfo(id,user.getId());
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(comfromAccountInfo);
    }


    /**
     * 编辑绑定任务平台账号
     *
     * @param comfromId:任务平台的id(抖音快手那些)
     * @param account:对应平台上的账号
     * @param nickName:对应平台上的昵称
     * @param img:截图
     * @param request
     * @return
     */
    @RequestMapping("/dx-api/task/editComfromAccount")
    public Result editComfromAccount(@RequestParam("id") String id, @RequestParam("comfromId") String comfromId, @RequestParam("account") String account,
                                     @RequestParam("nickName") String nickName, @RequestParam(value = "img", required = false) String img, HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        // 判断是否是正在审核的状态
        JSONObject jsonObject = taskUserAccountService.checkTaskUserAccountExist(id, user.getId(), comfromId, MsgConstants.IS_NO, MsgConstants.CHECK_STATUS_CHECKING, MsgConstants.CHECK_STATUS_CHECKING);
        if (null != jsonObject) {
            return Result.isFail().msg("请等待审核");
        }
        boolean b = taskUserAccountService.editComfromAccount(id, user.getId(), comfromId, account, nickName, img, MsgConstants.CHECK_STATUS_CHECKING);
        if (b) {
            return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS);
        }
        return Result.isFail().msg(MsgConstants.OPERATOR_FAIL);
    }


    /**
     * 获取任务详情
     *
     * @param id:任务id
     * @param request
     * @return
     */
    @RequestMapping("/dx-api/task/geTaskOrderInfo")
    public Result geTaskOrderInfo(@RequestParam("id") String id, HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        JSONObject taskOrder = taskRecService.geTaskOrderInfo(id, user.getId());
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(taskOrder);
    }

    /**
     * 提交任务让后台审核
     *
     * @param id:任务订单的id
     * @param img:截图
     * @param request
     * @return
     */
    @RequestMapping("/dx-api/task/submitTask")
    public Result submitTask(@RequestParam("id") String id, @RequestParam(value = "img",required = false) String img, HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        //通过订单id拿到订单信息
        TaskOrder taskOrder = taskRecService.selectTaskOrderById(id);
        //拿到任务部分信息
        JSONObject taskRec = taskRecService.getTaskStopTimeById(taskOrder.getTaskId());
        if (null == taskRec.get("stopTime") || null == taskRec.get("status")){
            log.info("任务信息（stopTime或者status）缺失，查找的任务id为："+taskOrder.getTaskId());
            return Result.isFail().msg(MsgConstants.DATE_NEVER_INIT);
        }
        Date now = DateUtils.getNowDate();
        if (now.compareTo((Date) taskRec.get("stopTime")) > 0){
            return Result.isFail().msg("该任务已过期，无法提交");
        }
        if (MsgConstants.TASK_DOWN_SHELF == taskRec.getInt("status")){
            return Result.isFail().msg("该任务已下架，无法提交");
        }
        //获取会员等级信息
        JSONObject rankUserRec = userRankRecService.getRankUserRecById(user.getRankId(), null, user.getPlatformId(), MsgConstants.IS_NO);
        if (null == rankUserRec || null == rankUserRec.get("canDoTaskTimesDay")){
            log.info("会员等级信息缺失，查找的会员等级id为："+user.getRankId());
            return Result.isFail().msg(MsgConstants.DATE_NEVER_INIT);
        }
        //拿到今日提交和完成的任务数
//        int submitTaskNum = taskRecService.getSubmitTaskNum(user.getId(), MsgConstants.WAIT_CHECK, MsgConstants.IS_FINISHED);
//        if (submitTaskNum >= rankUserRec.getInt("canDoTaskTimesDay")){
//            return Result.isFail().msg("已达今日最大提交数");
//        }
        boolean b = taskRecService.checkTaskOrderStatus(id, MsgConstants.WAIT_CHECK, user.getPlatformId(), user.getId(), MsgConstants.NO_START, img, now, null, null);

        if (b) {
//            //任务的设置信息，主要拿是否需要自动审核
//            JSONObject taskConf = sysPlatformInfoService.getSysConf(user.getPlatformId(), InfoConstants.REDIS_TASK_CONF, InfoConstants.TASK_CONF);
//            //如果配置不为空，
//            if (null != taskConf && null != taskConf.get("verify")){
//                //开启自动审核
//                if (MsgConstants.IS_YES == taskConf.getInt("verify")){
//                    //队列延迟发送时间
//                    int delay;
//                    //未设置时间，就取默认
//                    if (null == taskConf.get("verifyTime")){
//                        delay = MsgConstants.QUEUE_DELAY_TIME;
//                    }else {
//                        delay = taskConf.getInt("verifyTime");
//                    }
//                    TaskOrder newTaskOrder = taskRecService.selectTaskOrderById(taskOrder.getId());
//                    //自动审核
//                    taskRecService.autoCheckTaskOrderStatus(newTaskOrder, user,delay);
//                    return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS);
//                }
//            }
            // 通知后台
            JSONObject msg = new JSONObject();
            msg.put("code", "1");
            msg.put("data", "task");
            pushService.sendToGroup(user.getPlatformId(), msg.toString());

            return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS);
        }
        return Result.isFail().msg(MsgConstants.OPERATOR_FAIL);
    }

    /**
     * 获取轮播图
     *
     * @param request
     * @return
     */
    @RequestMapping("/dx-api/base/getRotationChart")
    public Result getRotationChart(HttpServletRequest request) {
        String originName = super.getOrigin(request);
        PlatformBase platformBase = sysPlatformInfoService.getPlatformBaseByDomain(MsgConstants.IS_NO, MsgConstants.IS_NO, originName);
        if (null == platformBase) {
            log.info("域名查找平台号出错，查找的域名为："+originName);
            return Result.isFail().msg(MsgConstants.DATE_NEVER_INIT);
        }
        List<JSONObject> rotationChart = sysPlatformInfoService.getRotationChart(platformBase.getId(), MsgConstants.IS_NO, MsgConstants.IS_YES);
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(rotationChart);
    }

    /**
     * 获取公告列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/dx-api/base/getBaseArticleList")
    public Result getBaseArticleList(@RequestParam("pageNumber") Integer pageNumber, @RequestParam("limit") Integer limit, HttpServletRequest request) {
        String originName = super.getOrigin(request);
        PlatformBase platformBase = sysPlatformInfoService.getPlatformBaseByDomain(MsgConstants.IS_NO, MsgConstants.IS_NO, originName);
        if (null == platformBase) {
            log.info("域名查找平台号出错，查找的域名为："+originName);
            return Result.isFail().msg(MsgConstants.DATE_NEVER_INIT);
        }
        PageDTO dto = new PageDTO(pageNumber, limit);
        List<JSONObject> baseArticleList = sysPlatformInfoService.getBaseArticleList(dto,platformBase.getId(), MsgConstants.IS_NO, MsgConstants.IS_YES, "0");
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(baseArticleList);
    }

    /**
     * 获取消息列表
     *
     * @return
     */
    @RequestMapping("/dx-api/task/getNewsList")
    public Result getNewsList(HttpServletRequest request) {
        String originName = super.getOrigin(request);
        PlatformBase platformBase = sysPlatformInfoService.getPlatformBaseByDomain(MsgConstants.IS_NO, MsgConstants.IS_NO, originName);
        if (null == platformBase) {
            log.info("域名查找平台号出错，查找的域名为："+originName);
            return Result.isFail().msg(MsgConstants.DATE_NEVER_INIT);
        }
        String cashKey = InfoConstants.CASH_LIST+":"+platformBase.getId();
        String taskKey = InfoConstants.TASK_LIST+":"+platformBase.getId();
        //提现的消息
        List<Object> cashList = redisService.getList(cashKey, InfoConstants.DB_NOTICE);
        //完成任务的消息
        List<Object> taskList = redisService.getList(taskKey, InfoConstants.DB_NOTICE);
        //最后返回的数据，将上面两个任务的元素加进来
        List<Object> objects = new ArrayList<>();
        if (0 != cashList.size()){
            objects.addAll(cashList);
        }
        if (0 != taskList.size()){
            objects.addAll(taskList);
        }
        //打乱顺序
        Collections.shuffle(objects);
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(objects);
    }

    /**
     * 获取图片url前缀，平台域名
     * @return
     */
    @RequestMapping("/dx-api/base/init")
    public Result getPlatformAppInfo (HttpServletRequest request) {
        //域名解析，获取平台号
        String originName = super.getOrigin(request);
        PlatformBase platformBase = sysPlatformInfoService.getPlatformBaseByDomain(MsgConstants.IS_NO, MsgConstants.IS_NO, originName);
        if (null == platformBase) {
            log.info("域名查找平台号出错，查找的域名为："+originName);
            return Result.isFail().msg(MsgConstants.DATE_NEVER_INIT);
        }
        JSONObject platformAppInfo = sysPlatformInfoService.getPlatformAppInfo(platformBase.getId());
        if (null == platformAppInfo){
            log.info("app详情缺失，查找的平台id为："+platformBase.getId());
            return Result.isFail().msg(MsgConstants.DATE_NEVER_INIT);
        }
        //获取图片地址前缀
        String aliyunImgKey = sysPlatformInfoService.selectConfigByKey("upload.storage.type");
        if (StringUtils.isEmpty(aliyunImgKey)){
            log.info("图片地址缺失，查找的key为：upload.storage.type");
            return Result.isFail().msg(MsgConstants.DATE_NEVER_INIT);
        }
        String imgURL = sysPlatformInfoService.selectConfigByKey(aliyunImgKey);
        if (null == platformAppInfo.get("appName") || null == platformAppInfo.get("androidDownload") ||null == platformAppInfo.get("iosDownload")){
            log.info("app信息缺失，appName或者下载地址");
            return Result.isFail().msg(MsgConstants.DATE_NEVER_INIT);
        }
        JSONObject sysConf = sysPlatformInfoService.getSysConf(platformBase.getId(),InfoConstants.REDIS_PLATFORM_SYS_CONF, InfoConstants.PLATFORM_SYS_CONF);
        String name = platformAppInfo.getStr("appName");
        String androidDownload = platformAppInfo.getStr("androidDownload");
        String iosDownload = platformAppInfo.getStr("iosDownload");
        String customerJsCode = (null == sysConf ? "" : sysConf.getStr("customerJsCode"));
        Integer isUploadTaskImg = MsgConstants.IS_NO;
        if (null != sysConf && null != sysConf.get("isUploadTaskImg")){
            isUploadTaskImg = sysConf.getInt("isUploadTaskImg");
        }
        JSONObject data = new JSONObject();
        //app名称
        data.put("name",name);
        //域名
        data.put("originName",originName);
        //图片url前缀
        data.put("imgURL",imgURL);
        //安卓下载地址
        data.put("androidDownload",androidDownload);
        //ios下载地址
        data.put("iosDownload",iosDownload);
        data.put("logo",platformBase.getLogo());
        data.put("customerJsCode", customerJsCode);
        data.put("isUploadTaskImg", isUploadTaskImg);
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(data);
    }

    /**
     * 获取任务等级列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/dx-api/task/getEveryRankRec")
    public Result getEveryRankRec(HttpServletRequest request) {
        User user = systemUtil.getPlatformIdAndUserId(request);
        List<JSONObject> everyRankRec = taskRecService.getEveryRankRec(user.getPlatformId(), MsgConstants.IS_NO);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id","0");
        jsonObject.put("name","所有任务");
        everyRankRec.add(0,jsonObject);
        return Result.isOk().msg(MsgConstants.OPERATOR_SUCCESS).data(everyRankRec);
    }


}
