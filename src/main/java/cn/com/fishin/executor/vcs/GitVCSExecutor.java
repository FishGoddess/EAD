package cn.com.fishin.executor.vcs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.com.fishin.common.Config;
import cn.com.fishin.executor.ProcessExecutor;

import java.io.File;
import java.io.IOException;

/**
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/01/03 00:17:45
 */
public class GitVCSExecutor implements VCSExecutor {

    // 记录日志
    private static final Log log = LogFactory.getLog(GitVCSExecutor.class);

    // 项目已经是最新的了
    public static final String ALREADY_UP_TO_DATE = "Already up to date.";

    @Override
    public boolean cloneProject() {
        try {
            // 执行 git clone 命令
            ProcessExecutor.execute(Runtime.getRuntime().exec(new String[]{
                    Config.VCS_COMMAND,
                    "clone",
                    Config.CLONE_URL,
                    Config.CLONE_LOCATION
            }));

            return true;
        } catch (IOException | InterruptedException e) {
            log.error("clone 失败！url: [" + Config.CLONE_URL + "]", e);

            return false;
        }
    }

    @Override
    public boolean updateProject() {
        try {
            // 执行 git pull 命令
            ProcessExecutor.execute(Runtime.getRuntime().exec(new String[]{
                    Config.VCS_COMMAND,
                    "pull"
            }, null, new File(Config.CLONE_LOCATION)));

            return true;
        } catch (IOException | InterruptedException e) {
            log.error("pull 失败！url: [" + Config.CLONE_URL + "]", e);

            return false;
        }
    }
}
