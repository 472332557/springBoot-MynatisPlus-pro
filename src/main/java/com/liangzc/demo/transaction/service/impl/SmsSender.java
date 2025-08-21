package com.liangzc.demo.transaction.service.impl;

import com.liangzc.demo.transaction.service.MessageSender;

public class SmsSender implements MessageSender {
    @Override
    public void sendMessage(String message) {
        System.out.println("发送短信：" + message);
    }
}
