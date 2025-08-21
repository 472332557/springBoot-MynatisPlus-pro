package com.liangzc.demo.transaction.service.impl;

import com.liangzc.demo.transaction.service.MessageSender;

public class EmailSender implements MessageSender {
    @Override
    public void sendMessage(String message) {
        System.out.println("发送邮件：" + message);
    }
}
