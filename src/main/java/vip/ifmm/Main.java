package vip.ifmm;

import vip.ifmm.common.Environment;
import vip.ifmm.executor.build.BuildExecutor;
import vip.ifmm.executor.build.MavenBuildExecutor;
import vip.ifmm.executor.vcs.GitVCSExecutor;
import vip.ifmm.executor.vcs.VCSExecutor;

/**
 * 主程序
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/01/03 00:51:00
 */
public class Main {

    public static void main(String[] args) {
        VCSExecutor git = Environment.getVCSExecutor();
        //git.cloneProject();
        git.updateProject();

        BuildExecutor mvn = Environment.getBuildExecutor();
        mvn.packageProject();
        mvn.deployProject();
    }
}
