package com.summerr.eventbusforthread;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import de.greenrobot.event.EventBus;

public class NetThread extends Thread {
    private DefaultHttpClient client;
    private String url;
    private boolean flag = true;    //控制线程的标志位

    public NetThread() {
        url = "http://www.baidu.com/";
    }

    public void stopThreadByFlag() {
        flag = false;
    }

    public void run() {
        client = new DefaultHttpClient();
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);     //请求超时时间为2秒
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);            //读取超时时间为3秒

        try {
            HttpGet get = new HttpGet(url);
            get.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");
            HttpResponse response = client.execute(get);
            ThreadEvent threadEvent = new ThreadEvent();
            EventBus.getDefault().post(threadEvent);

            if (response.getStatusLine().getStatusCode() == 200) {
                BufferedReader bin = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                char content;

                /** 一次读取一个字符，每读取一个字符就发送一个事件 */
                while ((bin.read()!=-1) && flag) {
                    content = (char) bin.read();
                    threadEvent.setContent(String.valueOf(content));
                    /** EventBus发送ThreadEvent事件， 由注册了该ThreadEvent事件的对象接收 */
                    EventBus.getDefault().post(threadEvent);
                    /** 线程休眠0.05秒钟 */
                    Thread.sleep(50);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}