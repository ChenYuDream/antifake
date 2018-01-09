package com.sinosoft.antifake.entity;

public class VersionInfo {
	private String versionNumber;
	private String downloadUrl;
	private String versionCode;
	private String urlType;
	private boolean force;
	
	
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public String getUrlType() {
		return urlType;
	}
	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}
	public boolean isForce() {
		return force;
	}
	public void setForce(boolean force) {
		this.force = force;
	}
	public String getVersionNumber(){
		return versionNumber;
	}
	public void setVersionNumber(String versionNumber){
		this.versionNumber=versionNumber;
	}
	public String getDownloadUrl(){
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl){
		this.downloadUrl=downloadUrl;
	}
}
