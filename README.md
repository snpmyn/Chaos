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
| litepool | 轻量水池 | 无 |
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
api 'com.guolindev.permissionx:permissionx:xxx'
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
api project(path: ':bmob')
api project(path: ':doraemonkit')
api project(path: ':banner')
api project(path: ':jpush')
api project(path: ':matisse')
api project(path: ':janalytics')
api project(path: ':ucrop')
api project(path: ':tbs')
api project(path: ':litepal')
api project(path: ':fragmentation')
api project(path: ':bugly')
api project(path: ':mobsms')
api project(path: ':widget')
api project(path: ':util')
```
#### litepool
```
api project(path: ':bmob')
api project(path: ':doraemonkit')
api project(path: ':banner')
api project(path: ':jpush')
api project(path: ':matisse')
api project(path: ':janalytics')
api project(path: ':ucrop')
api project(path: ':tbs')
api project(path: ':litepal')
api project(path: ':fragmentation')
api project(path: ':bugly')
api project(path: ':widget')
api project(path: ':util')
```
## 使用
gradle(app)
```
xxx
```
gradle(project)
```
xxx
```
gradle(config)
``` 
xxx
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