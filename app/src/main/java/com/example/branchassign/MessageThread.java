package com.example.branchassign;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MessageThread {
    private float id;

    public MessageThread(float id, float thread_id, String user_id, String latestTimestamp, Message[] messages, String agent_id) {
        this.id = id;
        this.thread_id = thread_id;
        this.user_id = user_id;
        this.latestTimestamp = latestTimestamp;
        this.messages = messages;
        this.agent_id = agent_id;
    }

    private float thread_id;
    private String user_id;
   private String latestTimestamp;
    private Message [] messages;
    private String agent_id = null;

}


class Message implements Comparable<Message>, Serializable {


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    private Float threadId;
    private String timestamp;
    private String body;
    private LocalDateTime time;

    public Float getThreadId() {
        return threadId;
    }

    public void setThreadId(Float threadId) {
        this.threadId = threadId;
    }

    public Message(String timestamp, String body, LocalDateTime time, String agent_id, Float threadId) {
        this.timestamp = timestamp;
        this.body = body;
        this.time = time;
        this.agent_id = agent_id;
        this.threadId = threadId;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    private String agent_id = null;

//    public Message(String timestamp, String body, LocalDateTime time) {
//        this.timestamp = timestamp;
//        this.body = body;
//        this.time = time;
//    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int compareTo(Message o) {
//        return 0;
        return o.getTime().compareTo(this.time);
    }
}