package com.vbyte.p2p;

import android.os.Handler;

/**
 * Created by passion on 16-5-10.
 */
public interface P2PModule {

    String getPlayPath(String channel);

    void getPlayPath(String channel, OnLoadedListener listener);

    String getSDKVersion();

    String getStatistics();

    int getCurrentPlayTime();

    void closeModule();

    void setP2PHandler(P2PHandler handler);
}
