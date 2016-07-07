package com.vbyte.update;

public class UpdateService extends Thread{
	private UpdateService(){}
	private static class Holder{
		private static UpdateService instance = new UpdateService();
	}
	public static UpdateService getInstance() {
		return Holder.instance;
	}

}
