<div align=center><img src="https://github.com/snpmyn/Chaos/raw/master/image.png"/></div>

[![SNAPSHOT](https://jitpack.io/v/Jaouan/Revealator.svg)](https://jitpack.io/#snpmyn/Chaos)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/9a0b01a4875242408cd21a8d20be2604)](https://www.codacy.com/manual/snpmyn/Chaos?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=snpmyn/Chaos&amp;utm_campaign=Badge_Grade)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
[![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=23)

[![GitHub stars](https://img.shields.io/github/stars/Bigkoo/Chaos.svg?style=social)](https://github.com/Bigkoo/Chaos/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/Bigkoo/Chaos.svg?style=social)](https://github.com/Bigkoo/Chaos/network)
[![GitHub watchers](https://img.shields.io/github/watchers/Bigkoo/Chaos.svg?style=social)](https://github.com/Bigkoo/Chaos/watchers)
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
## 依赖
#### basic
api 'androidx.appcompat:appcompat:1.2.0'
api 'com.google.android.material:material:1.3.0'
api 'com.github.bumptech.glide:glide:4.12.0'
api 'io.reactivex:rxandroid:1.2.1'
api 'io.reactivex:rxjava:1.3.8'
api 'com.jakewharton.timber:timber:4.7.1'
api 'com.tencent:mmkv-static:1.0.23'
api 'com.getkeepsafe.relinker:relinker:1.3.1'
api 'org.apache.commons:commons-lang3:3.12.0'
api 'com.squareup.okio:okio:3.0.0-alpha.1'
api 'org.greenrobot:eventbus:3.1.1'
#### util
api project(path: ':basic')
#### widget
implementation project(path: ':util')
implementation 'com.willowtreeapps.spruce:spruce-android:1.0.1'
#### banner
implementation project(path: ':basic')
implementation 'com.youth.banner:banner:1.4.10'
#### matisse
implementation project(path: ':basic')
implementation 'com.zhihu.android:matisse:0.5.3-beta3'
#### ucrop
implementation project(path: ':basic')
implementation 'com.github.yalantis:ucrop:2.2.6-native'
#### fragmentation
implementation project(path: ':util')
#### jpush
implementation project(path: ':util')
implementation 'cn.jiguang.sdk:jcore:2.6.0'
api 'cn.jiguang.sdk:jpush:3.9.0'
#### janalytics
implementation project(path: ':util')
implementation 'cn.jiguang.sdk:jcore:2.6.0'
api 'cn.jiguang.sdk:janalytics:2.1.2'
## TODO
#### 处理注解
#### 支持 kotlin
#### 整合 util、widget、fairy
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