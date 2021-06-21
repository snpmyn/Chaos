# Chaos
## 释义
混沌
## 模块
| 名称 | 说明 | 补充 |
|:-:|:-:|:-:|
| basic | 基础 | 无 |
| util | 工具类 | 无 |
| widget | 组件 | 无 |
| banner | 轮播图 | 无 |
| matisse | 选图 | 无 |
| ucrop | 裁剪 | 无 |
| fragmentation | 碎片 | 无 |
| jpush | 极光推送 | 无 |
| janalytics | 极光统计 | 无 |
| bugly | Bugly | 无 |
| mobsms | SMSSDK | 无 |
| bmob | Bmob | 无 |
| litepal | LitePal | 无 |
| doraemonkit | DoraemonKit | 无 |
| tbs | 腾讯浏览服务 | 无 |
| pool | 水池 | 无 |
## 依赖
#### basic
```
api 'androidx.appcompat:appcompat:xxx'
api 'com.google.android.material:material:xxx'
api 'com.github.bumptech.glide:glide:xxx'
api 'io.reactivex:rxandroid:xxx'
api 'io.reactivex:rxjava:xxx'
api 'io.reactivex.rxjava2:rxandroid:xxx1'
api 'io.reactivex.rxjava2:rxjava:xxx'
api 'io.reactivex.rxjava3:rxandroid:xxx'
api 'io.reactivex.rxjava3:rxjava:xxx'
api 'com.squareup.retrofit2:retrofit:xxx'
api 'com.squareup.retrofit2:adapter-rxjava:xxx'
api 'com.squareup.retrofit2:converter-gson:xxx'
api 'com.jakewharton.timber:timber:xxx'
api 'com.tencent:mmkv-static:xxx'
api 'com.getkeepsafe.relinker:relinker:xxx'
api 'org.apache.commons:commons-lang3:xxx'
api 'com.squareup.okio:okio:xxx'
api 'org.greenrobot:eventbus:xxx'
api 'com.permissionx.guolindev:permissionx:xxx'
```
#### util
```
api project(path: ':basic')
```
#### widget
```
implementation project(path: ':util')
implementation 'com.willowtreeapps.spruce:spruce-android:xxx'
```
#### banner
```
implementation project(path: ':basic')
implementation 'com.youth.banner:banner:xxx'
```
#### matisse
```
implementation project(path: ':basic')
api 'com.zhihu.android:matisse:xxx'
```
#### ucrop
```
implementation project(path: ':basic')
api 'com.github.yalantis:ucrop:xxx'
```
#### fragmentation
```
implementation project(path: ':util')
```
#### jpush
```
implementation project(path: ':util')
implementation 'cn.jiguang.sdk:jcore:xxx'
api 'cn.jiguang.sdk:jpush:xxx'
```
#### janalytics
```
implementation project(path: ':util')
implementation 'cn.jiguang.sdk:jcore:xxx'
api 'cn.jiguang.sdk:janalytics:xxx'
```
#### bugly
```
implementation project(path: ':basic')
implementation project(path: ':tbs')
api 'com.tencent.bugly:crashreport_upgrade:xxx'
implementation 'com.tencent.bugly:nativecrashreport:xxx'
```
#### mobsms
```
implementation project(path: ':util')
```
#### bmob
```
implementation project(path: ':util')
api 'io.github.bmob:android-sdk:xxx'
```
#### litepal
```
implementation project(path: ':util')
api 'org.litepal.guolindev:core:xxx'
```
#### doraemonkit
```
implementation project(path: ':util')
// 核心模块
debugImplementation "io.github.didi.dokit:dokitx:xxx"
debugImplementation "io.github.didi.dokit:dokitx-ft:xxx"
debugImplementation "io.github.didi.dokit:dokitx-mc:xxx"
debugImplementation "io.github.didi.dokit:dokitx-weex:xxx"
releaseImplementation "io.github.didi.dokit:dokitx-no-op:xxx"
```
#### tbs
```
api 'com.tencent.tbs.tbssdk:sdk:xxx'
```
#### pool
```
待补充
```
## 使用
gradle(app)
```
plugins {
    id 'com.android.application'
}
// MobSDK 插件
apply plugin: 'com.mob.sdk'
// MobSDK 扩展
MobSDK {
    // 严格模式
    // 终端用户接受隐私条款前 MobSDK 不进行任何操作
    fp true
    // 注册 SMSSDK 相关信息
    SMSSDK {
        // 默用 GUI（不用下关）
        gui false
        // 打开短信本机号验证功能（3.7.0+）
        // 独用短信才需通开关打开短信本机号验证功能
        // 同用秒验和短信，默支持短信本机号验证功能，无需也不可通开关打开（类冲突）
        mobileAuth true
    }
}

android {

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    configurations.all {
        resolutionStrategy.cacheChangingModulesFor 0, 'seconds'
    }
}

dependencies {

    implementation 'com.github.snpmyn.Chaos:pool:xxx'
    /*chaos*/
    debugImplementation 'com.glance.guolindev:glance:xxx'
    /*glance*/
    // 核心模块
    debugImplementation "io.github.didi.dokit:dokitx:xxx"
    // 文件同步模块
    debugImplementation "io.github.didi.dokit:dokitx-ft:xxx"
    // 一机多控模块
    debugImplementation "io.github.didi.dokit:dokitx-mc:xxx"
    // weex 模块
    debugImplementation "io.github.didi.dokit:dokitx-weex:xxx"
    // no-op 模块
    // 仅 release 环境需引 no-op
    // 否则插件注入相关代码致找不到对应 class
    releaseImplementation "io.github.didi.dokit:dokitx-no-op:xxx"
    /*DoKit*/
}
```
gradle(project)
```
// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    apply from: "config.gradle"
    repositories {
        google()
        jcenter()
        mavenCentral()
        // Mob Maven 仓库地址
        maven {
            url "http://mvn.mob.com/android"
        }
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:xxx'
        // [极光统计]
        // 动态圈选插件（可选）
        /*classpath 'cn.jiguang.android:janalytics-gradle-plugin:xxx'*/
        // [Bugly]
        // 自动上传符号表插件
        // 快准定用户 Crash 代码位
        // 通符号表解析还原 Crash 程序堆栈
        /*classpath 'com.tencent.bugly:symtabfileuploader:xxx'*/
        // [MobTech]
        // 注册 MobSDK
        classpath "com.mob.sdk:MobSDK:xxx"
        // [DoraemonKit]
        // 流量监控及其它 AOP 功能（可选）
        // AOP 包括以下功能：
        // 1. 百度、腾讯、高德地图经纬度模拟
        // 2. UrlConnection、OkHttp 抓包及后续的接口 hook 功能
        // 3. App 启动耗时统计
        // 4. 慢函数
        // 5. 大图
        /*classpath 'io.github.didi.dokit:dokitx-plugin:xxx'*/

        // NOTE: Do not place your com.zsp.clicktonote.application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven { url "https://jitpack.io" }
    }
}

// 运行 gradle clean 时执行此处所定 task 任务（继承 Delete）删根目录 build 目录
// 相当执行 Delete.delete(rootProject.buildDir)
// gradle 用 groovy语言调 method 可不加 ()
task clean(type: Delete) {
    delete rootProject.buildDir
}
```
gradle(config)
```
ext {
    android = [
            compileSdkVersion: 30,
            applicationId    : "xxx",
            minSdkVersion    : 23,
            targetSdkVersion : 30,
            versionCode      : 1,
            versionName      : "1.0"
    ]
    compileOptions = [
            sourceCompatibility: JavaVersion.VERSION_1_8,
            targetCompatibility: JavaVersion.VERSION_1_8
    ]
    /*jpush = [
            // JPush 注册包名对应 AppKey
            jpushAppKey : "xxx",
            // 暂默值即可
            jpushChannel: "developer-default",
    ]*/
    bugly = [
            appId : 'xxx',
            appKey: 'xxx',
    ]
    /*mobsms = [
            // 严格模式
            // 终端用户接受隐私条款前 MobSDK 不进行任何操作
            fp        : true,
            // 默用 GUI（不用下关）
            gui       : true,
            // 打开短信本机号验证功能（3.7.0+）
            // 独用短信才需通开关打开短信本机号验证功能
            // 同用秒验和短信，默支持短信本机号验证功能，无需也不可通开关打开（类冲突）
            mobileAuth: true,
    ]*/
    doraemonkit = [
            // 地图经纬度开关
            gpsSwitch                  : true,
            // 网络开关
            networkSwitch              : true,
            // 大图开关
            bigImgSwitch               : true,
            // webView js 抓包
            webViewSwitch              : true,
            // [调用栈模式配置] 默 5ms（小于该值的函数于调用栈中不显示）
            stackMethodThresholdTime   : 10,
            // [调用栈模式配置] 调用栈函数入口
            stackMethodEnterMethods    : ["com.didichuxing.doraemondemo.MainDebugActivity.test1"],
            // [调用栈模式配置] 黑名单（粒度最小到类，暂不支持到方法）
            stackMethodMethodBlacklist : ["com.facebook.drawee.backends.pipeline.Fresco"],
            // [普通模式配置] 默 500ms（小于该值的函数运行时不于控制台被打印）
            normalMethodThresholdTime  : 500,
            // [普通模式配置] 需针对函数插装的包名
            normalMethodPackageNames   : ["com.didichuxing.doraemondemo"],
            // [普通模式配置] 无需针对函数插装的包名和类名
            normalMethodMethodBlacklist: ["com.didichuxing.doraemondemo.dokit"],
    ]
}
```
## TODO
#### 持续优化
#### 处理注解
#### 支持 kotlin
#### 优化 jpush、mobsms、litepal、doraemonkit 集成
## License
```
Copyright [2021] [snpmyn]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```