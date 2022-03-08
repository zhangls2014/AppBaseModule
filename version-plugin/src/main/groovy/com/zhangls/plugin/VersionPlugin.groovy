package com.zhangls.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * gradle 插件执行任务，将 versions.gradle 文件中的依赖编译到指定的文件中，实现自定义的版本提示和更新。
 *
 * @author zhangls
 */
class VersionPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.getExtensions().create("versions", VersionExtension)

        project.afterEvaluate {
            VersionExtension extension = project["versions"]
            String inputFilePath = extension.inputFile
            String outputFilePath = extension.outputFile

            if (inputFilePath == null || outputFilePath == null) {
                return
            }

            updateVersions(project, inputFilePath, outputFilePath)
        }
    }

    private static void updateVersions(Project project, String inputFilePath, String outputFilePath) {
        project.task("updateDependencies") {
            String rootGradlePath = "${project.rootDir}/build.gradle"

            // 将 inputFilePath 声明为该 Task 的 inputs
            inputs.file(inputFilePath)
            // 将 outputFilePath 声明为 outputs
            outputs.file(outputFilePath)

            doLast {
                File inputFile = new File(inputFilePath)
                if (!inputFile.exists()) {
                    return
                }

                String inputTxt = inputFile.text
                StringBuilder builder = new StringBuilder()

                /**
                 * 解析拼接版本 interface
                 */
                builder.append("/**\n")
                        .append(" * 版本信息\n")
                        .append(" */\n")
                        .append("interface Versions {\n")

                String startFlag = "/*<version>*/"
                String endFlag = "/*</version>*/"

                int start = inputTxt.indexOf(startFlag)
                int end = inputTxt.indexOf(endFlag)

                builder.append("    ")
                        .append(inputTxt.substring(start + startFlag.length(), end).trim())
                        .append("\n}\n\n")

                /**
                 * 解析拼接依赖 interface
                 */
                builder.append("/**\n")
                        .append(" * 依赖库路径\n")
                        .append(" */\n")
                        .append("interface Deps {\n")

                String depFlag = "implementation"
                startFlag = "/*<dep>*/"
                endFlag = "/*</dep>*/"

                start = inputTxt.indexOf(startFlag)
                end = inputTxt.indexOf(endFlag)

                String depsTxt = inputTxt.substring(start + startFlag.length(), end).trim()

                int implementationIndex
                int doubleSlashIndex

                while (true) {
                    implementationIndex = depsTxt.indexOf(depFlag)
                    if (implementationIndex == -1) {
                        break
                    }
                    doubleSlashIndex = depsTxt.lastIndexOf("//", implementationIndex)
                    String namePart
                    String name
                    while (true) {
                        namePart = depsTxt.substring(doubleSlashIndex + 2, implementationIndex)
                        name = namePart.split(":")[0].trim()
                        if (!name.contains("/")) {
                            break
                        }
                        doubleSlashIndex = depsTxt.lastIndexOf("//", doubleSlashIndex - 2)
                    }
                    depsTxt = depsTxt.replaceFirst(depFlag, String.format("String %s =", name))
                }

                builder.append("    ")
                        .append(depsTxt)
                        .append("\n}\n")

                String resultTxt = builder.toString()

                new File(outputFilePath).withPrintWriter("utf-8", { writer ->
                    writer.print(resultTxt)
                    writer.flush()
                })

                /**
                 * 同步插件信息到项目根目录 build.gradle 中
                 */
                File gradleFile = new File(rootGradlePath)
                if (!gradleFile.exists()) {
                    return
                }

                String gradleTxt = gradleFile.text

                startFlag = "/*<plugin>*/"
                endFlag = "/*</plugin>*/"

                start = inputTxt.indexOf(startFlag)
                end = inputTxt.indexOf(endFlag)

                inputTxt.substring(start + startFlag.length(), end)
                        .eachLine { line ->
                            int index = line.indexOf(depFlag)
                            if (index != -1) {
                                String dep = line.substring(index + depFlag.length()).trim()
                                dep = dep.substring(1, dep.length() - 1)
                                index = dep.lastIndexOf(":")
                                String mainPart = dep.substring(0, index)
                                gradleTxt = gradleTxt.replaceFirst("$mainPart[^\"|']+", dep)
                            }
                        }

                gradleFile.withPrintWriter("utf-8", { writer ->
                    writer.print(gradleTxt)
                    writer.flush()
                })
            }
        }
    }
}