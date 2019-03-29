package cn.com.fishin.ead.executor.deploy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.com.fishin.ead.common.Config;
import cn.com.fishin.ead.executor.ProcessExecutor;

import java.io.File;
import java.io.IOException;

/**
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/01/04 23:11:37
 */
public class UnixDeployExecutor implements DeployExecutor {

    // 记录日志
    private static final Log log = LogFactory.getLog(UnixDeployExecutor.class);

    @Override
    public boolean deploy(String projectRootDirectory, String projectDirectory, String deployLocation, String deployName) {

        try {
            ProcessExecutor.execute(Runtime.getRuntime().exec(new String[]{
                    "cp",
                    "-rf",
                    projectDirectory + "/.",
                    deployLocation + "/" + deployName
            }, null, new File(projectRootDirectory)));

            return true;
        } catch (InterruptedException | IOException e) {
            log.error("deploy 失败！deploy_location: [" + Config.DEPLOY_LOCATION + "]", e);
            return false;
        }
    }
}
