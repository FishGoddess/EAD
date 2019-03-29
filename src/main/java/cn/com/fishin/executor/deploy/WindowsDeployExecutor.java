package cn.com.fishin.executor.deploy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.com.fishin.common.Config;
import cn.com.fishin.executor.ProcessExecutor;

import java.io.File;
import java.io.IOException;

/**
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/01/04 23:11:37
 */
public class WindowsDeployExecutor implements DeployExecutor {

    // 记录日志
    private static final Log log = LogFactory.getLog(WindowsDeployExecutor.class);

    @Override
    public boolean deploy(String projectRootDirectory, String projectDirectory, String deployLocation, String deployName) {

        try {
            // 由于 windows 上的 xcopy 不支持 / 分隔符，所以要替换成 \\
            deployLocation = deployLocation.replace('/', '\\');
            ProcessExecutor.execute(Runtime.getRuntime().exec(new String[]{
                    "xcopy",
                    projectDirectory,
                    deployLocation + "\\" + deployName,
                    "/E", "/I", "/Q", "/Y" /* E: 递归复制；I: 复制到目录中；Q: 不显示复制的文件名；Y: 覆盖原有的文件 */
            }, null, new File(projectRootDirectory)));

            return true;
        } catch (InterruptedException | IOException e) {
            log.error("deploy 失败！deploy_location: [" + Config.DEPLOY_LOCATION + "]", e);

            return false;
        }
    }
}
