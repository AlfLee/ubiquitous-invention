package com.telecom.utils;

import java.io.Serializable;

/**
 * 用于返回页面的信息
 * @author zj
 * @Copyright: Copyright (c) 2015
 */
@SuppressWarnings("serial")
public class QResponseVo<T> implements Serializable {
	private String code = CODE.Success.code;// 请求返回状态码
	private String action = QString.EMPTY;// 跳转连接
	private String msg = QString.EMPTY;// 友好消息提示
	private String stackover = QString.EMPTY;// 堆栈异常消息
	private String content = QString.EMPTY;// html数据
	private T data;// json数据
	private int count;

	public static enum CODE {
		Success("0"), // 正常访问
		Fail("1"), // 业务操作失败
		Error("2"); // 系统异常
		public String code;

		private CODE(String code) {
			this.code = code;
		}

		@Override
		public String toString() {
			return this.code;
		}
	}

	public QResponseVo() {
		super();
	}
	
	public QResponseVo(String code, String msg, T data, int count) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.count = count;
	}


	public static <T> QResponseVo<T> getInstance() {
		return new QResponseVo<T>();
	}

	public String getCode() {
		return code;
	}

	private QResponseVo<T> setCode(String code) {
		this.code = code;
		return this;
	}

	public QResponseVo<T> setCode(CODE code) {
		return setCode(code.code);
	}

	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public String getContent() {
		return content;
	}

	public QResponseVo<T> setContent(String content) {
		this.content = content;
		return this;
	}

	public T getData() {
		return data;
	}

	public QResponseVo<T> setData(T data) {
		this.data = data;
		return this;
	}

	public String getAction() {
		return action;
	}

	public QResponseVo<T> setAction(String action) {
		this.action = action;
		return this;
	}

	public String getStackover() {
		return stackover;
	}

	public QResponseVo<T> setStackover(String stackover) {
		this.stackover = stackover;
		return this;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
}
