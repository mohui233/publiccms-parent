package org.publiccms.common.base;

public class AbstractBaseResp {
	private int code;
	private String message;
	private Object object;
	public AbstractBaseResp(int code, String message, Object object) {
		super();
		this.code = code;
		this.message = message;
		this.object = object;
	}
	public AbstractBaseResp() {
		super();
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	

}
