package com.sinosoft.antifake.entity;

import java.util.List;

public class SerialNumberQueryJson {
	String status;
	String info;
	List<SerialNumberQueryData> data;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public List<SerialNumberQueryData> getList() {
		return data;
	}
	public void setList(List<SerialNumberQueryData> data) {
		this.data = data;
	}
}
