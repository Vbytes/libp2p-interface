package com.vbyte.update;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

import android.util.Log;

public class FileUpdateCheck implements FileUpdateChecker {
	@Override
	public UpdateCheckResult checkUpdate(String id, String version,
			String token, String checkUpdateUrl) {
		UpdateCheckResult checkResult = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(checkUpdateUrl).append("?").append("fifoVersion=")
					.append(version).append("&token=").append(token)
					.append("&").append("fileId=").append(id);
			String checkRes = sendRequest(sb.toString());
			if (checkRes != null) {
				JSONObject json = new JSONObject(checkRes);
				boolean needUpdate = Boolean.parseBoolean(json.get("com/vbyte/update")
						.toString());
				String downloadUrl = null;
				String newVersion = version;
				String md5token = null;
				Log.e("CHECKRESULT", needUpdate + " result");
				if (needUpdate == true) {
					downloadUrl = json.getString("downloadUrl");
					newVersion = json.getString("version");
					md5token = json.getString("md5token");
				}
				checkResult = new UpdateCheckResult(needUpdate, newVersion,
						downloadUrl, md5token);
			} else {
				checkResult = new UpdateCheckResult(false, null, null, null);
			}
		} catch (Exception e) {
			Log.e("MSG", "exception run update runnable : " + e);
		} finally {
			return checkResult;
		}
	}

	public static String sendRequest(String url) {
		String result = "";
		BufferedReader in = null;
		// 打开和URL之间的连接
		Log.e("MyMSG", url);
		HttpURLConnection conn = null;
		try {
			URL realUrl = new URL(url);
			conn = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			conn.setReadTimeout(1000);
			// 建立实际的连接
			conn.connect();
			if (conn.getResponseCode() == 200) {
				// 定义 BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
				return result;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
			return null;
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
					conn.disconnect();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}

}
