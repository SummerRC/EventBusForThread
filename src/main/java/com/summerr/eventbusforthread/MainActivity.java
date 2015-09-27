package com.summerr.eventbusforthread;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import de.greenrobot.event.EventBus;


public class MainActivity extends Activity {

    private TextView tv_content;
    private NetThread netThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);     //去掉标题栏
        setContentView(R.layout.activity_main);
        tv_content = (TextView) findViewById(R.id.tv_content);

        /** register EventBus*/
        EventBus.getDefault().register(this);
        netThread = new NetThread();
        netThread.start();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        /** Unregister EventBus*/
        EventBus.getDefault().unregister(this);
        netThread.stopThreadByFlag();
    }


    /**
     * 接收ThreadEvent事件
     * @param threadEvent 事件类型
     */
    public void onEventMainThread(ThreadEvent threadEvent) {
        StringBuffer content = new StringBuffer(tv_content.getText().toString());
        content.append(threadEvent.getContent());
        tv_content.setText(content);
    }

}
