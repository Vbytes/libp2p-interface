package com.vbyte.update;

import java.io.File;
import java.io.FilenameFilter;

import android.util.Log;

public class FileUpdate implements Runnable {

	protected CanUpdateFile canUpdateFile;
	protected FileUpdateChecker updateChecker;
	protected FileDownloader fileDownloader;
	protected FilenameFilter filenameFilter;
	private IFileUpdateFinished updateFinished;


	public FileUpdate(String id, String path, String updateToPath, String version,
			String tokenPlain, String checkUpdateUrl) {
		canUpdateFile = new CanUpdateFile(id, path, updateToPath, version, checkUpdateUrl );
		updateChecker = new FileUpdateCheck();
		fileDownloader = new RangeFileDownload();
		filenameFilter = new TmpFilenameFilter();
	}
	
	public FileUpdate(CanUpdateFile canUpdateFile, IFileUpdateFinished updateFinished) {
		this.canUpdateFile = canUpdateFile;
		this.updateFinished = updateFinished;
		updateChecker = new FileUpdateCheck();
		fileDownloader = new RangeFileDownload();
		filenameFilter = new TmpFilenameFilter();
		Log.e("MyMSG", "fileupdate params" + canUpdateFile.getId());
	}


	public CanUpdateFile getCanUpdateFile() {
		return canUpdateFile;
	}

	public void setCanUpdateFile(CanUpdateFile canUpdateFile) {
		this.canUpdateFile = canUpdateFile;
	}

	public FileUpdateChecker getUpdateChecker() {
		return updateChecker;
	}

	public void setUpdateChecker(FileUpdateChecker updateChecker) {
		this.updateChecker = updateChecker;
	}

	public FileDownloader getFileDownloader() {
		return fileDownloader;
	}

	public void setFileDownloader(FileDownloader fileDownloader) {
		this.fileDownloader = fileDownloader;
	}

	public class TmpFilenameFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			String fileName = new File(canUpdateFile.getPath()).getName();
			return name.startsWith(fileName) && name.endsWith(".tmp");
		}
	}

	public void run() {
		Log.e("mYmsg", canUpdateFile.getId());
		UpdateCheckResult checkResult = updateChecker.checkUpdate(canUpdateFile.getId(), canUpdateFile.getVersion(), canUpdateFile.getToken(), canUpdateFile.getCheckUpdateUrl());
		if (checkResult != null && checkResult.isNeedUpdate()) {
			String tempFileName = canUpdateFile.getName() +"." + checkResult.getNewVersion() +".tmp";
			String tempFilePath = canUpdateFile.getUpdateToDir() + "/" + tempFileName;
			Log.e("MyMSG", "tempfile path " + tempFilePath);
			File[] tmpFiles = new File(canUpdateFile.getUpdateToDir()).listFiles(filenameFilter);
			for (File tmpFile : tmpFiles) {
				if (!tmpFile.getName().equals(tempFileName)) {
					tmpFile.delete();
				}
			}
			Log.e("MyMSG", "start to download");
			if (fileDownloader.download(checkResult.getDownloadUrl(), canUpdateFile.getUpdateToDir(), tempFileName) != null) {
				Log.e("MSG", "new files token is " + checkResult.getMd5Token());
				File tmp = new File(tempFilePath);
				String md5token = MD5Util.MD5(tmp);
				Log.e("MSG", "tmp file md5 token " + md5token);
				Log.e("MSG", "origin md5 token " + checkResult);
				if (md5token.equalsIgnoreCase(checkResult.getMd5Token())) {
					Log.e("MSG", "md5 match with origin");
					this.updateFinished.solve(true, tempFilePath, checkResult.getNewVersion());
				} else {
					Log.e("MSG", "download file md5 mismatch with origin");
					tmp.delete();
					this.updateFinished.solve(false, "", "");
				}
				Log.e("MSG", "whole update finished");
			}
			
		} else {
			this.updateFinished.solve(false, "", "");
		}
	}
}
