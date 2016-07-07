package com.vbyte.update;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import android.util.Log;

public class RangeFileDownload implements FileDownloader {

	/*
	 * @see net.ventureinc.fifo.FileDownloader#download(java.lang.String,
	 * java.lang.String) 返回null：表示下载位置不可写或无效的下载目录；否则表示非完全或完全下载时文件的位置
	 */
	public String download(String downUrl, String desDir, String fileName) {
		Log.e("MyMSG", "execute downlaod funciton");
		File dir = new File(desDir);
		if (!dir.canWrite() || !dir.exists()) {
			return null;
		}
		String filePath = dir.getAbsolutePath() + "/" + fileName;
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				// 判断写权限
				file.createNewFile();
			} catch (IOException e) {
				return null;
			}
		}
		BufferedInputStream bis = null;
		RandomAccessFile raf = null;

		try {
			URL realUrl = new URL(downUrl);
			HttpURLConnection conn = (HttpURLConnection) realUrl
					.openConnection();
			long finishedSize = file.length();
			Log.e("MyMSG", "pre downlaoded siez" + finishedSize);
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setRequestProperty("Range", "bytes=" + finishedSize + "-");
			conn.connect();
			if (conn.getResponseCode() == 206 || conn.getResponseCode() == 200) {
				bis = new BufferedInputStream(conn.getInputStream());
				raf = new RandomAccessFile(file, "rw");
				byte[] bytes = new byte[1024];
				int count;
				while ((count = bis.read(bytes)) != -1) {
					raf.seek(finishedSize);
					raf.write(bytes, 0, count);
					finishedSize += count;
				}
				Log.e("MyMSG", conn.getContentLength() +":" + finishedSize);
				return filePath;
			} else {
				Log.e("MyMSG", "something error");
				return null;
			}
		} catch (Exception e) {
			return null;
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (raf != null) {
					raf.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}
