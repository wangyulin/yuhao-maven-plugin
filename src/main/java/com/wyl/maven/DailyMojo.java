package com.wyl.maven;

import com.wyl.maven.utils.EnumDemo;
import com.wyl.maven.utils.RemoteData;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author wangyulin
 * @date 2021/7/3 8:11 AM
 */
@Mojo(name = "daily", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class DailyMojo extends AbstractMojo {

    public static final String ENUMS_PKG = "com.wyl.maven.enums";

    // mvn 可通过 -Dcontent=www 指定参数
    @Parameter(property = "content", defaultValue = "daily")
    private String env;

    @Parameter(property="project.build.sourceDirectory", defaultValue = "")
    private File sourceDirectory;

    @Parameter(property = "enumsPkg", defaultValue = "org.example")
    private String enumsPkg;

    private Log log;

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("--------------------< com.wyl.maven:yuhao-maven-plugin >---------------------");
        if (this.log == null) {
            this.log = getLog();
        }
        getLog().info("daily plugin print out : " + env);
        getLog().info("sourceDirectory : " + sourceDirectory);

        try {
            // enumsPkg.replace(".", "/"),
            Path path = Paths.get(sourceDirectory.getPath());
            getLog().info("enumsPath : " + path.toString());
            EnumDemo.gener(path, enumsPkg, log);
        } catch (IOException e) {
            e.printStackTrace();
        }
        checkEnumsDir(ENUMS_PKG);
        getLog().info("--------------------< com.wyl.maven:yuhao-maven-plugin >---------------------");
    }

    public void checkEnumsDir(String enumsDir) {
        Path path = Paths.get(sourceDirectory.getPath(), "com/wyl/maven/enums/");
        log.info("Path : " + path);
    }
}
