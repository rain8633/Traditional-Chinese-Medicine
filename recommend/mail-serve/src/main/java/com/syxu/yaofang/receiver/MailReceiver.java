package com.syxu.yaofang.receiver;


import com.rabbitmq.client.Channel;
import com.syxu.yaofang.deploy.ServerConfig;
import com.syxu.yaofang.model.Client;
import com.syxu.yaofang.model.MailConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;

@Component
public class MailReceiver {

    public static final Logger logger = LoggerFactory.getLogger(com.syxu.yaofang.receiver.MailReceiver.class);

    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    MailProperties mailProperties;
    @Autowired
    TemplateEngine templateEngine;
    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    ServerConfig serverConfig;

    //消费者
    @RabbitListener(queues = MailConstants.MAIL_QUEUE_NAME)
    public void handler(Message message, Channel channel) throws IOException, MessagingException {
        Client consumer = (Client) message.getPayload();
        MessageHeaders headers = message.getHeaders();
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        String msgId = (String) headers.get("spring_returned_message_correlation");
        if (redisTemplate.opsForHash().entries("mail_log").containsKey(msgId)) {
            //redis 中包含该 key，说明该消息已经被消费过
            logger.info(msgId + ":消息已经被消费");
            channel.basicAck(tag, false);//确认消息已消费,不用回到队列
            return;
        }
        //收到消息，发送邮件
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg,true);
        try {
            //组装发送邮件参数
                String title = "账户激活";
                Context context = new Context();
                //组装激活地址
//                String url = serverConfig.getUrl().concat("/activation").concat("?email=")
//                      .concat(consumer.getEmail()).concat("&validateCode=").concat(consumer.getValidateCode());

            String url = "http://localhost:9090/user".concat("/activation").concat("?email=")
                    .concat(consumer.getEmail()).concat("&validateCode=").concat(consumer.getValidateCode());

                context.setVariable("url", url);
               String emailContent = templateEngine.process("email/email", context);
//            helper.setFrom(emailConfig.getEmailFrom());
            helper.setTo(consumer.getEmail());
            helper.setFrom(mailProperties.getUsername());
            helper.setSubject(title);
            helper.setSentDate(new Date());
            helper.setText(emailContent, true);
            javaMailSender.send(msg);
            redisTemplate.opsForHash().put("mail_log", msgId, "syxu");
            channel.basicAck(tag, false);
            logger.info(msgId + ":邮件发送成功");
        } catch (MessagingException e) {
            channel.basicNack(tag, false, true);
            e.printStackTrace();
            logger.error("邮件发送失败：" + e.getMessage());
        }
    }
}
