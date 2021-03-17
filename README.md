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
## 依赖
#### basic
```
api 'androidx.appcompat:appcompat:1.2.0'
api 'com.google.android.material:material:1.3.0'
api 'com.github.bumptech.glide:glide:4.12.0'
api 'io.reactivex:rxandroid:1.2.1'
api 'io.reactivex:rxjava:1.3.8'
api 'io.reactivex.rxjava2:rxandroid:2.1.1'
api 'io.reactivex.rxjava2:rxjava:2.2.14'
api 'com.jakewharton.timber:timber:4.7.1'
api 'com.tencent:mmkv-static:1.0.23'
api 'com.getkeepsafe.relinker:relinker:1.3.1'
api 'org.apache.commons:commons-lang3:3.12.0'
api 'com.squareup.okio:okio:3.0.0-alpha.1'
api 'org.greenrobot:eventbus:3.1.1'
api 'com.permissionx.guolindev:permissionx:1.4.0'
```
#### util
```
api project(path: ':basic')
```
#### widget
```
implementation project(path: ':util')
implementation 'com.willowtreeapps.spruce:spruce-android:1.0.1'
```
#### banner
```
implementation project(path: ':basic')
implementation 'com.youth.banner:banner:1.4.10'
```
#### matisse
```
implementation project(path: ':basic')
api 'com.zhihu.android:matisse:0.5.3-beta3'
```
#### ucrop
```
implementation project(path: ':basic')
api 'com.github.yalantis:ucrop:2.2.6-native'
```
#### fragmentation
```
implementation project(path: ':util')
```
#### jpush
```
implementation project(path: ':util')
implementation 'cn.jiguang.sdk:jcore:2.6.0'
api 'cn.jiguang.sdk:jpush:3.9.0'
```
#### janalytics
```
implementation project(path: ':util')
implementation 'cn.jiguang.sdk:jcore:2.6.0'
api 'cn.jiguang.sdk:janalytics:2.1.2'
```
#### bugly
```
implementation project(path: ':basic')
api 'com.tencent.bugly:crashreport_upgrade:1.4.2'
implementation 'com.tencent.bugly:nativecrashreport:3.7.5'
```
#### mobsms
```
implementation project(path: ':util')
```
#### bmob
```
implementation project(path: ':util')
api 'cn.bmob.android:bmob-sdk:3.7.8'
```
#### litepal
```
implementation project(path: ':util')
api 'org.litepal.guolindev:core:3.2.3'
```
#### doraemonkit
```
implementation project(path: ':util')
debugImplementation 'com.didichuxing.doraemonkit:dokitx:3.3.5'
releaseImplementation 'com.didichuxing.doraemonkit:dokitx-no-op:3.3.5'
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

    defaultConfig {

        manifestPlaceholders = [
                JPUSH_PKGNAME: applicationId,
                // JPush 注册包名对应 AppKey
                JPUSH_APPKEY : "00067aa741beb793e0ddc000",
                // 暂默值即可
                JPUSH_CHANNEL: "developer-default",
        ]
    }
}

dependencies {

    debugImplementation 'com.glance.guolindev:glance:1.0.0'
    /*glance*/
    debugImplementation 'com.didichuxing.doraemonkit:dokitx:3.3.5'
    // 仅 release 环境需引 no-op
    // 否则插件注入相关代码致找不到对应 class
    releaseImplementation 'com.didichuxing.doraemonkit:dokitx-no-op:3.3.5'
    /*DoraemonKit*/
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
    }
    dependencies {
        classpath "com.android.tools.build:gradle:4.1.2"
        // [MobTech]
        // 注册 MobSDK
        classpath "com.mob.sdk:MobSDK:2018.0319.1724"

        // NOTE: Do not place your com.zsp.clicktonote.application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url "https://jitpack.io" }
        // [Bmob]
        // Bmob maven 仓库地址
        maven { url 'https://dl.bintray.com/chaozhouzhang/maven' }
    }
}
```
gradle(config)
```
ext {
    android = [
            compileSdkVersion: 30,
            applicationId    : "com.zsp.clicktonote",
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
            jpushAppKey : "86067aa741beb793e0ddc1a5",
            // 暂默值即可
            jpushChannel: "developer-default",
    ]*/
    bugly = [
            appId : '26162df435',
            appKey: '52339694-ce7d-4857-903f-e6112afc19b4',
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