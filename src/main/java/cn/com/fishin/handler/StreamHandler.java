package cn.com.fishin.handler;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2018/08/30 16:04:13
 */
public interface StreamHandler {

    // 判断流处理是否还要继续
    boolean keepGoing();

    // 关闭流处理器
    void close();

    // 处理输入流的字符串
    default void handleReadString(String line) throws IOException {}

    // 处理输出流的字符串
    default void handleWriteString(BufferedWriter bw, String line) throws IOException {}

    // 处理 null 字符串
    default void handleNullString() throws Exception {}
}
