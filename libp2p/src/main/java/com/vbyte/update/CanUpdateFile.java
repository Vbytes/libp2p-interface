package com.vbyte.update;


public class CanUpdateFile {
	private String path;
	private String version;
	private String checkUpdateUrl;
	private String id;
	private String name;
	public String updateToDir;
	private String token;
	
	public CanUpdateFile( String id,String path,String updateToDir,  String version, String checkUpdateUrl) {
		this.version = version;
		this.path = path;
		this.updateToDir = updateToDir;
		this.checkUpdateUrl = checkUpdateUrl;
		this.id = id;
		String[] strs = path.split("/");
		this.name = strs[strs.length - 1];
		setToken(this.id);
		this.id = id;
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getCheckUpdateUrl() {
		return checkUpdateUrl;
	}
	public void setCheckUpdateUrl(String checkUpdateUrl) {
		this.checkUpdateUrl = checkUpdateUrl;
	}
	

	public String getToken() {
		return token;
		
		
	}
	public void setToken(String plain) {
		String tokenPlain = plain + "ventureinc";
		token = MD5Util.MD5(tokenPlain.getBytes());
	}

	public String getName() {
		return name;
	}


	public String getUpdateToDir() {
		return updateToDir;
	}


	public String getId() {
		return id;
	}

}
