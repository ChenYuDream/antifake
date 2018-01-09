package com.sinosoft.antifake.repository;

import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.sinosoft.antifake.entity.VersionInfo;
@Service
public class VersionDao {
	/*
	public String getVersion(String deviceType) {
		String version = "";
		final InputStream inStream = this.getClass().getResourceAsStream(
				"/version.properties");
		final Properties pro = new Properties();
		try {
			pro.load(inStream);
			if ("ios".equals(deviceType)) {
				version = pro.getProperty("ios_version");
			} else if ("android".equals(deviceType)) {
				version = pro.getProperty("android_version");
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return version;
	}
	*/
	public VersionInfo findVersionInfo(String deviceType) {
		VersionInfo versionInfo=new VersionInfo();
		String versionNumber = "";
		String downloadUrl = "";
		String versionCode = "";
		String urlType = "";
		boolean force = false;
		final InputStream inStream = this.getClass().getResourceAsStream(
				"/version.properties");
		final Properties pro = new Properties();
		try {
			pro.load(inStream);
			if ("ios".equals(deviceType)) {
				versionNumber = pro.getProperty("ios_version");
				downloadUrl = pro.getProperty("download_ios_url");
				versionCode = pro.getProperty("ios_versionCode");
				urlType = pro.getProperty("ios_urlType");
				force = pro.getProperty("ios_force").equals("true");
			} else if ("android".equals(deviceType)) {
				versionNumber = pro.getProperty("android_version");
				downloadUrl = pro.getProperty("download_android_url");
				versionCode = pro.getProperty("android_versionCode");
				urlType = pro.getProperty("android_urlType");
				force = pro.getProperty("android_force").equals("true");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		versionInfo.setVersionNumber(versionNumber);
		versionInfo.setDownloadUrl(downloadUrl);
		versionInfo.setVersionCode(versionCode);
		versionInfo.setUrlType(urlType);
		versionInfo.setForce(force);
		return versionInfo;
	}
}
