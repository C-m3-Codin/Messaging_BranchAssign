package com.example.branchassign;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.O)
public class PostPojo implements Comparable<PostPojo> {
    private float id;
    private float thread_id;
    private String user_id;
    private String body;
    private String timestamp;
    private String agent_id = null;
    private LocalDateTime time;
    //    String startDate = "2010/09/12 00:00";
//    "2017-02-01T00:05:55.000Z"
    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public PostPojo(float id, float thread_id, String user_id, String body, String timestamp, String agent_id) {
        this.id = id;
        this.thread_id = thread_id;
        this.user_id = user_id;
        this.body = body;
        this.timestamp = timestamp;
        this.agent_id = agent_id;


//        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.ENGLISH);
//        time = LocalDateTime.parse(timestamp, inputFormatter);


    }


    public void sortMessages(List<PostPojo> allMessages) {
//        allMessages.sort();


    }
// Getter Methods

    public float getId() {
        return id;
    }

    public float getThread_id() {
        return thread_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getBody() {
        return body;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getAgent_id() {
        return agent_id;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setThread_id(float thread_id) {
        this.thread_id = thread_id;
    }


    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    @Override
    public int compareTo(PostPojo o) {
//        float compareage
//                = o.getThread_id();
//
//        //  For Ascending order
//        return (int)(this.getThread_id() - compareage);
        return o.getTime().compareTo(this.time);
    }
}

