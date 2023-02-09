package com.syxu.yaofang;

import com.syxu.yaofang.model.MailConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MailApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class, args);
    }

    @Bean
    Queue queue() {
        return new Queue(MailConstants.MAIL_QUEUE_NAME);
    }
}
