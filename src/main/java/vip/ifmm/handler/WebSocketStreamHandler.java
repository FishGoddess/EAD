package vip.ifmm.handler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import vip.ifmm.Main;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * WebSocket 服务器处理器
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/1/8 13:29:27
 */
public class WebSocketStreamHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>
        implements StreamHandler {

    // 将所有已连接上来的通道都保存起来
    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // 是否是第一次运行
    private boolean firstStart = true;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

        // 只有等一个用户完全执行完才能执行下一个指令
        if (firstStart || exited) {

            // 判断命令执行
            switch (msg.text()) {
                case Main.INIT:
                    restart(); // 重新开始
                    firstStart = false;
                    sendToAll("项目是否下载成功：" + Main.init());
                    break;
                case Main.UPDATE:
                    restart(); // 重新开始
                    sendToAll("项目是否更新成功：" + Main.update());
                    break;
            }
        }

        //ctx.writeAndFlush(new TextWebSocketFrame(LocalDateTime.now().toString()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.writeAndFlush(cause);
        ctx.close();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel());
        System.out.println("added ==> " + channels.size());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channels.remove(ctx.channel()); // netty 会自动处理离开的通道，所以这里其实是可以省略的
        System.out.println("removed ==> " + channels.size());
    }

    // 是否终止程序
    private volatile boolean exited = false;

    // 必须是同步，强行让使用者等待！
    private synchronized void restart() {
        this.exited = false;
    }

    @Override
    public void close() {
        exited = true;
    }

    @Override
    public boolean keepGoing() {
        return !exited;
    }

    @Override
    public void handleReadString(String line) throws IOException {
        sendToAll(line);
    }

    // 向所有连接上来的用户发送消息
    private static void sendToAll(String line) {
        System.out.println(line);
        channels.forEach(channel -> {
            channel.writeAndFlush(new TextWebSocketFrame(line + "\r\n"));
        });
    }
}
