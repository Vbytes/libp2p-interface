package com.vbyte.update;

public interface FileUpdateChecker {
	public UpdateCheckResult checkUpdate(String id, String version, String hashToken, String checkUpdateUrl);
}
