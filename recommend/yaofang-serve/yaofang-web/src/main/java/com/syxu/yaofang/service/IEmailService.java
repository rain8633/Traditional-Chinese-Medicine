package com.syxu.yaofang.service;

public interface IEmailService {
    /**
     * @param sendTo 收件人地址
     * @param titel 邮件标题
     * @param content 邮件内容
     */
    public void sendMail(String sendTo,String titel,String content);
}
