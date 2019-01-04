package vip.ifmm;

import vip.ifmm.executor.build.MavenBuildExecutor;
import vip.ifmm.executor.vcs.GitVCSExecutor;

/**
 * 主程序
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/01/03 00:51:00
 */
public class Main {

    public static void main(String[] args) {
        /*GitVCSExecutor git = new GitVCSExecutor();
        git.cloneProject();
        git.updateProject();*/

        MavenBuildExecutor mvn = new MavenBuildExecutor();
        //mvn.packageProject();
        mvn.deployProject();
    }
}
