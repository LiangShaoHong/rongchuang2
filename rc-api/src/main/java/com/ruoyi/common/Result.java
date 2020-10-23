package com.ruoyi.common;

import java.io.Serializable;

public class Result implements Serializable {

    private static final long serialVersionUID = -4577255781088498763L;
    private static final boolean OK = true;
    private static final boolean FAIL = false;

    private Object data; //服务端数据
    private boolean status = OK; //状态码
    private String msg = ""; //描述信息
    private int code = 1; //应答

    //APIS
    public static Result isOk() {
        return new Result().status(OK).code(1).data("");
    }
    
    public static Result unauthorized() {
    	return new Result().status(FAIL).code(401).data("账号还未登录，请先登录！");
    }

    public static Result isFail() {
        return new Result().status(FAIL).code(0).data("");
    }
    
    public static Result isFail(String msg) {
        return new Result().status(FAIL).msg(msg).code(0).data("");
    }

    public static Result isFail(Throwable e) {
        return isFail().msg(e).code(0).data("");
    }

    public Result msg(Throwable e) {
        this.setMsg(e.toString());
        return this;
    }
    
    public Result msg(String msg) {
        this.setMsg(msg);
        return this;
    }

    public Result data(Object data) {
        this.setData(data);
        return this;
    }

    public Result status(boolean status) {
        this.setStatus(status);
        return this;
    }
    
    public Result code(int code	) {
    	this.setCode(code);
    	return this;
    }

    //Constructors
    public Result() {

    }

   //Getter&Setters

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
