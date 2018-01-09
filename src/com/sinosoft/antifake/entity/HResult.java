package com.sinosoft.antifake.entity;

import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * The Result of Service API execute
 */
public class HResult {
	// RESULT:true , VALUE: Return Value
	// RESULT:false , VALUE: Error Message
	private Boolean result;
	private String value;
	// if return value is an Object
	private Object objValue;

	public static HResult DEFAULT_OK = new HResult(true, "");

	public HResult(Boolean result, String value) {
		this.result = result;
		this.value = value == null ? "" : value;
	}

	public HResult(Boolean result, String value, Object objValue) {
		this.result = result;
		this.value = value == null ? "" : value;
		this.objValue = objValue;
	}

	public HResult(Object objValue) {
		this.result = true;
		this.value = "";
		this.objValue = objValue;
	}

	public Boolean isOK() {
		return result;
	}

	public String getValue() {
		return value;
	}

	public Object getObjValue() {
		return objValue;
	}

	public void setObjValue(Object objValue) {
		this.objValue = objValue;
	}

	public static void main(String args[]) throws Exception {
		String j = new ObjectMapper().writeValueAsString(HResult.DEFAULT_OK);
		System.out.println(j);
	}
}
