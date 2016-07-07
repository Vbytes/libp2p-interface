package com.vbyte.update;

public interface IFileUpdateFinished {
	public void solve(boolean needUpdate, String newestFilePath, String newestVersion);
}
