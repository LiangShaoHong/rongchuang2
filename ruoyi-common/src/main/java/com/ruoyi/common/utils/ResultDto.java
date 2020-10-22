package com.ruoyi.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;


/**
 * 返回结果类型
 * @param <T>
 */
@ApiModel("结果集实体类")
@Data
public class ResultDto<T> implements Serializable {
    private static Logger log = LoggerFactory.getLogger(ResultDto.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();
    /**返回的状态值 1 成功 , 0失败*/
    @ApiModelProperty("状态码，1：成功；0：失败")
    private Integer code = 1;

    /**返回的信息*/
    @ApiModelProperty("操作消息")
    private String msg;


    /**返回值*/
    @ApiModelProperty("业务数据")
    private T data;

    /**是否成功*/
    @ApiModelProperty("状态")
    private boolean status;


    @Override
    public String toString() {
        try {
            return 	MAPPER.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("convert ResResultDto to Json String error!");
            return null;
        }
    }

    /**
     * 带返回值和错误码,和核心返回值
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static ResultDto build(Integer code, String msg, Object data) {
        return new ResultDto(code, msg, data);
    }

    /**
     * 错误返回值
     * @param errorCode
     * @param errorMsg
     * @return
     */
    public static ResultDto error(Integer errorCode, String errorMsg) {
        return new ResultDto(errorCode, errorMsg);
    }

    /**
     * 错误的构造方法
     * @param code
     * @param msg
     */
    public ResultDto(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.status = false;

    }

    /**
     * 正确的返回值
     * @param data
     * @return
     */
    public static ResultDto ok(Object data) {
        return new ResultDto(data);
    }


    /**
     * 正确的构造方法
     * @param result
     */
    public ResultDto(T result) {
        this.code = 1;
        this.msg = "ok";
        this.data = result;
        this.status = true;
    }


    /**
     * 正常传值构造方法
     * @param code
     * @param msg
     * @param data
     */
    public ResultDto(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data =  data;
        if (code == 200) {
            this.status = true;
        }
    }


    /**
     * 将json结果集转化为TaotaoResult对象
     *
     * @param jsonData json数据
     * @param clazz TaotaoResult中的object类型
     * @return
     */
    public static ResultDto formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, ResultDto.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("result");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("code").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }




    /**
     * 没有object对象的转化
     *
     * @param json
     * @return
     */
    public static ResultDto format(String json) {
        try {
            return MAPPER.readValue(json, ResultDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     * @param jsonData json数据
     * @param clazz 集合中的类型
     * @return
     */
    public static ResultDto formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("result");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("code").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }



}