package cn.com.fishin.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.com.fishin.common.Config;

import java.io.*;

/**
 * 处理流的工具类
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2018/08/30 15:57:27
 */
public class StreamHandlerWrapper {

    // 记录日志
    private static final Log log = LogFactory.getLog(StreamHandlerWrapper.class);

    // 具体处理流数据的接口
    private StreamHandler streamHandler = null;

    // 设置字符集
    private String charset = Config.DEFAULT_CHARSET;

    /**
     * 使用特定对象构造一个流处理器
     *
     * @param streamHandler 用于处理流的接口对象
     */
    public StreamHandlerWrapper(StreamHandler streamHandler) {
        this.streamHandler = streamHandler;
    }

    /**
     * 使用特定对象构造一个流处理器
     *
     * @param streamHandler 用于处理流的接口对象
     * @param charset 字符集
     */
    public StreamHandlerWrapper(StreamHandler streamHandler, String charset) {
        this.streamHandler = streamHandler;
        this.charset = charset;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    // 处理输入流
    public void handleInput(InputStream is) {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is, charset));
            String line = null;
            while (streamHandler.keepGoing()) {
                line = reader.readLine();
                if (line != null) {
                    streamHandler.handleReadString(line);
                } else {
                    streamHandler.handleNullString();
                }
            }
        } catch (Exception e) {
            log.error("程序已经关闭...", e);
        } finally {
            closeInput(reader);
        }
    }

    // 处理输出流
    public void handleOutput(OutputStream os) {

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
        try {
            streamHandler.handleWriteString(writer, null);
        } catch (IOException e) {
            log.error("程序已经关闭...", e);
        } finally {
            closeOutput(writer);
        }
    }

    // 关闭输入流
    private void closeInput(BufferedReader reader) {

        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                log.error(reader + "关闭失败！", e);
            }
        }
    }

    // 关闭输出流
    private void closeOutput(BufferedWriter writer) {

        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                //e.printStackTrace();
                log.error(writer + "关闭失败！", e);
            }
        }
    }

    // 关闭流处理器释放资源
    public void finish() {
        streamHandler.close();
    }
}
