# Chaos

## 释义

混沌

## 模块

#### [basic](./basic/)

基础

#### [util](./util/)

工具类

#### [widget](./widget/)

组件

#### [banner](./banner/)

轮播图

#### [matisse](./matisse/)

选图

#### [ucrop](./ucrop/)

裁剪

#### [fragmentation](./fragmentation/)

碎片

#### [jpush](./jpush/)

极光推送

#### [janalytics](./janalytics/)

极光统计

#### [bugly](./bugly/)

Bugly

#### [mobsms](./mobsms/)

SMSSDK

#### [bmob](./bmob/)

Bmob

#### [litepal](./litepal/)

LitePal

#### [doraemonkit](./doraemonkit/)

DoraemonKit

#### [tbs](./tbs/)

腾讯浏览服务

#### [scan](./scan/)

扫描

#### [lottie](./lottie/)

动画

#### [pool](./pool/)

水池（供外部使用）

#### [litepool](./litepool/)

轻量水池（供外部使用）

## 依赖

#### basic

```
api 'androidx.appcompat:appcompat:xxx'
api 'com.google.android.material:material:xxx'
api 'androidx.constraintlayout:constraintlayout:xxx'
api "androidx.activity:activity:xxx"
api 'com.github.bumptech.glide:glide:xxx'
api 'io.reactivex:rxandroid:xxx'
api 'io.reactivex:rxjava:xxx'
api 'io.reactivex.rxjava2:rxandroid:xxx'
api 'io.reactivex.rxjava2:rxjava:xxx'
api 'io.reactivex.rxjava3:rxandroid:xxx'
api 'io.reactivex.rxjava3:rxjava:xxx'
api 'com.squareup.retrofit2:retrofit:xxx'
api 'com.squareup.retrofit2:adapter-rxjava:xxx'
api 'com.squareup.retrofit2:converter-gson:xxx'
api 'androidx.core:core-ktx:xxx'
api "org.jetbrains.kotlin:kotlin-stdlib:xxx"
api 'com.jakewharton.timber:timber:xxx'
api 'com.tencent:mmkv-static:xxx'
api 'com.getkeepsafe.relinker:relinker:xxx'
api 'org.apache.commons:commons-lang3:xxx'
api 'com.squareup.okio:okio:xxx'
api 'org.greenrobot:eventbus:xxx'
api 'com.guolindev.permissionx:permissionx:xxx'
```

#### util

```
api project(path: ':basic')
```

#### widget

```
implementation project(path: ':util')
implementation project(path: ':lottie')
implementation 'com.willowtreeapps.spruce:spruce-android:xxx'
```

#### banner

```
implementation project(path: ':basic')
api 'com.youth.banner:banner:xxx'
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

#### scan

```
implementation project(path: ':util')
implementation project(path: ':matisse')
api 'cn.bingoogolapple:bga-qrcode-zxing:xxx'
```

### lottie

```
api 'com.airbnb.android:lottie:xxx'
```

#### pool

```
api project(path: ':lottie')
api project(path: ':scan')
api project(path: ':tbs')
api project(path: ':doraemonkit')
api project(path: ':litepal')
api project(path: ':bmob')
api project(path: ':mobsms')
api project(path: ':bugly')
api project(path: ':janalytics')
api project(path: ':jpush')
api project(path: ':fragmentation')
api project(path: ':ucrop')
api project(path: ':matisse')
api project(path: ':banner')
api project(path: ':widget')
api project(path: ':util')
```

#### litepool

```
api project(path: ':litepal')
api project(path: ':fragmentation')
api project(path: ':widget')
api project(path: ':util')
```

## 使用

gradle(app)

```
省略
```

gradle(project)

```
省略
```

gradle(config)

``` 
省略
```

## TODO

#### 持续优化

#### 处理注解

#### 优化集成

* [mobsms](./mobsms/)
* [doraemonkit](./doraemonkit/)

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