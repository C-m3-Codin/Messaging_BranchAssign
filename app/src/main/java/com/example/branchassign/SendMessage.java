package com.example.branchassign;

public class SendMessage {
    String body;
    int thread_id;



    public SendMessage(String messageBody, int threadId) {
        this.body = messageBody;
        this.thread_id = threadId;
    }
}
