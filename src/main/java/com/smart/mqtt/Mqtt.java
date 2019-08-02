package com.smart.mqtt;

import com.yb.socket.service.SocketType;
import com.yb.socket.service.server.Server;
import org.springframework.stereotype.Service;

@Service
public class Mqtt {
    public Mqtt() {
        init();
    }

    private void init() {
        Server server = new Server();
        // 设置Broker端口
        server.setPort(1884);
        // 设置启动信息统计。默认true
        server.setOpenCount(true);
        // 设置启用心跳功能。默认true
        server.setCheckHeartbeat(true);
        // 设置启动服务状态，默认端口8001。通过telnet server_ip 8001; get status查看服务信息
        server.setOpenStatus(true);
        // 服务状态端口。默认8001
        server.setStatusPort(1885);
        // 设置服务名称
        server.setServiceName("Demo");
        // 设置工作线程数量。默认CPU个数+1
        server.setWorkerCount(64);
        // 是否开户业务处理线程池。默认false
        server.setOpenExecutor(true);
        // 设置tcp no delay。默认true
        server.setTcpNoDelay(true);
        // 是否启用keepAlive。默认true
        server.setKeepAlive(true);
        // 自定义监听器，可处理相关事件
        server.addEventListener(new EchoMessageEventListener());
        // 设置Broker启动协议。SocketType.MQTT - MQTT协议； SocketType.NORMAL - 普通Socket协议；SocketType.MQTT_WS - MQTT web socket协议；
        server.setSocketType(SocketType.MQTT);
        // 绑定端口启动服务
        server.bind();
    }
}
