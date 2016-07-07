package com.vbyte.update;

public class UpdateCheckResult {
	private boolean needUpdate;
	private String newVersion;
	private String downloadUrl;
	private String md5Token;
	
	public String getNewVersion() {
		return newVersion;
	}
	public void setNewVersion(String newVersion) {
		this.newVersion = newVersion;
	}
	
	public boolean isNeedUpdate() {
		return needUpdate;
	}
	public void setNeedUpdate(boolean needUpdate) {
		this.needUpdate = needUpdate;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	
	public UpdateCheckResult(){}
	public UpdateCheckResult(boolean needUpdate, String version, String downloadUrl, String md5token) {
		this.needUpdate = needUpdate;
		this.downloadUrl = downloadUrl;
		this.newVersion = version;
		this.md5Token  =md5token;
	}
	public String getMd5Token() {
		return md5Token;
	}
	
}
