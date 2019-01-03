package vip.ifmm.executor.build;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import vip.ifmm.common.Config;
import vip.ifmm.executor.ProcessExecutor;
import vip.ifmm.handler.DefaultStreamHandler;
import vip.ifmm.handler.StreamHandlerWrapper;

import java.io.File;
import java.io.IOException;

/**
 * 默认的代码打包工具执行器
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/01/04 00:10:02
 */
public class MavenBuildExecutor implements BuildExecutor {

    // 记录日志
    private static final Log log = LogFactory.getLog(MavenBuildExecutor.class);

    @Override
    public boolean packageProject() {

        try {
            // 执行 mvn clean package 命令
            ProcessExecutor.execute(Runtime.getRuntime().exec(new String[]{
                    Config.MVN_COMMAND,
                    "clean",
                    "package",
                    "-Dmaven.test.skip=true"
            }, null, new File(Config.CLONE_LOCATION)));

            return true;
        } catch (IOException | InterruptedException e) {
            log.error("clean package 失败！project: [" + Config.CLONE_LOCATION + "]", e);

            return false;
        }
    }

    @Override
    public boolean deployProject() {
        // TODO 部署项目到指定位置

        return false;
    }
}
