package com.vbyte.update;

public interface FileDownloader {
	// 返回下载状态，0表示完全未下载，1表示下载了部分，2表示下载完成
	public String download(String downUrl, String desDir, String fileName);
}
