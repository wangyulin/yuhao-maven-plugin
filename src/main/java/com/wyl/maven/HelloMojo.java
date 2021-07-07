package com.wyl.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

@Mojo(name = "hello", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class HelloMojo extends AbstractMojo {

    @Parameter(property="project.build.sourceDirectory", defaultValue = "")
    private File sourceDirectory;

    // @Parameter(required = true)
    // private String moduleDir;

      @Parameter
      private String name;

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("----------------------------[yuhao-maven-plugin]----------------------------");
        getLog().info("Hello world ");

        getLog().info("name : " + name);
        // getLog().info("moduleDir : " + moduleDir);
        getLog().info("sourceDirectory : " + sourceDirectory);

        File f = sourceDirectory;
        if (Objects.nonNull(sourceDirectory)) {
            if (!f.exists()) {
                getLog().info("sourceDirectory not existed ");
            } else {
                getLog().info("sourceDirectory existed ");
            }
        } else {
            getLog().info("sourceDirectory is null ");
        }
        getLog().info("----------------------------[yuhao-maven-plugin]----------------------------");
    }
}

/**
 * Goal which touches a timestamp file.
 *
 * @goal touch
 * @phase process-sources
 *//*
public class MyMojo
        extends AbstractMojo {
    *//**
     * Location of the file.
     *
     * @parameter expression="${project.build.directory}"
     * @required
     *//*
    private File outputDirectory;

    public void execute()
            throws MojoExecutionException {
        File f = outputDirectory;

        if (!f.exists()) {
            f.mkdirs();
        }

        File touch = new File(f, "touch.txt");

        FileWriter w = null;
        try {
            w = new FileWriter(touch);

            w.write("touch.txt");
        } catch (IOException e) {
            throw new MojoExecutionException("Error creating file " + touch, e);
        } finally {
            if (w != null) {
                try {
                    w.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
}*/
