package com.jyq.jrpc.netty;

import com.jyq.jrpc.model.Request.Request;
import com.jyq.jrpc.serializer.KryoSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.UnsupportedEncodingException;

public class NettyServerHandler extends ChannelHandlerAdapter {



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            //调用服务
            Request request = (Request)KryoSerializer.readFromByteArray((byte[]) msg);
            request.getmData();
            ctx.writeAndFlush(getSendByteBuf("你好，客户端"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param buf
     * @return
     */
    private String getMessage(ByteBuf buf) {

        byte[] con = new byte[buf.readableBytes()];
        buf.readBytes(con);
        try {
            return new String(con, "UTF8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    private ByteBuf getSendByteBuf(String message)
            throws UnsupportedEncodingException {

        byte[] req = message.getBytes("UTF-8");
        ByteBuf pingMessage = Unpooled.buffer();
        pingMessage.writeBytes(req);

        return pingMessage;
    }
}
