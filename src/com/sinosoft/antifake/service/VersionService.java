package com.sinosoft.antifake.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinosoft.antifake.entity.VersionInfo;
import com.sinosoft.antifake.repository.VersionDao;
@Service
public class VersionService{
	@Autowired
	private VersionDao versionDao;
	private VersionInfo versionInfo;
	/*
	public String getVersion(String deviceType) {
		String version = versionDao.getVersion(deviceType);
		if (version == null || version.trim().equals("")) {
			version = "0";
		}
		return version;
	}
	*/
	public VersionInfo findVersionInfo(String deviceType) {
		versionInfo = versionDao.findVersionInfo(deviceType);
		return versionInfo;
	}
}
