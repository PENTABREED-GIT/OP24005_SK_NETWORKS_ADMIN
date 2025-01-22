package com.skn.admin.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 	hjkim
 * @date	2016.09.06
 */
public class NTResult {
	protected final Log logger = LogFactory.getLog(this.getClass());
	private final String CODE_KEY = "resultCode";
	private final String MSG_KEY = "resultMsg";
	private static final String CODE_FAIL = "FAIL";
	private static final String CODE_SUCCESS = "SUCCESS";
	
	private String failMsg = "처리 도중 문제가 발생했습니다.";
	private String code;
	private String msg;

	private Map<String,Object> data;

	private String resultCode;
	private String result;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public NTResult(){
		this.code = CODE_FAIL;
		this.resultCode = CODE_FAIL;
		this.msg = this.failMsg;
	}
	
	public static String getSuccessCode(){
		return CODE_SUCCESS;
	}
	
	public static String getFailCode(){
		return CODE_FAIL;
	}
	
	public void setSuccess(){
		this.code = CODE_SUCCESS;
		this.resultCode = CODE_SUCCESS;
		this.msg = "";
	}
	
	public void setFail(){
		this.code = CODE_FAIL;
	}
	
	public void setMsg(String msg){
		this.msg = msg;
	}
	
	public String getMsg(){
		return this.msg;
	}
	public String getMapCodeKey(){
		return this.CODE_KEY;
	}
	
	public String getMapMsgKey(){
		return this.MSG_KEY;
	}
	
	public Map getAsMap(){
		return this.getAsMap(null);
	}

	public Map getAsMap(Map map){
		if(map == null)
			map = new HashMap<String, String>();
		map.put(CODE_KEY, this.code);
		map.put(MSG_KEY, this.msg);
		map.put("result", this.result);
		map.put("data", this.data);
		return map;
	}
	
	public void setAll(Map<String, String> map){
		if(map == null)
			return;
		if(map.containsKey(CODE_KEY))
			this.code = map.get(CODE_KEY);
		if(map.containsKey(MSG_KEY))
			this.msg = map.get(MSG_KEY);
	}
	public boolean isFail(){
		return this.code.equals(CODE_FAIL);
	}

}
