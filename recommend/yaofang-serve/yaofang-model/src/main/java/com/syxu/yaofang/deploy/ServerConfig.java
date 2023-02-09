package com.syxu.yaofang.deploy;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;


@Component
public class ServerConfig implements ApplicationListener<WebServerInitializedEvent> {
    private int serverPort;

    public String getUrl() {

        try {
            InetAddress address = InetAddress.getLoopbackAddress();

            return "http://" + address.getHostAddress() + ":" + this.serverPort;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }


    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.serverPort = event.getWebServer().getPort();
    }

}
