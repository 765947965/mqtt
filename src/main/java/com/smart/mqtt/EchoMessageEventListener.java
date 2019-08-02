package com.smart.mqtt;

import com.yb.socket.listener.DefaultMqttMessageEventListener;
import com.yb.socket.service.WrappedChannel;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.mqtt.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author daoshenzzg@163.com
 * @date 2019/1/4 15:10
 */
public class EchoMessageEventListener extends DefaultMqttMessageEventListener {
    private static final Logger logger = LoggerFactory.getLogger(EchoMessageEventListener.class);


    @Override
    public void connect(WrappedChannel channel, MqttConnectMessage msg) {
        super.connect(channel, msg);
        // 做登录验证判断 user password clientId
        MqttConnectVariableHeader header = msg.variableHeader();
        // 如果不符合 直接关闭连接
//        channel.close();
        // 查询数据库clientId对应的deviceSn

        // 返回给用户deviceSn 以及配置信息

    }

    @Override
    public void publish(WrappedChannel channel, MqttPublishMessage msg) {
        String topic = msg.variableHeader().topicName();
        ByteBuf buf = null;
        try {
            buf = msg.content().duplicate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String content = null;
        if (buf != null) {
            byte[] tmp = new byte[buf.readableBytes()];
            buf.readBytes(tmp);
            content = new String(tmp);
        }
        System.out.println("topic : " + topic);
        System.out.println("content : " + content);

        MqttPublishMessage sendMessage = (MqttPublishMessage) MqttMessageFactory.newMessage(
                new MqttFixedHeader(MqttMessageType.PUBLISH, false, MqttQoS.AT_MOST_ONCE, false, 0),
                new MqttPublishVariableHeader(topic, 0),
                Unpooled.buffer().writeBytes(new String(content == null ? "收到空消息" : content.toUpperCase()).getBytes()));
        channel.writeAndFlush(sendMessage);
    }
}
