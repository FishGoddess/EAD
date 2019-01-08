package vip.ifmm.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.io.File;
import java.io.InputStream;
import java.util.List;


/**
 * 服务器处理器
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/1/8 21:35:30
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    // 类加载时就缓存 HTML 页面
    private static final String HTML_OK = loadHTML(HttpServerHandler.class.getClassLoader().getResourceAsStream("html/index.html"));

    // 接收到任何数据都返回首页
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {

        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.copiedBuffer(HTML_OK, CharsetUtil.UTF_8)
        );

        // 设置响应头
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());

        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.writeAndFlush(cause);
        ctx.close();
    }

    // 加载 HTML 页面作为 String 缓存起来
    private static String loadHTML(InputStream htmlStream) {

        // 读取 HTML 文件
        FileReaderStreamHandler fileHandler = new FileReaderStreamHandler();
        StreamHandlerWrapper streamHandlerWrapper = new StreamHandlerWrapper(fileHandler, "UTF8");
        streamHandlerWrapper.handleInput(htmlStream);
        List<String> lines = fileHandler.getLines();

        // 关闭流处理
        streamHandlerWrapper.finish();

        // 转换成一个 String 对象
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line).append('\n');
        }

        return sb.toString();
    }
}
