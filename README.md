# BaseUtils
自己在开发过程中经常使用的工具/代码片段

#### version-plugin 依赖管理的使用

1. 需要在根目录中的 `settings.gradle ` 文件中设置 `includeBuild 'version-plugin` 

2. 在项目的 `build.gradle` 文件的导入如下代码

   ```groovy
   plugins {
       id 'version-plugin' apply true
   }
   ```

3. 在 module 中通过 `Versions/Deps` 进行引用依赖
4. 在 `versions.gradle` 文件中更新依赖之后，需要运行 `updateDependencies` 脚本命令，以自动生成依赖文件

#### base module 包含的内容

- retrofit 的封装
- 自定义 View 相关的扩展方法
- 一些常见的扩展方法
- 简单的对话框实现

#### 使用方法

[![](https://jitpack.io/v/zhangls2014/BaseUtils.svg)](https://jitpack.io/#zhangls2014/BaseUtils)

```
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
   
dependencies {
	implementation 'com.github.zhangls2014:BaseUtils:Tag'
}
```
