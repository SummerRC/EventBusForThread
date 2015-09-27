package com.summerr.eventbusforthread;

/**
 * @author : SummerRC on 2015/9/27 0027
 * @version :  V1.0 <当前版本>
 *          description : <在此填写描述信息>
 */
public class ThreadEvent {
    private int count;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        count++;
        this.content = content;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
