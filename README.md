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
| doraemonkit | DoraemonKit | 无 |
| litepal | LitePal | 无 |
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
implementation 'com.zhihu.android:matisse:0.5.3-beta3'
```
#### ucrop
```
implementation project(path: ':basic')
implementation 'com.github.yalantis:ucrop:2.2.6-native'
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
implementation 'com.tencent.bugly:crashreport_upgrade:1.4.2'
implementation 'com.tencent.bugly:nativecrashreport:3.7.5'
```
#### mobsms
```
implementation project(path: ':util')
```
#### bmob
```
implementation project(path: ':util')
implementation 'cn.bmob.android:bmob-sdk:3.7.8'
```
#### doraemonkit
```
implementation project(path: ':util')
debugImplementation 'com.didichuxing.doraemonkit:dokitx:3.3.5'
releaseImplementation 'com.didichuxing.doraemonkit:dokitx-no-op:3.3.5'
```
#### litepal
```
implementation project(path: ':util')
implementation 'org.litepal.guolindev:core:3.2.3'
debugImplementation 'com.glance.guolindev:glance:1.0.0'
```
## TODO
#### 处理注解
#### 支持 kotlin
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